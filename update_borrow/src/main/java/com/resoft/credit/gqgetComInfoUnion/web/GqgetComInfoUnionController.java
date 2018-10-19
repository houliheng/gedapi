package com.resoft.credit.gqgetComInfoUnion.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.gqgetComInfo.entity.WarehouseGoods;
import com.resoft.credit.gqgetComInfo.service.WarehouseGoodsService;
import com.resoft.credit.gqgetComInfoUnion.entity.GqgetComInfoUnion;
import com.resoft.credit.gqgetComInfoUnion.service.GqgetComInfoUnionService;
import com.resoft.credit.mortageEquipmentUnion.entity.MortageEquipmentUnion;
import com.resoft.credit.mortageEquipmentUnion.service.MortageEquipmentUnionService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 联合授信冠e通信息Controller
 * 
 * @author lixing
 * @version 2016-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/gqgetComInfoUnion")
public class GqgetComInfoUnionController extends BaseController {

	@Autowired
	private GqgetComInfoUnionService gqgetComInfoUnionService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private WarehouseGoodsService warehouseGoodsService;
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private MortageEquipmentUnionService mortageEquipmentUnionService;

	@ModelAttribute
	public GqgetComInfoUnion get(@RequestParam(required = false) String id) {
		GqgetComInfoUnion entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = gqgetComInfoUnionService.get(id);
		}
		if (entity == null) {
			entity = new GqgetComInfoUnion();
		}
		return entity;
	}

	@RequiresPermissions("credit:gqgetComInfoUnion:view")
	@RequestMapping(value = { "list", "" })
	public String list(GqgetComInfoUnion gqgetComInfoUnion, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<GqgetComInfoUnion> page = gqgetComInfoUnionService
				.findCustomPage(new Page<GqgetComInfoUnion>(request, response), gqgetComInfoUnion);
		model.addAttribute("page", page);
		return "app/credit/gqgetComInfoUnion/gqgetComInfoUnionList";
	}

	@RequiresPermissions("credit:gqgetComInfoUnion:view")
	@RequestMapping(value = "form")
	public String form(GqgetComInfoUnion gqgetComInfoUnion, Model model, ActTaskParam actTaskParam, String readOnly) {
		if (StringUtils.isNotEmpty(actTaskParam.getHeadUrl())) {
			boolean flag = actTaskParam.getHeadUrl().contains("/claimlist/");
			if (flag) {
				readOnly = "false";
			}
		}
		try {
			Map<String, String> param1 = new HashMap<String, String>();
			param1.put("applyNo", actTaskParam.getApplyNo());
			param1.put("approId", gqgetComInfoUnion.getApproveId());
			gqgetComInfoUnion = gqgetComInfoUnionService.getGqgetComInfoByParam(param1);

			Map<String, String> param = new HashMap<String, String>();
			param.put("applyNo", actTaskParam.getApplyNo());
			List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(param);
			String productType = null;
			if (checkApproveList.size() > 0) {
				productType = checkApproveList.get(0).getApproProductTypeCode();
			}
			ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
			if (Constants.PRODUCT_TYPE_CG.equals(productType)) {
				param1.put("applyNo", null);
				CompanyInfo companyInfo = companyInfoService.getCompanyInfoByApplyNoAndCustId(param1);
				if (companyInfo == null) {
					companyInfo = new CompanyInfo();
				} else {
					if (StringUtils.isNull(companyInfo.getUnSocCreditNo())) {
						companyInfo.setUnSocCreditNo(companyInfo.getBusiLicRegNo());
					}
				}
				model.addAttribute("companyInfo", companyInfo);
			} else {
				if (Constants.CUST_TYPE_QY.equals(applyRegister.getCustType())) {
					param1.put("applyNo", null);
					CompanyInfo companyInfo = companyInfoService.getCompanyInfoByApplyNoAndCustId(param1);
					if (companyInfo == null) {
						companyInfo = new CompanyInfo();
					} else {
						if (StringUtils.isNull(companyInfo.getUnSocCreditNo())) {
							companyInfo.setUnSocCreditNo(companyInfo.getBusiLicRegNo());
						}
					}
					model.addAttribute("companyInfo", companyInfo);
				}
			}
			model.addAttribute("custType", applyRegister.getCustType());
			model.addAttribute("productType", productType);
			model.addAttribute("readOnly", readOnly);
			model.addAttribute("actTaskParam", actTaskParam);
		} catch (Exception e) {
			logger.error("查询冠E通信息或批复信息失败", e);
		}

		model.addAttribute("gqgetComInfo", gqgetComInfoUnion);
		return "app/credit/gqgetComInfoUnion/gqgetComInfoUnionForm";
	}

	@RequiresPermissions("credit:gqgetComInfoUnion:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(ActTaskParam actTaskParam, GqgetComInfoUnion gqgetComInfoUnion) {
		AjaxView ajaxView = new AjaxView();
		try {
			gqgetComInfoUnionService.save(gqgetComInfoUnion);
			ajaxView.setSuccess().setMessage("保存冠E通信息成功");
		} catch (Exception e) {
			logger.error("保存冠E通信息失败", e);
			ajaxView.setFailed().setMessage("保存冠E通信息失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:gqgetComInfoUnion:edit")
	@RequestMapping(value = "delete")
	public String delete(GqgetComInfoUnion gqgetComInfoUnion, RedirectAttributes redirectAttributes) {
		gqgetComInfoUnionService.delete(gqgetComInfoUnion);
		addMessage(redirectAttributes, "删除联合授信冠e通信息成功");
		return "redirect:" + Global.getAdminPath() + "/gqgetComInfoUnion/gqgetComInfoUnion/?repage";
	}

	@RequiresPermissions("credit:gqgetComInfoUnion:edit")
	@RequestMapping(value = "mortEquip")
	public String mortEquipment(ActTaskParam actTaskParam, MortageEquipmentUnion mortageEquipmentUnion,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		Map<String, String> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("approveId", mortageEquipmentUnion.getApproveId());
		List<MortageEquipmentUnion> mortEquipInfoList = mortageEquipmentUnionService.getPageByApplyNo(params);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("approveId", mortageEquipmentUnion.getApproveId());
		model.addAttribute("mortEquipInfoList", mortEquipInfoList);
		return "app/credit/mortageEquipmentUnion/mortageEquipmentUnionList";
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "warehouseList")
	public String warehouseList(ActTaskParam actTaskParam, WarehouseGoods warehouseGoods, Model model) {
		List<WarehouseGoods> warehouseGoodsList = warehouseGoodsService.getPageByApplyNoUnion(warehouseGoods);
		model.addAttribute("warehouseGoodsList", warehouseGoodsList);
		model.addAttribute("warehouseGoods", warehouseGoods);
		return "app/credit/gqgetComInfoUnion/warehouseGoodsListUnion";
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "warehouseForm")
	public String warehouseForm(WarehouseGoods warehouseGoods, Model model, HttpServletRequest request) {
		WarehouseGoods warehouseGoodsTemp = warehouseGoodsService.getWarehouseGoods(warehouseGoods.getId());
		if (!(StringUtils.isNull(warehouseGoodsTemp))) {
			warehouseGoods = warehouseGoodsTemp;
		}
		model.addAttribute("warehouseGoods", warehouseGoods);
		return "app/credit/gqgetComInfoUnion/warehouseGoodsFormUnion";
	}

	@ResponseBody
	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "warehouseSave")
	public AjaxView warehouseSave(WarehouseGoods warehouseGoods) {
		AjaxView rtn = new AjaxView();
		try {
			if (StringUtils.isNull(warehouseGoods.getId())) {
				warehouseGoods.preInsert();
				warehouseGoodsService.saveWarehouseGoodsUnion(warehouseGoods);
			} else {
				warehouseGoods.preUpdate();
				warehouseGoodsService.updateWarehouseGoodsUnion(warehouseGoods);
			}
			rtn.put("approId", warehouseGoods.getApproId());
			rtn.setSuccess().setMessage("保存库存货物成功!");
		} catch (Exception e) {
			logger.error("保存库存货物失败", e);
			rtn.setFailed().setMessage("保存库存货物失败!");
		}
		return rtn;
	}

}