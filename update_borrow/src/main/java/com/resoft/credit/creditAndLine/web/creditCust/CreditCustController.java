package com.resoft.credit.creditAndLine.web.creditCust;

import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.creditAndLine.entity.creditCust.CreditCust;
import com.resoft.credit.creditAndLine.service.creditCust.CreditCustService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;

/**
 * 个人征信列表Controller
 * @author wuxi01
 * @version 2016-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creditAndLine/creditCust")
public class CreditCustController extends BaseController {

	@Autowired
	private CreditCustService creditCustService;
	
	@Autowired
	private CustInfoService custInfoService;
	
	@Autowired
	private ApplyRelationService applyRelationService;
	
	@ModelAttribute
	public CreditCust get(@RequestParam(required=false) String id) {
		CreditCust entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = creditCustService.get(id);
		}
		if (entity == null){
			entity = new CreditCust();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:creditAndLine:creditCust:view")
	@RequestMapping(value = {"list", ""})
	public String list(String applyNo, String readOnly, Model model) {
		CreditCust creditCust = new CreditCust();
		creditCust.setApplyNo(applyNo);
		List<CreditCust> creditCustList = creditCustService.findList(creditCust);
		model.addAttribute("creditCustList", creditCustList);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("readOnly", readOnly);
		return "app/credit/creditAndLine/creditCust/creditCustList";
	}

	@RequiresPermissions("credit:creditAndLine:creditCust:view")
	@RequestMapping(value = "form")
	public String form(String applyNo, String creditCustId, String readOnly, Model model) {
		//1.新增
		CreditCust creditCust = new CreditCust();
		if (StringUtils.isNotBlank(applyNo)) {
			creditCust.setApplyNo(applyNo);
		}
		//2.修改
		if (StringUtils.isNotBlank(creditCustId)) {
			creditCust = creditCustService.get(creditCustId);
			try {
				applyNo = creditCust.getApplyNo();
				String roleType = creditCust.getRoleType();
				Map<String, String> params = Maps.newConcurrentMap();
				params.put("applyNo", applyNo);
				params.put("roleType", roleType);
				List<Map<String, String>> custNameMap = applyRelationService.findCustNameByRoleType(params);
				model.addAttribute("custNameMap", custNameMap);
			} catch (Exception e) {
				logger.error("系统数据出现错误，请联系管理员！");
			}
		}
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("creditCust", creditCust);
		return "app/credit/creditAndLine/creditCust/creditCustForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditCust:edit")
	@RequestMapping(value = "save")
	public AjaxView save(CreditCust creditCust) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditCustService.save(creditCust);
			ajaxView.setSuccess().setMessage("保存个人征信信息成功！");
		} catch (Exception e) {
			logger.error("保存个人征信信息失败！", e);
			ajaxView.setFailed().setMessage("保存个人征信信息失败！");
		}
		return ajaxView;
	}
	
	/**
	 * 删除银行卡流水信息Ajax
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditLineBank:edit")
	@RequestMapping(value = "delete")
	public AjaxView delete(String ids) {
		AjaxView ajaxView = new AjaxView();
		try {
			creditCustService.banchDelete(ids);
			ajaxView.setSuccess().setMessage("删除个人征信信息成功！");
		} catch (Exception e) {
			logger.error("保存银行卡流水信息失败！", e);
			ajaxView.setFailed().setMessage("删除个人征信信息失败！");
		}
		return ajaxView;
	}
	
	@ResponseBody
	@RequiresPermissions("credit:creditAndLine:creditLineBank:edit")
	@RequestMapping(value = "findidNumByCustId")
	public Map<String, String> findidNumByCustId(String custId){
		Map<String, String> resultMap = Maps.newConcurrentMap();
		try {
			CustInfo custInfo = custInfoService.get(custId);
			String idNum = custInfo.getIdNum();
			String idType = custInfo.getIdType();
			resultMap.put("idNum", idNum);
			resultMap.put("idType", idType);
			resultMap.put("status", "success");
			return resultMap;
		} catch (Exception e) {
			logger.error("查询身份信息失败，请联系管理员！", e);
			resultMap.put("status", "failed");
			return resultMap;
		}
	}

}