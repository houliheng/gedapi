package com.resoft.credit.companyInfo.web;

import java.util.List;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.industry.entity.Industry;
import com.resoft.credit.industry.service.IndustryService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;


/**
 *
 * 企业基本信息Controller
 * @author caoyinglong
 * @version 2016-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/companyInfo")
public class CompanyInfoController extends BaseController {

	@Autowired
	private CompanyInfoService companyInfoService;
	
	@Autowired
	private IndustryService industryService;
	
	@ModelAttribute
	public CompanyInfo get(@RequestParam(required=false) String id) {
		CompanyInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyInfoService.get(id);
		}
		if (entity == null){
			entity = new CompanyInfo();
		}
		return entity;
	}

	/**
	 * 主借企业
	 * @param companyId
	 * @param readOnly
	 * @param applyNo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "form")
	public String form(String companyId, String readOnly, String applyNo, Model model) {
		CompanyInfo companyInfo = new CompanyInfo();
		//	1.新增
		if (StringUtils.isNotEmpty(applyNo)) {
			readOnly = "1";
			companyInfo.setCurrApplyNo(applyNo);
		}
		//	2. 修改或查看详情
		if (StringUtils.isNotEmpty(companyId)) {
			companyInfo = companyInfoService.get(companyId);
			if (companyInfo == null) {
				companyInfo = new CompanyInfo();
			}
			
		}
		// 初始化行业门类
		List<Industry> categoryMainList = industryService.findByParentCode("root");
		model.addAttribute("categoryMainList", categoryMainList);
		// 初始化行业大类
		if (StringUtils.isNotEmpty(companyInfo.getCategoryLarge())) {
			List<Industry> categoryLargeList = industryService.findByParentCode(companyInfo.getCategoryMain());
			model.addAttribute("categoryLargeList", categoryLargeList);
		}
		// 初始化行业中类
		if (StringUtils.isNotEmpty(companyInfo.getCategoryMedium())) {
			List<Industry> categoryMediumList = industryService.findByParentCode(companyInfo.getCategoryLarge());
			model.addAttribute("categoryMediumList", categoryMediumList);
		}
		// 初始化行业小类
		if (StringUtils.isNotEmpty(companyInfo.getCategorySmall())) {
			List<Industry> categorySmallList = industryService.findByParentCode(companyInfo.getCategoryMedium());
			model.addAttribute("categorySmallList", categorySmallList);
		}
		model.addAttribute("companyInfo", companyInfo);
		model.addAttribute("readOnly", readOnly);
		
		
		
		return "app/credit/companyInfo/guarantorCompanyInfoForm";
	}
	

	/**
	 * 保存主借企业Ajax
	 * @param companyInfo
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "save")
	public AjaxView save(CompanyInfo companyInfo) {
		AjaxView ajaxView = new AjaxView();
		String roleType = Constants.ROLE_TYPE_ZJQY;
		try {
			companyInfoService.saveCompanyInfo(companyInfo, roleType);
			ajaxView.put("companyInfoId", companyInfo.getId());
			ajaxView.put("id", companyInfo.getId());
			ajaxView.setSuccess().setMessage("保存主借人企业信息成功！");
		} catch (Exception e) {
			logger.error("保存主借人企业信息失败！", e);
			ajaxView.setFailed().setMessage("保存主借人企业信息失败！");
		}
		return ajaxView;
	}


	/**
	 * 查询批量借款企业信息
	 */
     @RequestMapping(value = "/companyList")
	 public String companyList (@RequestParam(required = true) String applyNo){


     	return null;
	 }

	/**
	 *  前往批量借款企业
	 */
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "index")
	public String index( ActTaskParam actTaskParam, String readOnly, Model model) {
		// 流程信息
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		// 前台index页
		return "app/credit/companyInfo/companyInfoIndex";
	}


}