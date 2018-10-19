package com.resoft.credit.industry.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resoft.credit.industry.entity.Industry;
import com.resoft.credit.industry.service.IndustryService;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 行业分类信息表Controller
 * @author songmin
 * @version 2016-01-06
 */
/**
 * @reqno:H1512210027
 * @date-designer:2016年1月6日-songmin
 * @date-author:2016年1月6日-songmin:行业分类信息表Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/industry")
public class IndustryController extends BaseController {

	@Autowired
	private IndustryService industryService;
	
	/**
	 * @reqno:H1512210027
	 * @date-designer:2016年1月6日-songmin
	 * @date-author:2016年1月6日-songmin:CRE_信贷审批_企业_申请录入_客户信息
	 * 	根据上级行业编码，获取下级行业编码信息
	 */
	@RequestMapping("/loadIndustry")
	@ResponseBody
	public List<Industry> loadIndustry(String parentInduCode){
		List<Industry> list = industryService.findByParentCode(parentInduCode);
		return list;
	}
}