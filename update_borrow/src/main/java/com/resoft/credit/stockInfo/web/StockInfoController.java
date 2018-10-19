package com.resoft.credit.stockInfo.web;

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

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.stockInfo.entity.StockInfo;
import com.resoft.credit.stockInfo.service.StockInfoService;
import com.resoft.credit.stockOpinion.entity.CreStockOpinion;
import com.resoft.credit.stockOpinion.service.CreStockOpinionService;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.resoft.credit.stockTaskReceive.service.StockTaskReceiveService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 股权尽调业务表Controller
 * @author jml
 * @version 2017-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/stockInfo")
public class StockInfoController extends BaseController {

	@Autowired
	private StockInfoService stockInfoService;
	@Autowired
	private CreStockOpinionService stockOpinionService;
	@Autowired
	private StockTaskReceiveService stockTaskReceiveService;
	
	@ModelAttribute
	public StockInfo get(@RequestParam(required=false) String id) {
		StockInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = stockInfoService.get(id);
		}
		if (entity == null){
			entity = new StockInfo();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(StockInfo stockInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StockInfo> page = stockInfoService.findCustomPage(new Page<StockInfo>(request, response), stockInfo); 
		model.addAttribute("page", page);
		return "app/credit/stockInfo/stockInfoList";
	}

	@RequestMapping(value = "form")
	public String form(StockInfo stockInfo, Model model, ActTaskParam actTaskParam, String readOnly,String stockTaskReceiveId) {
		String grade="";
		if(StringUtils.isEmpty(stockTaskReceiveId)){
			String taskDefKey = actTaskParam.getTaskDefKey();
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
		}else{
			StockTaskReceive stockTaskReceive = stockTaskReceiveService.getById(stockTaskReceiveId);
			grade=stockTaskReceive.getGrade();
			String isStockPost = stockTaskReceive.getIsStockPost().toString();
			model.addAttribute("isStockPost", isStockPost);
		}
		stockInfo=stockInfoService.getStockInfoByApplyGrade(actTaskParam.getApplyNo(),grade);
		if(stockInfo==null){
			if(grade.equals("4")){//分公司为空，不查上一级
				stockInfo=new StockInfo();
			}else{
				String gradeNew=Integer.parseInt(grade)+1+"";
				stockInfo=stockInfoService.getStockInfoByApplyGrade(actTaskParam.getApplyNo(),gradeNew);
			}
		}else if(StringUtils.isEmpty(stockInfo.getChairmanName())){
			if(grade.equals("4")){//分公司为空，不查上一级
				stockInfo=new StockInfo();
			}else{
				String gradeNew=Integer.parseInt(grade)+1+"";
				stockInfo=stockInfoService.getStockInfoByApplyGrade(actTaskParam.getApplyNo(),gradeNew);
			}
		}
		if(stockInfo==null){
			stockInfo=new StockInfo();
		}
		List<CreStockOpinion> stockOpinions=stockOpinionService.getByApplyNo(actTaskParam.getApplyNo());
		for (CreStockOpinion creStockOpinion : stockOpinions) {
			String officeFlag = creStockOpinion.getOfficeFlag();
			if("4".equals(officeFlag)){
				stockInfo.setSuggestionBranch(creStockOpinion.getOfficeOpinion());
				stockInfo.setBranchName(creStockOpinion.getEmployeeName());
				stockInfo.setBranchNo(creStockOpinion.getEmployeeNo());
				String submitStatus = creStockOpinion.getSubmitStatus();
				if("1".equals(submitStatus)){
					model.addAttribute("branchStatus", "1");
				}
			}
			if("3".equals(officeFlag)){
				stockInfo.setSuggestionArea(creStockOpinion.getOfficeOpinion());
				stockInfo.setAreaName(creStockOpinion.getEmployeeName());
				stockInfo.setAreaNo(creStockOpinion.getEmployeeNo());
				String submitStatus = creStockOpinion.getSubmitStatus();
				if("1".equals(submitStatus)){
					model.addAttribute("areaStatus", "1");
				}
			}
			if("2".equals(officeFlag)){
				stockInfo.setSuggestionLargeArea(creStockOpinion.getOfficeOpinion());
				stockInfo.setLargeAreaName(creStockOpinion.getEmployeeName());
				stockInfo.setLargeAreaNo(creStockOpinion.getEmployeeNo());
				String submitStatus = creStockOpinion.getSubmitStatus();
				if("1".equals(submitStatus)){
					model.addAttribute("largeAreaStatus", "1");
				}
			}
			if("1".equals(officeFlag)){
				stockInfo.setSuggestionHead(creStockOpinion.getOfficeOpinion());
				stockInfo.setHeadName(creStockOpinion.getEmployeeName());
				stockInfo.setHeadNo(creStockOpinion.getEmployeeNo());
				String submitStatus = creStockOpinion.getSubmitStatus();
				if("1".equals(submitStatus)){
					model.addAttribute("headStatus", "1");
				}
			}
		}
		model.addAttribute("stockInfo", stockInfo);
		model.addAttribute("grade", grade);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("stockTaskReceiveId", stockTaskReceiveId);
		return "app/credit/stockInfo/stockInfoForm";
	}

	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(String stockTaskReceiveId,StockInfo stockInfo, Model model, HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView = new AjaxView();  
		try{
			String grade="";
			StockTaskReceive stockTaskReceive = stockTaskReceiveService.getById(stockTaskReceiveId);
			grade=stockTaskReceive.getGrade();
			/*ApplyRegister applyRegisterByApplyNo = applyRegisterService.getApplyRegisterByApplyNo(stockInfo.getApplyNo());
			String procInsId = applyRegisterByApplyNo.getProcInsId();
			String taskDefKey=stockInfoService.selectActRuTasKDefKeyByInstId(procInsId);*/
			/*if("utask_fgsfksh".equals(taskDefKey)||"utask_fgsjlsh".equals(taskDefKey)){
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
			}*/
			stockInfoService.saveStockInfo(stockInfo,grade);
			ajaxView.setSuccess().setMessage("保存数据成功！");
		}catch(Exception e){
			logger.error("保存数据错误" + e.getMessage(), e);
			ajaxView.setFailed().setMessage("保存数据错误!");
		}
		return ajaxView;
	}
	
	@RequestMapping(value = "delete")
	public String delete(StockInfo stockInfo, RedirectAttributes redirectAttributes) {
		stockInfoService.delete(stockInfo);
		addMessage(redirectAttributes, "删除股权尽调业务表成功");
		return "redirect:"+Global.getAdminPath()+"/stockInfo/stockInfo/?repage";
	}
}