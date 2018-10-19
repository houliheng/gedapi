package com.resoft.postloan.checkIndeed.web;

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
import com.resoft.postloan.checkIndeed.entity.CheckIndeed;
import com.resoft.postloan.checkIndeed.service.CheckIndeedService;
import com.resoft.postloan.common.utils.AjaxView;

/**
 * 借后实地外访Controller
 * 
 * @author wangguodong
 * @version 2016-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/checkIndeed")
public class CheckIndeedController extends BaseController {

	@Autowired
	private CheckIndeedService checkIndeedService;

	@ModelAttribute
	public CheckIndeed get(@RequestParam(required = false) String id) {
		CheckIndeed entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkIndeedService.get(id);
		}
		if (entity == null) {
			entity = new CheckIndeed();
		}
		return entity;
	}

	@RequiresPermissions("postloan:checkIndeed:view")
	@RequestMapping(value = "index")
	public String index(CheckIndeed checkIndeed, Model model) {
		model.addAttribute("contractNo", checkIndeed.getContractNo());
		model.addAttribute("allocateId", checkIndeed.getAllocateId());
		return "app/postloan/checkIndeed/checkIndeedIndex";
	}

	@RequiresPermissions("postloan:checkIndeed:view")
	@RequestMapping(value = { "list", "" })
	public String list(CheckIndeed checkIndeed, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckIndeed> page = checkIndeedService.findCustomPage(new Page<CheckIndeed>(request, response), checkIndeed);
		model.addAttribute("page", page);
		model.addAttribute("checkIndeed", checkIndeed);
		return "app/postloan/checkIndeed/checkIndeedList";
	}

	@RequiresPermissions("postloan:checkIndeed:view")
	@RequestMapping(value = "form")
	public String form(CheckIndeed checkIndeed, Model model) {
		model.addAttribute("checkIndeed", checkIndeed);
		return "app/postloan/checkIndeed/checkIndeedForm";
	}

	@RequiresPermissions("postloan:checkIndeed:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(CheckIndeed checkIndeed, Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			checkIndeedService.save(checkIndeed);
			ajaxView.setSuccess().setMessage("保存外访信息成功");
			ajaxView.put("allocateId", checkIndeed.getAllocateId());
			ajaxView.put("contractNo", checkIndeed.getContractNo());
		} catch (Exception e) {
			logger.error("保存外访信息失败", e);
			ajaxView.setFailed().setMessage("保存外访信息失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("postloan:checkIndeed:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckIndeed checkIndeed, RedirectAttributes redirectAttributes) {
		checkIndeedService.delete(checkIndeed);
		addMessage(redirectAttributes, "删除外访信息成功");
		return "redirect:" + Global.getAdminPath() + "/checkIndeed/checkIndeed/?repage";
	}

}