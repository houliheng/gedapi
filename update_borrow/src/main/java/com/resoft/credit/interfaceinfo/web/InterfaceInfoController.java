package com.resoft.credit.interfaceinfo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;

/**
 * 接口日志记录Controller
 * @author wanghong
 * @version 2016-10-31
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/interfaceInfo")
public class InterfaceInfoController extends BaseController {

	@Autowired
	private InterfaceInfoService interfaceInfoService;
	
	@ModelAttribute
	public InterfaceInfo get(@RequestParam(required=false) String id) {
		InterfaceInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = interfaceInfoService.get(id);
		}
		if (entity == null){
			entity = new InterfaceInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:interfaceInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(InterfaceInfo interfaceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InterfaceInfo> page = interfaceInfoService.findCustomPage(new Page<InterfaceInfo>(request, response), interfaceInfo); 
		model.addAttribute("page", page);
		return "app/credit/interfaceinfo/interfaceInfoList";
	}

	@RequiresPermissions("credit:interfaceInfo:view")
	@RequestMapping(value = "form")
	public String form(InterfaceInfo interfaceInfo, Model model) {
		//if("Themis接口".equals(interfaceInfo.getInterfaceName())||"SV服务".equals(interfaceInfo.getInterfaceName())){
		String json = interfaceInfo.getJson();
		json="<xmp>"+json+"</xmp>";
		interfaceInfo.setJson(json);
		//}
		model.addAttribute("interfaceInfo", interfaceInfo);
		return "app/credit/interfaceinfo/interfaceInfoForm";
	}

	@RequiresPermissions("credit:interfaceInfo:edit")
	@RequestMapping(value = "save")
	public String save(InterfaceInfo interfaceInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, interfaceInfo)){
			return form(interfaceInfo, model);
		}
		interfaceInfoService.save(interfaceInfo);
		addMessage(redirectAttributes, "保存接口日志记录成功");
		return "redirect:"+Global.getAdminPath()+"/credit/interfaceInfo/?repage";
	}
	
	@RequiresPermissions("credit:interfaceInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(InterfaceInfo interfaceInfo, RedirectAttributes redirectAttributes) {
		interfaceInfoService.delete(interfaceInfo);
		addMessage(redirectAttributes, "删除接口日志记录成功");
		return "redirect:"+Global.getAdminPath()+"/credit/interfaceInfo/?repage";
	}

}