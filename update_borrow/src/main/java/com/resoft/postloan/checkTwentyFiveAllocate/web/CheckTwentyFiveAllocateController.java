package com.resoft.postloan.checkTwentyFiveAllocate.web;

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

import com.google.common.collect.Lists;
import com.resoft.postloan.check25.service.CheckTwentyFiveService;
import com.resoft.postloan.checkTwentyFiveAllocate.entity.CheckTwentyFiveAllocate;
import com.resoft.postloan.checkTwentyFiveAllocate.service.CheckTwentyFiveAllocateService;
import com.resoft.postloan.common.utils.AjaxView;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 借后25日复核任务分配表Controller
 * 
 * @author yanwanmei
 * @version 2016-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/checkTwentyFiveAllocate")
public class CheckTwentyFiveAllocateController extends BaseController {

	@Autowired
	private CheckTwentyFiveAllocateService checkTwentyFiveAllocateService;
	@Autowired
	private CheckTwentyFiveService checkTwentyFiveService;

	@ModelAttribute
	public CheckTwentyFiveAllocate get(@RequestParam(required = false) String id) {
		CheckTwentyFiveAllocate entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkTwentyFiveAllocateService.get(id);
		}
		if (entity == null) {
			entity = new CheckTwentyFiveAllocate();
		}
		return entity;
	}

	@RequiresPermissions("postloan:checkTwentyFiveAllocate:view")
	@RequestMapping(value = { "list", "" })
	public String list(CheckTwentyFiveAllocate checkTwentyFiveAllocate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckTwentyFiveAllocate> page = checkTwentyFiveAllocateService.findCustomPage(new Page<CheckTwentyFiveAllocate>(request, response), checkTwentyFiveAllocate);
		model.addAttribute("page", page);
		return "app/postloan/checkTwentyFiveAllocate/checkTwentyFiveAllocateList";
	}

	@RequiresPermissions("postloan:checkTwentyFiveAllocate:view")
	@RequestMapping(value = "form")
	public String form(CheckTwentyFiveAllocate checkTwentyFiveAllocate, Model model) {
		model.addAttribute("checkTwentyFiveAllocate", checkTwentyFiveAllocate);
		return "app/postloan/checkTwentyFiveAllocate/checkTwentyFiveAllocateForm";
	}

	@RequiresPermissions("postloan:checkTwentyFiveAllocate:edit")
	@RequestMapping(value = "save")
	public String save(CheckTwentyFiveAllocate checkTwentyFiveAllocate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkTwentyFiveAllocate)) {
			return form(checkTwentyFiveAllocate, model);
		}
		checkTwentyFiveAllocateService.save(checkTwentyFiveAllocate);
		addMessage(redirectAttributes, "保存借后25日复核任务分配表成功");
		return "redirect:" + Global.getAdminPath() + "/checkTwentyFiveAllocate/checkTwentyFiveAllocate/?repage";
	}

	@RequiresPermissions("postloan:checkTwentyFiveAllocate:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckTwentyFiveAllocate checkTwentyFiveAllocate, RedirectAttributes redirectAttributes) {
		checkTwentyFiveAllocateService.delete(checkTwentyFiveAllocate);
		addMessage(redirectAttributes, "删除借后25日复核任务分配表成功");
		return "redirect:" + Global.getAdminPath() + "/checkTwentyFiveAllocate/checkTwentyFiveAllocate/?repage";
	}

	@ResponseBody
	@RequiresPermissions("postloan:checkTwentyFiveAllocate:edit")
	@RequestMapping(value = "saveList")
	public AjaxView saveList(CheckTwentyFiveAllocate allocate, Model model, String dateFlag, HttpServletRequest request, HttpServletResponse response) {
		AjaxView ajaxView = new AjaxView();
		try {
			String contractNos = request.getParameter("contractNos");
			List<String> contractNoList = null;
			List<CheckTwentyFiveAllocate> checkTwentyFiveAllocates = Lists.newArrayList();
			if (StringUtils.isNotEmpty(contractNos)) {
				contractNoList = Arrays.asList(contractNos.split(","));
			}
			for (int i = 0; i < contractNoList.size(); i++) {
				CheckTwentyFiveAllocate checkTwentyFiveAllocate = new CheckTwentyFiveAllocate();
				checkTwentyFiveAllocate.setCheckedById(allocate.getCheckedById());
				checkTwentyFiveAllocate.setCheckedBy(allocate.getCheckedBy());
				checkTwentyFiveAllocate.setAllocateBy(UserUtils.getUser().getName());
				checkTwentyFiveAllocate.setAllocateById(UserUtils.getUser().getId());
				checkTwentyFiveAllocate.setId(IdGen.uuid());
				checkTwentyFiveAllocate.setContractNo(contractNoList.get(i));
				checkTwentyFiveAllocates.add(checkTwentyFiveAllocate);
			}
			checkTwentyFiveAllocateService.saveList(checkTwentyFiveAllocates);
			ajaxView.setSuccess().setMessage("下发任务成功");
		} catch (Exception e) {
			logger.error("查询复核人员失败", e);
			ajaxView.setFailed().setMessage("下发任务失败");
		}
		ajaxView.put("dateFlag", dateFlag);
		return ajaxView;
	}

	// 借后经理下发任务，选择借后专员进行下发
	@RequiresPermissions("postloan:checkTwentyFiveAllocate:view")
	@RequestMapping(value = "taskDown")
	public String taskDown(CheckTwentyFiveAllocate checkTwentyFiveAllocate, Model model, String dateFlag, HttpServletResponse response, HttpServletRequest request, String contractNos) {
		Page<MyMap> page = null;
		try {
			MyMap paramMap = new MyMap();
			paramMap.put("checkUserName", checkTwentyFiveAllocate.getCheckedBy());
			paramMap.put("id", UserUtils.getUser().getId());
			paramMap.put("companyId", UserUtils.getUser().getCompany().getId());
			page = checkTwentyFiveService.getCheckUserInfo(new Page<MyMap>(request, response), paramMap);
		} catch (Exception e) {
			logger.error("查询复核人员失败", e);
		}
		model.addAttribute("page", page);
		model.addAttribute("contractNos", contractNos);
		model.addAttribute("dateFlag", dateFlag);
		return "app/postloan/check25/checkUserList";
	}

	/**
	 * 重新复核
	 */
	@RequiresPermissions("postloan:checkTwentyFiveAllocate:view")
	@RequestMapping(value = "checkedAgain")
	@ResponseBody
	public AjaxView checkedAgain(String contractNo) {
		AjaxView ajaxView = new AjaxView();
		try {
			checkTwentyFiveAllocateService.checkedAgain(contractNo);
			ajaxView.setSuccess().setMessage("修改成功");
		} catch (Exception e) {
			logger.error("修改重新复核标识失败",e);
			ajaxView.setFailed().setMessage("修改失败");
		}
		return ajaxView;
	}
}