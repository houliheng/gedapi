package com.resoft.credit.checkWeb.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkFace.entity.CheckFace;
import com.resoft.credit.checkFace.service.CheckFaceService;
import com.resoft.credit.checkWeb.entity.CheckWeb;
import com.resoft.credit.checkWeb.service.CheckWebService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 网查Controller
 * @author yanwanmei
 * @version 2016-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/checkWeb")
public class CheckWebController extends BaseController {

	@Autowired
	private CheckWebService checkWebService;
	
	@Autowired
	private ApplyRelationService applyRelationService;
	
	@Autowired
	private CustInfoService custInfoService;
	
	@Autowired
	private CheckFaceService checkFaceService;
	
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	
	@Autowired
	private CompanyInfoService companyInfoService;
	
	@ModelAttribute
	public CheckWeb get(@RequestParam(required=false) String id) {
		CheckWeb entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkWebService.get(id);
		}
		if (entity == null){
			entity = new CheckWeb();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:checkWeb:view")
	@RequestMapping(value = {"list", ""})
	public String list(ActTaskParam actTaskParam, String readOnly, CheckWeb checkWeb, HttpServletRequest request, HttpServletResponse response,String suggestionDesc, Model model) {
		
		String applyNo = actTaskParam.getApplyNo();
		
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("applyNo", applyNo);
		List<CheckWeb> checkWebList = checkWebService.getCheckWebByApplyNo(applyNo);
		//网查信息列表中的数据
	    model.addAttribute("checkWebList", checkWebList);
	    
		//从数据库中查出一个进件中的所有对象，是待核查列表中的数据
		List<ApplyRelation> applyRelationList = applyRelationService.getCheckWebByApplyNo(applyNo);
		
		//查询一个进件中，同一个对象的外访次数
		String visitCountFlag = "true";
		for(int i=0;i<applyRelationList.size();i++){
			ApplyRelation tempApplyRelation = applyRelationList.get(i);
			String id = tempApplyRelation.getCustId();
			//查询企业信息
			if(Constants.ROLE_TYPE_ZJQY.equals(tempApplyRelation.getRoleType()) || Constants.ROLE_TYPE_DBQY.equals(tempApplyRelation.getRoleType())){
				CompanyInfo companyInfo = companyInfoService.get(id);
				tempApplyRelation.setCompanyInfo(companyInfo);
			}else{
				//查询客户信息
				CustInfo custInfo = custInfoService.get(id);
				tempApplyRelation.setCustInfo(custInfo);
			}
			//获取访问次数
			String count = checkWebService.getCheckWebCount(tempApplyRelation.getApplyNo(),id,tempApplyRelation.getRoleType());
			if (count.equals("0")) {
				visitCountFlag = "false";
			}
			tempApplyRelation.setVisitCount(count);
			applyRelationList.set(i, tempApplyRelation);
		}
			Map<String, String> params = Maps.newHashMap();
			params.put("applyNo", applyNo);
			params.put("taskDefKey", Constants.UTASK_WC);
			ProcessSuggestionInfo info = processSuggestionInfoService.findByApplyNo(params);
			if (info != null) {
				suggestionDesc = info.getSuggestionDesc();
				model.addAttribute("statusFlag", info.getIsAbnormal());
			}
		model.addAttribute("applyRelationList",applyRelationList);
		model.addAttribute("visitCountFlag",visitCountFlag);
		model.addAttribute("suggestionDesc", suggestionDesc);
		return "app/credit/checkWeb/checkWebList";
	}

	@RequiresPermissions("credit:checkWeb:view")
	@RequestMapping(value = "form")
	public String form(CheckWeb checkWeb, Model model,HttpServletRequest request) {
		if("true".equals(request.getParameter("readOnly"))){
			model.addAttribute("readOnly", true);
		}
		String notice = null;
		try {
			String applyno = checkWeb.getApplyNo();
			CheckFace checkFace= checkFaceService.getCheckFaceByApplyNo(applyno);
		    notice = checkFace.getDescription();
			String custName = URLDecoder.decode(checkWeb.getCustName(), "UTF-8");
			checkWeb.setCustName(custName);
			
			if(Constants.ROLE_TYPE_ZJQY.equals(checkWeb.getRoleType()) || Constants.ROLE_TYPE_DBQY.equals(checkWeb.getRoleType())){
				//查询证件类型和证件号码,电话号码
				CompanyInfo companyInfo = companyInfoService.get(checkWeb.getCustId());
				if(companyInfo == null){
					companyInfo = new CompanyInfo();
				}
				model.addAttribute("myCom", companyInfo);
				checkWeb.setMobileNum(companyInfo.getCorporationMobile());
				//主借企业和担保企业没有法人证件类型，默认为身份证
				if(companyInfo.getUnSocCreditNo() != null && !("").equals(companyInfo.getUnSocCreditNo())){
					checkWeb.setIdType(Constants.CUSTOMER_P_ID_TYPE_COM);
				}else{
					checkWeb.setIdType(Constants.CUSTOMER_P_ID_TYPE_COM);
				}
			}else{
				//查询证件类型和证件号码,电话号码
				CustInfo custInfo = custInfoService.get(checkWeb.getCustId());
				if(custInfo == null){
					custInfo = new CustInfo();
				}
				
				checkWeb.setMobileNum(custInfo.getMobileNum());
				checkWeb.setIdType(custInfo.getIdType());
				checkWeb.setIdNum(custInfo.getIdNum());
			}
			
			
		} catch (UnsupportedEncodingException e) {
			logger.error("中文解码失败", e);
		}
		model.addAttribute("notice", notice);
		model.addAttribute("checkWeb", checkWeb);
		return "app/credit/checkWeb/checkWebForm";
	}

	@RequiresPermissions("credit:checkWeb:edit")
	@RequestMapping(value = "save")
	public String save(CheckWeb checkWeb, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, checkWeb)){
			return form(checkWeb, model,request);
		}
		
		try{
			User loginUser = UserUtils.getUser();
			checkWeb.setCheckUserId(loginUser.getId());
			checkWeb.setCheckUserName(loginUser.getName());
			checkWebService.save(checkWeb);
			model.addAttribute("saveMessage", "保存网查成功");
			model.addAttribute("closeWindow", true);
		}catch(Exception e){
			logger.error("保存数据错误" + e.getMessage(), e);
			model.addAttribute("saveMessage", "保存网查失败");
			model.addAttribute("closeWindow", false);
		}
		
		return "app/credit/checkWeb/checkWebForm";
	}
	
	@RequiresPermissions("credit:checkWeb:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckWeb checkWeb, RedirectAttributes redirectAttributes) {
		checkWebService.delete(checkWeb);
		addMessage(redirectAttributes, "删除网查成功");
		return "redirect:"+Global.getAdminPath()+"/checkWeb/checkWeb/?repage";
	}
	
	@RequiresPermissions("credit:checkPhone:edit")
	@RequestMapping(value = "conclusion")
	public String conclusion(CheckWeb checkWeb, String readOnly, ActTaskParam actTaskParam,HttpServletRequest request,HttpServletResponse response,Model model){
		String suggestionDesc = request.getParameter("suggestionDesc");
		try {
			if(!(Constants.UTASK_FGSFKSH.equalsIgnoreCase(actTaskParam.getTaskDefKey()))){
				Map<String, String> processMap = Maps.newHashMap();
				String passFlag = request.getParameter("passFlag");
				processMap.put("suggestionDesc", suggestionDesc);
				processMap.put("passFlag", passFlag);
				processMap.put("flag", "0");
				processMap.put("isAbnormal", request.getParameter("isAbnormal"));
				processMap.put("blacklistRemarks", request.getParameter("sugession"));
				processSuggestionInfoService.saveApproveSuggestion(actTaskParam, processMap,model);
				model.addAttribute("close", "close");
			}else{
				actTaskParam.setTaskDefKey(Constants.UTASK_WC);
				Map<String, String> processMap = Maps.newHashMap();
				processMap.put("suggestionDesc", suggestionDesc);
				processMap.put("isAbnormal", request.getParameter("isAbnormal"));
				processSuggestionInfoService.saveProcessSuggestionInfo(actTaskParam, processMap);
				actTaskParam.setTaskDefKey(Constants.UTASK_FGSFKSH);
				model.addAttribute("closeNewJob", "closeNewJob");
			}
			model.addAttribute("submitMessg", "操作成功！");
		} catch (Exception e) {
			logger.error("保存网查结论失败！",e);
			addMessage(model, "保存网查结论失败!");
		}
		return list(actTaskParam, readOnly, checkWeb, request, response,suggestionDesc, model);
	}

}