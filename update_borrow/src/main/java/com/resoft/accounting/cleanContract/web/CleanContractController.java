package com.resoft.accounting.cleanContract.web;

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

import com.resoft.accounting.cleanContract.entity.CleanContract;
import com.resoft.accounting.cleanContract.service.CleanContractService;
import com.resoft.accounting.common.utils.AjaxView;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 数据处理Controller
 * @author wangguodong
 * @version 2017-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/accounting/cleanContract")
public class CleanContractController extends BaseController {

	@Autowired
	private CleanContractService cleanContractService;
	
	@ModelAttribute
	public CleanContract get(@RequestParam(required=false) String id) {
		CleanContract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cleanContractService.get(id);
		}
		if (entity == null){
			entity = new CleanContract();
		}
		return entity;
	}
	
	@RequiresPermissions("accounting:cleanContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(CleanContract cleanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CleanContract> page = null;
		if(StringUtils.isNotEmpty(cleanContract.getContractNo())){
			 page = cleanContractService.findPage(new Page<CleanContract>(request, response), cleanContract); 
		}
		model.addAttribute("page", page);
		return "app/accounting/cleanContract/cleanContractList";
	}

	@RequiresPermissions("accounting:cleanContract:view")
	@RequestMapping(value = "form")
	public String form(CleanContract cleanContract, Model model) {
		cleanContract = cleanContractService.findCleanContractByContractNoAndPeriodNum(cleanContract.getContractNo(),cleanContract.getPeriodNum());
		model.addAttribute("cleanContract", cleanContract);
		return "app/accounting/cleanContract/cleanContractForm";
	}

	@RequiresPermissions("accounting:cleanContract:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(CleanContract cleanContract) {
		AjaxView ajaxView= new AjaxView();
		try {
			if(StringUtils.isNull(cleanContract.getPeriodNum())){
				cleanContract = cleanContractService.findCleanContractByContractNoAndPeriodNum(cleanContract.getContractNo(),null);
				cleanContractService.save(cleanContract);
			}else{
				cleanContractService.updateCleanContract(cleanContract);
			}
			ajaxView.setSuccess().setMessage("操作成功");
		} catch (Exception e) {
			logger.error("操作失败",e);
			ajaxView.setFailed().setMessage("操作失败");
		}
		return ajaxView;
	}
	
	@RequiresPermissions("accounting:cleanContract:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxView delete(CleanContract cleanContract) {
		AjaxView ajaxView= new AjaxView();
		try {
			cleanContractService.delete(cleanContract);
			ajaxView.setSuccess().setMessage("操作成功");
		} catch (Exception e) {
			logger.error("操作失败",e);
			ajaxView.setFailed().setMessage("操作失败");
		}
		return ajaxView;
	}
	

	@RequiresPermissions("accounting:cleanContract:edit")
	@RequestMapping(value = "clean")
	@ResponseBody
	public AjaxView clean(CleanContract cleanContract) {
		AjaxView ajaxView= new AjaxView();
		try {
			cleanContractService.clean(cleanContract);
			ajaxView.setSuccess().setMessage("操作成功");
		} catch (Exception e) {
			logger.error("操作失败",e);
			ajaxView.setFailed().setMessage("操作失败");
		}
		return ajaxView;
	}


}