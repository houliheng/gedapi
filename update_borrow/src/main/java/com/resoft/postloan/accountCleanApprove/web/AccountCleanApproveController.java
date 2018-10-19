package com.resoft.postloan.accountCleanApprove.web;

import java.util.ArrayList;
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

import com.resoft.postloan.accountCleanApprove.entity.AccountCleanApprove;
import com.resoft.postloan.accountCleanApprove.service.AccountCleanApproveService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 账务清收审批Controller
 * @author yanwanmei
 * @version 2016-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/accountCleanApprove")
public class AccountCleanApproveController extends BaseController {

	@Autowired
	private AccountCleanApproveService accountCleanApproveService;
	
	@ModelAttribute
	public AccountCleanApprove get(@RequestParam(required=false) String id) {
		AccountCleanApprove entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountCleanApproveService.get(id);
		}
		if (entity == null){
			entity = new AccountCleanApprove();
		}
		return entity;
	}
	
	@RequiresPermissions("postloan:accountCleanApprove:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccountCleanApprove accountCleanApprove, HttpServletRequest request, HttpServletResponse response, Model model,String status) {
		try {
			MyMap paramMap = new MyMap();
			User user = UserUtils.getUser();
			List<String> checkResultList = new ArrayList<String>();
			List<String> hqCheckResultList = new ArrayList<String>();
			paramMap.put("contractNo", accountCleanApprove.getContractNo());

			if("check".equalsIgnoreCase(status)){
				checkResultList.add(Constants.TO_BE_CHECKED);
				paramMap.put("checkResultList", checkResultList);
			}
			if("apply".equalsIgnoreCase(status)){
				paramMap.put("applyById", user.getId());
				if(StringUtils.isNotEmpty(accountCleanApprove.getCheckResult())){
					checkResultList.add(accountCleanApprove.getCheckResult());
					paramMap.put("checkResultList", checkResultList);
				}
			}
			if("hasChecked".equalsIgnoreCase(status)){
				checkResultList.add(Constants.PASS_THROUGH);
				checkResultList.add(Constants.BEAT_BACK);
				paramMap.put("checkResultList", checkResultList);
			}
			if("hqCheck".equalsIgnoreCase(status)){
				hqCheckResultList.add(Constants.TO_BE_CHECKED);
				paramMap.put("hqCheckResultList", hqCheckResultList);
			}
			if("hasHqChecked".equalsIgnoreCase(status)){
				hqCheckResultList.add(Constants.PASS_THROUGH);
				hqCheckResultList.add(Constants.BEAT_BACK);
				paramMap.put("hqCheckResultList", hqCheckResultList);
			}
			Page page = accountCleanApproveService.getAccountCleanApproveList(new Page<MyMap>(request, response), paramMap);
			
			model.addAttribute("page", page);
			model.addAttribute("status", status);
		} catch (Exception e) {
			logger.error("查询清收审批信息失败", e);
		}
		return "app/postloan/accountCleanApprove/accountCleanApproveList";
	}

	@RequiresPermissions("postloan:accountCleanApprove:view")
	@RequestMapping(value = "form")
	public String form(AccountCleanApprove accountCleanApprove, Model model,String status) {
		try {
			User user = UserUtils.getUser();
			if("apply".equalsIgnoreCase(status)){
				accountCleanApprove.setApplyById(user.getId());
				accountCleanApprove.setApplyBy(user.getName());
				accountCleanApprove.setCheckResult(Constants.TO_BE_CHECKED);
			}else if("check".equalsIgnoreCase(status)){
				accountCleanApprove = accountCleanApproveService.get(accountCleanApprove.getId());
				accountCleanApprove.setCheckedBy(user.getName());
				accountCleanApprove.setCheckedById(user.getId());
			}else if("hqCheck".equalsIgnoreCase(status)){
				accountCleanApprove = accountCleanApproveService.get(accountCleanApprove.getId());
				accountCleanApprove.setHqCheckedBy(user.getName());
				accountCleanApprove.setHqCheckedById(user.getId());
			}
		} catch (Exception e) {
			logger.error("清收识别'申请'与'审批'的标识参数传递错误！", e);
		}
		model.addAttribute("accountCleanApprove", accountCleanApprove);
		model.addAttribute("status", status);
		return "app/postloan/accountCleanApprove/accountCleanApproveForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:accountCleanApprove:view")
	@RequestMapping(value = "save")
	public AjaxView save(String contractNo,AccountCleanApprove accountCleanApprove,String status) {
		AjaxView ajaxView = new AjaxView();
		try {
			if("apply".equalsIgnoreCase(status)){
				accountCleanApprove.setApplyDate(DateUtils.getCurrDateTime());
			}else if("check".equalsIgnoreCase(status)){
				accountCleanApprove.setCheckDate(DateUtils.getCurrDateTime());
			}else if("hqCheck".equalsIgnoreCase(status)){
				accountCleanApprove.setHqCheckDate(DateUtils.getCurrDateTime());
			}
			String legalFlag = accountCleanApproveService.save(accountCleanApprove,status);
			ajaxView.setSuccess().setMessage("清收任务提交成功");
			ajaxView.setData(status);
			ajaxView.put("legalFlag", legalFlag);
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("清收任务提交失败");
			logger.error("清收任务提交失败", e);
		}
		
		return ajaxView;
	}	
	@RequiresPermissions("postloan:accountCleanApprove:edit")
	@RequestMapping(value = "delete")
	public String delete(AccountCleanApprove accountCleanApprove, RedirectAttributes redirectAttributes) {
		accountCleanApproveService.delete(accountCleanApprove);
		addMessage(redirectAttributes, "删除账务清收审批成功");
		return "redirect:"+Global.getAdminPath()+"/AccountCleanApprove/accountCleanApprove/?repage";
	}

}