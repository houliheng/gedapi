package com.resoft.credit.creditAndLine.web.creditLineBank;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBank;
import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBankDetail;
import com.resoft.credit.creditAndLine.service.creditLineBank.CreditLineBankDetailService;
import com.resoft.credit.creditAndLine.service.creditLineBank.CreditLineBankService;

/**
 * 征信银行卡流水明细Controller
 * 
 * @author wuxi01
 * @version 2016-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditAndLine/creditLineBank/creditLineBankDetail")
public class CreditLineBankDetailController extends BaseController {

	@Autowired
	private CreditLineBankDetailService creditLineBankDetailService;

	@Autowired
	private CreditLineBankService creditLineBankService;

	@ModelAttribute
	public CreditLineBankDetail get(@RequestParam(required = false) String id) {
		CreditLineBankDetail entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = creditLineBankDetailService.get(id);
		}
		if (entity == null) {
			entity = new CreditLineBankDetail();
		}
		return entity;
	}

	@RequiresPermissions("credit:creditAndLine:creditLineBank:creditLineBankDetail:view")
	@RequestMapping(value = { "list", "" })
	public String list(String creditLineBankId, String readOnly, Model model, String applyNo,HttpServletRequest request) {
//		String queryString = request.getQueryString();
//		System.out.println(queryString);
		CreditLineBankDetail creditLineBankDetail = new CreditLineBankDetail();
		if (creditLineBankId == null) {
			logger.error("系统参数出现错误，请联系管理员！");
		}
		CreditLineBank creditLineBank = creditLineBankService.get(creditLineBankId);
		if (creditLineBank == null) {
			creditLineBank = new CreditLineBank();
			logger.error("数据库数据出现错误，请联系管理员！");
		}
		creditLineBankDetail.setCreditLineBank(creditLineBank);
		List<CreditLineBankDetail> creditLineBankDetailList = creditLineBankDetailService.findList(creditLineBankDetail);
		model.addAttribute("creditLineBankDetailList", creditLineBankDetailList);
		model.addAttribute("creditLineBankId", creditLineBankId);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/creditAndLine/creditLineBank/creditLineBankDetailList";
	}

	@RequiresPermissions("credit:creditAndLine:creditLineBank:creditLineBankDetail:view")
	@RequestMapping(value = "form")
	public String form(String creditLineBankId, String creditLineBankDetailId, String readOnly, Model model, String applyNo) {
		CreditLineBankDetail creditLineBankDetail = new CreditLineBankDetail();
		// 1.新增
		if (StringUtils.isNotEmpty(creditLineBankId)) {
			readOnly = "1";
			CreditLineBank creditLineBank = creditLineBankService.get(creditLineBankId);
			try {
				creditLineBankDetail.setCreditLineBank(creditLineBank);
			} catch (Exception e) {
				logger.error("系统参数出现错误，请联系管理员！", e);
			}
		}
		// 2.修改
		if (StringUtils.isNotEmpty(creditLineBankDetailId)) {
			creditLineBankDetail = creditLineBankDetailService.get(creditLineBankDetailId);
			try {
				creditLineBankId = creditLineBankDetail.getCreditLineBank().getId();
				CreditLineBank creditLineBank = creditLineBankService.get(creditLineBankId);
				creditLineBankDetail.setCreditLineBank(creditLineBank);
			} catch (Exception e) {
				logger.error("系统参数出现错误，请联系管理员！", e);
			}
		}
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("creditLineBankDetail", creditLineBankDetail);
		return "app/credit/creditAndLine/creditLineBank/creditLineBankDetailForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditLineBank:creditLineBankDetail:edit")
	@RequestMapping(value = "save")
	public AjaxView save(@RequestBody CreditLineBankDetail creditLineBankDetail) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditLineBankDetail.setLineBankId(creditLineBankDetail.getCreditLineBank().getId());
			// 新增时，查询是否存在当月流水，存在则不允许再次保存，提示信息
			if (creditLineBankDetail == null || StringUtils.isBlank(creditLineBankDetail.getId())) {
				Map<String, String> params = Maps.newConcurrentMap();
				try {
					params.put("lineBankId", creditLineBankDetail.getLineBankId());
					params.put("lineMonth", creditLineBankDetail.getLineMonth());
				} catch (Exception e1) {
					logger.error("流水参数丢失！", e1);
					ajaxView.setFailed().setMessage("流水参数丢失，请联系管理员！");
					return ajaxView;
				}
				List<CreditLineBankDetail> creditLineBankDetailList = creditLineBankDetailService.findLineBankDetailByLineMonth(params);
				if (creditLineBankDetailList != null && creditLineBankDetailList.size() > 0) {
					ajaxView.setFailed().setMessage("该月份流水明细已录入，请录入其他月份流水明细！");
					return ajaxView;
				}
			}
			creditLineBankDetailService.saveDetail(creditLineBankDetail);
			try {
				// 计算进项总额、出项总额、月均有效流水，更新credit_line_bank表
				CreditLineBank creditLineBank = creditLineBankDetail.getCreditLineBank();
				String creditLineBankId = creditLineBank.getId();
				creditLineBank = creditLineBankService.get(creditLineBankId);
				Map<String, String> param = Maps.newConcurrentMap();
				param.put("creditLineBankId", creditLineBankId);
				Map<String, String> resultMap = creditLineBankDetailService.getAvgAndSum(param);
				String sumIncomeAmount = null;
				String sumExpenseAmount = null;
				String avgCurreValidLineAmount = null;
				if (resultMap == null) {// 第一次为该银行卡流水增加明细信息
					sumIncomeAmount = creditLineBankDetail.getIncomeAmount();
					sumExpenseAmount = creditLineBankDetail.getExpenseAmount();
					avgCurreValidLineAmount = creditLineBankDetail.getCurreValidLineAmount();
				} else {
					Object sumIncomeAmountobj = resultMap.get("sumIncomeAmount");
					sumIncomeAmount = sumIncomeAmountobj.toString();
					Object sumExpenseAmountobj = resultMap.get("sumExpenseAmount");
					sumExpenseAmount = sumExpenseAmountobj.toString();
					Object avgCurreValidLineAmountobj = resultMap.get("avgCurreValidLineAmount");
					avgCurreValidLineAmount = avgCurreValidLineAmountobj.toString();
				}
				creditLineBank.setSumIncomeAmount(sumIncomeAmount);
				creditLineBank.setSumExpenseAmount(sumExpenseAmount);
				creditLineBank.setAvgCurreValidLineAmount(avgCurreValidLineAmount);
				creditLineBankService.updateSumAndAvg(creditLineBank);
				ajaxView.setSuccess().setMessage("保存银行卡流水明细成功！");
			} catch (Exception e) {
				logger.error("保存银行卡流水明细成功，但更新银行卡流水信息失败，请联系管理员！", e);
				ajaxView.setFailed().setMessage("保存银行卡流水明细成功，但更新银行卡流水信息失败，请联系管理员！");
				return ajaxView;
			}
		} catch (Exception e) {
			logger.error("保存银行卡流水明细失败！", e);
			ajaxView.setFailed().setMessage("保存银行卡流水明细失败！");
			return ajaxView;
		}
		return ajaxView;
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditLineBank:creditLineBankDetail:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(String ids) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditLineBankDetailService.banchDelete(ids);
			String[] idArray = ids.split(",");
			ajaxView.setSuccess().setMessage("删除银行卡流水明细成功！");
			try {
				CreditLineBankDetail creditLineBankDetail = new CreditLineBankDetail();
				Map<String, String> param = Maps.newConcurrentMap();
				creditLineBankDetail = creditLineBankDetailService.get(idArray[0]);
				param.put("creditLineBankId", creditLineBankDetail.getCreditLineBank().getId());
				Map<String, String> resultMap = creditLineBankDetailService.getAvgAndSum(param);
				String sumIncomeAmount = null;
				String sumExpenseAmount = null;
				String avgCurreValidLineAmount = null;
				if (resultMap != null) {
					Object sumIncomeAmountobj = resultMap.get("sumIncomeAmount");
					sumIncomeAmount = sumIncomeAmountobj.toString();
					Object sumExpenseAmountobj = resultMap.get("sumExpenseAmount");
					sumExpenseAmount = sumExpenseAmountobj.toString();
					Object avgCurreValidLineAmountobj = resultMap.get("avgCurreValidLineAmount");
					avgCurreValidLineAmount = avgCurreValidLineAmountobj.toString();
				}
				CreditLineBank creditLineBank = creditLineBankService.get(creditLineBankDetail.getCreditLineBank().getId());

				creditLineBank.setSumIncomeAmount(sumIncomeAmount);
				creditLineBank.setSumExpenseAmount(sumExpenseAmount);
				creditLineBank.setAvgCurreValidLineAmount(avgCurreValidLineAmount);
				creditLineBankService.updateSumAndAvg(creditLineBank);
				ajaxView.setSuccess();
			} catch (Exception e) {
				logger.error("删除银行卡流水明细成功，更新征信流水银行卡信息失败", e);
				ajaxView.setFailed().setMessage("删除银行卡流水明细成功，更新征信流水银行卡信息失败");
			}
		} catch (Exception e) {
			logger.error("删除银行卡流水明细失败！", e);
			ajaxView.setFailed().setMessage("删除银行卡流水明细失败！");
		}
		return ajaxView;
	}

}