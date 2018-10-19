package com.resoft.credit.creOrgMapping.web;

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
import com.resoft.credit.creOrgMapping.entity.CreOrgMapping;
import com.resoft.credit.creOrgMapping.service.CreOrgMappingService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;


@Controller
@RequestMapping(value = "${adminPath}/sy/code")
public class CreOrgMappingController extends BaseController {
	
	@Autowired
	private CreOrgMappingService creOrgMappingService; 
	@ModelAttribute("code")
	public CreOrgMapping get(@RequestParam(required=false) String ids) {
		if (StringUtils.isNotBlank(ids)){
			return creOrgMappingService.get(ids);
		}else{
			return new CreOrgMapping();
		}
	}
	/**
	 * 机构对应关系的添加
	 * @param creOrgMapping
	 * @param model
	 * @param redirectAttributes
	 * @param name
	 * @return
	 */
	@RequiresPermissions("sys:code:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView  save(CreOrgMapping creOrgMapping, Model model, RedirectAttributes redirectAttributes,@RequestParam("parent.name") String name){
		AjaxView ajaxView = new AjaxView();
		try{
		creOrgMapping.setName(name);
		creOrgMappingService.save(creOrgMapping);
		 ajaxView.setSuccess().setMessage("保存机构映射成功！");
		}catch(Exception e){
			logger.error("保存个人客户登记信息失败！", e);
			ajaxView.setFailed().setMessage("保存机构信息失败！");
			
		}
		return ajaxView;
	}
	
	/**
	 * 验证码值是否存在
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "validate")
	@ResponseBody
	public AjaxView validate(@RequestParam("id") String id){
		AjaxView ajaxView = new AjaxView();
		CreOrgMapping cre = creOrgMappingService.findCode(id);
		if(cre != null){
			return ajaxView.setStatus("1");
		}
		return ajaxView.setStatus("0");
	}
	/**
	 * 展示机构列表
	 * @param creOrgMapping
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list",""})
	public String index(CreOrgMapping creOrgMapping,Model model,HttpServletResponse response,HttpServletRequest request){
		Page<CreOrgMapping> creOrgMappingList =creOrgMappingService.findAllCode(new Page<CreOrgMapping>(request, response), creOrgMapping);
		model.addAttribute("name",request.getParameter("name"));
		model.addAttribute("id",request.getParameter("id"));
		model.addAttribute("page", creOrgMappingList);
		return "modules/sys/orgList";
	}
	/**
	 * 跳转添加表单
	 * @param creOrgMapping
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"creForm"})
	public String creCodeForm(CreOrgMapping creOrgMapping,Model model,@RequestParam(value="id", required=false) String id){
		Office office = new Office();
		model.addAttribute("office",office);
		if(id != null && id != ""){
		CreOrgMapping cre = creOrgMappingService.get(id);
		model.addAttribute("cre",cre);
		}
		
		return "modules/sys/codeForm";
	}
	/**
	 * 修改机构码
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	/*@RequestMapping(value={"edit"})
	@ResponseBody
	public AjaxView edit(CreOrgMapping creOrgMapping,RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		try {
			creOrgMappingService.update(creOrgMapping);
			return ajaxView.setSuccess().setMessage("修改机构码成功！");
		} catch (Exception e) {
			logger.error("修改失败",e);
			return ajaxView.setFailed().setMessage("修改机构码失败！");
		}
	}*/
}