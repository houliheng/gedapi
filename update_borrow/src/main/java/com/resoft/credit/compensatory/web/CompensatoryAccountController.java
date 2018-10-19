package com.resoft.credit.compensatory.web;

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

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.compensatory.dao.CompensatoryAccountDao;
import com.resoft.credit.compensatory.entity.CompensatoryAccount;
import com.resoft.credit.compensatory.service.CompensatoryAccountService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 代偿资金账户Controller
 * @author jml
 * @version 2018-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/compensatoryAccount")
public class CompensatoryAccountController extends BaseController {

	@Autowired
	private CompensatoryAccountService compensatoryAccountService;
	@Autowired
	private CompensatoryAccountDao compensatoryAccountDao;
	
	@ModelAttribute
	public CompensatoryAccount get(@RequestParam(required=false) String id) {
		CompensatoryAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = compensatoryAccountService.get(id);
		}
		if (entity == null){
			entity = new CompensatoryAccount();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(String accountStatus,String id,CompensatoryAccount compensatoryAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CompensatoryAccount> page = compensatoryAccountService.findPage(new Page<CompensatoryAccount>(request, response), compensatoryAccount); 
		/*List<CompensatoryAccount> oldCompensatoryAccountList = page.getList();
		List<CompensatoryAccount> newCompensatoryAccountList = AccFacade.facade.repaymentFindAccount(oldCompensatoryAccountList);
		page.setList(newCompensatoryAccountList);*/
		model.addAttribute("page", page);
		return "app/credit/compensatory/compensatoryAccountList";
	}

	@RequestMapping(value = "form")
	public String form(CompensatoryAccount compensatoryAccount, Model model) {
		model.addAttribute("compensatoryAccount", compensatoryAccount);
		return "app/credit/compensatory/compensatoryAccountForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(CompensatoryAccount compensatoryAccount, Model model, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			
			List<CompensatoryAccount> compensatoryAccountList=compensatoryAccountService.checkDouble(compensatoryAccount);
			if(compensatoryAccountList.size()>0) {
				rtn.setFailed().setMessage("客户编码,或者账户已经存在!");
			}else {
				compensatoryAccount.setAccountStatus("0");
				compensatoryAccount.preInsert();
				compensatoryAccountDao.insert(compensatoryAccount);
				rtn.setSuccess().setMessage("保存成功!");
			}
		} catch (Exception e) {
			logger.error("保存失败！", e);
			rtn.setFailed().setMessage("保存失败!");
		}
		return rtn;
	}
	
	@RequiresPermissions("credit:compensatoryAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(CompensatoryAccount compensatoryAccount, RedirectAttributes redirectAttributes) {
		compensatoryAccountService.delete(compensatoryAccount);
		addMessage(redirectAttributes, "删除代偿资金账户成功");
		return "redirect:"+Global.getAdminPath()+"/compensatory/compensatoryAccount/?repage";
	}
	@ResponseBody
	@RequestMapping(value = "changeStatus")
	public AjaxView changeStatus(String accountStatus,String id,CompensatoryAccount compensatoryAccount,Model model, HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView = new AjaxView();  
		try{
			CompensatoryAccount compensatoryAccountUpdate = compensatoryAccountService.get(id);
			compensatoryAccountUpdate.setAccountStatus(accountStatus);
			compensatoryAccountService.save(compensatoryAccountUpdate);
			ajaxView.setSuccess().setMessage("修改成功！");
		}catch(Exception e){
			logger.error("保存数据错误" + e.getMessage(), e);
			ajaxView.setFailed().setMessage("修改错误!");
		}
		return ajaxView;
	}
}