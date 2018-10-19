package com.resoft.credit.stockTaskReceive.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.common.utils.Constants;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.resoft.credit.stockTaskReceive.service.StockTaskReceiveService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 股权任务接收表Controller
 * @author jml
 * @version 2017-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/stockTaskReceive")
public class StockTaskReceiveController extends BaseController {

	@Autowired
	private StockTaskReceiveService stockTaskReceiveService;
	
	@ModelAttribute
	public StockTaskReceive get(@RequestParam(required=false) String id) {
		StockTaskReceive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = stockTaskReceiveService.get(id);
		}
		if (entity == null){
			entity = new StockTaskReceive();
		}
		return entity;
	}
		
	
	// 办理(增加签收功能)
	@RequestMapping(value = "doBusiness/{procDefId}/{execId}/{taskDefKey}/{applyNo}/{isDone}")
	public @ResponseBody Map<String, String> doBusiness(@PathVariable("procDefId") String procDefId,@PathVariable("isDone") String isDone,
			@PathVariable("taskDefKey") String taskDefKey, HttpServletResponse response, String taskId,String procInstId,@PathVariable("applyNo") String applyNo,String stockTaskReceiveId)
			throws Exception {
		//StockTaskReceive st = stockTaskReceiveService.get(taskId);
		
		StockTaskReceive stockTaskReceive = stockTaskReceiveService.getById(stockTaskReceiveId);
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put("location", "/credit/workflow/stockRevice");
		msgMap.put("flag", "success");
		/*String grade="";
		if("utask_fgsfksh".equals(taskDefKey)||"utask_fgsjlsh".equals(taskDefKey)){
			grade="4"; //分公司
		}
		if("utask_qyfksh".equals(taskDefKey)||"utask_qyjlsh".equals(taskDefKey)){
			grade="3"; 		
		}
		if("utask_dqfkzysh".equals(taskDefKey)||"utask_dqfkjlsh".equals(taskDefKey)){
			grade="2"; 
		}
		if("utask_zgsfksh".equals(taskDefKey)||"utask_zgsjlsh".equals(taskDefKey)){
			grade="1"; 
		}
		StockTaskReceive stockTaskReceive = stockTaskReceiveService.getReceiveByApplyNoAndGrade(applyNo, grade);*/
		if("1".equals(stockTaskReceive.getStatus())){//已经完成在已办列表可以打开
			if("1".equals(isDone)){//已经完成在已办列表
				msgMap.put("open", "0");
			}else{//已经完成在未办列表
				msgMap.put("open", "1");
			}
		}else{
			msgMap.put("open", "0");
		}
		return msgMap;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(StockTaskReceive stockTaskReceive, HttpServletRequest request, HttpServletResponse response, Model model) {
		String isDone = request.getParameter("isDone");
		if (!Constants.ADMIN_USER_ID.equalsIgnoreCase(UserUtils.getUser().getId())) {
			stockTaskReceive.setReceiver(UserUtils.getUser().getId());
		}
		if(StringUtils.isNotEmpty(isDone)){//已经完成
			stockTaskReceive.setIsDone(isDone);
			model.addAttribute("isDone", isDone);
		}else{//未完成
			stockTaskReceive.setIsDone("0");
			model.addAttribute("isDone", "0");
		}
		Page<StockTaskReceive> page = stockTaskReceiveService.findCustomPage(new Page<StockTaskReceive>(request, response), stockTaskReceive); 
		model.addAttribute("page", page);
		model.addAttribute("headUrl", "/credit/stockTaskReceive/list");
		return "app/credit/stockTaskReceive/stockTaskReceiveList";
	}

	@RequestMapping(value = "form")
	public String form(StockTaskReceive stockTaskReceive, Model model) {
		model.addAttribute("stockTaskReceive", stockTaskReceive);
		return "app/credit/stockTaskReceive/stockTaskReceiveForm";
	}

	@RequestMapping(value = "save")
	public String save(StockTaskReceive stockTaskReceive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, stockTaskReceive)){
			return form(stockTaskReceive, model);
		}
		stockTaskReceiveService.save(stockTaskReceive);
		addMessage(redirectAttributes, "保存股权任务接收表成功");
		return "redirect:"+Global.getAdminPath()+"/stockTaskReceive/stockTaskReceive/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(StockTaskReceive stockTaskReceive, RedirectAttributes redirectAttributes) {
		stockTaskReceiveService.delete(stockTaskReceive);
		addMessage(redirectAttributes, "删除股权任务接收表成功");
		return "redirect:"+Global.getAdminPath()+"/stockTaskReceive/stockTaskReceive/?repage";
	}

}