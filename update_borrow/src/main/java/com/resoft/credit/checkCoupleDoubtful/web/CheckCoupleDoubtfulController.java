package com.resoft.credit.checkCoupleDoubtful.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkCoupleDoubtful.entity.CheckCoupleDoubtful;
import com.resoft.credit.checkCoupleDoubtful.service.CheckCoupleDoubtfulService;
import com.resoft.credit.checkFace.entity.CheckFace;
import com.resoft.credit.checkFace.service.CheckFaceService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 两人外访Controller
 * 
 * @author yanwanmei
 * @version 2016-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/checkCoupleDoubtful")
public class CheckCoupleDoubtfulController extends BaseController {
/*	@Autowired
	private CreGedBorrowStatusService creGedBorrowStatusService;*/
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private CheckCoupleDoubtfulService checkCoupleDoubtfulService;

	@Autowired
	private ApplyRelationService applyRelationService;

	@Autowired
	private CheckFaceService checkFaceService;


	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	
	@ModelAttribute
	public CheckCoupleDoubtful get(@RequestParam(required = false) String id) {
		CheckCoupleDoubtful entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkCoupleDoubtfulService.get(id);
		}
		if (entity == null) {
			entity = new CheckCoupleDoubtful();
		}
		return entity;
	}

	@RequiresPermissions("credit:checkCoupleDoubtful:view")
	@RequestMapping(value = { "list", "" })
	public String list(ActTaskParam actTaskParam, String readOnly, CheckCoupleDoubtful checkCoupleDoubtful, HttpServletRequest request, HttpServletResponse response,String suggestionDesc ,  Model model) throws ParseException {

		String applyNo = actTaskParam.getApplyNo();
		User logger = UserUtils.getUser();
		Map<String,String> params = Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
		params.put("checkUserId", logger.getId());
		//外访五节点 判断
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		Date compareDate = df.parse("2017-06-22");
		List<CheckCoupleDoubtful> checkCoupleDoubtfulList  = new ArrayList<CheckCoupleDoubtful>();
		if(applyRegister.getRegisterDate().getTime() > compareDate.getTime()){
			checkCoupleDoubtfulList = checkCoupleDoubtfulService.getCheckCoupleDoubtfulByApplyNoForNewJob(params);
		}else{
			checkCoupleDoubtfulList = checkCoupleDoubtfulService.getCheckCoupleDoubtfulByApplyNo(params);
		}
				// 信息列表中的数据
		model.addAttribute("checkCoupleDoubtfulList", checkCoupleDoubtfulList);
		// 从数据库中查出一个进件中的所有对象
		List<ApplyRelation> applyRelationList = applyRelationService.getCheckCoupleDoubtfulByApplyNo(applyNo);

		// 查询一个进件中，同一个对象的外访次数
		String visitCountFlag = "true";
		String loggerId = UserUtils.getUser().getId();
		for (int i = 0; i < applyRelationList.size(); i++) {
			ApplyRelation tempApplyRelation = applyRelationList.get(i);
			String count="";
			if(applyRegister.getRegisterDate().getTime() > compareDate.getTime()){
				count = checkCoupleDoubtfulService.getCheckCoupleDoubtfulCountForNewJob(tempApplyRelation,loggerId);
			}else{
				count = checkCoupleDoubtfulService.getCheckCoupleDoubtfulCount(tempApplyRelation,loggerId);
			}
			if (count.equals("0")) {
				visitCountFlag = "false";
			}
			tempApplyRelation.setVisitCount(count);
			applyRelationList.set(i, tempApplyRelation);
		}
		if(StringUtils.isNull(suggestionDesc)){
			Map<String, String> params1 = Maps.newHashMap();
			params1.put("applyNo", applyNo);
			params1.put("taskDefKey", Constants.UTASK_LRWF1);
			ProcessSuggestionInfo info = processSuggestionInfoService.findByApplyNo(params1);
			if (info != null) {
				suggestionDesc = info.getSuggestionDesc();
			}
		}
		model.addAttribute("visitCountFlag", visitCountFlag);
		model.addAttribute("applyRelationList", applyRelationList);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("suggestionDesc", suggestionDesc);
		return "app/credit/checkCoupleDoubtful/checkCoupleDoubtfulList";
	}

	@RequiresPermissions("credit:checkCoupleDoubtful:view")
	@RequestMapping(value = "form")
	public String form(CheckCoupleDoubtful checkCoupleDoubtful, Model model, HttpServletRequest request) {
		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}

		String notice = null;
		try {
			String applyno = checkCoupleDoubtful.getApplyNo();
			CheckFace checkFace = checkFaceService.getCheckFaceByApplyNo(applyno);
			notice = checkFace.getDescription();
			String custName = URLDecoder.decode(checkCoupleDoubtful.getCustName(), "UTF-8");
			checkCoupleDoubtful.setCustName(custName);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("checkCoupleDoubtful", checkCoupleDoubtful);
		model.addAttribute("notice", notice);
		return "app/credit/checkCoupleDoubtful/checkCoupleDoubtfulForm";
	}

	@RequiresPermissions("credit:checkCoupleDoubtful:edit")
	@RequestMapping(value = "save")
	public String save(CheckCoupleDoubtful checkCoupleDoubtful, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, checkCoupleDoubtful)) {
			return form(checkCoupleDoubtful, model, request);
		}
		try {
			User loginUser = UserUtils.getUser();
			checkCoupleDoubtful.setCheckUserId(loginUser.getId());
			checkCoupleDoubtful.setCheckUserName(loginUser.getName());
			model.addAttribute("closeWindow", true);
			checkCoupleDoubtfulService.save(checkCoupleDoubtful);
			model.addAttribute("saveMessage", "保存数据成功");
		} catch (Exception e) {
			logger.error("保存数据错误" + e.getMessage(), e);
			model.addAttribute("saveMessage", "保存数据失败");
		}
		return "app/credit/checkCoupleDoubtful/checkCoupleDoubtfulForm";
	}

	@RequiresPermissions("credit:checkCoupleDoubtful:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckCoupleDoubtful checkCoupleDoubtful, RedirectAttributes redirectAttributes) {
		checkCoupleDoubtfulService.delete(checkCoupleDoubtful);
		addMessage(redirectAttributes, "删除两人外访成功");
		return "redirect:" + Global.getAdminPath() + "/checkCoupleDoubtful/checkCoupleDoubtful/?repage";
	}

	@RequiresPermissions("credit:checkCoupleDoubtful:edit")
	@RequestMapping(value = "conclusion")
	@Transactional(value = "CRE", readOnly = false)
	public String conclusion(CheckCoupleDoubtful checkCoupleDoubtful, String readOnly, ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		String suggestionDesc = request.getParameter("suggestionDesc");
		try {
			if(!(Constants.UTASK_FGSFKSH.equalsIgnoreCase(actTaskParam.getTaskDefKey()))){
				Map<String, String> processMap = Maps.newHashMap();
				String passFlag = request.getParameter("passFlag");
				processMap.put("suggestionDesc", suggestionDesc);
				processMap.put("passFlag", passFlag);
				processMap.put("flag", "0");
				processMap.put("blacklistRemarks", request.getParameter("sugession"));
				processSuggestionInfoService.saveApproveSuggestion(actTaskParam, processMap,model);
				model.addAttribute("close", "close");
//				if("no".equals(passFlag) || "black".equals(passFlag)){
//					creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_sbjj,0,null);
//				}
			}else{
				actTaskParam.setTaskDefKey(Constants.UTASK_LRWF1);
				Map<String, String> processMap = Maps.newHashMap();
				processMap.put("suggestionDesc", suggestionDesc);
				processSuggestionInfoService.saveProcessSuggestionInfo(actTaskParam, processMap);
				actTaskParam.setTaskDefKey(Constants.UTASK_FGSFKSH);
				model.addAttribute("closeNewJob", "closeNewJob");
			}
			model.addAttribute("submitMessg", "操作成功！");
		} catch (Exception e) {
			logger.error("保存外访结论失败！", e);
			addMessage(model, "保存外访结论失败!");
		}
		return list(actTaskParam, readOnly, checkCoupleDoubtful, request, response,suggestionDesc, model);
	}

}