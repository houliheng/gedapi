package com.resoft.credit.userGroup.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.userGroup.entity.UserGroup;
import com.resoft.credit.userGroup.service.UserGroupService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 群组管理Controller
 * @author 赵华奎
 * @version 2016-07-06
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/userGroup")
public class UserGroupController extends BaseController {
	@Autowired
	private UserGroupService userGroupService;
	@RequestMapping(value = {"list"})
	public String list(UserGroup userGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String id = userGroup.getUserId();
			List<UserGroup> userGroupList = userGroupService.findUserGroupById(userGroup.getUserId());
			List<UserGroup> userAllGroupList = userGroupService.findUserGroup();
			model.addAttribute("userGroupList", userGroupList);
			model.addAttribute("userAllGroupList", userAllGroupList);
			model.addAttribute("userId", userGroup.getUserId());
		} catch (Exception e) {
			logger.error("查询群组失败",e);
			model.addAttribute("message", "查询群组失败，请查看后台信息");
		}
		return "app/credit/sys/template/userGroupList";
	}
	@ResponseBody
	@RequestMapping(value = "saveUser")
	public AjaxView saveUser(UserGroup userGroup,String allyId,String userId,Model model, HttpServletResponse response, HttpServletRequest request) {
		AjaxView rtn = new AjaxView();
		List<String> allyIdList = new ArrayList<String>();
		if(!(StringUtils.isNull(allyId))){
			String [] str1 =allyId.split(",");
			for (String ids:str1){
				allyIdList.add(ids);
			}
		}
		try {
			if(allyIdList.size() == 0){
				userGroupService.deleteUserGroup(userId);
			}else{
				userGroupService.updateUserGroup(allyIdList,userId);
			}
			rtn.setSuccess().setMessage("保存成功！");
		} catch (Exception e) {
			rtn.setSuccess().setMessage("保存失败！");
			logger.error("保存失败", e);
		}
		return rtn;
	}
	
}