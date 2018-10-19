package com.resoft.credit.guaranteeRelation.web;

import java.util.Arrays;
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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;

/**
 * 担保信息关联表Controller
 * @author jml
 * @version 2018-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/guaranteeRelation")
public class GuaranteeRelationController extends BaseController {

	@Autowired
	private GuaranteeRelationService guaranteeRelationService;
	
	@ModelAttribute
	public GuaranteeRelation get(@RequestParam(required=false) String id) {
		GuaranteeRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = guaranteeRelationService.get(id);
		}
		if (entity == null){
			entity = new GuaranteeRelation();
		}
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "saveRelation")
	public AjaxView saveRelation(String ids, String applyNo,String companyId) {
		AjaxView ajaxView = new AjaxView();
		if (ids != null && !"".equals(ids)) {// 批量删除
			List<String> idList = Arrays.asList(ids.split(","));
			try {
				guaranteeRelationService.saveRelation(idList,applyNo,companyId);
				//custInfoService.newBanchDelete(idList, applyNo, Constants.ROLE_TYPE_DBR);
				ajaxView.setSuccess().setMessage("关联担保人信息成功！");
				
			} catch (Exception e) {
				logger.error("删除担保人信息失败！", e);
				ajaxView.setSuccess().setMessage("关联担保人信息失败！");
			}
		}
		return ajaxView;
	}
	
	@ResponseBody
	@RequestMapping(value = "saveRelationCompany")
	public AjaxView saveRelationCompany(String ids, String applyNo,String companyId) {
		AjaxView ajaxView = new AjaxView();
		if (ids != null && !"".equals(ids)) {// 批量删除
			List<String> idList = Arrays.asList(ids.split(","));
			try {
				guaranteeRelationService.saveCompanyRelation(idList,applyNo,companyId);
				ajaxView.setSuccess().setMessage("关联担保企业信息成功！");
			} catch (Exception e) {
				logger.error("删除担保人信息失败！", e);
				ajaxView.setSuccess().setMessage("关联担保企业信息失败！");
			}
		}
		return ajaxView;
	}
}