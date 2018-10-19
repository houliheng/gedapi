package com.resoft.credit.companyInfo.web;

import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.industry.entity.Industry;
import com.resoft.credit.industry.service.IndustryService;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;


@Controller

@RequestMapping(value = "${adminPath}/credit/guarantorCompanyInfo/")
public class GuarCompanyInfoFormController extends BaseController {

	@Autowired
	private ApplyRelationService applyRelationService;

	@Autowired
	private CompanyInfoService companyInfoService;

	@Autowired
	private IndustryService industryService;
	
	@Autowired
	private ApplyRegisterService applyRegisterService;

	@Autowired
	private GuaranteeRelationService guaranteeRelationService;

	/**
	 * 担保企业列表
	 * 
	 * @param applyNo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(String taskDefKey ,String applyNo, Model model) {
		int showGedRegisterCompany =0;
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())&&Constants.UTASK_HTYY.equals(taskDefKey)){
			showGedRegisterCompany=1;
			model.addAttribute("showGedRegisterCompany", showGedRegisterCompany);
			model.addAttribute("taskDefKey", taskDefKey);
//			model.addAttribute("applyNo", applyNo);
		}
		Map<String, Object> params = Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_DBQY);
		List<CompanyInfo> companyInfoList = companyInfoService.findListByParams(params);
		//担保关系
		Map<String, GuaranteeRelation> confirmMap = new HashMap<>();
		if (Constants.UTASK_HTMQ.equals(taskDefKey)) {
			List<GuaranteeRelation> confirmList =
				guaranteeRelationService.listMainConfirmStatus(applyNo, Constants.ROLE_TYPE_ZJQY, "2");
			for (GuaranteeRelation confirm:confirmList) {
				confirmMap.put(confirm.getCustId(), confirm);
			}
		}
		for (CompanyInfo companyInfo : companyInfoList) {
			if(1==showGedRegisterCompany) {
				FindIsRegisterRequest findIsRegister = new FindIsRegisterRequest();
				findIsRegister.setUserRole("2");
				findIsRegister.setMobile(companyInfo.getCorporationMobile());
				findIsRegister.setType("1");
				findIsRegister.setCode(companyInfo.getUnSocCreditNo());
				FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister , applyNo);
				if(findIsRegisterResponse!=null){
					String data=null;
					if(findIsRegisterResponse.getData()!=null){
						data = findIsRegisterResponse.getData().getMobile();
						companyInfo.setGedAccount(data);
					}
				}
			}
			if (Constants.UTASK_HTMQ.equals(taskDefKey)) {
				GuaranteeRelation confirm = confirmMap.get(companyInfo.getId());
				if (confirm != null) {
					if ("1".equals(confirm.getIsConfirm())) {
						companyInfo.setIsConfirm("已确认");
					} else if ("0".equals(confirm.getIsConfirm())){
						companyInfo.setIsConfirm("已拒绝");
					} else {
						companyInfo.setIsConfirm("未确认");
					}
				}
			}
		}
		model.addAttribute("companyInfoList", companyInfoList);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("taskDefKey", taskDefKey);
		return "app/credit/guarantorInfo/guarantorCompanyInfo";
	}

	/*查批量借款企业的担保公司
	 * */
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = { "guaranteeList", "" })
	public String guaranteeList(String applyNo, Model model,String custId,String delflag,
								@RequestParam(required = false) String taskDefKey) {
		Map<String, Object> params = Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
		params.put("companyId", custId);
		List<CompanyInfo> companyInfoList = companyInfoService.findGuaranteeListByParams(params);
		Map<String, GuaranteeRelation> confirmMap = new HashMap<>();
		if (Constants.UTASK_HTMQ.equals(taskDefKey)) {
			List<GuaranteeRelation> confirmList =
				guaranteeRelationService.listConfirmStatus(applyNo, Constants.ROLE_TYPE_PLJK, "2", custId);
			for (GuaranteeRelation confirm: confirmList) {
				confirmMap.put(confirm.getCustId(), confirm);
			}
		}
		if (Constants.UTASK_HTMQ.equals(taskDefKey)) {
			for (CompanyInfo company: companyInfoList) {
				GuaranteeRelation confirm = confirmMap.get(company.getId());
				if (confirm != null) {
					if ("1".equals(confirm.getIsConfirm())) {
						company.setIsConfirm("已确认");
					} else if ("0".equals(confirm.getIsConfirm())) {
						company.setIsConfirm("已拒绝");
					} else {
						company.setIsConfirm("未确认");
					}
				}
			}
		}
		model.addAttribute("companyInfoList", companyInfoList);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("companyId", custId);
		model.addAttribute("delflag",delflag);
		model.addAttribute("taskDefKey", taskDefKey);
		return "app/credit/guaranteeRelation/newGuarantorCompanyInfo";
	}

	/**
	 * 保存担保企业信息Ajax
	 * 
	 * @param companyInfo
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "save")
	public AjaxView save(CompanyInfo companyInfo) {
		AjaxView ajaxView = new AjaxView();
		Map<String, Object> params = Maps.newHashMap();
		String roleType = Constants.ROLE_TYPE_DBQY;
		try {
			params.put("applyNo", companyInfo.getCurrApplyNo());
			List<CompanyInfo> infos = companyInfoService.findListByParams(params);
			if (infos.size() != 0) {
				for (CompanyInfo info : infos) {
					if (companyInfo.getBusiRegName().equalsIgnoreCase(info.getBusiRegName()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId())))) {
						ajaxView.setFailed().setMessage("此企业已存在，请重新录入");
						return ajaxView;
					} else {
						if (!(StringUtils.isNull(companyInfo.getUnSocCreditNo()))) {// 统一社会信用贷码不为空
							if (companyInfo.getUnSocCreditNo().equalsIgnoreCase(info.getUnSocCreditNo()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId()))) ) {
								ajaxView.setFailed().setMessage("此企业(统一社会信用代码)已存在，请重新录入");
								return ajaxView;
							} else {
								if (!(StringUtils.isNull(companyInfo.getBusiLicRegNo()))) {
									if (companyInfo.getBusiLicRegNo().equalsIgnoreCase(info.getBusiLicRegNo()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId()))) ) {
										ajaxView.setFailed().setMessage("此企业(营业执照注册号)已存在，请重新录入");
										return ajaxView;
									}
								}
							}
						} else {
							if (!(StringUtils.isNull(companyInfo.getBusiLicRegNo()))) {
								if (companyInfo.getBusiLicRegNo().equalsIgnoreCase(info.getBusiLicRegNo()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId()))) ) {
									ajaxView.setFailed().setMessage("此企业(营业执照注册号)已存在，请重新录入");
									return ajaxView;
								}
							}
						}
					}
				}
			}
			companyInfoService.saveCompanyInfo(companyInfo, roleType);
			ajaxView.setSuccess().setMessage("保存担保企业信息成功！");
		} catch (Exception e) {
			logger.error("保存担保企业信息失败！", e);
			ajaxView.setFailed().setMessage("保存担保企业信息失败！");
		}

		return ajaxView;

	}

	/**
	 * 删除担保企业信息Ajax
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:view")
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxView banchDelete(String ids, String applyNo) {
		AjaxView ajaxView = new AjaxView();
		if (ids != null && !"".equals(ids)) {// 批量删除
			List<String> idList = Arrays.asList(ids.split(","));
			try {
				companyInfoService.banchDelete(idList, applyNo, Constants.ROLE_TYPE_DBQY);  // 这是担保企业信息
				ajaxView.setSuccess().setMessage("删除担保企业信息成功！");
			} catch (Exception e) {
				logger.error("删除担保企业信息失败！", e);
				ajaxView.setSuccess().setMessage("删除担保企业信息失败！");
			}
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "form")
	public String form(String companyId, String applyNo, String readOnly, Model model) {
		// 1.新增
		CompanyInfo guarantorCompanyInfo = new CompanyInfo();
		if (StringUtils.isNotEmpty(applyNo) && StringUtils.isEmpty(companyId)) {
			readOnly = "1";
		}
		// 2.修改或详情
		if (StringUtils.isNotEmpty(companyId)) {
			guarantorCompanyInfo = companyInfoService.get(companyId);
			if (guarantorCompanyInfo == null) {
				guarantorCompanyInfo = new CompanyInfo();
			}
			ApplyRelation applyRelation = applyRelationService.getApplyRelation(applyNo, companyId);
			if (applyRelation != null) {
				guarantorCompanyInfo.setSvFlag(applyRelation.getSvFlag());
			}
		}
		guarantorCompanyInfo.setCurrApplyNo(applyNo);
		model.addAttribute("guarantorCompanyInfo", guarantorCompanyInfo);

		// 4.行业信息
		// 初始化行业门类
		List<Industry> categoryMainList = industryService.findByParentCode("root");
		model.addAttribute("categoryMainList", categoryMainList);
		// 初始化行业大类
		if (StringUtils.isNotEmpty(guarantorCompanyInfo.getCategoryLarge())) {
			List<Industry> categoryLargeList = industryService.findByParentCode(guarantorCompanyInfo.getCategoryMain());
			model.addAttribute("categoryLargeList", categoryLargeList);
		}
		// 初始化行业中类
		if (StringUtils.isNotEmpty(guarantorCompanyInfo.getCategoryMedium())) {
			List<Industry> categoryMediumList = industryService.findByParentCode(guarantorCompanyInfo.getCategoryLarge());
			model.addAttribute("categoryMediumList", categoryMediumList);
		}
		// 初始化行业小类
		if (StringUtils.isNotEmpty(guarantorCompanyInfo.getCategorySmall())) {
			List<Industry> categorySmallList = industryService
					.findByParentCode(guarantorCompanyInfo.getCategoryMedium());
			model.addAttribute("categorySmallList", categorySmallList);
		}
		model.addAttribute("readOnly", readOnly);
		return "app/credit/companyInfo/guarantorCompanyInfoForm";
	}
}
