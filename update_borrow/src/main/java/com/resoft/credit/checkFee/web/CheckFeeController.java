package com.resoft.credit.checkFee.web;

import java.math.BigDecimal;

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

import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.checkFee.entity.CheckFee;
import com.resoft.credit.checkFee.entity.CheckFeeVO;
import com.resoft.credit.checkFee.service.CheckFeeService;
import com.resoft.credit.checkFee.service.CheckFeeVOService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 外访费登记Controller
 * 
 * @author yanwanmei
 * @version 2016-02-29
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/checkFee")
public class CheckFeeController extends BaseController {

	@Autowired
	private CheckFeeService checkFeeService;
	@Autowired
	private CheckFeeVOService checkFeeVOService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	@ModelAttribute
	public CheckFee get(@RequestParam(required = false) String id) {
		CheckFee entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkFeeService.get(id);
		}
		if (entity == null) {
			entity = new CheckFee();
		}
		return entity;
	}

	@RequiresPermissions("credit:checkFee:view")
	@RequestMapping(value = { "list", "" })
	public String checkFeeInfo(ActTaskParam actTaskParam, String readOnly, CheckFee checkFee, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		if (StringUtils.isBlank(checkFee.getId())) {
			CheckFee checkFeeTemp = checkFeeService.findByApplyNo(actTaskParam.getApplyNo());
			if (checkFeeTemp != null) {
				checkFee = checkFeeTemp;
			}
		}
		model.addAttribute("checkFee", checkFee);
		return "app/credit/checkFee/checkFeeForm";
	}

	@RequiresPermissions("credit:checkFee:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(CheckFee checkFee,ProcessSuggestionInfo processSuggestionInfo, ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			checkFeeService.save(checkFee);
			if ("submit".equals(request.getParameter("operate"))) {
				actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + checkFee.getDescription(), "提交", null);
				processSuggestionInfo.setUpdateBy(UserUtils.getUser().getUpdateBy());
				processSuggestionInfo.setUpdateDate(UserUtils.getUser().getUpdateDate());
				processSuggestionInfo.setPassFlag("yes");
				processSuggestionInfoService.insertFlag(processSuggestionInfo,actTaskParam.getTaskDefKey());
				rtn.setSuccess().setMessage("提交成功！");
			} else {
				rtn.setSuccess().setMessage("保存成功！");
				addMessage(model, "保存外访费登记成功");
			}
		} catch (Exception e) {
			logger.error("外访费登记失败！", e);
			rtn.setFailed().setMessage("外访费登记失败！");
			addMessage(model, "外访费登记失败！");
		}
		return rtn;
	}

	@RequiresPermissions("credit:checkFee:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckFee checkFee, RedirectAttributes redirectAttributes) {
		checkFeeService.delete(checkFee);
		addMessage(redirectAttributes, "删除外访费登记成功");
		return "redirect:" + Global.getAdminPath() + "/checkFee/checkFee/?repage";
	}

	/**
	 * 新增外访费返还
	 * 
	 * @param applyNo
	 * @param checkFeeId
	 * @param readOnly
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:checkFeeReturn:edit")
	@RequestMapping(value = { "checkFeeReturn" })
	public String checkFeeReturn(String applyNo, String checkFeeId, String readOnly, Model model) {
		CheckFee checkFee = new CheckFee();
		// 1.新增
		if (StringUtils.isNotBlank(applyNo)) {
			readOnly = "1";
			checkFee.setApplyNo(applyNo);
		}
		// 2.修改
		if (StringUtils.isNotBlank(checkFeeId)) {
			checkFee = checkFeeService.get(checkFeeId);
			if (checkFee == null) {
				checkFee = new CheckFee();
				readOnly = "1";
				checkFee.setApplyNo(applyNo);
			}
		}
		model.addAttribute("checkFee", checkFee);
		return "app/credit/checkFee/checkFeeReturnForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:checkFeeReturn:edit")
	@RequestMapping(value = "saveReturn")
	public AjaxView saveReturn(CheckFee checkFee) {
		AjaxView ajaxView = new AjaxView();
		BigDecimal allCheckFee = checkFee.getCheckFee();
		int fee = allCheckFee.compareTo(new BigDecimal(checkFee.getReturnCheckFee()));
		if (fee == -1) {
			ajaxView.setFailed().setMessage("返还金额过大，请重新输入");
		} else {
			try {
				checkFeeService.save(checkFee);
				ajaxView.setSuccess().setMessage("保存外访费返还信息成功！");
			} catch (Exception e) {
				logger.error("保存外访费返还信息失败！", e);
				ajaxView.setFailed().setMessage("保存外访费返还信息失败！");
			}
		}
		return ajaxView;
	}

	/**
	 * 加载外访费返还列表Index
	 * 
	 * @param applyNo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:checkFeeReturn:view")
	@RequestMapping(value = { "checkFeeReturnIndex", "" })
	public String checkFeeReturnIndex(ActTaskParam actTaskParam, String readOnly, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/checkFee/checkFeeReturnIndex";
	}

	/**
	 * 外访费返还列表
	 * 
	 * @param contract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:checkFeeReturn:view")
	@RequestMapping(value = { "checkFeeReturnList", "" })
	public String checkFeeReturn(CheckFeeVO checkFeeVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 模糊查询条件
		/*
		 * Map<String, String> params = new HashMap<String, String>();
		 * params.put("applyStatus", Constants.APPLY_STATUS_REFUSED);
		 * params.put("listStatus", Constants.BLACKLIST_STATUS_HMD); if
		 * (checkFeeVO != null) { String custName = checkFeeVO.getCustName();
		 * String contractNo = checkFeeVO.getContractNo(); //
		 * 查询在任何一个环节被拒绝或拒件并加入黑名单的客户 if (StringUtils.isNotBlank(custName)) {
		 * params.put("custName", custName); } if
		 * (StringUtils.isNotBlank(contractNo)) { params.put("contractNo",
		 * contractNo); } }
		 * 
		 * String loginId = UserUtils.getUser().getId(); params.put("loginId",
		 * loginId); List<CheckFeeVO> checkFeeVOList =
		 * checkFeeService.findCheckFeeVOList(params); Page<CheckFeeVO> page =
		 * new Page<CheckFeeVO>(); model.addAttribute("page", page);
		 * page.setList(checkFeeVOList);
		 */
		checkFeeVO.setApplyStatus(Constants.APPLY_STATUS_REFUSED);
		String loginId = UserUtils.getUser().getId(); 
		checkFeeVO.setLoginId(loginId);
		if(Constants.ADMIN_USER_ID.equalsIgnoreCase(UserUtils.getUser().getId())){
			MyMap paramMap = new MyMap();
			paramMap.put("applyStatus", Constants.APPLY_STATUS_REFUSED);
			paramMap.put("custName", checkFeeVO.getCustName());
			paramMap.put("contractNo", checkFeeVO.getContractNo());
			Page<MyMap> page = checkFeeVOService.findAllPage(new Page<MyMap>(request, response), paramMap);
			model.addAttribute("page", page);
		}else{
			Page<CheckFeeVO> page = checkFeeVOService.findPage(new Page<CheckFeeVO>(request, response), checkFeeVO);
			model.addAttribute("page", page);
		}
		return "app/credit/checkFee/checkFeeReturnList";
	}

	@RequiresPermissions("credit:creditViewBook:view")
	@RequestMapping(value = "creditViewBook")
	public String creditViewBook(CheckFee checkFee, HttpServletRequest request, HttpServletResponse response, Model model) {
		checkFee = checkFeeService.findByApplyNo(checkFee.getApplyNo());
		if (checkFee == null) {
			checkFee = new CheckFee();
		}
		model.addAttribute("checkFee", checkFee);
		return "app/credit/creditViewBook/checkFeeForCreditForm";
	}

}