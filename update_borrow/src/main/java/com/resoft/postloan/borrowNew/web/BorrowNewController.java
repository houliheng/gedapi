package com.resoft.postloan.borrowNew.web;

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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.google.common.collect.Maps;
import com.resoft.postloan.borrowNew.entity.BorrowNew;
import com.resoft.postloan.borrowNew.service.BorrowNewService;
import com.resoft.postloan.checkDaily.entity.CheckDaily;
import com.resoft.postloan.checkDaily.entity.CheckDailyAllocate;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.common.utils.DateUtils;

/**
 * 借新还旧信息Controller
 * 
 * @author wuxi01
 * @version 2016-06-17
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/borrowNew")
public class BorrowNewController extends BaseController {

	@Autowired
	private BorrowNewService borrowNewService;

	@ModelAttribute
	public BorrowNew get(@RequestParam(required = false) String id) {
		BorrowNew entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = borrowNewService.get(id);
		}
		if (entity == null) {
			entity = new BorrowNew();
		}
		return entity;
	}

	@RequiresPermissions("postloan:borrowNew:view")
	@RequestMapping(value = { "list", "" })
	public String list(BorrowNew borrowNew, HttpServletRequest request, HttpServletResponse response, Model model) {
		borrowNew.setZBOrDQ("DQ");
		borrowNew.setOperateOrgId(UserUtils.getUser().getCompany().getId());
		Page<BorrowNew> page = borrowNewService.findPage(new Page<BorrowNew>(request, response), borrowNew);
		model.addAttribute("page", page);
		model.addAttribute("list", "list");
		// model.addAttribute("tocheck", Constants.BORROW_NEW_STATUS_TOCHECK);
		return "app/postloan/borrowNew/borrowNewList";
	}

	@RequiresPermissions("postloan:borrowNew:view")
	@RequestMapping(value = "ZBlist")
	public String ZBlist(BorrowNew borrowNew, HttpServletRequest request, HttpServletResponse response, Model model) {
		borrowNew.setZBOrDQ("ZB");
		borrowNew.setOperateOrgId(UserUtils.getUser().getCompany().getId());
		Page<BorrowNew> page = borrowNewService.findPage(new Page<BorrowNew>(request, response), borrowNew);
		model.addAttribute("page", page);
		model.addAttribute("list", "ZBlist");
		// model.addAttribute("tocheck", Constants.BORROW_NEW_STATUS_TOCHECK);
		return "app/postloan/borrowNew/borrowNewList";
	}

	@RequiresPermissions("postloan:borrowNew:view")
	@RequestMapping(value = "applyList")
	public String applyList(BorrowNew borrowNew, HttpServletRequest request, HttpServletResponse response, Model model) {
		User loginUser = UserUtils.getUser();
		borrowNew.setApplyBy(loginUser.getLoginName());
		Page<BorrowNew> page = borrowNewService.findPage(new Page<BorrowNew>(request, response), borrowNew);
		model.addAttribute("page", page);
		model.addAttribute("list", "applyList");
		model.addAttribute("noCheck", "true");
		return "app/postloan/borrowNew/borrowNewList";
	}

	@RequiresPermissions("postloan:borrowNew:view")
	@RequestMapping(value = "form")
	public String form(BorrowNew borrowNew, CheckDailyAllocate checkDailyAllocate, String applyFlag, String hiddenResult, Model model) {
		try {
			User loginUser = UserUtils.getUser();
			if ("apply".equals(applyFlag)) {// 申请借新还旧
				borrowNew.setApplyBy(loginUser.getLoginName());
				borrowNew.setApplyById(loginUser.getId());
				borrowNew.setApplyDate(DateUtils.getCurrDateTime());
				borrowNew.setCheckResult(Constants.BORROW_NEW_STATUS_TOCHECK);
				CheckDaily checkDaily = checkDailyAllocate.getCheckDaily();
				checkDaily.setCheckDailyResult(hiddenResult);
				checkDailyAllocate.setCheckDaily(checkDaily);
				model.addAttribute("checkDailyAllocate", checkDailyAllocate);
			} else if ("DQ".equals(applyFlag)) {// 审批借新还旧
				borrowNew = borrowNewService.get(borrowNew.getId());
				borrowNew.setDQcheckedBy(loginUser.getLoginName());
				borrowNew.setDQcheckedById(loginUser.getId());
			} else if ("ZB".equals(applyFlag)) {
				borrowNew = borrowNewService.get(borrowNew.getId());
				borrowNew.setZBcheckedBy(loginUser.getLoginName());
				borrowNew.setZBcheckedById(loginUser.getId());
				borrowNew.setCheckResult(null);
			} else {
				logger.error("借新还旧识别'申请'与'审批'的标识参数传递错误！");
				model.addAttribute("message", "系统参数错误！");
			}
			model.addAttribute("applyFlag", applyFlag);
			model.addAttribute("borrowNew", borrowNew);
			return "app/postloan/borrowNew/borrowNewForm";
		} catch (Exception e) {
			logger.error("申请借新还旧或审批借新还旧出现错误，可能是数据错误！", e);
			model.addAttribute("borrowNew", new BorrowNew());
			return "app/postloan/borrowNew/borrowNewForm";
		}
	}

	@ResponseBody
	@RequiresPermissions("postloan:borrowNew:edit")
	@RequestMapping(value = "save")
	public Map<String, Object> save(BorrowNew borrowNew, CheckDailyAllocate checkDailyAllocate, CheckDaily checkDaily, String applyFlag, Model model) {
		Map<String, Object> resultMap = Maps.newHashMap();
		try {
			checkDailyAllocate.setCheckDaily(checkDaily);
			String flag = borrowNewService.saveBorrowNew(borrowNew, checkDailyAllocate, applyFlag);
			if ("success".equals(flag)) {
				resultMap.put("status", "1");// 成功标识
				resultMap.put("message", "借新还旧信息提交成功！");
			} else {
				resultMap.put("status", "0");// 失败标识
				resultMap.put("message", flag);
			}

		} catch (Exception e) {
			logger.error("借新还旧信息保存失败！", e);
			resultMap.put("status", "0");// 失败标识
			resultMap.put("message", "借新还旧信息提交失败！");
		}
		return resultMap;
	}

	@RequiresPermissions("postloan:borrowNew:edit")
	@RequestMapping(value = "delete")
	public String delete(BorrowNew borrowNew, RedirectAttributes redirectAttributes) {
		borrowNewService.delete(borrowNew);
		addMessage(redirectAttributes, "删除借新还旧信息成功");
		return "redirect:" + Global.getAdminPath() + "/borrowNew/borrowNew/?repage";
	}

}