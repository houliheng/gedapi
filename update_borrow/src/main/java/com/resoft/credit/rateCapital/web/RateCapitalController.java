package com.resoft.credit.rateCapital.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

import com.google.common.collect.Lists;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.rateCapital.entity.RateCapital;
import com.resoft.credit.rateCapital.service.RateCapitalService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;


@Controller
@RequestMapping(value = "${adminPath}/credit/rateCapital")
public class RateCapitalController extends BaseController {
	
	@Autowired
	private RateCapitalService rateCapitalService;
	
	@ModelAttribute
	public RateCapital get (@RequestParam(required=false) String id){
		RateCapital entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rateCapitalService.get(id);
		}
		if (entity == null){
			entity = new RateCapital();
		}
		return entity;	
	}
	
	@RequiresPermissions("credit:rateCapital:view")
	@RequestMapping(value = {"list"})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {		
		return "app/credit/rateCapital/rateCapitalList";
	}
	
	@RequiresPermissions("credit:rateCapital:view")
	@RequestMapping(value = {""})
	public String list(RateCapital rateCapital, HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {	
		List<RateCapital> resultList = rateCapitalService.findListAll(rateCapital);
		if(0==resultList.size()){
			addMessage(redirectAttributes, "删除阶梯本金成功");
			return "app/credit/rateCapital/rateCapitalList";
		}else{
			List<Integer> periodValueList = Lists.newArrayList();
			for(int i = 0; i < Integer.valueOf((resultList.get(resultList.size() - 1).getPeriodNum())); i++){
				periodValueList.add(i + 1);	
			}
			model.addAttribute("periodValueList", periodValueList);		
			List<RateCapital> selectList = new ArrayList<RateCapital>();
			for(int i = 0 ; i < resultList.size()-1;){
				selectList.add(resultList.get(i));
				i = i + Integer.valueOf(resultList.get(i).getPeriodValue());
			}
			model.addAttribute("selectList", selectList);
			model.addAttribute("resultList",resultList);
			return "app/credit/rateCapital/rateCapitalList";
	 }
	}

	@RequiresPermissions("credit:rateCapital:view")
	@RequestMapping(value = "form")
	public String form(Model model) {
		RateCapital rateCapital = new RateCapital();
		model.addAttribute("rateCapital", rateCapital);
		model.addAttribute("judgeFlag", 0);
		return "app/credit/rateCapital/rateCapitalForm";
	}
	
	@RequiresPermissions("credit:rateCapital:view")
	@RequestMapping(value = "editform")
	public String editForm( Model model) {
		RateCapital rateCapital = new RateCapital();
		rateCapital.setProductTypeCode(null);
		model.addAttribute("judgeFlag", 1);
		return "app/credit/rateCapital/rateCapitalForm";
	}
	
	@RequiresPermissions("credit:rateCapital:view")
	@RequestMapping(value = "getData")
	@ResponseBody
	public List<RateCapital> getData(HttpServletRequest request) {	
		List<RateCapital> getDataList = Lists.newArrayList();
		String judgeFlagString = request.getParameter("judgeFlag");
		if (judgeFlagString.equalsIgnoreCase("1")){
			RateCapital rateCapital = new RateCapital();
			rateCapital.setProductTypeCode(request.getParameter("productTypeCode"));
			rateCapital.setLoanRepayType(request.getParameter("loanRepayType"));
			rateCapital.setPeriodValue(request.getParameter("periodValue"));
			getDataList = rateCapitalService.selectCapitalEdit(rateCapital);
		}else{
			for(int i=0; i<=Integer.parseInt(request.getParameter("periodValue"))-1;i++){
				RateCapital rateCapital = new RateCapital();
				rateCapital.setId(IdGen.uuid());
				rateCapital.setProductTypeCode(request.getParameter("productTypeCode"));
				rateCapital.setLoanRepayType(request.getParameter("loanRepayType"));
				rateCapital.setLoanRepayDesc(DictUtils.getDictLabel(request.getParameter("loanRepayType"), "LOAN_REPAY_TYPE", null));
				rateCapital.setPeriodValue(request.getParameter("periodValue"));
				rateCapital.setPeriodNum(i+1+"");
				getDataList.add(rateCapital);
			}
		}
		return getDataList;		
	}

	@RequiresPermissions("credit:rateCapital:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(@RequestBody List<RateCapital> rateCapital, String judgeFlag){
		AjaxView ajaxView = new AjaxView();	
		String judgeFlagString = judgeFlag;
		//list包含前台传递所有rateCapital
		List<RateCapital> resuletList = rateCapital;
		//前台输入的本金比例数和
		int valiData = 0 ;
		  for(int i = 0; i < resuletList.size(); i++){
			valiData = valiData + Integer.parseInt(resuletList.get(i).getRateCapitalCurr());
		}
		 if(valiData != 100){
			ajaxView.setFailed().setMessage("此处输入的本金比例为百分号前面的数值，因此所有期数和应为100。您输入的和为"+valiData);
			return ajaxView;
		}else{
			BigDecimal sumCapital = new BigDecimal(100) ;
			for (int i = 0; i < resuletList.size(); i++) {
				if(i == 0){
					resuletList.get(i).setRateCapitalRemain("1");
				}else{
					sumCapital = sumCapital.subtract((new BigDecimal(resuletList.get(i-1).getRateCapitalCurr())).multiply(new BigDecimal(100)));
					resuletList.get(i).setRateCapitalRemain((sumCapital.divide(new BigDecimal(100))).toString());
				}
				
				resuletList.get(i).setRateCapitalCurr((new BigDecimal((resuletList.get(i).getRateCapitalCurr())).divide(new BigDecimal(100))).toString());
			}
			try {
				if(judgeFlagString.equalsIgnoreCase("1")){
					rateCapitalService.update(resuletList);
				}else{
					rateCapitalService.saveAll(resuletList);
				}
				ajaxView.setSuccess().setMessage("提交本金比例信息成功！");
				return ajaxView;
			} catch (Exception e) {
				logger.error("保存本金比例信息失败！", e);
				ajaxView.setFailed().setMessage("保存的本金比例信息失败！");
				return ajaxView;
			}	  
	  }
	}
	
	@RequiresPermissions("credit:rateCapital:edit")
	@RequestMapping(value = "delete")
	public String delete(RateCapital rateCapital, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response) {
		String ids = request.getParameter("ids");
		if (ids != null && !"".equals(ids)) {
			List<String> idList = Arrays.asList(ids.split(","));
			try {
				rateCapitalService.banchDelete(idList);
				addMessage(redirectAttributes, "删除阶梯利率成功");
			} catch (Exception e) {
				logger.error("删除阶梯利率失败", e);
				addMessage(redirectAttributes, "删除阶梯利率失败");
			}
		}else{
			
			try {
				rateCapitalService.delete(rateCapital);
				addMessage(redirectAttributes, "删除阶梯利率成功");
			} catch (Exception e) {
				logger.error("删除阶梯利率失败", e);
				addMessage(redirectAttributes, "删除阶梯利率失败");
			}
		}
		return "redirect:" + adminPath + "/credit/rateCapital/list";
	}
	
}


