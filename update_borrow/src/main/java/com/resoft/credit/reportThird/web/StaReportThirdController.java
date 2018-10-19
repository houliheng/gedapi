package com.resoft.credit.reportThird.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.resoft.credit.reportThird.entity.StaReportThird;
import com.resoft.credit.reportThird.service.StaReportThirdService;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 统计报表3Controller
 * 
 * @author wangguodong
 * @version 2017-03-08
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/reportThird")
public class StaReportThirdController extends BaseController {

	@Autowired
	private StaReportThirdService staReportThirdService;

	@RequiresPermissions("credit:staReportSecond:view")
	@RequestMapping(value = "thirdList")
	public String thirdList(StaReportThird staReportThird, Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = Maps.newHashMap();
		// List<StaReportThird> staReportThirds =
		// staReportThirdService.findListThird(params);
		// model.addAttribute("staReportThirds", staReportThirds);
		params.put("reviewedSheet", "1");
		List<StaReportThird> staReportThirds = staReportThirdService.findListThird(params);
		StaReportThird ruleLst0 = staReportThirds.get(0);
		StaReportThird ruleLst1 = staReportThirds.get(1);
		StaReportThird ruleLst2 = staReportThirds.get(2);
		model.addAttribute("ruleLst0", ruleLst0);
		model.addAttribute("ruleLst1", ruleLst1);
		model.addAttribute("ruleLst2", ruleLst2);
		model.addAttribute("ruleLst", staReportThirds);

		{
			params.put("reviewedSheet", "2");
			List<StaReportThird> tsbstkshList = staReportThirdService.findListThird(params);
			model.addAttribute("tsbstkshList", tsbstkshList);
		}

		{
			params.put("reviewedSheet", "3");
			params.put("range", "");
			List<StaReportThird> xyjkshList = staReportThirdService.findListThird(params);
			model.addAttribute("xyjkshList", xyjkshList);
		}

		{
			params.put("reviewedSheet", "3");
			params.put("range", "1");
			List<StaReportThird> xyjkshList1 = staReportThirdService.findListThird(params);
			model.addAttribute("xyjkshList1", xyjkshList1);

			params.put("reviewedSheet", "3");
			params.put("range", "2");
			List<StaReportThird> xyjkshList2 = staReportThirdService.findListThird(params);
			model.addAttribute("xyjkshList2", xyjkshList2);

			params.put("reviewedSheet", "3");
			params.put("range", "3");
			List<StaReportThird> xyjkshList3 = staReportThirdService.findListThird(params);
			model.addAttribute("xyjkshList3", xyjkshList3);
		}

		{
			params.put("reviewedSheet", "4-1");
			params.put("range", null);
			List<StaReportThird> dq_1hhjkshList = staReportThirdService.findListThird(params);
			model.addAttribute("dq_1hhjkshList", dq_1hhjkshList);
		}

		{
			params.put("reviewedSheet", "4-2");
			List<StaReportThird> dq_2hhjkshList = staReportThirdService.findListThird(params);
			model.addAttribute("dq_2hhjkshList", dq_2hhjkshList);
		}

		{
			params.put("reviewedSheet", "5");
			List<StaReportThird> dq_fcdyjkshList = staReportThirdService.findListThird(params);
			model.addAttribute("dq_fcdyjkshList", dq_fcdyjkshList);
		}

		{
			params.put("reviewedSheet", "6");
			List<StaReportThird> dq_fcdzyjkshList = staReportThirdService.findListThird(params);
			model.addAttribute("dq_fcdzyjkshList", dq_fcdzyjkshList);
		}
		return "app/credit/reportThird/staReportThirdList";
	}

}
