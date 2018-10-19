package com.resoft.accounting.holiday.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.accounting.holiday.entity.Holiday;
import com.resoft.accounting.holiday.service.HolidayService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/accounting/holiday/")
public class HolidayController extends BaseController {

	@Autowired
	private HolidayService holidayService;

	@RequestMapping(value = {"list", ""})
	public String list(Holiday holiday, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Holiday> page = holidayService.findPage(new Page<Holiday>(request, response), holiday); 
        model.addAttribute("page", page);
		return "app/accounting/holiday/holidayList";
	}	
	
	//新增节假日对日期做唯一性校验
	@ResponseBody
	@RequestMapping(value="validate")
	public Map<String, String> validate(Holiday holiday,HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		//查询前台选择的日期在数据表中是否有对应记录存在	
		int holidayCount = holidayService.findDateCount(holiday);
		//如果记录总数大于0，表示数据表中已经存在该日期对应的记录
		Map<String, String> map=new HashMap<String, String>();
		if(holidayCount>0){
			map.put("jg", "cz");
		}else{
			map.put("jg", "cg");
		}
		return map;
	}
	
	@RequestMapping(value="save")
	public String save(Holiday holiday,HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {

			holidayService.save(holiday);
			addMessage(redirectAttributes, "保存节假日信息'" + holiday.getHldName() + "'成功");
			return    "redirect:" + adminPath + "/accounting/holiday/";
	}

	@RequestMapping(value="add")
	public String add(Holiday holiday,  Model model,Integer holidayCount) {
		//id不为空时，查询单条数据，将结果传值前台，页面为修改页面
		if(StringUtils.isNotBlank(holiday.getId())){
			Holiday hld = holidayService.get(holiday.getId());
			model.addAttribute("holiday", hld);
		}
		
		
		model.addAttribute("holidayCount", holidayCount);
		return "app/accounting/holiday/holidayForm";
	}

	@RequestMapping(value = "delete")
	public String delete(Holiday holiday, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String idStr = request.getParameter("id");
		String str[] = idStr.split(",");
		for(int i=0;i<str.length;i++){
			if(!("").equals(str[i])){
				String id = str[i];
				holiday.setId(id);
				holidayService.delete(holiday);
			}
		}
			addMessage(redirectAttributes, "删除节假日成功");
			return   "redirect:" + adminPath + "/accounting/holiday/";
	}
}