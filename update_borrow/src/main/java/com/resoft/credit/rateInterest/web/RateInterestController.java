package com.resoft.credit.rateInterest.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.rateInterest.entity.RateInterest;
import com.resoft.credit.rateInterest.service.RateInterestService;

/**
 * 阶梯利率管理Controller
 * @author gaozhi
 * @version 2016-07-06
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/rateInterest")
public class RateInterestController extends BaseController {

	@Autowired
	private RateInterestService rateInterestService;
	
	@ModelAttribute
	public RateInterest get(@RequestParam(required=false) String id) {
		RateInterest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rateInterestService.get(id);
		}
		if (entity == null){
			entity = new RateInterest();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:rateInterest:view")
	@RequestMapping(value = {"list"})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "app/credit/rateInterest/rateInterestList";
	}
	
	@RequiresPermissions("credit:rateInterest:view")
	@RequestMapping(value = {""})
	public String list(RateInterest rateInterest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RateInterest> page = rateInterestService.findPage(new Page<RateInterest>(request, response), rateInterest);
		model.addAttribute("page", page);
		return "app/credit/rateInterest/rateInterestList";
	}

	@RequiresPermissions("credit:rateInterest:view")
	@RequestMapping(value = "form")
	public String form(RateInterest rateInterest, Model model) {
		BigDecimal lilv = new BigDecimal(DictUtils.getDictLabel("1", "RATE_BASE", "0.06"));
		model.addAttribute("ratebase",lilv);
		model.addAttribute("rateInterest", rateInterest);
		return "app/credit/rateInterest/rateInterestForm";
	}
	
	@RequiresPermissions("credit:rateInterest:view")
	@RequestMapping(value = "editform")
	public String editForm(RateInterest rateInterest, Model model) {
		BigDecimal lilv = new BigDecimal(DictUtils.getDictLabel("1", "RATE_BASE", "0.06"));
		model.addAttribute("ratebase",lilv);
		model.addAttribute("rateInterest", rateInterest);
		return "app/credit/rateInterest/rateInterestEditForm";
	}
	
	@RequiresPermissions("credit:rateInterest:view")
	@RequestMapping(value = "getData")
	@ResponseBody
	public List<Map<String,Integer>> getData() {	
		List<Dict>	listperiz = DictUtils.getDictList("PRODUCT_PERIOD_VALUE");
		List<Map<String , Integer>> listperi = new ArrayList<Map<String,Integer>>();	
		for(int i=0; i<listperiz.size();i++){
			Map<String , Integer> mapperi = new HashMap<String, Integer>();
			
			int peri = Integer.parseInt(listperiz.get(i).getLabel());

			mapperi.put("periodValue", peri);
			
			listperi.add(mapperi);
		}
		return listperi;
			
	}

	@RequiresPermissions("credit:rateInterest:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(@RequestBody List<RateInterest> rateInterest, String productTypeCode, String loanRepayType,String startTime,String endTime){
	
		AjaxView ajaxView = new AjaxView();
		Date startTimeTransfor = DateUtils.parseDate(startTime);
		Date endTimeTransfor = DateUtils.parseDate(endTime);
		
		//list包含前台传递所有rateInterest
		List<RateInterest> list = rateInterest;
		String loanRepayDesc = DictUtils.getDictLabel(loanRepayType, "LOAN_REPAY_TYPE", null);
		//获取单独rateInterest的对象
		
		List<RateInterest> rateInterestAllInsert = new ArrayList<RateInterest>();
		String numList="";
		int flag = 0 ;
		int count;
		for (int i = 0; i < list.size(); i++) {
			RateInterest rateInterestInsert = list.get(i);
			//需要判断前面传递的值是否为空，空的不需要进入下一层处理
			if (StringUtils.isBlank(rateInterestInsert.getRateInterest())) {
				flag += 1;
				if(flag == list.size()){
					count=i+1;
					ajaxView.setFailed().setMessage("保存的第"+count+"期利率信息失败！");
					return ajaxView;
				}
				continue;
			} else {
				rateInterestInsert.setProductTypeCode(productTypeCode);
				rateInterestInsert.setLoanRepayType(loanRepayType);
				rateInterestInsert.setLoanRepayDesc(loanRepayDesc);
				rateInterestInsert.setStartTime(startTimeTransfor);
				rateInterestInsert.setEndTime(endTimeTransfor);
				List<RateInterest> rateInterestNum = rateInterestService.checkRateInterestIsDouble(rateInterestInsert);
				if(rateInterestNum.size()>0){//将已经存在的期数记录下来,去重
					count=i+1;
					numList=numList+count+",";
				}
				rateInterestAllInsert.add(rateInterestInsert);
			}
		}
		try {
			if(numList.length()>0){
				ajaxView.setFailed().setMessage("保存的期利率信息失败！"+"第"+numList+"期的利率 已经存在");
				return ajaxView;
			}
			rateInterestService.saveAll(rateInterestAllInsert);
			ajaxView.setSuccess().setMessage("提交利率信息成功！");
			return ajaxView;
		} catch (Exception e) {
			logger.error("保存利率信息失败！", e);
			ajaxView.setFailed().setMessage("保存的期利率信息失败！");
			return ajaxView;
		}
	}
	
	@RequiresPermissions("credit:rateInterest:edit")
	@ResponseBody
	@RequestMapping(value = "editSave")
	public AjaxView editSave(RateInterest rateInterest) {
		AjaxView ajaxView = new AjaxView();
		
		String loanRepayType = rateInterest.getLoanRepayType();
		String loanRepayDesc = DictUtils.getDictLabel(loanRepayType, "LOAN_REPAY_TYPE", null);
		
		rateInterest.setLoanRepayDesc(loanRepayDesc);
		
		rateInterestService.save(rateInterest);
		ajaxView.setSuccess().setMessage("提交利率信息成功！");
		return ajaxView;
	}
	
	@RequiresPermissions("credit:rateInterest:edit")
	@RequestMapping(value = "delete")
	public String delete(RateInterest rateInterest, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response) {
		String idsStr = request.getParameter("ids");
		if (idsStr != null && !"".equals(idsStr)) {
			List<String> idList = Arrays.asList(idsStr.split(","));
			try {
				rateInterestService.banchDelete(idList);
				addMessage(redirectAttributes, "删除阶梯利率成功");
			} catch (Exception e) {
				logger.error("删除阶梯利率失败", e);
				addMessage(redirectAttributes, "删除阶梯利率失败");
			}
		}else{
			try {
				rateInterestService.delete(rateInterest);
				addMessage(redirectAttributes, "删除阶梯利率成功");
			} catch (Exception e) {
				logger.error("删除阶梯利率失败", e);
				addMessage(redirectAttributes, "删除阶梯利率失败");
			}
		}
		return "redirect:" + adminPath + "/credit/rateInterest/list";
	}

}