package com.resoft.credit.noEntry.web;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.credit.noEntry.entity.UserNoEntry;
import com.resoft.credit.noEntry.service.UserNoEntryService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 人员禁件表Controller
 * @author lirongchao
 * @version 2016-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/userNoEntry/")
public class UserNoEntryController extends BaseController {

	@Autowired
	private UserNoEntryService userNoEntryService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public UserNoEntry get(@RequestParam(required=false) String id) {
		UserNoEntry entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userNoEntryService.get(id);
		}
		if (entity == null){
			entity = new UserNoEntry();
		}
		return entity;
	}
	/**
	 * @reqno: H1512260023
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z : cre_user_no_entry : id
	 * @date-author:2016年01月13日-lirongchao:人员禁件列表页面，根据查询条件查询人员禁件列表信息
	 * 										登录名【模糊查询】、姓名【模糊查询】、所属机构
	 */		
	@RequiresPermissions("usernoentry:userNoEntry:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserNoEntry userNoEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserNoEntry> page = userNoEntryService.findPage(new Page<UserNoEntry>(request, response), userNoEntry); 
		model.addAttribute("page", page);
		return "app/credit/noEntry/userNoEntryList";
	}

	@RequiresPermissions("usernoentry:userNoEntry:view")
	@RequestMapping(value = "form")
	public String form(UserNoEntry userNoEntry, Model model) {
		model.addAttribute("userNoEntry", userNoEntry);
		return "app/credit/noEntry/userNoEntryForm";
	}
	/**
	 * @reqno: H1512260024
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z : cre_user_no_entry : userid
	 * @date-author:2016年01月13日-lirongchao:人员禁件列表设置页面，点击保存之后，后台进行保存操作
	 */	
	@RequiresPermissions("usernoentry:userNoEntry:edit")
	@RequestMapping(value = "save")
	public String save(UserNoEntry userNoEntry,String idsArr, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userNoEntry)){
			return form(userNoEntry, model);
		}
		userNoEntryService.save(userNoEntry,idsArr);
		addMessage(redirectAttributes, "保存人员禁件成功");
		return "redirect:"+Global.getAdminPath()+"/credit/userNoEntry/?repage";
	}
	/**
	 * @reqno: H1512260024
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z : cre_user_no_entry : id
	 * @date-author:2016年01月13日-lirongchao:批量删除人员禁件列表
	 */		
	@RequiresPermissions("usernoentry:userNoEntry:edit")
	@RequestMapping(value = "delete")
	public String delete(UserNoEntry userNoEntry, RedirectAttributes redirectAttributes) {
		String ids = userNoEntry.getId();
		List<String> idList = new ArrayList<String>();
		if(!StringUtils.isEmpty(ids)){
			for(String id: ids.split(",")){
				idList.add(id);
			}
			userNoEntryService.batchDelete(idList);
		}
		addMessage(redirectAttributes, "删除成功!");
		return "redirect:"+Global.getAdminPath()+"/credit/userNoEntry/?repage";
	}
	/**
	 * @reqno: H1512260024
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z : sys_office : id
	 * @db-z : cre_user_no_entry : login_name
	 * @db-j : sys_user : id
	 * @db-j : sys_office : id
	 * @date-author:2016年01月13日-lirongchao:跳转到人员禁件设置页面
	 * 										获取所有机构列表officeList
	 * 										获取已添加禁件人员列表userNoEntryList
	 */	
	@RequiresPermissions("usernoentry:userNoEntry:edit")
	@RequestMapping(value = "installUserNoEntry")
	public String installUserNoEntry(Model model) {
		List<Office> officeList = officeService.findAll();
		for(int i = 0;i < officeList.size();){
			//（1：机构；2：部门；3：小组；4：其他）
			if(!"1".equals(officeList.get(i).getType())){
				officeList.remove(i);
			}
			else {
				i++;
			}
		}
		model.addAttribute("officeList", officeList);
		model.addAttribute("userNoEntryList", userNoEntryService.findList(new UserNoEntry()));
		return "app/credit/noEntry/installUserNoEntry";
	}
	/**
	 * @reqno: H1512260024
	 * @date-designer:2016年01月13日-lirongchao
	 * @db-z : sys_user : office_id
	 * @date-author:2016年01月13日-lirongchao:根据部门编号officeId获取用户列表
	 */
	@RequiresPermissions("usernoentry:userNoEntry:edit")
	@ResponseBody
	@RequestMapping(value = "users")
	public List<Map<String, Object>> users(String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = new User();
		user.setOffice(new Office(officeId));
		Page<User> page = systemService.findUser(new Page<User>(1, -1), user);
		for (User e : page.getList()) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("loginName", e.getLoginName());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}
}