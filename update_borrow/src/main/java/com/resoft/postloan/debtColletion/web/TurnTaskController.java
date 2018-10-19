package com.resoft.postloan.debtColletion.web;

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

import com.resoft.postloan.debtColletion.entity.TurnTask;
import com.resoft.postloan.debtColletion.service.TurnTaskService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 转办已审核Controller
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/doneTurnTask")
public class TurnTaskController extends BaseController {

	@Autowired
	private TurnTaskService turnTaskService;

	@ModelAttribute
	public TurnTask get(@RequestParam(required = false) String id) {
		TurnTask entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = turnTaskService.get(id);
		}
		if (entity == null) {
			entity = new TurnTask();
		}
		return entity;
	}

	@RequiresPermissions("postloan:debtCollection:view")
	@RequestMapping(value = "detailList")
	public String list(TurnTask turnTask, HttpServletRequest request, String readOnly, HttpServletResponse response, Model model) {
		try {
			List<TurnTask> turnTaskList = turnTaskService.getTurnTaskDetailList(turnTask);
			model.addAttribute("turnTaskList", turnTaskList);
			model.addAttribute("turnTask", turnTask);
			model.addAttribute("readOnly", readOnly);
		} catch (Exception e) {
			logger.error("数据出现问题", e);
		}
		return "app/postloan/turnTask/doneDebtCollectionTurnTaskDetailList";
	}

	@RequiresPermissions("postloan:debtCollection:view")
	@RequestMapping(value = "/list/{turnAfter}")
	public String detailList(TurnTask turnTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<TurnTask> pageTemp = turnTaskService.findPage(new Page<TurnTask>(request, response), turnTask);
			Page<TurnTask> page = turnTaskService.getUserNames(pageTemp);
			model.addAttribute("page", page);
			model.addAttribute("turnTask", turnTask);
			model.addAttribute("turnAfter", turnTask.getTurnAfter());
		} catch (Exception e) {
			logger.error("数据出现问题", e);
		}
		return "app/postloan/turnTask/doneDebtCollectionTurnTaskList";
	}

}