package com.resoft.postloan.overdueHandle.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.overdueHandle.entity.OverdueHandleConclusion;
import com.resoft.postloan.overdueHandle.service.OverdueHandleConclusionService;
import com.resoft.postloan.taskCenter.entity.ActTaskParam;

/**
 * 逾期处理流程意见Controller
 * @author lixing
 * @version 2017-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/overdueHandleConclusion")
public class OverdueHandleConclusionController extends BaseController {

	@Autowired
	private OverdueHandleConclusionService overdueHandleConclusionService;
	@Autowired
	private ActTaskService actTaskService;
	
	@ModelAttribute
	public OverdueHandleConclusion get(@RequestParam(required=false) String id) {
		OverdueHandleConclusion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = overdueHandleConclusionService.get(id);
		}
		if (entity == null){
			entity = new OverdueHandleConclusion();
		}
		return entity;
	}
	
	@RequiresPermissions("postloan:overdueHandleConclusion:view")
	@RequestMapping(value = {"list", ""})
	public String list(OverdueHandleConclusion overdueHandleConclusion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OverdueHandleConclusion> page = overdueHandleConclusionService.findCustomPage(new Page<OverdueHandleConclusion>(request, response), overdueHandleConclusion); 
		model.addAttribute("page", page);
		return "app/postloan/overdueHandle/overdueHandleConclusionList";
	}

	@RequiresPermissions("postloan:overdueHandleConclusion:view")
	@RequestMapping(value = "form")
	public String form(OverdueHandleConclusion overdueHandleConclusion, Model model) {
		model.addAttribute("overdueHandleConclusion", overdueHandleConclusion);
		return "app/postloan/overdueHandle/overdueHandleConclusionForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:overdueHandleConclusion:edit")
	@RequestMapping(value = "save")
	public AjaxView save(OverdueHandleConclusion overdueHandleConclusion,ActTaskParam actTaskParam, HttpServletRequest request, HttpServletResponse response, Model model) {
		String passFlag = request.getParameter("passFlag");
		try {
			String contractNo = URLDecoder.decode(overdueHandleConclusion.getContractNo(), "UTF-8");
			overdueHandleConclusion.setContractNo(contractNo);
		} catch (UnsupportedEncodingException e) {
			logger.error("汉字解码失败", e);
		}
		AjaxView ajaxView = new AjaxView();
		overdueHandleConclusion.setTaskDefKey(actTaskParam.getTaskDefKey());
		overdueHandleConclusionService.save(overdueHandleConclusion);
		if ("yes".equals(passFlag)) {
			actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + overdueHandleConclusion.getConclusionDesc(), "提交", null);
		}else if ("back".equals(passFlag)) {
			actTaskService.backOnANode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【打回】" + overdueHandleConclusion.getConclusionDesc());
		}else if("yesToFinish".equals(passFlag)){
			//结清结束流程
			actTaskService.finishProcessInstance(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【结束】" + overdueHandleConclusion.getConclusionDesc());
		} 
		ajaxView.setSuccess().setMessage("任务处理成功！");
		return ajaxView;
	}
	
	@RequiresPermissions("postloan:overdueHandleConclusion:edit")
	@RequestMapping(value = "delete")
	public String delete(OverdueHandleConclusion overdueHandleConclusion, RedirectAttributes redirectAttributes) {
		overdueHandleConclusionService.delete(overdueHandleConclusion);
		addMessage(redirectAttributes, "删除逾期处理流程意见成功");
		return "redirect:"+Global.getAdminPath()+"/overdueHandle/overdueHandleConclusion/?repage";
	}

}