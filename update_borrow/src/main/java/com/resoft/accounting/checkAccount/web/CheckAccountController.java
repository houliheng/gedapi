package com.resoft.accounting.checkAccount.web;

import com.resoft.accounting.checkAccount.entity.ApplyStatusEnum;
import com.resoft.accounting.checkAccount.entity.CheckAccount;
import com.resoft.accounting.checkAccount.entity.CheckAccountApply;
import com.resoft.accounting.checkAccount.entity.CheckAccountHistory;
import com.resoft.accounting.checkAccount.service.CheckAccountApplyService;
import com.resoft.accounting.checkAccount.service.CheckAccountHisService;
import com.resoft.accounting.checkAccount.service.CheckAccountService;
import com.resoft.accounting.checkAccount.utils.CheckAccountUtils;
import com.resoft.common.utils.AjaxView;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 查账Controller.
 *
 * @author SeppSong
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/checkAccount")
public class CheckAccountController extends BaseController {

    private final CheckAccountService checkAccountService;
    private final CheckAccountApplyService checkAccountApplyService;
    private final CheckAccountHisService checkAccountHisService;

    private final ActTaskService actTaskService;
    private final HistoryService historyService;

    @Autowired
    public CheckAccountController(CheckAccountService checkAccountService,
        CheckAccountApplyService checkAccountApplyService, CheckAccountHisService checkAccountHisService,
        ActTaskService actTaskService, HistoryService historyService) {
        this.checkAccountService = checkAccountService;
        this.checkAccountApplyService = checkAccountApplyService;
        this.checkAccountHisService = checkAccountHisService;
        this.actTaskService = actTaskService;
        this.historyService = historyService;
    }

    @RequestMapping(value = {"list", ""})
    public String list(CheckAccount checkAccount, HttpServletRequest request, HttpServletResponse response,
        Model model) {
        Page<CheckAccount> page = new Page<CheckAccount>(request, response);
        checkAccount.setPage(page);
        List<CheckAccount> checkAccountList = checkAccountService.list(checkAccount);
        page.setList(checkAccountList);
        model.addAttribute("page", page);
        return "app/accounting/checkAccount/checkAccountList";
    }

    @RequestMapping(value = "listDone")
    public String listDone(CheckAccount checkAccount, HttpServletRequest request, HttpServletResponse response,
        Model model) {
        Page<CheckAccount> page = new Page<CheckAccount>(request, response);
        checkAccount.setPage(page);
        List<CheckAccount> checkAccountList = checkAccountService.listDone(checkAccount);
        CheckAccountUtils.updateCheckAccount(checkAccountList);
        page.setList(checkAccountList);
        model.addAttribute("page", page);
        return "app/accounting/checkAccount/checkAccountDoneList";
    }

    @RequestMapping(value = "checkAccountApply")
    public String checkAccountApply(String contractNoTemp, Model model) {
        CheckAccount checkAccount = checkAccountService.getCheckAccount(contractNoTemp);
        CheckAccountApply checkAccountApply = checkAccountApplyService.get(contractNoTemp, ApplyStatusEnum.SAVE);
        if (checkAccountApply == null) {
            checkAccountApply = new CheckAccountApply();
            checkAccountApply.setLoanName(checkAccount.getBorrowName());
        }
        model.addAttribute("checkAccount", checkAccount);
        model.addAttribute("checkAccountApply", checkAccountApply);
        return "app/accounting/checkAccount/checkAccountApply";
    }

    @RequestMapping(value = "saveApply")
    @ResponseBody
    public AjaxView saveApply(CheckAccountApply checkAccountApply) {
        AjaxView ajaxView = new AjaxView();
        try {
            checkAccountApplyService.save(checkAccountApply);
            ajaxView.setSuccess().setMessage("保存成功");
        } catch (Exception e) {
            logger.error("保存失败", e.getMessage());
            ajaxView.setFailed().setMessage("保存失败");
        }
        return ajaxView;
    }

    @RequestMapping(value = "submitApply")
    @ResponseBody
    public AjaxView submitApply(CheckAccountApply checkAccountApply) {
        AjaxView ajaxView = new AjaxView();
        try {
            checkAccountApplyService.save(checkAccountApply);
            String proInsId = actTaskService
                .startProcess("check_account_apply", "acc_check_account_apply",
                    String.valueOf(checkAccountApply.getId()));
            List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(proInsId).unfinished().orderByHistoricTaskInstanceStartTime().asc().list();
            String taskId = hisTasks.get(0).getId();
            actTaskService.claim(taskId, UserUtils.getUser().getName());
            checkAccountApply.setProInsId(proInsId);
            checkAccountApply.setStatus(ApplyStatusEnum.SUBMIT.getValue());
            checkAccountApply.setTaskId(taskId);
            checkAccountApplyService.update(checkAccountApply);
            CheckAccountHistory checkAccountHistory = CheckAccountUtils.initSubmitApply(checkAccountApply);
            checkAccountHisService.insert(checkAccountHistory);
            ajaxView.setSuccess().setMessage("提交成功");
        } catch (Exception e) {
            logger.error("提交失败", e.getMessage());
            ajaxView.setFailed().setMessage("提交失败");
        }
        return ajaxView;
    }

    @RequestMapping(value = "listHistory")
    public String listHistory(String contractNo, Model model) {
        List<CheckAccountHistory> list = checkAccountHisService.list(new CheckAccountHistory(contractNo));
        model.addAttribute("list", list);
        return "app/accounting/checkAccount/checkAccountHisList";
    }

    @RequestMapping(value = "listApplyHis")
    public String listApplyHis(Long applyId, Model model) {
        List<CheckAccountHistory> list = checkAccountHisService.list(new CheckAccountHistory(applyId));
        model.addAttribute("list", list);
        return "app/accounting/checkAccount/checkAccountHisList";
    }

    @RequestMapping(value = "queryBankCode")
    @ResponseBody
    public AjaxView queryBankCode(String bankName) {
        AjaxView ajaxView = new AjaxView();
        try {
            String bank = DictUtils.getDictValue(bankName, "BANKS", "");
            if (StringUtils.isBlank(bank)) {
                throw new Exception("查询银行编码失败");
            }
            ajaxView.setSuccess().setMessage(bank);
        } catch (Exception e) {
            logger.error("查询银行编码失败", e);
            ajaxView.setFailed().setMessage("查询银行编码失败, 请确认输入的打款银行名");
        }
        return ajaxView;
    }

}
