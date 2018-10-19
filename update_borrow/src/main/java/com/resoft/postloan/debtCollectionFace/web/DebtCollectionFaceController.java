package com.resoft.postloan.debtCollectionFace.web;

import java.util.LinkedHashMap;
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
import com.resoft.multds.accounting.PLContract.entity.PLContract;
import com.resoft.multds.accounting.PLContract.service.PLContractService;
import com.resoft.postloan.collectionPaymentInfo.entity.CollectionPaymentInfo;
import com.resoft.postloan.collectionPaymentInfo.service.CollectionPaymentInfoService;
import com.resoft.postloan.common.utils.AjaxView;
import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.debtCollectionFace.entity.DebtCollectionFace;
import com.resoft.postloan.debtCollectionFace.service.DebtCollectionFaceService;
import com.resoft.postloan.debtColletion.entity.DebtCollection;
import com.resoft.postloan.debtColletion.service.DebtCollectionService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.AreaService;

/**
 * 上门催收Controller
 * 
 * @author wangguodong
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/debtCollectionFace")
public class DebtCollectionFaceController extends BaseController {

	@Autowired
	private AreaService areaService;// 区域地质service

	@Autowired
	private DebtCollectionFaceService debtCollectionFaceService;

	@Autowired
	private PLContractService plContractService;

	@Autowired
	private CollectionPaymentInfoService collectionPaymentInfoService;

	@Autowired
	private DebtCollectionService debtCollectionService;

	@ModelAttribute
	public DebtCollectionFace get(@RequestParam(required = false) String id) {
		DebtCollectionFace entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = debtCollectionFaceService.get(id);
		}
		if (entity == null) {
			entity = new DebtCollectionFace();
		}
		return entity;
	}

	@RequiresPermissions("postloan:debtCollectionFace:view")
	@RequestMapping(value = "index")
	public String index(DebtCollection debtCollection, CollectionPaymentInfo collectionPaymentInfo, Model model) {
		try {
			// 查询合同信息
			PLContract plContract = plContractService.get(debtCollection.getContractNo());
			model.addAttribute("plContract", plContract);
			// 查询账务合同待催收统计表
			debtCollection = debtCollectionService.get(debtCollection.getId());
			model.addAttribute("debtCollection", debtCollection);
			// 客户汇款详情信息
			List<CollectionPaymentInfo> collectionPaymentInfoList = collectionPaymentInfoService.findList(collectionPaymentInfo);
			model.addAttribute("collectionPaymentInfoList", collectionPaymentInfoList);
		} catch (Exception e) {
			logger.error("查询催收信息失败", e);
		}
		return "app/postloan/debtCollectionFace/debtCollectionFaceIndex";
	}

	@RequiresPermissions("postloan:debtCollectionFace:view")
	@RequestMapping(value = { "list", "" })
	public String list(DebtCollectionFace debtCollectionFace, String readOnly, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DebtCollectionFace> page = debtCollectionFaceService.findPage(new Page<DebtCollectionFace>(request, response), debtCollectionFace);
		model.addAttribute("page", page);
		model.addAttribute("readOnly", readOnly);
		return "app/postloan/debtCollectionFace/debtCollectionFaceList";
	}

	// 省市级联数据加载
	private LinkedHashMap<String, String> loadAreaData(String areaCode) {
		Map<String, String> param = Maps.newConcurrentMap();
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

	@RequiresPermissions("postloan:debtCollectionFace:view")
	@RequestMapping(value = "form")
	public String form(DebtCollectionFace debtCollectionFace, String readOnly, Model model) {
		if ("true".equals(readOnly)) {
			model.addAttribute("readOnly", true);
		}
		// 省份下拉列表数据
		LinkedHashMap<String, String> provinceMap = loadAreaData("1");// 这里的1表示全国
		model.addAttribute("debtProvinceMap", provinceMap);
		// 市级下拉列表数据
		LinkedHashMap<String, String> regCityMap = loadAreaData(debtCollectionFace.getAddressPro());
		model.addAttribute("debtCityMap", regCityMap);
		// 区县下拉列表数据
		LinkedHashMap<String, String> regDistinctMap = loadAreaData(debtCollectionFace.getAddressCity());
		model.addAttribute("debtDistinctMap", regDistinctMap);

		model.addAttribute("debtCollectionFace", debtCollectionFace);
		return "app/postloan/debtCollectionFace/debtCollectionFaceForm";
	}

	@ResponseBody
	@RequiresPermissions("postloan:debtCollectionFace:edit")
	@RequestMapping(value = "save")
	public AjaxView save(DebtCollectionFace debtCollectionFace, Model model, RedirectAttributes redirectAttributes) {
		AjaxView ajaxView = new AjaxView();
		try {
			debtCollectionFaceService.save(debtCollectionFace);
			ajaxView.setSuccess().setMessage("保存上门催收成功");
			ajaxView.put("debtId", debtCollectionFace.getDebtId());
		} catch (Exception e) {
			logger.error("保存上门催收失败", e);
			ajaxView.setFailed().setMessage("保存上门催收失败");
		}
		addMessage(redirectAttributes, "保存上门催收成功");
		return ajaxView;
	}

	@RequiresPermissions("postloan:debtCollectionFace:edit")
	@RequestMapping(value = "delete")
	public String delete(DebtCollectionFace debtCollectionFace, RedirectAttributes redirectAttributes) {
		debtCollectionFaceService.delete(debtCollectionFace);
		addMessage(redirectAttributes, "删除上门催收成功");
		return "redirect:" + Global.getAdminPath() + "/debtCollectionFace/debtCollectionFace/?repage";
	}

	@ResponseBody
	@RequiresPermissions("postloan:debtCollectionFace:edit")
	@RequestMapping(value = "finishDebtCollectionFace")
	public AjaxView finishDebtCollectionFace(DebtCollectionFace debtCollectionFace) {
		AjaxView ajaxView = new AjaxView();
		try {
			Map<String, Object> param = Maps.newHashMap();
			param.put("currCollectionStatus", Constants.DEBT_COLLECTION_STATUS_CSJS);
			param.put("contractNo", debtCollectionFace.getContractNo());
			param.put("debtId", debtCollectionFace.getDebtId());
			debtCollectionService.updateCurrCollectionStatus(param);
			ajaxView.setSuccess().setMessage("操作成功");
		} catch (Exception e) {
			ajaxView.setFailed().setMessage("操作失败");
			logger.error("操作失败", e);
		}
		return ajaxView;
	}

}