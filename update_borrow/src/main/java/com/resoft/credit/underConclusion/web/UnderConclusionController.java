package com.resoft.credit.underConclusion.web;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkApprove.web.CheckApproveController;
import com.resoft.credit.gedApiUser.service.CreGedapiUserService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.underCompanyInfo.service.UnderCompanyInfoService;
import com.resoft.credit.underConclusion.service.UnderConclusionService;
import com.resoft.outinterface.rest.newged.entity.GedRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.GedRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/credit/underConclusion")
public class UnderConclusionController extends BaseController {

	@Autowired
	private UnderConclusionService underConclusionService;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private CreGedapiUserService creGedapiUserService;
	@Autowired
	private UnderCompanyInfoService underCompanyInfoService;
	
	@RequestMapping(value = "index")
	public String index(ActTaskParam actTaskParam, String readOnly, Model model) {
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		params.put("taskDefKeyV", actTaskParam.getTaskDefKey());
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null) {
			processSuggestionInfo = new ProcessSuggestionInfo();
			processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
			processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
		}
		List<CheckApprove> checkApprove = checkApproveService.getCheckApproveByApplyNo(params);
		model.addAttribute("checkApprove", checkApprove);
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/underCompanyInfo/underConclusionIndex";
	}


	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(ActTaskParam actTaskParam, Model model, String passFlag, String suggestionDesc, HttpServletRequest request) {
		AjaxView rtn = new AjaxView();
		String validateFlag = null;
		try {
			validateFlag = underConclusionService.valiProcessDate(actTaskParam.getApplyNo(),actTaskParam.getTaskDefKey());
			if (validateFlag != null) {
				rtn.setFailed().setMessage(validateFlag);
				return rtn;
			}
			rtn = underConclusionService.save(actTaskParam, passFlag, suggestionDesc);
		} catch (Exception e) {
			e.printStackTrace();
			rtn.setFailed().setMessage("提交失败！");
		}
		return rtn;
	}



	@RequestMapping(value = "jump")
	public String jump(String applyNo,Model model,String custId ,Boolean readOnly, String taskDefKey){
		model.addAttribute("taskDefKey", taskDefKey);
		return "app/credit/guaranteeRelation/guaranteeRelationIndex";
	}

	@ResponseBody
	@RequestMapping(value = "registerGed")
	public AjaxView registerGed(String applyNo,ActTaskParam actTaskParam, Model model, String unSocCreditNo,String busiRegName,String comId, String corporationMobile, HttpServletRequest request) {
		AjaxView ajaxView = new AjaxView();
		GedRegisterRequest gedRegisterRequest = new GedRegisterRequest();
		gedRegisterRequest.setType("1");
		gedRegisterRequest.setCode(unSocCreditNo);
		gedRegisterRequest.setMobile(corporationMobile);
		gedRegisterRequest.setUserRole("0");
		gedRegisterRequest.setRegisterType("0");
		CheckApprove checkApprove = checkApproveService.getByApplyNo(applyNo, "");
		int loanMainBodyType = checkApprove.getLoanMainBodyType();
		if(loanMainBodyType!=1&&loanMainBodyType!=2){
			return ajaxView.setFailed().setMessage("请先完善借款信息！");
		}else{
			gedRegisterRequest.setUserType(loanMainBodyType-1);
		}
		gedRegisterRequest.setStatus(1);
		GedRegisterResponse gedRegisterResponse = Facade.facade.registerGEDAccountInterface(gedRegisterRequest, applyNo);
		try {
			if(gedRegisterResponse!=null){
				if("0".equals(gedRegisterResponse.getCode())){
					ajaxView.setSuccess();//注册成功
					ajaxView.setMessage("注册成功,手机号码为"+corporationMobile+"!");
					underCompanyInfoService.updateGedAccount(corporationMobile,applyNo);
				}else if("110".equals(gedRegisterResponse.getCode())){
					ajaxView.setSuccess();//注册成功
					ajaxView.setMessage("注册成功,手机号码为"+corporationMobile+"!");
					underCompanyInfoService.updateGedAccount(corporationMobile,applyNo);
				}else{
					String exception = gedRegisterResponse.getException();
					ajaxView.setFailed().setMessage(exception);
				}
			}else{
				ajaxView.setFailed().setMessage("访问冠易贷异常！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxView.setFailed().setMessage("访问冠易贷异常！");
		}
		return ajaxView;
	}


	@ResponseBody
	@RequestMapping(value = "pushTarget")
	public AjaxView pushTarget(String applyNo, Model model, String unSocCreditNo, HttpServletRequest request) {
		AjaxView ajaxView = new AjaxView();
		try {
			String flag  = underConclusionService.pushTarget(applyNo);
			ajaxView.setSuccess().setMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxView.setSuccess().setMessage("推标失败");
		}
		return ajaxView;
	}
}