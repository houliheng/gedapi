package com.resoft.credit.underCompanyInfo.web;

import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.gqgetComInfo.entity.GqgetComInfo;
import com.resoft.credit.gqgetComInfo.service.GqgetComInfoService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.GedAccount.entity.GedAccount;
import com.resoft.credit.GedAccount.service.GedAccountService;
import com.resoft.credit.GedCompanyAccount.entity.CreGedAccountCompany;
import com.resoft.credit.GedCompanyAccount.service.CreGedAccountCompanyService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.gqgetComInfo.entity.BankLoan;
import com.resoft.credit.gqgetComInfo.service.BankLoanService;
import com.resoft.credit.industry.entity.Industry;
import com.resoft.credit.industry.service.IndustryService;
import com.resoft.credit.underCustInfo.entity.UnderCustInfo;
import com.resoft.credit.underCustInfo.service.UnderCustInfoService;
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
import com.resoft.credit.underCompanyInfo.entity.UnderCompanyInfo;
import com.resoft.credit.underCompanyInfo.service.UnderCompanyInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线下借款-企业信息Controller
 * @author jml
 * @version 2018-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/underCompanyInfo")
public class UnderCompanyInfoController extends BaseController {

	@Autowired
	private UnderCompanyInfoService underCompanyInfoService;
	@Autowired
	private IndustryService industryService;// 行业分类service
	@Autowired
	private BankLoanService bankLoanService;
	//冠e贷个人账户接口
	@Autowired
	private GedAccountService gedAccountService;
	//冠e贷企业账户接口
	@Autowired
	private CreGedAccountCompanyService gedAccountCompanyService;

	@Autowired
	private UnderCustInfoService underCustInfoService;

	@Autowired
	private GqgetComInfoService gqgetComInfoService;

	@ModelAttribute
	public UnderCompanyInfo get(@RequestParam(required=false) String id) {
		UnderCompanyInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = underCompanyInfoService.get(id);
		}
		if (entity == null){
			entity = new UnderCompanyInfo();
		}
		return entity;
	}

	
	@RequiresPermissions("credit:underCompanyInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(UnderCompanyInfo underCompanyInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UnderCompanyInfo> page = underCompanyInfoService.findCustomPage(new Page<UnderCompanyInfo>(request, response), underCompanyInfo); 
		model.addAttribute("page", page);
		return "app/credit/underCompanyInfo/underCompanyInfoList";
	}

	@RequiresPermissions("credit:underCompanyInfo:view")
	@RequestMapping(value = "underCompanyDetail")
	public String form(ActTaskParam actTaskParam, String readOnly, Model model) {
		String applyNo=actTaskParam.getApplyNo();
		UnderCompanyInfo underCompanyInfo = underCompanyInfoService.getByApplyNo(applyNo);
		if (underCompanyInfo != null)
			underCompanyInfo.setApplyNo(applyNo);
		model.addAttribute("underCompanyInfo", underCompanyInfo);
		// 初始化行业门类
		List<Industry> categoryMainList3 = industryService.findByParentCode("root");
		model.addAttribute("categoryMainList3", categoryMainList3);
//		System.out.println("=============88888888888888================" + JSONObject.toJSONString(actTaskParam));
//		System.out.println("=============88888888888888================" + JSONObject.toJSONString(underCompanyInfo));
//		System.out.println("=============88888888888888================" + JSONObject.toJSONString(categoryMainList3));
		// 初始化行业大类
		if (StringUtils.isNotEmpty(underCompanyInfo.getCategoryLarge())) {
			List<Industry> categoryLargeList3 = industryService.findByParentCode(underCompanyInfo.getCategoryMain());
			model.addAttribute("categoryLargeList3", categoryLargeList3);
		}
		// 初始化行业中类
		if (StringUtils.isNotEmpty(underCompanyInfo.getCategoryMedium())) {
			List<Industry> categoryMediumList3 = industryService.findByParentCode(underCompanyInfo.getCategoryLarge());
			model.addAttribute("categoryMediumList3", categoryMediumList3);
		}
		// 初始化行业小类
		if (StringUtils.isNotEmpty(underCompanyInfo.getCategorySmall())) {
			List<Industry> categorySmallList3 = industryService.findByParentCode(underCompanyInfo.getCategoryMedium());
			model.addAttribute("categorySmallList3", categorySmallList3);
		}

		UnderCustInfo underCustInfo = underCustInfoService.getByApplyNo(applyNo);
		model.addAttribute("underCustInfo",underCustInfo == null ? new UnderCustInfo() : underCustInfo);


		return "app/credit/underCompanyInfo/underCompanyDetail";
	}

	@RequiresPermissions("credit:applyInfo:edit")
	@RequestMapping(value = "saveCompanyInfo")
	public String save(UnderCompanyInfo underCompanyInfo, Model model, ActTaskParam actTaskParam) {
		try {
			Integer res = underCompanyInfoService.updateByApplyNo(underCompanyInfo);
			addMessage(model, "保存企业信息成功");
		} catch (Exception e) {
			logger.error("保存企业信息失败",e);
			addMessage(model, "保存企业信息失败");
		}
//		System.out.println("=============00000000000================" + JSONObject.toJSONString(actTaskParam));
//		System.out.println("=============00000000000================" + JSONObject.toJSONString(underCompanyInfo));
		model.addAttribute("underCompanyInfo",underCompanyInfo);
		return form( actTaskParam, "false",  model);
	}

	@RequiresPermissions("credit:applyInfo:edit")
	@RequestMapping(value = "saveCustInfo")
	public String save(UnderCustInfo underCustInfo, Model model, ActTaskParam actTaskParam) {
		underCustInfo.setApplyNo(actTaskParam.getApplyNo());
		try {
			Integer res = underCustInfoService.insertOrUpdate(underCustInfo);
			addMessage(model, "保存借款人基本信息成功");
		} catch (Exception e) {
			logger.error("保存借款人基本信息失败",e);
			addMessage(model, "保存借款人基本信息失败");
		}
		return form( actTaskParam, "false",  model);
	}

	@RequiresPermissions("credit:custinfo:edit")
	@ResponseBody
	@RequestMapping(value = "getAccountInfo")
	public AjaxView getAccountInfo(String idCardNum) {
		AjaxView ajaxView = new AjaxView();
		try {
			GedAccount account = gedAccountService.findGedAccountByIdNum(idCardNum);
			if (account != null){

				ajaxView.setSuccess().setMessage("获取个人账户信息！");
				ajaxView.put("account", account);
				if (account.getCompanyBankOfDeposit() != null){
					String bankName = DictUtils.getDictLabel(account.getCompanyBankOfDeposit(), "BANKS", "");
					ajaxView.put("bankName", bankName);
					logger.error("===================================6666666===bankName==========================================="+bankName);
				}

			}else {
				logger.error("获取个人账户信息失败！");
				ajaxView.setFailed().setMessage("获取个人账户信息失败！");
			}
		} catch (Exception e) {
			logger.error("获取个人账户信息失败！", e);
			ajaxView.setFailed().setMessage("获取个人账户信息失败！");
		}
		return ajaxView;
	}
	@RequiresPermissions("credit:custinfo:edit")
	@ResponseBody
	@RequestMapping(value = "getAccountCompanyInfo")
	public AjaxView getAccountCompanyInfo(String socialCreditNo) {
		AjaxView ajaxView = new AjaxView();
		try {
			List<CreGedAccountCompany> accountCompanys = gedAccountCompanyService.findCompanyAccountBySocialCreditNo(socialCreditNo);
			if (accountCompanys !=null && accountCompanys.size() == 1 ){
				ajaxView.setSuccess().setMessage("获取企业账户信息！");
				ajaxView.put("accountCompany", accountCompanys.get(0));
				ajaxView.put("id", accountCompanys.get(0).getId());
				return ajaxView;
			}else {
				logger.error("获取企业账户信息失败！");
				ajaxView.setFailed().setMessage("获取企业账户信息失败！");
			}

		} catch (Exception e) {
			logger.error("获取企业账户信息失败！", e);
			ajaxView.setFailed().setMessage("获取企业账户信息失败！");
		}
		return ajaxView;
	}
	@RequiresPermissions("credit:underCompanyInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(UnderCompanyInfo underCompanyInfo, RedirectAttributes redirectAttributes) {
		underCompanyInfoService.delete(underCompanyInfo);
		addMessage(redirectAttributes, "删除线下借款-企业信息成功");
		return "redirect:"+Global.getAdminPath()+"/underCompanyInfo/underCompanyInfo/?repage";
	}

	@RequestMapping(value = "saveUnderOrgInfo")
	@ResponseBody
	public AjaxView saveUnderOrgInfo(UnderCompanyInfo underCompanyInfo, Model model) {
		AjaxView result = new AjaxView();
		try {
			underCompanyInfoService.updateOrgInfoByApplyNo(underCompanyInfo);
			result.setSuccess().setMessage("保存机构信息成功");
			addMessage(model, "保存机构信息成功");
		} catch (Exception e) {
			result.setFailed().setMessage("保存机构信息失败!");
			addMessage(model, "保存机构信息失败!");
			logger.error("保存机构信息失败!", e);
		}
		return result;
	}


	@RequiresPermissions("credit:underCompanyInfo:view")
	@RequestMapping(value = "zichan")
	public String zichan(ActTaskParam actTaskParam, String readOnly, Model model) {
		String applyNo=actTaskParam.getApplyNo();
		try {
			GqgetComInfo hasGqgetComInfo = gqgetComInfoService.getGqgetComInfoByApplyNo(actTaskParam.getApplyNo());
			if (hasGqgetComInfo != null){
				model.addAttribute("gqgetComInfo", hasGqgetComInfo);
			}else {
				model.addAttribute("gqgetComInfo", new GqgetComInfo());
			}

			Map<String, String> param = new HashMap<String, String>();
			param.put("applyNo", actTaskParam.getApplyNo());
			model.addAttribute("readOnly", readOnly);
			model.addAttribute("actTaskParam", actTaskParam);
		} catch (Exception e) {
			logger.error("查询冠E通信息或批复信息失败", e);
		}
		return "app/credit/underCompanyInfo/zichanxinxi";
	}
}