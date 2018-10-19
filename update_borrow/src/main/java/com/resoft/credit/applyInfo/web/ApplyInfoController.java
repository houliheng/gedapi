package com.resoft.credit.applyInfo.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.rateInterest.entity.RateInterest;
import com.resoft.credit.rateInterest.service.RateInterestService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 申请借款信息表Controller
 * @author chenshaojia
 * @version 2016-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/applyInfo")
public class ApplyInfoController extends BaseController {

	@Autowired
	private ApplyInfoService applyInfoService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	
	@Autowired
	private RateInterestService rateInterestService;
	
	@ModelAttribute
	public ApplyInfo get(@RequestParam(required=false) String id) {
		ApplyInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = applyInfoService.get(id);
		}
		if (entity == null){
			entity = new ApplyInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:applyInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ApplyInfo applyInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ApplyInfo> page = applyInfoService.findPage(new Page<ApplyInfo>(request, response), applyInfo); 
		model.addAttribute("page", page);
		return "app/credit/applyInfo/applyInfoList";
	}

	@RequiresPermissions("credit:applyInfo:view")
	@RequestMapping(value = "form")
	public String form(ApplyInfo applyInfo, Model model) {
		model.addAttribute("applyInfo", applyInfo);
		return "app/credit/applyInfo/applyInfoForm";
	}

	@RequiresPermissions("credit:applyInfo:edit")
	@RequestMapping(value = "save")
	public String save(ApplyInfo applyInfo, Model model, ActTaskParam actTaskParam) {
		try {
			applyInfoService.saveApplyInfo(applyInfo);
			addMessage(model, "保存申请借款信息成功");
		} catch (Exception e) {
			logger.error("保存申请借款信息失败",e);
			addMessage(model, "保存申请借款信息失败");
		}
		return loanApplyInfoForm(actTaskParam, model, false);
	}
	
	@RequiresPermissions("credit:applyInfo:view")
	@RequestMapping("/loanApplyInfoForm")
	public String loanApplyInfoForm(ActTaskParam actTaskParam, Model model, boolean readOnly){
		try {
			//外访费返还中的借款申请信息页签，会传入applyNo,同时会传入流程中的applyNo,所以applyNo中会出现逗号
			String applyNo =  actTaskParam.getApplyNo();
			int location = applyNo.indexOf(",");
			if (location > 0 ){
				applyNo = applyNo.substring(0,location);
				actTaskParam.setApplyNo(applyNo);
			}
			model.addAttribute("actTaskParam", actTaskParam);
			model.addAttribute("readOnly", readOnly);
			//查询客户贷款登记信息
			ApplyRegister  applyRegister = new ApplyRegister();
			applyRegister.setApplyNo(actTaskParam.getApplyNo());
			List<ApplyRegister> list = applyRegisterService.findList(applyRegister);
			String productCategoryValue="";
			if (!list.isEmpty()) {
				model.addAttribute("applyRegister", list.get(0));
				//是否显示分类
				productCategoryValue=list.get(0).getProductCategory();
				//根据申请的产品类型查询还款方式
				Map<String,String> param = new HashMap<String,String>();
				param.put("productType", list.get(0).getApplyProductTypeCode());
				List<RateInterest> loanRepayTypeList = rateInterestService.getLoanRepayType(param);
				model.addAttribute("loanRepayTypeList", loanRepayTypeList);
			}else {
				model.addAttribute("applyRegister", new ApplyRegister());
			}
			//查询贷款申请信息
			ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(actTaskParam.getApplyNo());
			if(null == applyInfo){
				applyInfo = new ApplyInfo();
			}
			model.addAttribute("applyInfo", applyInfo);
			//是否显示分类
			String productCategory=DictUtils.getDictLabel(productCategoryValue, "product_category", null);
			model.addAttribute("productCategoryValue", productCategoryValue);
			model.addAttribute("productCategory", productCategory);
			/*List<ProductPeriod>  periodList = productPeriodService.findPeriodByApplyNo(actTaskParam.getApplyNo());
			model.addAttribute("periodList", periodList);*/
		} catch (Exception e) {
			logger.error("查询客户借款申请信息失败", e);
		}
		return "app/credit/applyRegister/loanApplyForm";
	}

}