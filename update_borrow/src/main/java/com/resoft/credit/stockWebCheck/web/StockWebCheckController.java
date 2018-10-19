package com.resoft.credit.stockWebCheck.web;

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

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.stockWebCheck.entity.StockWebCheck;
import com.resoft.credit.stockWebCheck.service.StockWebCheckService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 一次网查（企查查）Controller
 * @author jml
 * @version 2017-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/stockWebCheck")
public class StockWebCheckController extends BaseController {

	@Autowired
	private StockWebCheckService stockWebCheckService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	
	@ModelAttribute
	public StockWebCheck get(@RequestParam(required=false) String id) {
		StockWebCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = stockWebCheckService.get(id);
		}
		if (entity == null){
			entity = new StockWebCheck();
		}
		return entity;
	}
	
	@RequestMapping(value = "form")
	public String form(StockWebCheck stockWebCheck, Model model, ActTaskParam actTaskParam, String readOnly) {
		String applyNo = stockWebCheck.getApplyNo();
		ApplyRegister  applyRegister = new ApplyRegister();
		applyRegister.setApplyNo(applyNo);
		List<ApplyRegister> list = applyRegisterService.findList(applyRegister);
		if (!list.isEmpty()) {
			model.addAttribute("applyRegister", list.get(0));
		}
		stockWebCheck=stockWebCheckService.getByApplyNo(applyNo);
		if(stockWebCheck==null){
			stockWebCheck=new StockWebCheck();
			stockWebCheck.setApplyNo(applyNo);
		}
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("stockWebCheck", stockWebCheck);
		return "app/credit/stockWebCheck/stockWebCheckForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(StockWebCheck stockWebCheck, Model model, HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView = new AjaxView();  
		try{
			stockWebCheckService.save(stockWebCheck);
			ajaxView.setSuccess().setMessage("保存数据成功！");
		}catch(Exception e){
			logger.error("保存数据错误" + e.getMessage(), e);
			ajaxView.setFailed().setMessage("保存数据错误!");
		}
		return ajaxView;
	}
	

}