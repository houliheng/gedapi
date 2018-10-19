package com.resoft.postloan.checkTwentyFiveInfo.web;

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

import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfo;
import com.resoft.postloan.checkTwentyFiveInfo.entity.CheckTwentyFiveInfoVO;
import com.resoft.postloan.checkTwentyFiveInfo.service.CheckTwentyFiveInfoService;
import com.resoft.postloan.common.utils.AjaxView;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 25日复核信息Controller
 * @author admin
 * @version 2016-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/checkTwentyFiveInfo")
public class CheckTwentyFiveInfoController extends BaseController {

	@Autowired
	private CheckTwentyFiveInfoService checkTwentyFiveInfoService;
	
	@ModelAttribute
	public CheckTwentyFiveInfo get(@RequestParam(required=false) String id) {
		CheckTwentyFiveInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkTwentyFiveInfoService.get(id);
		}
		if (entity == null){
			entity = new CheckTwentyFiveInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("postloan:checkTwentyFiveInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckTwentyFiveInfo checkTwentyFiveInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckTwentyFiveInfo> page = checkTwentyFiveInfoService.findCustomPage(new Page<CheckTwentyFiveInfo>(request, response), checkTwentyFiveInfo); 
		model.addAttribute("page", page);
		return "app/postloan/checkTwentyFiveInfo/checkTwentyFiveInfoList";
	}

	@RequiresPermissions("postloan:checkTwentyFiveInfo:view")
	@RequestMapping(value = "form")
	public String form(CheckTwentyFiveInfo checkTwentyFiveInfo, Model model) {
		model.addAttribute("checkTwentyFiveInfo", checkTwentyFiveInfo);
		return "app/postloan/checkTwentyFiveInfo/checkTwentyFiveInfoForm";
	}

	@RequiresPermissions("postloan:checkTwentyFiveInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckTwentyFiveInfo checkTwentyFiveInfo, RedirectAttributes redirectAttributes) {
		checkTwentyFiveInfoService.delete(checkTwentyFiveInfo);
		addMessage(redirectAttributes, "删除25日复核信息成功");
		return "redirect:"+Global.getAdminPath()+"/checkTwentyFiveInfo/checkTwentyFiveInfo/?repage";
	}

	@ResponseBody
	@RequiresPermissions("postloan:checkTwentyFive:edit")
	@RequestMapping(value = "save")
	public AjaxView save(CheckTwentyFiveInfoVO checkTwentyFiveInfoVO,String contractNo,String allocateId,String procedure) {
		AjaxView ajaxView = new AjaxView();
		String flag = null;
		try {
			flag = checkTwentyFiveInfoService.saveCheckTwentyFiveInfo(checkTwentyFiveInfoVO,allocateId,contractNo,procedure);
		} catch (Exception e) {
			logger.error("插入25日复核数据失败", e);
		}
		if("true".equalsIgnoreCase(flag)){
			ajaxView.setSuccess().setMessage("保存25日复核数据成功");
		}else{
			ajaxView.setFailed().setMessage("保存25日复核数据失败");

		}
		return ajaxView;
	}
}