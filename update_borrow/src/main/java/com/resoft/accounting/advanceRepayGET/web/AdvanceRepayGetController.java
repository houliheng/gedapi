package com.resoft.accounting.advanceRepayGET.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.accounting.advanceRepayGET.entity.AdvanceRepayGet;
import com.resoft.accounting.advanceRepayGET.service.AdvanceRepayGetService;
import com.resoft.accounting.applyAdvanceRepay.entity.ApplyAdvanceRepay;
import com.resoft.accounting.applyAdvanceRepay.service.ApplyAdvanceRepayService;
import com.resoft.accounting.common.utils.AjaxView;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.contract.dao.ContractLockDao;
import com.resoft.accounting.contract.entity.ContractLock;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.accounting.staRepayPlanStatus.service.StaRepayPlanStatusService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 提前还款记录Controller
 * @author jiangmenglun
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/advanceRepayGet")
public class AdvanceRepayGetController extends BaseController {

	@Autowired
	private ContractLockDao contractLockDao;
	@Autowired
	private StaRepayPlanStatusService staRepayPlanStatusService;
	@Autowired
	private AdvanceRepayGetService advanceRepayGetService;
	@Autowired
	private ApplyAdvanceRepayService applyAdvanceRepayService;
	
	@ModelAttribute
	public AdvanceRepayGet get(@RequestParam(required=false) String id) {
		AdvanceRepayGet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = advanceRepayGetService.get(id);
		}
		if (entity == null){
			entity = new AdvanceRepayGet();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:advanceRepayGet:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdvanceRepayGet advanceRepayGet, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdvanceRepayGet> page = advanceRepayGetService.findCustomPage(new Page<AdvanceRepayGet>(request, response), advanceRepayGet); 
		model.addAttribute("page", page);
		return "app/credit/advanceRepayGET/advanceRepayGetList";
	}

	@RequiresPermissions("credit:advanceRepayGet:view")
	@RequestMapping(value = "form")
	public String form(AdvanceRepayGet advanceRepayGet, Model model,String contractNo) {
		advanceRepayGet.setContractNo(contractNo);
		List<AdvanceRepayGet> advanceRepayGetList=advanceRepayGetService.getByContractNo(advanceRepayGet);
		if(advanceRepayGetList.size()>0){//有记录
			//已经保存过
			advanceRepayGet=advanceRepayGetList.get(0);
			if("0000".equals(advanceRepayGet.getAdvanceStatus())){//已经成功的，加个标识
				model.addAttribute("submitIndex", 1);
				boolean readOnly=true;
				model.addAttribute("readOnly", readOnly);
			}
		}
		model.addAttribute("advanceRepayGet", advanceRepayGet);
		return "app/accounting/advanceRepayGET/advanceRepayGetForm";
	}

	@RequiresPermissions("credit:advanceRepayGet:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(AdvanceRepayGet advanceRepayGet) {
		AjaxView ajaxView = new AjaxView();
		try {
			//计算期数
			String minPeriodNum = applyAdvanceRepayService.getMinPeriodNum(advanceRepayGet.getContractNo());
			advanceRepayGet.setPeriodNum(minPeriodNum);
			String resq_code=advanceRepayGetService.saveAdvanceRepay(advanceRepayGet);
			if("0000".equals(resq_code)){
				ajaxView.setSuccess().setMessage("保存提前还款成功！");
			}else{
				ajaxView.setFailed().setMessage("访问冠E通提前还款接口失败！");
				//在接口日志中查看具体原因
			}
		} catch (Exception e) {
			logger.error("保存提前还款失败", e);
			ajaxView.setFailed().setMessage("保存提前还款失败！");
		}
		return ajaxView;
	}
	
	@RequiresPermissions("credit:advanceRepayGet:edit")
	@RequestMapping(value = "delete")
	public String delete(AdvanceRepayGet advanceRepayGet, RedirectAttributes redirectAttributes) {
		advanceRepayGetService.delete(advanceRepayGet);
		addMessage(redirectAttributes, "删除提前还款记录成功");
		return "redirect:"+Global.getAdminPath()+"/advanceRepayGET/advanceRepayGet/?repage";
	}
	
	@ResponseBody
	@RequiresPermissions("accounting:applyAdvanceRepay:view")
	@RequestMapping(value = "validateApplyAdvanceRepay")
	public AjaxView validateApplyAdvanceRepay(ApplyAdvanceRepay applyAdvanceRepay) {
		try {
			String contractNo = URLDecoder.decode(applyAdvanceRepay.getContractNo(), "UTF-8");
			applyAdvanceRepay.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		AjaxView ajaxView = new AjaxView();
//		Contract contract = null;
		ContractLock contractLock = null;
		int num = 0;
		List<StaRepayPlanStatus> statuses = staRepayPlanStatusService.findStaRepayPlanStatus(applyAdvanceRepay.getContractNo());
		if (statuses != null) {
			for (StaRepayPlanStatus statuss : statuses) {
				num++;
				if (Constants.PERIOD_STATE_YQ.equals(statuss.getRepayPeriodStatus())) {
					ajaxView.setFailed().setMessage("第 " + num + " 期逾期未结清，请先结清逾期。");
					return ajaxView;
				} 
			}
		}
		//校验最后一期不允许提前还款，需求没作要求，暂时注释
		/*String newperiodNum = applyAdvanceRepayService.getMinPeriodNum(applyAdvanceRepay.getContractNo());
		contract = contractService.findContractByContractNo(applyAdvanceRepay.getContractNo());
		if (Integer.parseInt(newperiodNum) == Integer.parseInt(contract.getApproPeriodValue())) {
			ajaxView.setFailed().setMessage("已到最后期数，不可进行提前还款");
			return ajaxView;
		}*/
		contractLock = new ContractLock();
		contractLock.setContractNo(applyAdvanceRepay.getContractNo());
		contractLock = contractLockDao.validateIsLock(contractLock);
		if (contractLock != null) {
			ajaxView.setFailed().setMessage("此合同号存在正在申请中的操作");
		} else {
			ajaxView.setSuccess();
		}
		return ajaxView;
	}

}