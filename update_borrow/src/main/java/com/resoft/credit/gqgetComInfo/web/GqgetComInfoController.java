package com.resoft.credit.gqgetComInfo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.opensaml.xmlsec.signature.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApprove.service.CheckApproveService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.gqgetAssertHouse.entity.GqgetAssetHouse;
import com.resoft.credit.gqgetAssertHouse.service.GqgetAssetHouseService;
import com.resoft.credit.gqgetAssetCar.entity.GqgetAssetCar;
import com.resoft.credit.gqgetAssetCar.service.GqgetAssetCarService;
import com.resoft.credit.gqgetComInfo.entity.BankLoan;
import com.resoft.credit.gqgetComInfo.entity.GqgetComInfo;
import com.resoft.credit.gqgetComInfo.entity.MortageEquipment;
import com.resoft.credit.gqgetComInfo.entity.WarehouseGoods;
import com.resoft.credit.gqgetComInfo.service.BankLoanService;
import com.resoft.credit.gqgetComInfo.service.GqgetComInfoService;
import com.resoft.credit.gqgetComInfo.service.MortageEquipmentService;
import com.resoft.credit.gqgetComInfo.service.WarehouseGoodsService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 冠E通Controller
 * 
 * @author yanwanmei
 * @version 2016-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/gqgetComInfo")
public class GqgetComInfoController extends BaseController {

	@Autowired
	private GqgetComInfoService gqgetComInfoService;
	@Autowired
	private CheckApproveService checkApproveService;
	@Autowired
	private BankLoanService bankLoanService;
	@Autowired
	private MortageEquipmentService mortEquipService;
	@Autowired
	private WarehouseGoodsService warehouseGoodsService;
	@Autowired
	private GqgetAssetHouseService gqgetAssetHouseService;
	@Autowired
	private GqgetAssetCarService gqgetAssetCarService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private ApplyRegisterService applyRegisterService;

	@ModelAttribute
	public GqgetComInfo get(@RequestParam(required = false) String id) {
		GqgetComInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = gqgetComInfoService.get(id);
		}
		if (entity == null) {
			entity = new GqgetComInfo();
		}
		return entity;
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(GqgetComInfo gqgetComInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GqgetComInfo> page = gqgetComInfoService.findPage(new Page<GqgetComInfo>(request, response), gqgetComInfo);
		model.addAttribute("page", page);
		return "app/credit/gqgetComInfo/gqgetComInfoList";
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "form")
	public String form(ActTaskParam actTaskParam, String readOnly, GqgetComInfo gqgetComInfo, BankLoan bankLoan, Model model) {
		if (StringUtils.isNotEmpty(actTaskParam.getHeadUrl())) {
			boolean flag = actTaskParam.getHeadUrl().contains("/claimlist/");
			if (flag) {
				readOnly = "false";
			}
		}
		try {
			gqgetComInfo.setApplyNo(actTaskParam.getApplyNo());
			GqgetComInfo hasGqgetComInfo = gqgetComInfoService.getGqgetComInfoByApplyNo(actTaskParam.getApplyNo());
			BankLoan hasBankLoan = bankLoanService.getBankLoanByApplyNo(actTaskParam.getApplyNo());
			if (hasBankLoan != null) {
				hasGqgetComInfo.setBankLoan(hasBankLoan);
				model.addAttribute("gqgetComInfo", hasGqgetComInfo);
			} else {
				model.addAttribute("gqgetComInfo", gqgetComInfo);
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("applyNo", actTaskParam.getApplyNo());
			List<CheckApprove> checkApproveList = checkApproveService.getCheckApproveByApplyNo(param);
			String productType = null;
			if (checkApproveList.size() > 0) {
				productType = checkApproveList.get(0).getApproProductTypeCode();
			}
			ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
			if (Constants.PRODUCT_TYPE_CG.equals(productType)) {
				CompanyInfo companyInfo = companyInfoService.getCompanyInfoByApplyNoAndCustId(param);
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
					CompanyInfo companyInfo = companyInfoService.getCompanyInfoByApplyNoAndCustId(param);
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
		return "app/credit/gqgetComInfo/gqgetComInfoForm";
	}

	@RequiresPermissions("credit:gqgetComInfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxView save(ActTaskParam actTaskParam, GqgetComInfo gqgetComInfo) {
		AjaxView ajaxView = new AjaxView();
		try {
			String applyNo = gqgetComInfo.getApplyNo();
			if (!(gqgetComInfo.getPropertyHouse() == null) && !(gqgetComInfo.getPropertyHouse().isEmpty()) && gqgetComInfo.getPropertyHouse().equals("1")) {
				GqgetAssetHouse house = new GqgetAssetHouse();
				house.setApplyNo(applyNo);
				List<GqgetAssetHouse> houseList = gqgetAssetHouseService.findList(house);
				if (houseList.size() == 0) {
					if (gqgetComInfo.getBankLoan().getId() != null) {
						ajaxView.put("bankLoanId", gqgetComInfo.getBankLoan().getId());
					}
					ajaxView.setSuccess().setMessage("房屋资产必须填写");
					return ajaxView;
				}
			}
			if (!(gqgetComInfo.getPropertyCar() == null) && !(gqgetComInfo.getPropertyCar().isEmpty()) && gqgetComInfo.getPropertyCar().equals("1")) {
				GqgetAssetCar car = new GqgetAssetCar();
				car.setGqgetApplyNo(applyNo);
				List<GqgetAssetCar> carList = gqgetAssetCarService.findList(car);
				if (carList.size() == 0) {
					if (gqgetComInfo.getBankLoan().getId() != null) {
						ajaxView.put("bankLoanId", gqgetComInfo.getBankLoan().getId());
					}
					ajaxView.setSuccess().setMessage("车产信息必须填写");
					return ajaxView;
				}
			}
			gqgetComInfoService.saveGqgetData(actTaskParam, gqgetComInfo);
			ajaxView.put("bankLoanId", gqgetComInfo.getBankLoan().getId());
			ajaxView.setSuccess().setMessage("保存冠E通信息成功");
		} catch (Exception e) {
			logger.error("保存冠E通信息失败", e);
			ajaxView.setFailed().setMessage("保存冠E通信息失败");
		}
		return ajaxView;
	}

	@RequiresPermissions("credit:gqgetComInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GqgetComInfo gqgetComInfo, RedirectAttributes redirectAttributes) {
		gqgetComInfoService.delete(gqgetComInfo);
		addMessage(redirectAttributes, "删除冠E通成功");
		return "redirect:" + Global.getAdminPath() + "/gqgetComInfo/gqgetComInfo/?repage";
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "mortEquip")
	public String mortEquipment(ActTaskParam actTaskParam, MortageEquipment mortageEquipment, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<MortageEquipment> mortEquipInfoList = mortEquipService.getPageByApplyNo(applyNo);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("mortEquipInfoList", mortEquipInfoList);
		return "app/credit/gqgetComInfo/mortEquipInfoList";
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "equipform")
	public String equipform(MortageEquipment mortageEquipment, Model model, HttpServletRequest request) {
		model.addAttribute("mortageEquipment", mortageEquipment);
		if ("true".equals(request.getParameter("readOnly"))) {
			model.addAttribute("readOnly", true);
		}
		return "app/credit/gqgetComInfo/mortEquipInfoForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "equipdelete")
	public AjaxView equipdelete(MortageEquipment mortageEquipment, RedirectAttributes redirectAttributes) {
		AjaxView rtn = new AjaxView();
		try {
			mortEquipService.delete(mortageEquipment);
			rtn.setSuccess().setMessage("删除设备抵质押物成功!");
		} catch (Exception e) {
			logger.error("删除设备抵质押物失败", e);
			rtn.setFailed().setMessage("删除设备抵质押物失败!");
		}
		return rtn;
	}

	@ResponseBody
	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "equipsave")
	public AjaxView equipsave(MortageEquipment mortageEquipment, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		AjaxView rtn = new AjaxView();
		try {
			mortEquipService.save(mortageEquipment);
			rtn.setSuccess().setMessage("保存设备抵质押物成功!");
		} catch (Exception e) {
			logger.error("保存设备抵质押物失败", e);
			rtn.setFailed().setMessage("保存设备抵质押物失败!");
		}
		return rtn;
	}
	@ResponseBody
	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "zichanSave")
	public AjaxView zichanSave(GqgetComInfo gqgetComInfo, Model model,  HttpServletRequest request) {
		AjaxView rtn = new AjaxView();
		try {
			gqgetComInfoService.saveZichan(new ActTaskParam() ,gqgetComInfo);
//			logger.info("-----------------------------------------------------------"+ JSONObject.toJSONString(gqgetComInfo));
			rtn.setSuccess().setMessage("保存资产信息成功!");
		} catch (Exception e) {
			logger.error("保存资产信息失败", e);
			rtn.setFailed().setMessage("保存资产信息失败!");
		}
		return rtn;
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "warehouseList")
	public String warehouseList(ActTaskParam actTaskParam, WarehouseGoods warehouseGoods, Model model) {
		String applyNo = actTaskParam.getApplyNo();
		List<WarehouseGoods> warehouseGoodsList = warehouseGoodsService.getPageByApplyNo(applyNo);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("warehouseGoodsList", warehouseGoodsList);
		return "app/credit/gqgetComInfo/warehouseGoodsList";
	}

	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "warehouseForm")
	public String warehouseForm(WarehouseGoods warehouseGoods, Model model, HttpServletRequest request) {
		WarehouseGoods warehouseGoodsTemp = warehouseGoodsService.get(warehouseGoods.getId());
		if (!(StringUtils.isNull(warehouseGoodsTemp))) {
			warehouseGoods = warehouseGoodsTemp;
		}
		model.addAttribute("warehouseGoods", warehouseGoods);
		return "app/credit/gqgetComInfo/warehouseGoodsForm";
	}

	@ResponseBody
	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "warehouseDelete")
	public AjaxView warehouseDelete(WarehouseGoods warehouseGoods) {
		AjaxView rtn = new AjaxView();
		try {
			warehouseGoodsService.delete(warehouseGoods);
			rtn.setSuccess().setMessage("删除库存货物成功!");
		} catch (Exception e) {
			logger.error("删除库存货物失败", e);
			rtn.setFailed().setMessage("删除库存货物失败!");
		}
		return rtn;
	}

	@ResponseBody
	@RequiresPermissions("credit:gqgetComInfo:view")
	@RequestMapping(value = "warehouseSave")
	public AjaxView warehouseSave(WarehouseGoods warehouseGoods) {
		AjaxView rtn = new AjaxView();
		try {
			warehouseGoodsService.save(warehouseGoods);
			rtn.setSuccess().setMessage("保存库存货物成功!");
		} catch (Exception e) {
			logger.error("保存库存货物失败", e);
			rtn.setFailed().setMessage("保存库存货物失败!");
		}
		return rtn;
	}

	@RequestMapping(value = "repaySourceIndex")
	public String repaySourceIndex(ActTaskParam actTaskParam, String readOnly, Model model) {
		GqgetComInfo gqgetComInfo = gqgetComInfoService.getGqgetComInfoByApplyNo(actTaskParam.getApplyNo());
		if (gqgetComInfo == null) {
			gqgetComInfo = new GqgetComInfo();
		}
		model.addAttribute("repaySource", gqgetComInfo);
		model.addAttribute("actTaskParam", actTaskParam);
		if (!Constants.TRUE.equals(readOnly) && !Constants.UNDER_DQGLR.equals(actTaskParam.getTaskDefKey())) {
			readOnly = Constants.TRUE;
		}
		model.addAttribute("readOnly", readOnly);
		return "app/credit/gqgetComInfo/repaySourceIndex";
	}

	@RequestMapping(value = "saveRepaySource")
	@ResponseBody
	public AjaxView saveRepaySource(GqgetComInfo gqgetComInfo, ActTaskParam actTaskParam, Model model) {
		AjaxView result = new AjaxView();
		GqgetComInfo oldComInfo = gqgetComInfoService.getGqgetComInfoByApplyNo(actTaskParam.getApplyNo());
		if (oldComInfo != null) {
			gqgetComInfo.setId(oldComInfo.getId());
		}
		try {
			gqgetComInfoService.saveRepaySource(gqgetComInfo);
			result.setSuccess().setMessage("保存还款来源成功");
			addMessage(model, "保存还款来源成功");
		} catch (Exception e) {
			result.setFailed().setMessage("保存还款来源失败!");
			addMessage(model, "保存还款来源失败!");
			logger.error("保存还款来源失败!", e);
		}
		return result;
	}
}