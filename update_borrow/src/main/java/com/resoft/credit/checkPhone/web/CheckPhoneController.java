package com.resoft.credit.checkPhone.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.checkFace.entity.CheckFace;
import com.resoft.credit.checkFace.service.CheckFaceService;
import com.resoft.credit.checkPhone.entity.CheckPhone;
import com.resoft.credit.checkPhone.service.CheckPhoneService;
import com.resoft.credit.contactInfo.entity.ContactInfo;
import com.resoft.credit.contactInfo.service.ContactInfoService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 电话核查Controller
 * 
 * @author yanwanmei
 * @version 2016-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/checkPhone")
public class CheckPhoneController extends BaseController {

//	@Autowired
//	private CreGedBorrowStatusService creGedBorrowStatusService;
	@Autowired
	private CheckPhoneService checkPhoneService;

	@Autowired
	private CheckFaceService checkFaceService;

	@Autowired
	private ContactInfoService contactInfoService;

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;


	@ModelAttribute
	public CheckPhone get(@RequestParam(required = false) String id) {
		CheckPhone entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkPhoneService.get(id);
		}
		if (entity == null) {
			entity = new CheckPhone();
		}
		return entity;
	}

	@RequiresPermissions("credit:checkPhone:view")
	@RequestMapping(value = { "list", "" })
	public String list(ActTaskParam actTaskParam, String readOnly, CheckPhone checkPhone, HttpServletRequest request, HttpServletResponse response,String suggestionDesc , Model model) {
		String applyNo = actTaskParam.getApplyNo();
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("readOnly", readOnly);
		List<CheckPhone> checkPhoneList = checkPhoneService.getCheckPhoneByApplyNo(applyNo);
		// 电核信息列表中的数据
		model.addAttribute("checkPhoneList", checkPhoneList);
		String visitCountFlag = "true";
		// 从数据库中查出一个进件中的主借人、配偶、担保人
//		Map<String, Object> params = Maps.newConcurrentMap();
//		try {
//			params.put("applyNo", applyNo);
//			List<String> roleTypeList = new ArrayList<String>();
//			roleTypeList.add(Constants.ROLE_TYPE_ZJR);
//			roleTypeList.add(Constants.ROLE_TYPE_DBR);
//			//roleTypeList.add(Constants.ROLE_TYPE_MATE); 20161110配偶作为担保人是出现重复，改为不查配偶
//			params.put("roleTypeList", roleTypeList);
//		} catch (Exception e) {
//			logger.error("查询待核查列表参数错误", e);
//			model.addAttribute("message", "查询待核查列表参数错误，请联系管理员！");
//		}
//		List<ApplyRelation> applyRelationList = applyRelationService.findByApplyNoAndRoleTypeList(params);
//		// 查询一个进件中，同一个对象的外访次数
//		// 同时确定是否可提交
//		String visitCountFlag = "true";
//		for (int i = 0; i < applyRelationList.size(); i++) {
//			ApplyRelation tempApplyRelation = applyRelationList.get(i);
//			String id = tempApplyRelation.getCustId();
//			CustInfo custInfo = custInfoService.get(id);
//			tempApplyRelation.setCustInfo(custInfo);
//			String count = checkPhoneService.getCheckPhoneCount(tempApplyRelation.getApplyNo(), tempApplyRelation.getCustId(), tempApplyRelation.getRoleType());
//			if (count.equals("0")) {
//				visitCountFlag = "false";
//			}
//			tempApplyRelation.setVisitCount(count);
//			applyRelationList.set(i, tempApplyRelation);
//		}
//		model.addAttribute("applyRelationList", applyRelationList);
		// 从数据库中查出一个进件中的联系人
		Map<String, Object> map = Maps.newConcurrentMap();
		try {
			map.put("applyNo", applyNo);
			map.put("roleType", Constants.ROLE_TYPE_ZJR);
		} catch (Exception e) {
			logger.error("查询待核查列表参数错误", e);
			model.addAttribute("message", "查询待核查列表参数错误，请联系管理员！");
		}
		List<ContactInfo> contactInfoList = contactInfoService.findContactInfoByApplyNo(map);
		ContactInfo contractInfo = new ContactInfo();
		for (int j = 0; j < contactInfoList.size(); j++) {
			contractInfo = contactInfoList.get(j);
			String contractCount = checkPhoneService.getCheckPhoneCount(applyNo, contractInfo.getId(), Constants.ROLE_TYPE_CONTACT);
			if(contractCount.equals("0")){
				visitCountFlag = "false";
			}
			contractInfo.setVisitCount(contractCount);
			contactInfoList.set(j, contractInfo);
		}
			Map<String, String> params = Maps.newHashMap();
			params.put("applyNo", applyNo);
			params.put("taskDefKey", Constants.UTASK_DHHC);
			ProcessSuggestionInfo info = processSuggestionInfoService.findByApplyNo(params);
			if (info != null) {
				suggestionDesc = info.getSuggestionDesc();
				model.addAttribute("statusFlag", info.getIsAbnormal2());
				model.addAttribute("statusFlag1", info.getIsAbnormal());
			}
		model.addAttribute("contactInfoList", contactInfoList);
		model.addAttribute("visitCountFlag", visitCountFlag);
		model.addAttribute("suggestionDesc", suggestionDesc);
		return "app/credit/checkPhone/checkPhoneList";
	}

	@RequiresPermissions("credit:checkPhone:view")
	@RequestMapping(value = "form")
	public String form(CheckPhone checkPhone, Model model, HttpServletRequest request) {

		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		String notice = null;
		try {
			String applyno = checkPhone.getApplyNo();
			CheckFace checkFace= checkFaceService.getCheckFaceByApplyNo(applyno);
		    notice = checkFace.getDescription();

			String custName = URLDecoder.decode(checkPhone.getCustName(), "UTF-8");
			checkPhone.setCustName(custName);
		} catch (UnsupportedEncodingException e) {
			logger.error("中文解码失败", e);
		}
		model.addAttribute("checkPhone", checkPhone);
		model.addAttribute("notice", notice);
		return "app/credit/checkPhone/checkPhoneForm";
	}

	/**
	 * 新增联系人电话核查
	 * 
	 * @param checkPhone
	 * @param model
	 * @param request
	 * @return
	 */
	@RequiresPermissions("credit:checkPhone:view")
	@RequestMapping(value = "formContact")
	public String formContact(CheckPhone checkPhone, Model model, HttpServletRequest request) {
		checkPhone.setRoleType(Constants.ROLE_TYPE_CONTACT);
		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		String notice = null;
		try {
			String applyno = checkPhone.getApplyNo();
			CheckFace checkFace = checkFaceService.getCheckFaceByApplyNo(applyno);
			notice = checkFace.getDescription();
			String custName = URLDecoder.decode(checkPhone.getCustName(), "UTF-8");
			checkPhone.setCustName(custName);
		} catch (UnsupportedEncodingException e) {
			logger.error("中文解码错误！");
			model.addAttribute("message", "参数解码错误，请联系管理员！");
		}
		model.addAttribute("checkPhone", checkPhone);
		model.addAttribute("notice", notice);
		return "app/credit/checkPhone/checkPhoneForm";
	}

	@RequiresPermissions("credit:checkPhone:edit")
	@RequestMapping(value = "save")
	public String save(CheckPhone checkPhone, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, checkPhone)) {
			return form(checkPhone, model, request);
		}

		try {
			User loginUser = UserUtils.getUser();
			checkPhone.setCheckUserId(loginUser.getId());
			checkPhone.setCheckUserName(loginUser.getName());
			checkPhoneService.save(checkPhone);
			model.addAttribute("saveMessage", "保存电话核查成功");
		} catch (Exception e) {
			logger.error("保存数据错误" + e.getMessage(), e);
			model.addAttribute("saveMessage", "保存电话核查失败");
		}
		model.addAttribute("closeWindow", true);
		return "app/credit/checkPhone/checkPhoneForm";
	}

	@RequiresPermissions("credit:checkPhone:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckPhone checkPhone, RedirectAttributes redirectAttributes) {
		checkPhoneService.delete(checkPhone);
		addMessage(redirectAttributes, "删除电话核查成功");
		return "redirect:" + Global.getAdminPath() + "/checkPhone/checkPhone/?repage";
	}

	@RequiresPermissions("credit:checkPhone:edit")
	@RequestMapping(value = "conclusion")
	@Transactional(value = "CRE", readOnly = false)
	public String conclusion(CheckPhone checkPhone, String readOnly, ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response, Model model) {
		String suggestionDesc = request.getParameter("suggestionDesc");
		try {
			if(!(Constants.UTASK_FGSFKSH.equalsIgnoreCase(actTaskParam.getTaskDefKey()))){
				Map<String, String> processMap = Maps.newHashMap();
				String passFlag = request.getParameter("passFlag");
				processMap.put("suggestionDesc", suggestionDesc);
				processMap.put("passFlag", passFlag);
				processMap.put("flag", "0");
				processMap.put("isAbnormal", request.getParameter("isAbnormal"));
				processMap.put("isAbnormal2", request.getParameter("isAbnormal2"));
				processMap.put("blacklistRemarks", request.getParameter("sugession"));
				processSuggestionInfoService.saveApproveSuggestion(actTaskParam, processMap,model);
				model.addAttribute("close", "close");
//				if("no".equals(passFlag) || "black".equals(passFlag)){
//					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_sbjj,0,null);
//				}
			}else{
				actTaskParam.setTaskDefKey(Constants.UTASK_DHHC);
				Map<String, String> processMap = Maps.newHashMap();
				processMap.put("suggestionDesc", suggestionDesc);
				processMap.put("isAbnormal", request.getParameter("isAbnormal"));
				processMap.put("isAbnormal2", request.getParameter("isAbnormal2"));
				processSuggestionInfoService.saveProcessSuggestionInfo(actTaskParam, processMap);
				actTaskParam.setTaskDefKey(Constants.UTASK_FGSFKSH);
				model.addAttribute("closeNewJob", "closeNewJob");
			}
			model.addAttribute("submitMessg", "操作成功！");
		} catch (Exception e) {
			logger.error("保存电话核查结论失败！", e);
			addMessage(model, "保存电话核查结论失败!");
		}
		return list(actTaskParam, readOnly, checkPhone, request, response,suggestionDesc, model);
	}

}