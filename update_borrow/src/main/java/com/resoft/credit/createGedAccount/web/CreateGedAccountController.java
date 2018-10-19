package com.resoft.credit.createGedAccount.web;

import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.resoft.credit.gedApiUser.service.CreGedapiUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;
import com.resoft.credit.custWorkInfo.service.CustWorkInfoService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;
import com.resoft.outinterface.rest.newged.entity.GedRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.GedRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/credit/createGedAccount")
public class CreateGedAccountController  extends BaseController{
	
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private GuaranteeRelationService guaranteeRelationService;
	@Autowired
	private CustInfoService custInfoService;
	@Autowired
	private ApplyRelationService applyRelationService;
	@Autowired
	private CustWorkInfoService custWorkInfoService;
	@Autowired
	private CreGedapiUserService creGedapiUserService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private CheckApproveService checkApproveService;
	
	
	@RequestMapping(value = "index")
	public String index(ActTaskParam actTaskParam, String readOnly, Model model) {
		// 流程信息
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		// 前台index页
		return "app/credit/createGedAccount/createGedAccountIndex";
		//return "app/credit/guarantorInfo/guarantorInfoIndex";
	}
	
	@RequestMapping(value = "mainList")
	public String list(String applyNo, Model model) {
		Map<String, Object> params = Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_ZJQY);
		params.put("roleType2", Constants.ROLE_TYPE_PLJK);
		//主借企业
		List<CompanyInfo> companyInfoList = companyInfoService.findGedListByParams(params);
		//批量借款企业
		/*List<CompanyInfo> companyInfoList = companyInfoService.findGedDanBaoListByParams(params);
		companyInfoList.addAll(companyInfoList2);*/
		//检查是否建立冠易贷账号
		List<CompanyInfo> companyInfoNewList = companyInfoService.findGEDRegisterAccount(companyInfoList,applyNo);
		model.addAttribute("companyInfoList", companyInfoNewList);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/createGedAccount/mainCreateGedAccount";
		//return "app/credit/guarantorInfo/guarantorCompanyInfo";
	}
	
	@ResponseBody
	@RequestMapping(value = "createAccount")
	public AjaxView createAccount(String custId,String unSocCreditNo, String applyNo,String corporationMobile,String flag) {
		/*flag=1   piliangmain or main
		 * flag=2  pilianginfo
		 * flag=3  piliangcompany
		 * */
		AjaxView ajaxView = new AjaxView();
		 ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		String type=null;
		String userRole = null;
		String roleType = null;
		String registerType =null;
		Integer userType=null;
		if("1".equals(flag)) {
			//0借款人1自有担保人2自有担保企业3合作企业
			userRole="0";
			type="1";
			userType=1;
		}
		if("3".equals(flag)) {
			userRole="2";
			type="1";
			userType=1;
		}
		if("2".equals(flag)) {
			registerType="0";
			userType=0;
			roleType = applyRelationService.getRoleType(applyNo,unSocCreditNo);
			unSocCreditNo=null;
			if(StringUtils.isEmpty(roleType)) {//批量借款企业的担保人
				//0借款人1自有担保人2自有担保企业3合作企业
				userRole="1";
			}else {//主借企业的担保人，主借人
				if("1".equals(roleType)) {
					userRole="0";
					//主借企业的统一社会 
					unSocCreditNo=companyInfoService.getMainCodeByApply(applyNo);
					type="1";
				}else if("3".equals(roleType)||"4".equals(roleType)) {
					userRole="1";
				}else if("5".equals(roleType)||"6".equals(roleType)){
					userRole="2";
				}
			}
		}
		
		GedRegisterRequest gedRegisterRequest=new GedRegisterRequest(type,unSocCreditNo,corporationMobile,userRole);
		gedRegisterRequest.setUserType(Integer.parseInt(applyRegisterByApplyNo.getCustType())-1);
		//registerType 注册类型 0主借人1法人3社会统一代码 ,担保人给，主借人给0 o r1
		gedRegisterRequest.setRegisterType(registerType);
		GedRegisterResponse gedRegisterResponse = Facade.facade.registerGEDAccountInterface(gedRegisterRequest, applyNo);
		if(gedRegisterResponse!=null){
			if("0".equals(gedRegisterResponse.getCode())){
				ajaxView.setSuccess();//注册成功
				ajaxView.setMessage("注册成功,正在刷新!");
				if(custId!=null) {
					//custInfoService.saveRegisterType(custId,corporationMobile);
					creGedapiUserService.saveRegisterInfo(custId,"1",corporationMobile);
				}
				
			}else{
				String exception = gedRegisterResponse.getException();
				ajaxView.setFailed().setMessage(exception);
			}
		}else{
			ajaxView.setFailed().setMessage("访问冠易贷异常！");
		}
		return ajaxView;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "createAccountGR")
	public AjaxView createAccountGR(String id,String mobileNum,String flag,String applyNo) {
		AjaxView ajaxView = new AjaxView();
		ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		GedRegisterRequest gedRegisterRequest=null;
		String account = "";
		if("1".equals(flag)) {
			gedRegisterRequest=new GedRegisterRequest(null,null,mobileNum,"1");
			account = mobileNum;
		}
		if("2".equals(flag)) {
			CompanyInfo companyInfo = companyInfoService.get(id);
			gedRegisterRequest=new GedRegisterRequest("1",companyInfo.getUnSocCreditNo(),companyInfo.getCorporationMobile(),"2");
			account = companyInfo.getCorporationMobile();
		}
		gedRegisterRequest.setRegisterType("0");
		if (applyRegisterByApplyNo != null && StringUtils.isNotBlank(applyRegisterByApplyNo.getCustType())) {
			gedRegisterRequest.setUserType(Integer.parseInt(applyRegisterByApplyNo.getCustType())-1);
		} else {
			CheckApprove checkApprove = checkApproveService.getByApplyNo(applyNo, Constants.UNDER_DQGLR);
			gedRegisterRequest.setUserType(checkApprove.getLoanMainBodyType() - 1);
		}

		GedRegisterResponse gedRegisterResponse = Facade.facade.registerGEDAccountInterface(gedRegisterRequest, applyNo);
		if(gedRegisterResponse!=null){
			if("0".equals(gedRegisterResponse.getCode())){
				ajaxView.setSuccess();//注册成功
				ajaxView.setMessage("注册成功,正在刷新!");
				creGedapiUserService.saveRegisterInfo(id,"1",account);
				//custInfoService.saveRegisterType(id, account);
			}else{
				String exception = gedRegisterResponse.getException();
				ajaxView.setFailed().setMessage(exception);
			}
		}else{
			ajaxView.setFailed().setMessage("访问冠易贷异常！");
		}
		return ajaxView;
	}
	
	
	@RequestMapping(value = "guarantorInfo")
	public String guarantorInfo(String id,String applyNo, Model model) {//担保人列表
		if(StringUtils.isBlank(id)) {//id是批量借款企业，主借企业的companyId
			return "app/credit/createGedAccount/gedAccountGuarantorInfo";
		}
		Map<String, Object> params = Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
		params.put("companyId", id);
		params.put("roleType", "1");
		CompanyInfo companyInfo = companyInfoService.get(id);
		model.addAttribute("companyName", companyInfo.getBusiRegName());
		List<GuaranteeRelation> guaranteeRelation = guaranteeRelationService.findGedGuarantee(params);
		//检查是否注册
		guaranteeRelation = companyInfoService.guaranteeGEDRegisterAccount(guaranteeRelation,applyNo,"1");
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("companyId", id);
		model.addAttribute("guaranteeRelationList", guaranteeRelation);
		return "app/credit/createGedAccount/gedAccountGuarantorInfo";
	}
	
	@RequestMapping(value = "guaranteeInfoList")
	public String guaranteeInfoList(String companyId,String applyNo, Model model) {
		ApplyRelation coCustInfoRelation = new ApplyRelation();
		List<ApplyRelation> coCustRelations = new ArrayList<>();
		List<ApplyRelation> guarantorRelationList = new ArrayList<ApplyRelation>();
		List<CustWorkInfo> guarantorWorkInfoList = new ArrayList<CustWorkInfo>();
		//该企业是主借企业还是批量借款企业
		
		String role= companyInfoService.getRoleIsMainOrBatch(applyNo,companyId);
		if("5".equals(role)) {//主借企业的担保人
			coCustInfoRelation.setApplyNo(applyNo);
			coCustInfoRelation.setRoleType(Constants.ROLE_TYPE_DBR);
			coCustRelations = applyRelationService.findList(coCustInfoRelation);
			if (!coCustRelations.isEmpty()) {
				for (int i = 0; i < coCustRelations.size(); i++) {
					ApplyRelation coCustRelation = coCustRelations.get(i);
					if (coCustRelation != null && coCustRelation.getCustId() != null && StringUtils.isNotEmpty(coCustRelation.getCustId())) {
						CustInfo coCustInfo = custInfoService.get(coCustRelation.getCustId());
						coCustRelation.setCustInfo(coCustInfo);
						guarantorRelationList.add(coCustRelation);
						// 查询该担保人工作信息
						Map<String, String> map = Maps.newConcurrentMap();
						map.put("custId", coCustRelation.getCustId());
						CustWorkInfo coCustWorkInfo = custWorkInfoService.findCustWorkInfoByCustId(map);
						guarantorWorkInfoList.add(coCustWorkInfo);
					}
				}
			}
		}else{//批量借款企业的担保人
			List<CustInfo> custInfoList = custInfoService.findThisCompanyGuarantor(applyNo,companyId);
			for (CustInfo custInfo : custInfoList) {
				ApplyRelation applyRelation = new ApplyRelation();
				applyRelation.setCustInfo(custInfo);
				guarantorRelationList.add(applyRelation);
			}
		}
		model.addAttribute("guarantorRelationList", guarantorRelationList);
		model.addAttribute("guarantorWorkInfoList", guarantorWorkInfoList);
		model.addAttribute("companyId", companyId);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/createGedAccount/chooseGedAccountGuarantorInfo";
		//return "app/credit/guarantorInfo/guarantorInfoList";
	}
	
	@RequestMapping(value = "guarantorCompany")
	public String guarantorCompany(String id,String applyNo, Model model) {
		if(StringUtils.isBlank(id)) {
			return "app/credit/createGedAccount/gedAccountGuarantorCompany";
		}
		Map<String, Object> params = Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
		params.put("companyId", id);
		params.put("roleType", "2");
		CompanyInfo companyInfo = companyInfoService.get(id);
		model.addAttribute("companyName", companyInfo.getBusiRegName());
		List<GuaranteeRelation> guaranteeRelation = guaranteeRelationService.findGedCompanyGuarantee(params);
		//检查是否注册，放入注册号码
		guaranteeRelation = companyInfoService.guaranteeGEDRegisterAccount(guaranteeRelation,applyNo,"2");
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("companyId", id);
		model.addAttribute("guaranteeRelationList1", guaranteeRelation);
		return "app/credit/createGedAccount/gedAccountGuarantorCompany";
	}
	
	@RequestMapping(value = "guaranteeCompanyList")
	public String guaranteeCompanyList(String companyId,String applyNo, Model model) {
		Map<String, Object> params = Maps.newConcurrentMap();
		List<CompanyInfo> companyInfoList=null;
		//该企业是主借企业还是批量借款企业
		String role= companyInfoService.getRoleIsMainOrBatch(applyNo,companyId);
		if("5".equals(role)) {//主借企业 de 担保企业
			params.put("applyNo", applyNo);
			params.put("roleType", Constants.ROLE_TYPE_DBQY);
			companyInfoList = companyInfoService.findListByParams(params);
		}else{//批量借款的担保企业
			companyInfoList = companyInfoService.findBatchGuarantee(applyNo,companyId);
		}
		model.addAttribute("companyInfoList", companyInfoList);
		model.addAttribute("companyId", companyId);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/createGedAccount/chooseGedAccountGuarantorCompany";
		//return "app/credit/guarantorInfo/guarantorCompanyInfo";
	}
	
	@RequestMapping(value = "showCreateAccount")
	public String showCreateAccount(String custId,String unSocCreditNo,String applyNo,String corporationMobile,String flag,String roleType,String busiRegName,String id, Model model) {
		if(StringUtils.isEmpty(custId)) {
			if(Constants.ROLE_TYPE_ZJQY.equals(roleType)) {
				CustInfo mainCustInfo = custInfoService.getCustByRoleAndApplyNo(applyNo,Constants.ROLE_TYPE_ZJR);
				model.addAttribute("mobileNum", mainCustInfo.getMobileNum());
			}
			model.addAttribute("unSocCreditNo", unSocCreditNo);
			model.addAttribute("applyNo", applyNo);
			model.addAttribute("corporationMobile", corporationMobile);
			model.addAttribute("flag", flag);
			model.addAttribute("roleType", roleType);
			model.addAttribute("busiRegName", busiRegName);
			model.addAttribute("id", id);
		}else {
			model.addAttribute("unSocCreditNo", unSocCreditNo);
			model.addAttribute("applyNo", applyNo);
			model.addAttribute("corporationMobile", corporationMobile);
			model.addAttribute("flag", flag);
			model.addAttribute("busiRegName", busiRegName);
			model.addAttribute("id", custId);
		}
		return "app/credit/createGedAccount/showCreateGedAccountForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "createAccountNew")
	public AjaxView createAccountNew(String accountType,String unSocCreditNo,String applyNo,String corporationMobile,String flag,String roleType,String busiRegName,String id,String mobileNum) {
		AjaxView ajaxView = new AjaxView();
		ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		GedRegisterRequest gedRegisterRequest=null;
		//0借款人1自有担保人2自有担保企业3合作企业
		String account = "";
		if("1".equals(flag)) {
			if("1".equals(accountType)) {
				gedRegisterRequest=new GedRegisterRequest("1",unSocCreditNo,mobileNum,"0");
				gedRegisterRequest.setRegisterType("0");
				account=mobileNum;
			}
			if("2".equals(accountType)) {
				gedRegisterRequest=new GedRegisterRequest("1",unSocCreditNo,corporationMobile,"0");		
				gedRegisterRequest.setRegisterType("1");
				account = corporationMobile;
			}
			if("3".equals(accountType)) {
				gedRegisterRequest=new GedRegisterRequest("1",unSocCreditNo,null,"0");
				gedRegisterRequest.setRegisterType("2");
				account = unSocCreditNo;
			}
		}else {
			if("2".equals(accountType)) {
				gedRegisterRequest=new GedRegisterRequest("1",unSocCreditNo,corporationMobile,"2");		
				gedRegisterRequest.setRegisterType("1");
				account = corporationMobile;
			}
			if("3".equals(accountType)) {
				gedRegisterRequest=new GedRegisterRequest("1",unSocCreditNo,null,"2");
				gedRegisterRequest.setRegisterType("2");
				account = unSocCreditNo;
			}
		}
		gedRegisterRequest.setUserType(Integer.parseInt(applyRegisterByApplyNo.getCustType())-1);
		GedRegisterResponse gedRegisterResponse = Facade.facade.registerGEDAccountInterface(gedRegisterRequest, applyNo);
		if(gedRegisterResponse!=null){
			if("0".equals(gedRegisterResponse.getCode())){
				ajaxView.setSuccess();//注册成功
				ajaxView.setMessage("注册成功,正在刷新!");
				//插入记录
				if(id!=null) {
					creGedapiUserService.saveRegisterInfo(id,"2",account);
					//companyInfoService.saveRegisterType(account,id);
				}
			}else{
				String exception = gedRegisterResponse.getException();
				ajaxView.setFailed().setMessage(exception);
			}
		}else{
			ajaxView.setFailed().setMessage("访问冠易贷异常！");
		}
		return ajaxView;
	}
}
