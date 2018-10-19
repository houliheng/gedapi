package com.resoft.credit.custRemoveBind.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.common.utils.DataScopeFitter;
import com.resoft.credit.custRemoveBind.entity.CustRemoveBind;
import com.resoft.credit.custRemoveBind.service.CustRemoveBindService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/credit/custRemoveBind")
public class CustRemoveBindController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CustRemoveBindController.class);

	@Autowired
	private CustRemoveBindService custRemoveBindService;
	
	@ModelAttribute
	public CustRemoveBind get(@RequestParam(required=false) String id) {
		CustRemoveBind entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = custRemoveBindService.get(id);
		}
		if (entity == null){
			entity = new CustRemoveBind();
		}
		return entity;
	}
	
	//页面查询
	@RequiresPermissions("credit:custRemoveBind:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustRemoveBind custRemoveBind, HttpServletRequest request, HttpServletResponse response, Model model) {
		String userType = request.getParameter("userType");
		custRemoveBind.setUserType(userType);
		if("common".equalsIgnoreCase(userType))
		{
			custRemoveBind.setIsBind("1");
	    }
		DataScopeFitter.companyDataScopeFilter(custRemoveBind);
		Page<CustRemoveBind> page = custRemoveBindService.findPage(new Page<CustRemoveBind>(request, response), custRemoveBind); 
		model.addAttribute("page", page);
		model.addAttribute("userType", userType);
		return "app/credit/custRemoveBind/custRemoveBindList";
	}

	@RequiresPermissions("credit:custRemoveBind:view")
	@RequestMapping(value = "form")
	public String form(CustRemoveBind custRemoveBind, Model model) {
		model.addAttribute("custRemoveBind", custRemoveBind);
		return "app/credit/custRemoveBind/custRemoveBindForm";
	}
	
	//查询所有用户返回这用户选择界面
	@RequiresPermissions("credit:custRemoveBind:edit")
	@RequestMapping("/bindUser")
	public String bindUser(User user, HttpServletRequest request, HttpServletResponse response, Model model){
		String idStr = request.getParameter("id");
		String userType = request.getParameter("userType");
		String id = UserUtils.getUser().getId();
		String currentCompanyId = UserUtils.getUser().getCompany().getId();
		user.setId(id);
		user.getCurrentUser().getCompany().setId(currentCompanyId);
		user.setUserType(userType);
		Page<User> page = custRemoveBindService.findUserPage(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		model.addAttribute("id", idStr);
		model.addAttribute("userType", userType);
		return "app/credit/custRemoveBind/custRemoveBind_bind";
	}
	
	//绑定操作
	@RequiresPermissions("credit:custRemoveBind:edit")
	@RequestMapping("/bind")
	public String bind(CustRemoveBind custRemoveBind, HttpServletRequest request, HttpServletResponse response, Model model){
		try{
			String idStr = request.getParameter("id");
			String [] ids = idStr.split(",");
			String sysuserid=request.getParameter("sysuserid");
			String currentUserId=UserUtils.getUser().getId();//获取当前操作用户
			User user = new User();
			User updateBy = new User();
			custRemoveBind.setUser(user);
			custRemoveBind.setUpdateBy(updateBy);
			custRemoveBind.setIds(ids);
			custRemoveBind.getUser().setId(sysuserid);
			custRemoveBind.getUpdateBy().setId(currentUserId);
			custRemoveBind.setUpdateDate(new Date());
			custRemoveBind.setIsBind("1");
			custRemoveBindService.bind(custRemoveBind);
			model.addAttribute("userType", request.getParameter("userType"));
			return super.renderString(response, "success");
		}catch(Exception e){
			logger.error("绑定操作失败", e);
			return super.renderString(response, "error");
		}
	}
	
	//解绑定操作
	@RequiresPermissions("credit:custRemoveBind:edit")
	@RequestMapping("/bindOff")
	public String bindOff(CustRemoveBind custRemoveBind, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try{
			String idStr = request.getParameter("id");
			String [] ids = idStr.split(",");
			String currentUserId=UserUtils.getUser().getId();//获取当前操作用户
			User user = new User();
			User updateBy = new User();
			custRemoveBind.setUser(user);
			custRemoveBind.setUpdateBy(updateBy);
			custRemoveBind.setIds(ids);
			custRemoveBind.getUser().setId("");
			custRemoveBind.getUpdateBy().setId(currentUserId);
			custRemoveBind.setUpdateDate(new Date());
			custRemoveBind.setIsBind("0");
			custRemoveBindService.bind(custRemoveBind);
			redirectAttributes.addFlashAttribute("type", "success");
			redirectAttributes.addAttribute("userType", request.getParameter("userType"));
			addMessage(redirectAttributes, "解绑定成功");
			return "redirect:" + this.adminPath + "/credit/custRemoveBind";
		}catch(Exception e){
			logger.error("解绑定操作失败", e);
			redirectAttributes.addFlashAttribute("type", "error");
			redirectAttributes.addAttribute("userType", request.getParameter("userType"));
			addMessage(redirectAttributes, "解绑定发生异常");
			return "redirect:" + this.adminPath + "/credit/custRemoveBind";
		}
	}

}