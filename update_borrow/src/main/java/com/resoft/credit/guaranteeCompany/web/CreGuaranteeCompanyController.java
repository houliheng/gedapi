package com.resoft.credit.guaranteeCompany.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.resoft.accounting.taskCenter.service.TaskCenterService;
import com.resoft.credit.loanApply.service.CreApplyRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRegister.service.CreVieoPathService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.guaranteeCompany.dao.CreGuaranteeCompanyDao;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.credit.guranteeCompanyRelation.service.CreApplyCompanyRelationService;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.rest.newged.entity.GedRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.GedRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 担保公司Controller
 * @author gsh
 * @version 2018-04-16
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creGuaranteeCompany")
public class CreGuaranteeCompanyController extends BaseController {

	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;

	@Autowired
	private ApplyRelationService applyRelationService;

	@Autowired
	private CreGedAccountCompanyService creGedAccountService;
	@Autowired
	private CreVieoPathService creVieoPathService;
	@Autowired
	private CreApplyRegisterService creApplyRegisterService;
	@Autowired
	private TaskCenterService taskCenterService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private CreGuaranteeCompanyDao creGuaranteeCompanyDao;
	@Autowired
	private CreApplyCompanyRelationService creApplyCompanyRelationService;
	@ModelAttribute
	public CreGuaranteeCompany get(@RequestParam(required=false) String id) {
		CreGuaranteeCompany entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creGuaranteeCompanyService.get(id);
		}
		if (entity == null){
			entity = new CreGuaranteeCompany();
		}
		return entity;
	}

	@RequestMapping(value = {"list",""})
	public String list(CreGuaranteeCompany creGuaranteeCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreGuaranteeCompany> page = creGuaranteeCompanyService.findCustomPage(new Page<CreGuaranteeCompany>(request, response), creGuaranteeCompany);
		List<CreGuaranteeCompany> creGuaranteeCompaniesList = page.getList();
		for(CreGuaranteeCompany creGuaranteeCompanes:creGuaranteeCompaniesList){
			BigDecimal guaranty = new BigDecimal("0.00");//可担保额度
			BigDecimal guaranteeAmount = new BigDecimal("0.00");//已担保额度
			int guranteeCount = 0;
			List<ApplyRelation> applyRelations = applyRelationService.findAllGuranteeRelation(creGuaranteeCompanes.getId());
			if (applyRelations.size() >0) {
				for(ApplyRelation applyRelation :applyRelations){
					ApplyRelation applyRelationMian = applyRelationService.findMianApplyRelation(applyRelation.getApplyNo());
					ApplyRegister applyRegister = new ApplyRegister();
					applyRegister.setApplyNo(applyRelation.getApplyNo());
					List<ApplyRegister> registerList = applyRegisterService.findList(applyRegister);
					if (!registerList.isEmpty()) {
						applyRegister = registerList.get(0);
					}
					if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())){
						Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
						paramCheckApprove.put("applyNo", applyRelation.getApplyNo());
						List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
						if (checkApproveList.size() >0) {
							CheckApprove checkApprove = checkApproveList.get(0);
							if (checkApprove != null) {
								guaranty = guaranty.add(checkApprove.getContractAmount());
								guaranteeAmount = guaranteeAmount.add(checkApprove.getContractAmount());
								guranteeCount = guranteeCount + 1;
							}
						}
						
					}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegister.getProcDefKey()) && applyRelationMian != null) {
						CheckApproveUnion checkApproveUnion = creGuaranteeCompanyDao.getByApplyNoAndCustId(applyRelation.getApplyNo(),applyRelationMian.getCustId());
						if (checkApproveUnion != null) {
							guaranty = guaranty.add(checkApproveUnion.getContractAmount());
							guaranteeAmount = guaranteeAmount.add(checkApproveUnion.getContractAmount());
							guranteeCount = guranteeCount + 1;
						}
					}
					}
			}
			
			List<CreApplyCompanyRelation> creApplyCompanyRelations = creApplyCompanyRelationService.findApplyGuranteeRelations(creGuaranteeCompanes.getId());
			if (creApplyCompanyRelations.size() > 0) {
				for(CreApplyCompanyRelation creApplyCompanyRelation:creApplyCompanyRelations){
					CheckApproveUnion checkApproveUnion = creGuaranteeCompanyDao.getByApplyNoAndCustId(creApplyCompanyRelation.getApplyNo(),creApplyCompanyRelation.getCompanyId());
					if (checkApproveUnion != null) {
						guaranty = guaranty.add(checkApproveUnion.getContractAmount());
						guaranteeAmount = guaranteeAmount.add(checkApproveUnion.getContractAmount());
						guranteeCount = guranteeCount + 1;
					}
				}
			}
			if ("0.00".equals(guaranty.toString())) {
				creGuaranteeCompanes.setGuaranty(new BigDecimal(creGuaranteeCompanes.getGuaranteeLimit()));
			}else{
				creGuaranteeCompanes.setGuaranty(new BigDecimal(creGuaranteeCompanes.getGuaranteeLimit()).subtract(guaranty));
			}
			creGuaranteeCompanes.setGuaranteeAmount(guaranteeAmount);
			creGuaranteeCompanes.setGuranteeCount(String.valueOf(guranteeCount));
			if (creGuaranteeCompanes != null && creGuaranteeCompanes.getGedNumFlag() != null) {
				if ("1".equals(creGuaranteeCompanes.getGedNumFlag())) {
					creGuaranteeCompanes.setIsGEDNum("1");
					creGuaranteeCompanes.setMobileGed(creGuaranteeCompanes.getLinkMobile());
				}
				if ("2".equals(creGuaranteeCompanes.getGedNumFlag())) {
					creGuaranteeCompanes.setIsGEDNum("1");
					creGuaranteeCompanes.setMobileGed(creGuaranteeCompanes.getUnSocCreditNo());
				}
				
			}
		}
		page.setList(creGuaranteeCompaniesList);
		model.addAttribute("page", page);
		return "app/credit/guaranteeCompany/creGuaranteeCompanyList";
	}

	@RequestMapping(value = "form")
	public String form(CreGuaranteeCompany creGuaranteeCompany, Model model,String readOnly) {
		List<CreGedAccountCompany> creGedAccountCompanys = new ArrayList<>();
		CreGedAccountCompany creGedAccountCompany = null;
		String mobileGed = creGuaranteeCompany.getMobileGed();
		Integer businessLicenceNum = 0;
		Integer operatePermitNum = 0;
		Integer certificateSecurityNum = 0;
		Integer otherNum = 0;
		try {
			if (StringUtils.isBlank(creGuaranteeCompany.getId())) {
				readOnly = "1";
			}
			if (StringUtils.isNotBlank(creGuaranteeCompany.getId())) {
				creGuaranteeCompany = creGuaranteeCompanyService.get(creGuaranteeCompany.getId());
				if (creGuaranteeCompany == null) {
					creGuaranteeCompany = new CreGuaranteeCompany();
				}
			}
			if (creGuaranteeCompany != null && StringUtils.isNotBlank(creGuaranteeCompany.getUnSocCreditNo())) {
				creGedAccountCompanys = creGedAccountService.findCompanyAccountBySocialCreditNo(creGuaranteeCompany.getUnSocCreditNo());
				if (creGedAccountCompanys.size() >0) {
					creGedAccountCompany = creGedAccountCompanys.get(0);
				}
				if (creGedAccountCompanys.size() == 0) {
					creGedAccountCompany = new CreGedAccountCompany();
				}
			}
			if (creGuaranteeCompany.getUnSocCreditNo() != null && StringUtils.isNotBlank(creGuaranteeCompany.getUnSocCreditNo())) {
				businessLicenceNum = creVieoPathService.countNum(creGuaranteeCompany.getUnSocCreditNo(),Constants.DBGS_YYZZ);
				operatePermitNum = creVieoPathService.countNum(creGuaranteeCompany.getUnSocCreditNo(),Constants.DBGS_JYXKZ);
				certificateSecurityNum = creVieoPathService.countNum(creGuaranteeCompany.getUnSocCreditNo(),Constants.DBGS_DBHMB);
				otherNum = creVieoPathService.countNum(creGuaranteeCompany.getUnSocCreditNo(),Constants.DBGS_QT);
			}

			//	creVideoPathDao.findCreVideoPathQueryList();
		} catch (Exception e) {
			logger.error("系统参数出现错误，请联系管理员！", e);
			model.addAttribute("message", "系统参数出现错误，请联系管理员！");
		}
		model.addAttribute("mobileGed",mobileGed);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("creGuaranteeCompany", creGuaranteeCompany);
		model.addAttribute("creGedAccountCompany",creGedAccountCompany);
		model.addAttribute("businessLicenceNum",businessLicenceNum);
		model.addAttribute("operatePermitNum",operatePermitNum);
		model.addAttribute("certificateSecurityNum",certificateSecurityNum);
		model.addAttribute("otherNum",otherNum);
		return "app/credit/guaranteeCompany/creGuaranteeCompanyForm";
	}


	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(CreGuaranteeCompany creGuaranteeCompany, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		if (creGuaranteeCompany == null) {
			logger.error("数据出现异常，保存担保公司信息失败！");
			ajaxView.setFailed().setMessage("保存担保公司信息失败！");
			return ajaxView;
		}
		if ("1".equals(creGuaranteeCompany.getGuaranteeState())) {
			logger.info(creGuaranteeCompany.getGuaranteeAmount()+" "+creGuaranteeCompany.getGuranteeCount()+" "+creGuaranteeCompany.getGuaranty());
			if (creGuaranteeCompany != null && !"0.00".equals(creGuaranteeCompany.getGuaranteeAmount())  && !"0".equals(creGuaranteeCompany.getGuranteeCount()) && !creGuaranteeCompany.getGuaranteeLimit().equals(creGuaranteeCompany.getGuaranty().toString())) {
				logger.error("禁用失败");
				ajaxView.setFailed().setMessage("保存失败，该担保公司存在担保项目");
				return ajaxView;
			}
		}
		try {
			creGuaranteeCompanyService.save(creGuaranteeCompany);
			ajaxView.setSuccess().setMessage("保存担保公司信息成功！");
		} catch (Exception e) {
			logger.error("保存担保企业信息失败！", e);
			ajaxView.setFailed().setMessage("保存担保公司信息失败！");
		}
		return ajaxView;
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxView delete(@RequestParam("ids")List<String> idList) {
		AjaxView rtn = new AjaxView();
		try {
			creGuaranteeCompanyService.batchDelete(idList);;
			rtn.setSuccess().setMessage("删除担保公司成功!");
		} catch (Exception e) {
			logger.error("删除担保公司成功", e);
			rtn.setFailed().setMessage("删除担保公司成功!");
		}
		return rtn;
	}


	@RequestMapping(value = "guaranteeList")
	public String guaranteeList(CreGuaranteeCompany creGuaranteeCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreGuaranteeCompany> page = creGuaranteeCompanyService.findCustomPage(new Page<CreGuaranteeCompany>(request, response), creGuaranteeCompany);
		List<CreGuaranteeCompany> creGuaranteeCompaniesList = page.getList();
		for(CreGuaranteeCompany creGuaranteeCompanes:creGuaranteeCompaniesList){
			if (StringUtils.isNotBlank(creGuaranteeCompanes.getUnSocCreditNo())) {
				FindIsRegisterRequest findIsRegister=new FindIsRegisterRequest("1",creGuaranteeCompanes.getUnSocCreditNo());
				logger.info(creGuaranteeCompanes.getLinkMobile());
				findIsRegister.setMobile(creGuaranteeCompanes.getLinkMobile());
				FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister, creGuaranteeCompanes.getUnSocCreditNo());
				if(findIsRegisterResponse!=null){
					String data=null;
					if(findIsRegisterResponse.getData()!=null){
						data = findIsRegisterResponse.getData().getMobile();
						creGuaranteeCompanes.setMobileGed(data);
					}
					String code = findIsRegisterResponse.getCode();
					if("0".equals(code)){//冠易贷返回异常信息，没有注册
						logger.error("异常信息：",findIsRegisterResponse.getException());
						creGuaranteeCompanes.setIsGEDNum("0");
					}else{//不为0表示已经注册
						creGuaranteeCompanes.setIsGEDNum("1");
					}
				}else{//查询异常
					creGuaranteeCompanes.setIsGEDNum("2");
				}
			}
		}
		page.setList(creGuaranteeCompaniesList);
		model.addAttribute("page", page);
		return "app/credit/guaranteeCompany/guranteeProjectManageList";
	}

	@RequestMapping(value = "isRegisterGED")
	public String isRegisterGED(CreGuaranteeCompany creGuaranteeCompany,HttpServletRequest request, HttpServletResponse response, Model model) {
		creGuaranteeCompany = creGuaranteeCompanyService.get(creGuaranteeCompany.getId());
		if (creGuaranteeCompany == null) {
			model.addAttribute("flag","查询失败");
		}
		if (creGuaranteeCompany != null && creGuaranteeCompany.getGedNumFlag() != null) {
			if ("1".equals(creGuaranteeCompany.getGedNumFlag())) {
				model.addAttribute("findGedNum",creGuaranteeCompany.getLinkMobile());
			}
			if ("2".equals(creGuaranteeCompany.getGedNumFlag())) {
				model.addAttribute("findGedNum",creGuaranteeCompany.getUnSocCreditNo());
			}
			model.addAttribute("isHasGEDAccount", "1");//冠e贷已经有账号
		}
		model.addAttribute("creGuaranteeCompany", creGuaranteeCompany);
		return "app/credit/guaranteeCompany/regsiterGED";
	}

	@ResponseBody
	@RequestMapping(value = "registerGEDAccount")
	public AjaxView registerGEDAccount(CreGuaranteeCompany creGuaranteeCompany, Model model,HttpServletRequest request) {
		AjaxView rtn = new AjaxView();
		String registerFlag = request.getParameter("registerFlag");
		GedRegisterRequest gedRegisterRequest = null;
		if ("1".equals(registerFlag)) {
			gedRegisterRequest=new GedRegisterRequest("1",creGuaranteeCompany.getUnSocCreditNo(),creGuaranteeCompany.getLinkMobile(),"3");
			gedRegisterRequest.setRegisterType("1");
			creGuaranteeCompany.setGedNumFlag(registerFlag);
		}
		if ("2".equals(registerFlag)) {
			gedRegisterRequest=new GedRegisterRequest("1",creGuaranteeCompany.getUnSocCreditNo(),"","3");
			gedRegisterRequest.setRegisterType("2");
			creGuaranteeCompany.setGedNumFlag(registerFlag);
		}
		gedRegisterRequest.setUserType(1);
		gedRegisterRequest.setGuaranteeAmount(creGuaranteeCompany.getGuaranteeLimit());
		gedRegisterRequest.setUserType(1);
		GedRegisterResponse gedRegisterResponse = Facade.facade.registerGEDAccountInterface(gedRegisterRequest, creGuaranteeCompany.getUnSocCreditNo());
		if(gedRegisterResponse!=null){
			if("0".equals(gedRegisterResponse.getCode())){
				rtn.setSuccess();//注册成功
				if ("1".equals(registerFlag)) {
					rtn.setMessage(creGuaranteeCompany.getLinkMobile());
				}
				if ("2".equals(registerFlag)) {
					rtn.setMessage(creGuaranteeCompany.getUnSocCreditNo());
				}
				creGuaranteeCompanyService.updateGuranteeGedAccount(creGuaranteeCompany);
			}else{
				String exception = gedRegisterResponse.getException();
				rtn.setFailed().setMessage(exception);
			}
		}else{
			rtn.setFailed().setMessage("访问冠易贷异常！");
		}
		return rtn;
	}


	/**
	 *lb
	 *申请录入-->申请信息-->担保公司
	 *@param model
	 *@return
	 */
	@RequestMapping(value =  "/ApplyGuananteeList" )
	public String ApplyGuananteeList(@RequestParam(required = true) String applyNo,HttpServletRequest request,
									 Model model){
		model.addAttribute("applyNo",applyNo);
		Map<String,String> applyRelationMap = new HashMap<>();
		applyRelationMap.put("applyNo",applyNo);
		applyRelationMap.put("roleType","8"); // 8是担保公司类型
		String taskDefKey = request.getParameter("taskDefKey");  // 这是节点的key
		model.addAttribute("taskDefKey",taskDefKey);
 /*		// 在cre_apply_register 中根据申请编号 求出'流程实例ID',
		CreApplyRegister register = creApplyRegisterService.queryApplyRegisterByapplyNO(applyNo);
		String procinsId = register.getProcInsId(); //求出流程编号 id
		// 根据流程编号id 还有节点标示在 act_ru_task 查询是代办还是已办 有值就是代办 没有就是已办
		Map<String,String> ParamMap = new HashMap<>();
		ParamMap.put("taskDefKey",taskDefKey);
		ParamMap.put("procInstId",procinsId);
		Integer task = taskCenterService.queryActRuTaskByParamMap(ParamMap);
		System.out.println(task);

		 if(task == 1){
        	model.addAttribute("task",task); // 有值的话就是待办 没有的话就是已办
		}*/
		// 修改
		CreGuaranteeCompany company = creGuaranteeCompanyService.queryApplyCompany(applyRelationMap);

		if(null != company){
			String companyId = company.getId();
			applyRelationMap.put("custId",companyId);
			ApplyRelation relation = creGuaranteeCompanyService.queryApplyRelation(applyRelationMap);

			// 修改
			//查询该关系表的主键
			/*CreApplyCompanyRelation relation = creApplyCompanyRelationService.queryRelation(relation1);*/
			if ("1".equals(relation.getIsConfirm())) {
				relation.setIsConfirm("已确认");
			} else if ("0".equals(relation.getIsConfirm())){
				relation.setIsConfirm("已拒绝");
			} else {
				relation.setIsConfirm("未确认");
			}
			model.addAttribute("company",company);
			model.addAttribute("relation",relation);
			model.addAttribute("flag",0);//这是关联的担保公司标记位

			return "app/credit/guaranteeCompany/creGuaranteeCompanyDetailList";
		} else {
			model.addAttribute("flag",1); //这是没有关联的担保公司标记位
			return "app/credit/guaranteeCompany/creGuaranteeCompanyDetailList";
		}
	}

	/**
	 * lb
	 * 查询所有的担保公司信息
	 * @return
	 */
	@RequestMapping(value = "/GuananteeList")
	public String GuananteeList( CreGuaranteeCompany creGuaranteeCompany,
								 Model model,
								 HttpServletRequest request){

		String flag = request.getParameter("companyFlag");
		String applyNo = request.getParameter("applyNo");
		List<CreGuaranteeCompany> companyList = creGuaranteeCompanyService.queryCreGuaranteeCompanyList(creGuaranteeCompany);
		model.addAttribute("companyList",companyList);
		model.addAttribute("flag",flag);
		model.addAttribute("applyNo",applyNo);

		return "app/credit/guaranteeCompany/CreGuarantoXianshiCompanyList";
	}


	/**
	 * lb
	 * 添加关联关系表
	 */
	@RequestMapping(value = "/saveRelation1111")
	@ResponseBody
	public AjaxView saveRelation(@RequestParam(required = true) String companyId,
								 @RequestParam(required = true) String applyNo){
		AjaxView ajax = new AjaxView();
		Map<String,String> applyRelationMap = new HashMap<>();

		applyRelationMap.put("applyNo",applyNo);
		applyRelationMap.put("roleType","8"); // 8是担保公司类型

       // 查看主借人有没有关联担保公司
		applyRelationService.deleteApplyRelation(applyRelationMap);
		CreGuaranteeCompany company = creGuaranteeCompanyService.get(companyId);

		ApplyRelation relation = new ApplyRelation();
		relation.setApplyNo(applyNo);
		relation.setCustId(companyId);
		relation.setRoleType("8");
		relation.setCustName(company.getGuaranteeCompanyName());
		try {
			applyRelationService.save(relation);
			ajax.setSuccess().setMessage("关联成功");
		}catch (Exception e){
			logger.error("关联失败",e);
			ajax.setFailed().setMessage("稍后再试");
		}
		return  ajax;
	}


	/**
	 *  lb
	 * 逻辑删除该订单下的担保公司信息
	 * @return
	 */
	@RequestMapping("deleteCompany")
	@ResponseBody
	public AjaxView deleteCompany(@RequestParam(required = false) String id){
		AjaxView ajax = new AjaxView();
		ApplyRelation relation = new ApplyRelation();
		System.err.println(id);

		relation.setId(id);

		try {
			applyRelationService.delete(relation);
			ajax.setSuccess().setMessage("删除担保公司成功");
		}catch (Exception e){
			logger.error("删除担保公司成功",e);
			ajax.setFailed().setMessage("删除担保公司成功");
		}
		return ajax;
	}

}