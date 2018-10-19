package com.resoft.credit.checkFace.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.checkFace.entity.CheckFace;
import com.resoft.credit.checkFace.service.CheckFaceService;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 面审信息Controller
 * 
 * @author yanwanmei
 * @version 2016-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/checkFace")
public class CheckFaceController extends BaseController {
//	@Autowired
//	private CreGedBorrowStatusService creGedBorrowStatusService;
	@Autowired
	private CheckFaceService checkFaceService;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;
	@ModelAttribute
	public CheckFace get(@RequestParam(required = false) String id) {
		CheckFace entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkFaceService.get(id);
		}
		if (entity == null) {
			entity = new CheckFace();
		}
		return entity;
	}

	@RequiresPermissions("credit:checkFace:view")
	@RequestMapping(value = { "list", "" })
	public String checkFaceInfo(ActTaskParam actTaskParam, String readOnly, CheckFace checkFace, ProcessSuggestionInfo processSuggestionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", actTaskParam.getApplyNo());
		params.put("taskDefKey", actTaskParam.getTaskDefKey());
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		if (StringUtils.isBlank(checkFace.getId())) {
			CheckFace checkFaceTemp = checkFaceService.getCheckFaceByApplyNo(actTaskParam.getApplyNo());
			if (checkFaceTemp != null) {
				checkFace = checkFaceTemp;
			}
		}
		//加入参与者暂定是国鹏一个，后续会修改
		checkFace.setParticipant(applyRegisterService.getApplyRegisterByApplyNo(checkFace.getApplyNo()).getParticipant());
		model.addAttribute("checkFace", checkFace);
		if (StringUtils.isBlank(processSuggestionInfo.getId())) {
			processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		}
		model.addAttribute("processSuggestionInfo", processSuggestionInfo);
		return "app/credit/checkFace/checkFaceList";
	}

	@RequiresPermissions("credit:checkFace:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(ActTaskParam actTaskParam, String readOnly, CheckFace checkFace, HttpServletRequest request, HttpServletResponse response,Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			//将参与者存入applyregister
			ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(checkFace.getApplyNo());
			String participant = checkFace.getParticipant();
			applyRegisterByApplyNo.setParticipant(checkFace.getParticipant());
			applyRegisterService.save(applyRegisterByApplyNo);
			String flag = request.getParameter("sta");
			String suggestionDesc = request.getParameter("suggestionDesc");
			String passFlag = request.getParameter("passFlag");
			Map<String, String> processMap = Maps.newHashMap();
			processMap.put("suggestionDesc", suggestionDesc);
			processMap.put("passFlag", passFlag);
			processMap.put("flag", flag);
			processMap.put("blacklistRemarks", request.getParameter("blacklistRemarks"));
			checkFaceService.saveCheckFace(checkFace, actTaskParam, processMap, model);
			if ("yes".equals(passFlag) && "0".equals(flag)) {
				Facade.facade.SVServiceMethod(actTaskParam.getApplyNo());
			}
			if ("no".equals(passFlag) && "0".equals(flag)) {
				ApplyRegister applyRegisterGed = new ApplyRegister();
				applyRegisterGed.setApplyNo(actTaskParam.getApplyNo());
				List<ApplyRegister> registerList = applyRegisterService.findList(applyRegisterGed);
				if (!registerList.isEmpty()) {
					applyRegisterGed = registerList.get(0);
				}
				if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegisterGed.getProcDefKey())){
					Map<String, String> paramCheckApprove = Maps.newConcurrentMap();
					paramCheckApprove.put("applyNo", actTaskParam.getApplyNo());
					List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(paramCheckApprove);
					if (checkApproveList.size() >0) {
						CheckApprove checkApprove = checkApproveList.get(0);
						creGuaranteeCompanyService.updateGuranteFeeByApply(checkApprove, actTaskParam.getApplyNo());
					}
				}else if (Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(applyRegisterGed.getProcDefKey())) {
					creGuaranteeCompanyService.updateGuranteFeeByApplyNoUnion(actTaskParam.getApplyNo());
				}
			}
			ajaxView.setSuccess().setMessage("保存面审信息成功！");
			//拒绝或拒绝并加入黑名单，更新冠易贷状态
//			if("no".equals(passFlag) || "black".equals(passFlag)){
//				creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(), GedClient.ged_sbjj,0,null);
//			}
		} catch (Exception e) {
			logger.error("保存面审信息失败!", e);
			ajaxView.setFailed().setMessage("保存面审信息失败！");
		}
		return ajaxView;
	}

}