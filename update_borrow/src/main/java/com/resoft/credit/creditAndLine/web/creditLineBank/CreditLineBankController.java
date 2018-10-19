package com.resoft.credit.creditAndLine.web.creditLineBank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contactInfo.service.ContactInfoService;
import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBank;
import com.resoft.credit.creditAndLine.service.creditLineBank.CreditLineBankService;

/**
 * 流水信息列表Controller
 * @author wuxi01
 * @version 2016-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditAndLine/creditLineBank")
public class CreditLineBankController extends BaseController {

	@Autowired
	private CreditLineBankService creditLineBankService;
	
	@Autowired
	private ApplyRelationService applyRelationService;
	
	@Autowired
	private ContactInfoService contactInfoService;
	@Autowired
	
	private CompanyInfoService companyInfoService;
	
	@ModelAttribute
	public CreditLineBank get(@RequestParam(required=false) String id) {
		CreditLineBank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditLineBankService.get(id);
		}
		if (entity == null){
			entity = new CreditLineBank();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creditAndLine:creditLineBank:view")
	@RequestMapping(value = {"list", ""})
	public String list(String applyNo, String readOnly, Model model) {
		CreditLineBank creditLineBank = new CreditLineBank();
		creditLineBank.setApplyNo(applyNo);
		List<CreditLineBank> creditLineBankList = creditLineBankService.findList(creditLineBank);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("creditLineBankList", creditLineBankList);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/creditAndLine/creditLineBank/creditLineBankList";
	}
	

	@RequiresPermissions("credit:creditAndLine:creditLineBank:view")
	@RequestMapping(value = "form")
	public String form(String creditLineBankId, String readOnly, String applyNo, Model model) {
		//1.新增
		CreditLineBank creditLineBank = new CreditLineBank();
		if (StringUtils.isNotEmpty(applyNo)) {
			readOnly = "1";
			creditLineBank.setApplyNo(applyNo);
		}else if(StringUtils.isNotEmpty(creditLineBankId)){//2.修改
			creditLineBank = creditLineBankService.get(creditLineBankId);
			if (creditLineBank == null) {
				readOnly = "1";
				creditLineBank = new CreditLineBank();
				creditLineBank.setApplyNo(applyNo);
			}else {
				Map<String, String> params = Maps.newConcurrentMap();
				params.put("applyNo", creditLineBank.getApplyNo());
				params.put("roleType", creditLineBank.getRoleType());
				List<Map<String, String>> custNameMap = applyRelationService.findCustNameByRoleType(params);
				model.addAttribute("custNameMap", custNameMap);
			}
		}
		model.addAttribute("creditLineBank", creditLineBank);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/creditAndLine/creditLineBank/creditLineBankForm";
	}
	
	
	/**
	 * 在当前流程中，根据角色类型查询客户名称Ajax
	 * @param creditLineBank
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditLineBank:edit")
	@RequestMapping(value = "findCustNameByRoleType")
	public List<Map<String, String>> findCustNameByRoleType(String roleType, String applyNo) {
		List<Map<String, String>> custNamesAndId = new ArrayList<Map<String, String>>();
		Map<String, String> params = Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
		//人员类型为除了联系人其他的类型
		if(!Constants.ROLE_TYPE_CONTACT.equals(roleType)){
			params.put("roleType", roleType);
			custNamesAndId =  applyRelationService.findCustNameByRoleType(params);
		}else if(Constants.ROLE_TYPE_CONTACT.equals(roleType)){
			//主借人类型
			params.put("roleType", Constants.ROLE_TYPE_ZJR);
			custNamesAndId =  contactInfoService.getContactInfoByApplyNo(params);
		}
		return custNamesAndId;
	}
	
	/**
	 * 在当前流程中，根绝企业名称（id）查询证件号(组织机构代码)Ajax
	 * @param creditLineBank
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditLineBank:edit")
	@RequestMapping(value = "findCustByCompanyName")
	public String findCustByCompanyName(String custId) {
		String cardId=null;
		try {
			CompanyInfo companyInfo = companyInfoService.get(custId);
			cardId = companyInfo.getUnSocCreditNo();
		} catch (Exception e) {
			logger.error("根据企业ID获取企业统一社会信用代码失败！", e);
		}
		return cardId;
	}

	/**
	 * 保存银行卡流水信息Ajax
	 * @param creditLineBank
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditLineBank:edit")
	@RequestMapping(value = "save")
	public AjaxView save(@RequestBody CreditLineBank creditLineBank) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditLineBankService.save(creditLineBank);
			ajaxView.setSuccess().setMessage("保存银行卡流水信息成功！");
		} catch (Exception e) {
			logger.error("保存银行卡流水信息失败！", e);
			ajaxView.setFailed().setMessage("保存银行卡流水信息失败！");
		}
		return ajaxView;
	}
	
	/**
	 * 删除银行卡流水信息Ajax
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditLineBank:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(String ids) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditLineBankService.banchDelete(ids);
			ajaxView.setSuccess().setMessage("删除银行卡流水信息成功！");
		} catch (Exception e) {
			logger.error("保存银行卡流水信息失败！", e);
			ajaxView.setFailed().setMessage("删除银行卡流水信息失败！");
		}
		return ajaxView;
	}
	

}