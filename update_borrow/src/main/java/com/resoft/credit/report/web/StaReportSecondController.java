package com.resoft.credit.report.web;

import java.math.BigDecimal;
import java.util.List;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.report.entity.StaReportFirst;
import com.resoft.credit.report.entity.StaReportSecond;
import com.resoft.credit.report.service.StaReportSecondService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 统计报表2Controller
 * 
 * @author lixing
 * @version 2016-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/report")
public class StaReportSecondController extends BaseController {

	@Autowired
	private StaReportSecondService staReportSecondService;
	@Autowired
	OfficeService officeService;

	@ModelAttribute
	public StaReportSecond get(@RequestParam(required = false) String id) {
		StaReportSecond entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = staReportSecondService.get(id);
		}
		if (entity == null) {
			entity = new StaReportSecond();
		}
		return entity;
	}

	@RequiresPermissions("credit:staReportSecond:view")
	@RequestMapping(value = "secondList")
	public String secondList(StaReportSecond staReportSecond, Model model, HttpServletRequest request, HttpServletResponse response) {
		return "app/credit/report/staReportSecondList";
	}

	@RequiresPermissions("credit:staReportSecond:view")
	@RequestMapping(value = "firstList")
	public String firstList(StaReportSecond staReportSecond, Model model, HttpServletRequest request, HttpServletResponse response) {
		List<StaReportFirst> list = staReportSecondService.findOfficeLevelTwoList();
		model.addAttribute("list", list);
		return "app/credit/report/staReportFirstList";
	}

	@RequiresPermissions("credit:staReportSecond:view")
	@RequestMapping(value = "fourthList")
	public String fourthList(StaReportSecond staReportSecond, Model model) {
		Map<String, Object> params = Maps.newHashMap();
		if (staReportSecond != null && staReportSecond.getCompany() != null && staReportSecond.getCompany().getId() != null) {
			params.put("orgId", staReportSecond.getCompany().getId());
		}
		List<Map<String, Object>> lists = staReportSecondService.findListFourth(params);
		List<StaReportFirst> list = staReportSecondService.findOfficeLevelTwoList();
		model.addAttribute("list", list);
		model.addAttribute("lists", lists);
		return "app/credit/report/staReportFourthList";
	}

	@RequestMapping(value = "listfirstReport")
	@ResponseBody
	public List<Map<String, Object>> listfirstReport(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> datas = Lists.newArrayList();
		Map<String, Object> params = Maps.newHashMap();
		params.put("dataMonthStart", request.getParameter("dataMonthStart"));
		params.put("dataMonthEnd", request.getParameter("dataMonthEnd"));
		params.put("companyId", request.getParameter("company"));
		params.put("orgId", UserUtils.getUser().getCompany().getId());
		params.put("orgParentId", UserUtils.getUser().getCompany().getParentIds());
		datas = staReportSecondService.findListFirst(params);
		return datas;
	}

	@RequestMapping(value = "listSecondReport")
	@ResponseBody
	public Map<String, Object> listSecondReport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramData = Maps.newHashMap();
		Map<String, Object> paramDataPart = Maps.newHashMap();
		List<Map<String, Object>> datas = Lists.newArrayList();
		Map<String, Object> params = Maps.newHashMap();
		params.put("dataMonthStart", request.getParameter("dataMonthStart"));
		params.put("dataMonthEnd", request.getParameter("dataMonthEnd"));
		params.put("companyId", request.getParameter("companyName"));
		params.put("orgId", UserUtils.getUser().getCompany().getId());
		params.put("orgParentId", UserUtils.getUser().getCompany().getParentIds());
		datas = staReportSecondService.findListSecond(params);
		BigDecimal dqStatus = new BigDecimal(0);
		BigDecimal zbStatus = new BigDecimal(0);
		Long dqTime = 0l;
		Long zbTime = 0l;
		if (datas != null && datas.size() != 0) {
			for (Map<String, Object> param : datas) {
				if ("1".equals(param.get("appStatusDq").toString())) {
					dqStatus = dqStatus.add(new BigDecimal(1));
				}
				if ("1".equals(param.get("appStatusZb").toString())) {
					zbStatus = zbStatus.add(new BigDecimal(1));
				}
				if (!(StringUtils.isNull(param.get("appTimeDq")))) {
					dqTime = dqTime + Long.parseLong(param.get("appTimeDq").toString());
				}
				if (!(StringUtils.isNull(param.get("appTimeZb")))) {
					zbTime = zbTime + Long.parseLong(param.get("appTimeZb").toString());
				}
				param.put("appStatusDq", DictUtils.getDictLabel(param.get("appStatusDq").toString(), "APPROVE_RESULT", null));
				param.put("appStatusZb", DictUtils.getDictLabel(param.get("appStatusZb").toString(), "APPROVE_RESULT", null));
				param.put("appTimeDq", DateUtils.formatDateTime(Long.parseLong(param.get("appTimeDq").toString())));
				param.put("appTimeZb", DateUtils.formatDateTime(Long.parseLong(param.get("appTimeZb").toString())));
			}
			paramDataPart.put("appTimeDq", DateUtils.formatDateTime((dqTime / datas.size())));
			paramDataPart.put("appTimeZb", DateUtils.formatDateTime((zbTime / datas.size())));
			paramDataPart.put("appStatusDq", (dqStatus.divide(new BigDecimal(datas.size()), 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))) + "%");
			paramDataPart.put("appStatusZb", (zbStatus.divide(new BigDecimal(datas.size()), 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))) + "%");
		} else {
			paramDataPart.put("appTimeDq", 0);
			paramDataPart.put("appTimeZb", 0);
			paramDataPart.put("appStatusDq", 0);
			paramDataPart.put("appStatusZb", 0);
		}
		paramData.put("rows", datas);
		paramData.put("userdata", paramDataPart);
		return paramData;
	}
}