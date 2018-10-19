package com.resoft.credit.stockAssesseTarget.web;



import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.resoft.credit.stockAssesseTarget.entity.StockAssesseTarget;
import com.resoft.credit.stockAssesseTarget.service.StockAssesseTargetService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;


/**
 * 年度考核时点及指标Controller
 * @author jml
 * @version 2017-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/stockAssesseTarget")
public class StockAssesseTargetController extends BaseController {

	@Autowired
	private StockAssesseTargetService stockAssesseTargetService;
	
	@ModelAttribute
	public StockAssesseTarget get(@RequestParam(required=false,value="id") String id) {
		StockAssesseTarget entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = stockAssesseTargetService.get(id);
		}
		if (entity == null){
			entity = new StockAssesseTarget();
		}
		return entity;
	}
	
	/**
	 * 页面列表的展示
	 * @param stockAssesseTarget
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"list", ""})
	public String list(StockAssesseTarget stockAssesseTarget, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{ 
		String taskDefKey = request.getParameter("taskDefKey");
		String grade = request.getParameter("grade");
		List<StockAssesseTarget> stockList =  stockAssesseTargetService.findListByApplyNo(stockAssesseTarget.getApplyNo(),taskDefKey,grade);
		model.addAttribute("grade",grade);
		model.addAttribute("stockList", stockList);
		model.addAttribute("applyNo", stockAssesseTarget.getApplyNo());
		model.addAttribute("taskDefKey", taskDefKey);
		return "app/credit/stockAssesseTarget/stockAssesseList";
	}
	
	/**
	 * 跳转查看、编辑、保存页面
	 * @param stockAssesseTarget
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(StockAssesseTarget stockAssesseTarget, Model model,HttpServletRequest request) {
		model.addAttribute("stockAssesseTarget", stockAssesseTarget);
		System.out.println(stockAssesseTarget);
		model.addAttribute("applyNo",request.getParameter("applyNo"));
		model.addAttribute("taskDefKey", request.getParameter("taskDefKey"));
		model.addAttribute("grade",request.getParameter("grade"));
		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		return "app/credit/stockAssesseTarget/stockAssesseTargetForm";
	}
	
	/**
	 * 保存年度考时点及指标数据
	 * @param stockAssesseTarget
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(StockAssesseTarget stockAssesseTarget,Model model,HttpServletRequest request) {
		
		try{
			stockAssesseTargetService.save(stockAssesseTarget);
			model.addAttribute("grade",stockAssesseTarget.getGrade());
			model.addAttribute("saveMessage", "保存年度考核时点及指标成功");
		}catch(Exception e){
			logger.error("保存年度考时点及指标失败", e);
			model.addAttribute("saveMessage", "保存年度考核时点及指标失败");
		}
		
		model.addAttribute("closeWindow", true);
		return form(stockAssesseTarget, model, request);
	}
	
	/**
	 * 删除年度考时点及指标
	 * @param stockAssesseTarget
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(StockAssesseTarget stockAssesseTarget, RedirectAttributes redirectAttributes) {
		stockAssesseTargetService.delete(stockAssesseTarget);
		addMessage(redirectAttributes, "删除年度考时点及指标成功");
		return "redirect:" + Global.getAdminPath() + "/creditViewBook/creditAssets/?repage";
	}
	
	/**
	 * 批量删除年度考时点及指标
	 * @param ids
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public String batchDelete(String ids, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(ids)) {
			List<String> idList = Arrays.asList(ids.split(","));
			stockAssesseTargetService.batchDelete(idList);
		}
		return "success";
	}

}