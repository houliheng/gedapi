package com.resoft.accounting.checkAccount.web;

import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCRequest;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.AccAccountDCResponse;
import com.resoft.Accoutinterface.rest.financialPlatform.entry.TransferWithHoldEntity;
import com.resoft.Accoutinterface.utils.AccFacade;
import com.resoft.accounting.bankAccountStatement.entity.CheckAccountStatement;
import com.resoft.accounting.bankAccountStatement.service.CheckAccountStatementService;
import com.resoft.accounting.checkAccount.entity.ApplyStatusEnum;
import com.resoft.accounting.checkAccount.entity.CheckAccount;
import com.resoft.accounting.checkAccount.entity.CheckAccountApply;
import com.resoft.accounting.checkAccount.entity.CheckAccountHistory;
import com.resoft.accounting.checkAccount.entity.CheckAccountVerify;
import com.resoft.accounting.checkAccount.service.CheckAccountApplyService;
import com.resoft.accounting.checkAccount.service.CheckAccountHisService;
import com.resoft.accounting.checkAccount.service.CheckAccountService;
import com.resoft.accounting.checkAccount.service.CheckAccountVerifyService;
import com.resoft.accounting.checkAccount.utils.CheckAccountUtils;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.resoft.common.exception.BusinessException;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.credit.repayPlan.service.RepayPlanService;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.resoft.outinterface.utils.RSAUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 待审核/已审核.
 *
 * @author SeppSong
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/checkAccountVerify")
public class CheckAccountVerifyController extends BaseController {

    private final CheckAccountVerifyService checkAccountVerifyService;
    private final StaRepayPlanStatusService staRepayPlanStatusService;
    private final RepayPlanService repayPlanService;
    private final CheckAccountService checkAccountService;
    private final CheckAccountApplyService checkAccountApplyService;
    private final CheckAccountStatementService bankFlowService;
    private final CheckAccountHisService checkAccountHisService;
    private final ActTaskService actTaskService;

    private final ContractService contractService;
    private final ApplyRegisterService applyRegisterService;
    private final GedAccountService gedAccountService;
    private final CustInfoService custInfoService;
    private final CompanyInfoService companyInfoService;
    private final CreGedAccountCompanyService accountCompanyService;

    @Autowired
    public CheckAccountVerifyController(CheckAccountVerifyService checkAccountVerifyService,
        StaRepayPlanStatusService staRepayPlanStatusService, RepayPlanService repayPlanService,
        CheckAccountService checkAccountService, CheckAccountApplyService checkAccountApplyService,
        CheckAccountStatementService bankFlowService, CheckAccountHisService checkAccountHisService,
        ContractService contractService, ApplyRegisterService applyRegisterService, GedAccountService gedAccountService,
        CustInfoService custInfoService, CompanyInfoService companyInfoService,
        CreGedAccountCompanyService creGedAccountCompanyService, ActTaskService actTaskService) {
        this.checkAccountVerifyService = checkAccountVerifyService;
        this.staRepayPlanStatusService = staRepayPlanStatusService;
        this.repayPlanService = repayPlanService;
        this.checkAccountService = checkAccountService;
        this.checkAccountApplyService = checkAccountApplyService;
        this.bankFlowService = bankFlowService;
        this.checkAccountHisService = checkAccountHisService;
        this.contractService = contractService;
        this.applyRegisterService = applyRegisterService;
        this.gedAccountService = gedAccountService;
        this.custInfoService = custInfoService;
        this.companyInfoService = companyInfoService;
        this.accountCompanyService = creGedAccountCompanyService;
        this.actTaskService = actTaskService;
    }

    @RequestMapping(value = {"list", ""})
    public String list(CheckAccountVerify checkAccountVerify, HttpServletRequest request, HttpServletResponse response,
        Model model) {
        Page<CheckAccountVerify> page = new Page<>(request, response);
        checkAccountVerify.setPage(page);
        List<CheckAccountVerify> checkAccountVerifyList = checkAccountVerifyService.list(checkAccountVerify);
        listCommon(checkAccountVerifyList);
        page.setList(checkAccountVerifyList);
        model.addAttribute("page", page);
        return "app/accounting/checkAccount/checkAccountVerifyList";
    }

    @RequestMapping(value = "listDone")
    public String listDone(CheckAccountVerify checkAccountVerify, HttpServletRequest request,
        HttpServletResponse response, Model model) {
        Page<CheckAccountVerify> page = new Page<>(request, response);
        checkAccountVerify.setPage(page);
        List<CheckAccountVerify> checkAccountVerifyList = checkAccountVerifyService.listDone(checkAccountVerify);
        listCommon(checkAccountVerifyList);
        page.setList(checkAccountVerifyList);
        model.addAttribute("page", page);
        return "app/accounting/checkAccount/checkAccountVerifyListDone";
    }

    @RequestMapping(value = "checkAccountMatch")
    public String checkAccountMatch(Long applyId, Model model) {
        CheckAccountApply checkAccountApply = checkAccountApplyService.get(new CheckAccountApply(applyId));
        CheckAccount checkAccount = checkAccountService.getCheckAccount(checkAccountApply.getContractNumber());
        List<CheckAccountStatement> bankFlowList = bankFlowService.listMatchBankStatement(checkAccountApply);
        model.addAttribute("checkAccount", checkAccount);
        model.addAttribute("checkAccountApply", checkAccountApply);
        model.addAttribute("bankFlowList", bankFlowList);
        return "app/accounting/checkAccount/checkAccountMatch";
    }

    @RequestMapping(value = "submit")
    @ResponseBody
    public AjaxView submit(String passFlag, String suggestion, String ids, Long applyId) {
        AjaxView ajaxView = new AjaxView();
        try {
            CheckAccountApply apply = checkAccountApplyService.get(new CheckAccountApply(applyId));
            if (Constants.TRUE.equals(passFlag)) {
                List<CheckAccountStatement> bankFlowList = bankFlowService.list(ids);
                if (!CheckAccountUtils.matchAmount(apply, bankFlowList)) {
                    throw new BusinessException("查账金额不匹配");
                }
                transferWithHold(apply.getContractNumber(), apply.getCheckAmount());
                actTaskService.complete(apply.getTaskId(), apply.getProInsId(), "[提交]", null);
                List<CheckAccountStatement> updateList = CheckAccountUtils
                    .updateBankFlow(apply, bankFlowList);
                bankFlowService.updateList(updateList);
                apply.setStatus(ApplyStatusEnum.SUCCESS.getValue());
                checkAccountApplyService.update(apply);
                CheckAccountHistory checkAccountHistory = CheckAccountUtils.initSubmitMatch(apply, suggestion);
                checkAccountHisService.insert(checkAccountHistory);
                ajaxView.setSuccess().setMessage("匹配成功");
            } else if (Constants.FALSE.equals(passFlag)) {
                apply.setStatus(ApplyStatusEnum.SAVE.getValue());
                checkAccountApplyService.update(apply);
                CheckAccountHistory checkAccountHistory = CheckAccountUtils.initRefuseMatch(apply, suggestion);
                checkAccountHisService.insert(checkAccountHistory);
                ajaxView.setSuccess().setMessage("打回成功");
            }
        } catch (BusinessException e) {
            logger.error("金额不匹配", e.getMessage());
            ajaxView.setFailed().setMessage("查账金额不匹配");
        } catch (Exception e) {
            logger.error("匹配失败", e.getMessage());
            ajaxView.setFailed().setMessage("匹配失败");
        }
        return ajaxView;
    }

    private void listCommon(List<CheckAccountVerify> verifyList) {
        //组装最近还款日及最近还款金额
        List<String> contractNoList = CheckAccountUtils.extractContractNo(verifyList);
        List<StaRepayPlanStatus> staRepayPlanStatusList = staRepayPlanStatusService.listWithContracts(contractNoList);
        List<RepayPlan> repayPlanList = repayPlanService.listWithContractNoList(contractNoList);
        CheckAccountUtils.fillCheckAccountVerify(verifyList, staRepayPlanStatusList, repayPlanList);
    }

    private void transferWithHold(String contractNo, BigDecimal checkAmount) throws Exception{
        Contract contract = contractService.getContractByContractNo(contractNo);
        ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(contract.getApplyNo());
        Map<String, String> data = new HashMap<>();
        if (Constants.CUST_TYPE_PERSON.equals(applyRegister.getCustType())) {
            CustInfo custInfo = custInfoService.get(contract.getCustId());
            GedAccount gedAccount = gedAccountService.getByPhone(custInfo.getMobileNum());
            data.put("custId", gedAccount.getCustId());
            data.put("mobile", gedAccount.getLegalPerPhone());
            data.put("custName", gedAccount.getLegalPerName());
            data.put("companyName", "");
            Map<String, String> innerMap = new HashMap<>(4);
            innerMap.put("deductCustName", gedAccount.getLegalPerName());
            innerMap.put("bank", gedAccount.getCompanyBankOfDeposit());
            innerMap.put("bankCardNo", gedAccount.getCompanyAccount());
            String params = JsonTransferUtils.bean2Json(innerMap);
            data.put("remark", params);
        } else {
            Map<String, Object> companyParams = new HashMap<>(2);
            companyParams.put("applyNo", contract.getApplyNo());
            companyParams.put("roleType", Constants.ROLE_TYPE_ZJQY);
            CompanyInfo companyInfo = companyInfoService.getByApplyNoAndRoleType(companyParams);
            if (companyInfo != null) {
                List<CreGedAccountCompany> companyList = accountCompanyService.findCompanyAccountBySocialCreditNo(companyInfo.getUnSocCreditNo());
                if (companyList.size() > 0) {
                    CreGedAccountCompany accountCompany = companyList.get(0);
                    data.put("custId", accountCompany.getCustId());
                    data.put("mobile", accountCompany.getLegalMobile());
                    data.put("custName", accountCompany.getLegalName());
                    data.put("companyName", accountCompany.getCompanyName());
                    Map<String, String> innerMap = new HashMap<>(4);
                    innerMap.put("deductCustName", accountCompany.getLegalName());
                    innerMap.put("bank", accountCompany.getCompanyBankOfDeposit());
                    innerMap.put("bankCardNo", accountCompany.getCompanyAccount());
                    String params = JsonTransferUtils.bean2Json(innerMap);
                    data.put("remark", params);
                } else {
                    logger.error("冠易贷企业账号缺失 companyInfoId = " + companyInfo.getId() + " \r\n"
                        + " unSocCreditNo = " + companyInfo.getUnSocCreditNo());
                    throw new Exception("冠易贷企业账号缺失");
                }
            } else {
                logger.error("companyInfo 信息缺失 params : applyNo = " + contract.getApplyNo() + " \r\n"
                    + "custId = " + contract.getCustId());
                throw new Exception("companyInfo 信息缺失");
            }

        }
        data.put("contractNo", contractNo);
        data.put("amount", "1000.00");
        data.put("custType", "1");
        AccAccountDCRequest request = new AccAccountDCRequest();
        request.setMchn(Global.getConfig("FPMchn"));
        request.setSeq_no(OutInterfaceUtils.makeSeqNo());
        request.setTrade_type("11030043");
        request.setData(JsonTransferUtils.bean2Json(data));
        request.setPage_url("");
        request.setBack_url(Global.getConfig("Domain") + Global.getConfig("FPhbczCallback"));
        String sign = request.getMchn() + "|" + request.getSeq_no() + "|" + request.getTrade_type() + "|" + request.getData()
            + "|" + request.getPage_url() + "|" + request.getBack_url();
        String signature = RSAUtils.signCallBack(sign.getBytes(StandardCharsets.UTF_8), Global.getConfig("sysprkey"));
        request.setSignature(signature);
        AccAccountDCResponse response = AccFacade.facade.transferWithHold(request, contractNo);
        if (!"0000".equals(response.getResp_code())) {
            logger.error("资金接口调用异常 seq_no = " + response.getSeq_no() +
                " contractNo = " + contractNo);
            throw new Exception("资金接口调用异常");
        }
    }

}
