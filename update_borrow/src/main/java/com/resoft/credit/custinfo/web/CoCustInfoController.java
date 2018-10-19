package com.resoft.credit.custinfo.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;
import com.resoft.credit.custWorkInfo.service.CustWorkInfoService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.AreaService;

/**
 * 共借人信息Controller
 * 
 * @author wuxi01
 * @date 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/coCustInfo")
public class CoCustInfoController extends BaseController {

	@Autowired
	private CustInfoService custInfoService;

	@Autowired
	private ApplyRelationService applyRelationService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private CustWorkInfoService custWorkInfoService;

	/**
	 * 共借人详情、编辑、新增
	 * 
	 * @param model
	 * @param applyNo
	 * @param custId
	 * @param readOnly
	 * @return
	 */
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "form")
	public String form(Model model, String applyNo, String custId, String readOnly) {
		CustWorkInfo coCustWorkInfo = new CustWorkInfo();
		CustInfo coCustInfo = new CustInfo();
		if (StringUtils.isNotBlank(applyNo) && StringUtils.isBlank(custId)) {// 新增
			readOnly = "1";// 只读属性置否
			coCustInfo.setCurrApplyNo(applyNo);
			coCustWorkInfo.setCustInfo(coCustInfo);
			coCustWorkInfo.setCurrApplyNo(applyNo);
		} else if (StringUtils.isNotBlank(applyNo) && StringUtils.isNotBlank(custId)) {// 修改
			// 1.查询共借人
			coCustInfo = custInfoService.get(custId);
			coCustInfo.setCurrApplyNo(applyNo);
			// 2.查询共借人工作信息
			Map<String, String> map = Maps.newConcurrentMap();
			map.put("custId", custId);
			coCustWorkInfo = custWorkInfoService.findCustWorkInfoByCustId(map);
			coCustWorkInfo.setCurrApplyNo(applyNo);
			// 3.查询与主借人关系
			ApplyRelation applyRelation = new ApplyRelation();
			applyRelation.setApplyNo(applyNo);
			applyRelation.setCustId(custId);
			List<ApplyRelation> applyRelationList = applyRelationService.findList(applyRelation);
			applyRelation = applyRelationList.get(0);
			String relationForApply = applyRelation.getRelationForApply();
			coCustInfo.setRelationForApply(relationForApply);
		}

		coCustWorkInfo.setCustInfo(coCustInfo);
		model.addAttribute("coCustWorkInfo", coCustWorkInfo);
		model.addAttribute("coCustInfo", coCustInfo);
		model.addAttribute("readOnly", readOnly);
		// 省市级联
		// 9.基本信息省市级联处理
		// 省份下拉列表数据-注册地址+经营地址
		LinkedHashMap<String, String> provinceMap = loadAreaData("1");// 这里的1表示全国
		model.addAttribute("regProvinceMap", provinceMap);
		model.addAttribute("contProvinceMap", provinceMap);
		// 市级下拉列表数据-注册地址
		LinkedHashMap<String, String> regCityMap = loadAreaData(coCustInfo.getRegProvince());
		model.addAttribute("regCityMap", regCityMap);
		LinkedHashMap<String, String> conCityMap = loadAreaData(coCustInfo.getContProvince());
		model.addAttribute("conCityMap", conCityMap);
		// 区县下拉列表数据-注册地址
		LinkedHashMap<String, String> regDistinctMap = loadAreaData(coCustInfo.getRegCity());
		model.addAttribute("regDistinctMap", regDistinctMap);
		LinkedHashMap<String, String> contDistinctMap = loadAreaData(coCustInfo.getContCity());
		model.addAttribute("contDistinctMap", contDistinctMap);
		// 获取省级下拉
		LinkedHashMap<String, String> workProvinceMap = loadAreaData("1");// 这里的1表示全国
		model.addAttribute("companyProvinceMap", workProvinceMap);
		// 获取市级下拉
		LinkedHashMap<String, String> companyCityMap = loadAreaData(coCustWorkInfo.getCompanyProvince());
		model.addAttribute("companyCityMap", companyCityMap);
		// 获取区级下拉
		LinkedHashMap<String, String> companyDistinctMap = loadAreaData(coCustWorkInfo.getCompanyCity());
		model.addAttribute("companyDistinctMap", companyDistinctMap);
		return "app/credit/custinfo/coCustInfoForm";
	}

	/**
	 * 保存共借人信息
	 * 
	 * @param custInfo
	 * @return
	 */
	@RequiresPermissions("credit:custinfo:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView saveCoCust(CustWorkInfo coCustWorkInfo) {
		AjaxView ajaxView = new AjaxView();
		if (coCustWorkInfo == null || coCustWorkInfo.getCustInfo() == null) {
			logger.error("数据出现异常，保存共借人信息失败！");
			ajaxView.setFailed().setMessage("保存共借人信息失败！");
			return ajaxView;
		}
		CustInfo custInfo = coCustWorkInfo.getCustInfo();
		String currApplyNo = custInfo.getCurrApplyNo();
		String relationForApply = custInfo.getRelationForApply();
		try {
			custInfoService.saveCoCustInfo(custInfo, currApplyNo, relationForApply, Constants.ROLE_TYPE_GJR, coCustWorkInfo);
			ajaxView.setSuccess().setMessage("保存共借人信息成功！");
		} catch (Exception e) {
			logger.error("保存共借人信息失败！", e);
			ajaxView.setFailed().setMessage("保存共借人信息失败！");
		}
		return ajaxView;
	}

	/**
	 * 共借人信息列表
	 * 
	 * @param custInfo
	 * @return
	 */
	@RequiresPermissions("credit:custinfo:view")
	@RequestMapping(value = "list")
	public String list(String applyNo, Model model) {
		ApplyRelation coCustInfoRelation = new ApplyRelation();
		coCustInfoRelation.setApplyNo(applyNo);
		coCustInfoRelation.setRoleType(Constants.ROLE_TYPE_GJR);
		List<ApplyRelation> coCustRelations = applyRelationService.findList(coCustInfoRelation);
		List<ApplyRelation> coCustRelationList = new ArrayList<ApplyRelation>();
		List<CustWorkInfo> coCustWorkInfoList = new ArrayList<CustWorkInfo>();
		if (!coCustRelations.isEmpty()) {
			for (int i = 0; i < coCustRelations.size(); i++) {
				ApplyRelation coCustRelation = coCustRelations.get(i);
				if (coCustRelation != null && coCustRelation.getCustId() != null && StringUtils.isNotEmpty(coCustRelation.getCustId())) {
					CustInfo coCustInfo = custInfoService.get(coCustRelation.getCustId());
					coCustRelation.setCustInfo(coCustInfo);
					coCustRelationList.add(coCustRelation);
					// 查询该共借人工作信息
					Map<String, String> map = Maps.newConcurrentMap();
					map.put("custId", coCustRelation.getCustId());
					CustWorkInfo coCustWorkInfo = custWorkInfoService.findCustWorkInfoByCustId(map);
					coCustWorkInfoList.add(coCustWorkInfo);
				}
			}
		}
		model.addAttribute("coCustRelations", coCustRelationList);
		model.addAttribute("coCustWorkInfoList", coCustWorkInfoList);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/custinfo/coCustInfo";
	}

	/**
	 * 删除共借人信息
	 * 
	 * @param custInfo
	 * @return
	 */
	@RequiresPermissions("credit:custinfo:edit")
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxView banchDelete(String ids, String applyNo) {
		AjaxView ajaxView = new AjaxView();
		if (ids != null && !"".equals(ids)) {// 批量删除
			List<String> idList = Arrays.asList(ids.split(","));
			try {
				custInfoService.banchDelete(idList, applyNo, Constants.ROLE_TYPE_GJR);
				ajaxView.setSuccess().setMessage("删除共借人信息成功！");
			} catch (Exception e) {
				logger.error("删除共借人信息失败！", e);
				ajaxView.setSuccess().setMessage("删除共借人信息失败！");
			}
		}
		return ajaxView;
	}

	// 省市级联数据加载
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private LinkedHashMap<String, String> loadAreaData(String areaCode) {
		Map param = Maps.newConcurrentMap();
		LinkedHashMap<String, String> areaMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotEmpty(areaCode)) {
			param.put("parentId", areaCode);// 根据市级ID获取区县数据信息
			List<Map<String, String>> regDistinctList = this.areaService.getTreeNode(param);
			if (null != regDistinctList && regDistinctList.size() > 0) {
				for (Map<String, String> mp : regDistinctList) {
					areaMap.put(mp.get("id"), mp.get("name"));
				}
			}
		}
		return areaMap;
	}

	@ResponseBody
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "ensureAge")
	public AjaxView ensureAge(String card) {
		AjaxView ajaxView = new AjaxView();
		String brithYear = null;
		int age = 0;
		if (card.length() != 0) {
			int nowDateInt = Integer.parseInt(DateUtils.getYear());
			if (card.length() == 15) {
				brithYear = "19" + card.substring(6, 8);
				age = nowDateInt - Integer.parseInt(brithYear);
			} else if (card.length() == 18) {
				brithYear = card.substring(6, 10);
				age = nowDateInt - Integer.parseInt(brithYear);
			} else {
				age = 0;
			}
		} else {
			age = 0;
		}
		ajaxView.setSuccess().put("age", age);
		return ajaxView;
	}

}
