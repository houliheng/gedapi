package com.resoft.credit.contactInfo.web;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.contactInfo.entity.ContactInfo;
import com.resoft.credit.contactInfo.service.ContactInfoService;

/**
 * 联系人信息Controller
 * @author chenshaojia
 * @version 2016-03-11
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/contactInfo")
public class ContactInfoController extends BaseController {

	@Autowired
	private ContactInfoService contactInfoService;
	
	@ModelAttribute
	public ContactInfo get(@RequestParam(required=false) String id) {
		ContactInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contactInfoService.get(id);
		}
		if (entity == null){
			entity = new ContactInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:custinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContactInfo contactInfo, String applyNo,HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ContactInfo> contactList = contactInfoService.getContactListByRelation(contactInfo);
		model.addAttribute("contactList", contactList);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/custinfo/contactInfoList";
	}
	
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "form")
	public String form(ContactInfo contactInfo,String applyNo ,Model model,String readOnly) {
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("contactInfo", contactInfo);
		return "app/credit/custinfo/contactInfoForm";
	}
	@ResponseBody
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "save")
	public AjaxView save(ContactInfo contactInfo,String applyNo,Model model, String readOnly) {
		AjaxView rtn = new AjaxView();
		try {
			contactInfoService.save(contactInfo);
			rtn.setSuccess().setMessage("保存联系人成功！");
		} catch (Exception e) {
			logger.error("保存联系人失败！", e);
			rtn.setFailed().setMessage("保存联系人失败！");
		}
		return rtn;
	}
	
	@ResponseBody
	@RequiresPermissions("credit:custinfo:delete")
	@RequestMapping(value = "delete")
	public AjaxView delete(@RequestParam("ids")List<String> idList) {
		AjaxView rtn = new AjaxView();
		try {
			contactInfoService.batchDelete(idList);;
			rtn.setSuccess().setMessage("删除联系人成功!");
		} catch (Exception e) {
			logger.error("删除联系人失败", e);
			rtn.setFailed().setMessage("删除联系人失败!");
		}
		return rtn;
	}

}