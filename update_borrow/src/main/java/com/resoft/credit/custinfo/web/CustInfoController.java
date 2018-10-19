package com.resoft.credit.custinfo.web;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.industry.entity.Industry;
import com.resoft.credit.industry.service.IndustryService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.AreaService;

/**
 * @reqno:H1601220021
 * @date-designer:2016年1月28日-gaofeng
 * @date-author:2016年1月28日-gaofeng:客户基本信息Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/custinfo/custInfo")
public class CustInfoController extends BaseController {

	@Autowired
	private CustInfoService custInfoService;

	@Autowired
	private AreaService areaService;// 区域地质service

	@Autowired
	private IndustryService industryService;// 行业分类service

	@ModelAttribute
	public CustInfo get(@RequestParam(required = false) String id) {
		CustInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = custInfoService.get(id);
		}
		if (entity == null) {
			entity = new CustInfo();
		}
		return entity;
	}

	@RequiresPermissions("credit:custinfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(CustInfo custInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustInfo> page = custInfoService.findPage(new Page<CustInfo>(request, response), custInfo);
		model.addAttribute("page", page);
		return "app/credit/custinfo/custInfoList";
	}

	// 查看客户详情
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "form")
	public String form(CustInfo custInfo, Model model) {

		// 初始化行业门类
		List<Industry> categoryMainList = industryService.findByParentCode("root");
		model.addAttribute("categoryMainList", categoryMainList);
		// 初始化行业大类
		if (StringUtils.isNotEmpty(custInfo.getCategoryLarge())) {
			List<Industry> categoryLargeList = industryService.findByParentCode(custInfo.getCategoryMain());
			model.addAttribute("categoryLargeList", categoryLargeList);
		}
		// 初始化行业中类
		if (StringUtils.isNotEmpty(custInfo.getCategoryMedium())) {
			List<Industry> categoryMediumList = industryService.findByParentCode(custInfo.getCategoryLarge());
			model.addAttribute("categoryMediumList", categoryMediumList);
		}
		// 初始化行业小类
		if (StringUtils.isNotEmpty(custInfo.getCategorySmall())) {
			List<Industry> categorySmallList = industryService.findByParentCode(custInfo.getCategoryMedium());
			model.addAttribute("categorySmallList", categorySmallList);
		}
		model.addAttribute("readOnly", true);
		model.addAttribute("custInfo", custInfo);
		return "app/credit/custinfo/custInfoForm";
	}


	/**
	 * 申请录入-客户信息-保存
	 * 
	 * @param custInfo
	 * @return
	 */
	@RequiresPermissions("credit:custinfo:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView save(CustInfo custInfo) {
		AjaxView ajaxView = new AjaxView();
		String currApplyNo = custInfo.getCurrApplyNo();
		try {
			String relationId = custInfoService.saveMainCust(custInfo, currApplyNo, ajaxView);
			ajaxView.setSuccess().setMessage("保存主借人信息成功！");
			ajaxView.put("custIdForCustWork", custInfo.getId());
			ajaxView.put("relationId", relationId);
			ajaxView.put("id", custInfo.getId());
		} catch (Exception e) {
			logger.error("保存主借人信息失败！", e);
			ajaxView.setFailed().setMessage("保存主借人信息失败！");
		}
		return ajaxView;
	}

	/**
	 * 申请录入-客户信息-保存配偶信息
	 * 
	 * @param custInfo
	 * @return
	 */
	@RequiresPermissions("credit:custinfo:edit")
	@ResponseBody
	@RequestMapping(value = "saveMate")
	public AjaxView saveMate(@RequestBody CustInfo custInfo) {
		AjaxView ajaxView = new AjaxView();
		String currApplyNo = custInfo.getCurrApplyNo();
		try {
			custInfoService.saveMateCust(custInfo, currApplyNo, Constants.CONTACT_RELATIONS_MATE, Constants.ROLE_TYPE_MATE);
			ajaxView.setSuccess().setMessage("保存主借人配偶信息成功！");
			ajaxView.put("id", custInfo.getId());
		} catch (Exception e) {
			logger.error("保存主借人配偶信息失败！", e);
			ajaxView.setFailed().setMessage("保存主借人配偶信息失败！");
		}
		return ajaxView;
	}

	/**
	 * CRE_信贷审批_申请录入_客户信息_基本信息【默认数据项】展现
	 * 关联省市级联查询，目前在该页面存在4次的省市级联数据查询，每次都单独的查询，会造成代码务必冗余
	 * ，所以这里对查询做了简单封装，这样将代码最大程度的缩减
	 * 这里的基本思路就是：将第一次查询出的省市级联数据作为标记，后面的省市级联数据都根该标记做对比
	 * ，如果一致则直接使用标记中查询出的省市级联数据，如果不一致才进行数据库的查询
	 * 所以，在实际使用中，应该把最可能作为参照的省市级联数据作为单独查询的标记，在这里的查询中是把户籍地址的省市级联数据作为标记查询
	 * 
	 * @param cityFlagVal
	 *            用来参照的省市级联值-该值是同一个方法内首次查询的省市级联数据
	 * @param currCityVal
	 *            当前将要用于查询的省市级联数据的值 如果该值和上面的一样，就能说明将要查询的数据和用于参照的查询数据为同样的省市级联数据
	 * @param cityFlagMap
	 *            首次查询出的省市级联Map数据
	 * @param currkey
	 *            将要查询出的省市级联Map数据存储于Model中对应的Key
	 * @param parentID
	 *            用于查询省市级联数据的上级Key，用来查询该层级下的数据信息
	 * @param model
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void relateCityInit(String cityFlagVal, String currCityVal, LinkedHashMap<String, String> cityFlagMap, String currkey, String parentID, Model model) {
		if (null != cityFlagVal && cityFlagVal.equals(currCityVal)) {// 户籍地城市代码等于现居地城市代码
			model.addAttribute(currkey, cityFlagMap);
		} else {
			LinkedHashMap<String, String> queryMap = new LinkedHashMap<String, String>();
			if (StringUtils.isNotEmpty(parentID)) {
				Map param = Maps.newConcurrentMap();
				param.put("parentId", parentID);
				List<Map<String, String>> contCityList = this.areaService.getTreeNode(param);
				if (null != contCityList && contCityList.size() > 0) {
					for (Map<String, String> mp : contCityList) {
						queryMap.put(mp.get("id"), mp.get("name"));
					}
				}
			}
			model.addAttribute(currkey, queryMap);
		}
	}

	@ResponseBody
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "validateIdNum")
	public AjaxView validateIdNum(String applyNo,String id ,String idNum,String mobileNum) {
		AjaxView ajaxView = new AjaxView();
		if(StringUtils.isNull(id)){
			id="";
		}
		//查询主借人，担保人，配偶的身份证和手机号码
		List<Map<String, String>> idNums = custInfoService.getAllCardNoByApplyNo(applyNo);
		//查询联系人手机号
		List<Map<String, String>> mobiles = custInfoService.getAllMobileByApplyNo(applyNo);
		for (int i = 0; i < idNums.size(); i++) {
			if(!(StringUtils.isNull(idNum))){
				if (!(id.equals(idNums.get(i).get("id"))) && idNum.equals(idNums.get(i).get("idNum"))) {
					ajaxView.setFailed().setMessage("身份证号码不可与其他相关人员重复。");
					return ajaxView;
				}
			}
			if(!(StringUtils.isNull(mobileNum))){
				if (!(id.equals(idNums.get(i).get("id"))) && mobileNum.equals(idNums.get(i).get("mobileNum"))) {
					ajaxView.setFailed().setMessage("手机号码不可与其他相关人员重复。");
					return ajaxView;
				}
			}
		}
		if(mobiles.size() != 0){
			for(int i = 0; i < mobiles.size(); i++){
				if(!(StringUtils.isNull(mobileNum))){
					if (!(id.equals(mobiles.get(i).get("id"))) && mobileNum.equals(mobiles.get(i).get("mobileNum"))) {
						ajaxView.setFailed().setMessage("手机号码不可与其他相关人员重复。");
						return ajaxView;
					}
				}
			}
		}
		ajaxView.setSuccess();
		return ajaxView;
	}

	// 根据证件号生成出生日期和年龄
	@ResponseBody
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = "ensureAge")
	public AjaxView ensureAge(String card) {
		AjaxView ajaxView = new AjaxView();
		String birthYear = null;
		String birth = null;
		int age = 0;
		if (card.length() != 0) {
			int nowDateInt = Integer.parseInt(DateUtils.getYear());
			if (card.length() == 15) {
				birthYear = "19" + card.substring(6, 8);
				birth = birthYear + "-" + card.substring(8, 10) + "-" + card.substring(10, 12);
				age = nowDateInt - Integer.parseInt(birthYear);
			} else if (card.length() == 18) {
				birthYear = card.substring(6, 10);
				birth = birthYear + "-" + card.substring(10, 12) + "-" + card.subSequence(12, 14);
				age = nowDateInt - Integer.parseInt(birthYear);
			}
		}
		ajaxView.setSuccess().put("birth", birth);
		ajaxView.setSuccess().put("age", age);
		return ajaxView;
	}
	
	//根据身份证号查询用户信息
		@ResponseBody
		@RequiresPermissions("credit:custinfo:edit")
		@RequestMapping(value = "attachInfo")
		public AjaxView attachInfo(CustInfo custInfo, Model model){
			AjaxView ajaxView = new AjaxView();
			String birthYear = null;
			String birth = null;
			String card = custInfo.getIdNum();
			int age = 0;
			if (card.length() != 0) {
				int nowDateInt = Integer.parseInt(DateUtils.getYear());
				if (card.length() == 15) {
					birthYear = "19" + card.substring(6, 8);
					birth = birthYear + "-" + card.substring(8, 10) + "-" + card.substring(10, 12);
					age = nowDateInt - Integer.parseInt(birthYear);
				} else if (card.length() == 18) {
					birthYear = card.substring(6, 10);
					birth = birthYear + "-" + card.substring(10, 12) + "-" + card.subSequence(12, 14);
					age = nowDateInt - Integer.parseInt(birthYear);
				}
			}
			
			custInfo = custInfoService.getInfoByCard(card);
			if(StringUtils.isNull(custInfo)){
				custInfo = new CustInfo();
			}
			custInfo.setBirthDay(birth);
			custInfo.setAgeNo(String.valueOf(age));
			custInfo.setId(null);
			ajaxView.setSuccess().put("custInfo", custInfo);
			
			// 初始化行业大类
			if (StringUtils.isNotEmpty(custInfo.getCategoryLarge())) {
				List<Industry> categoryLargeList = industryService.findByParentCode(custInfo.getCategoryMain());
				ajaxView.setSuccess().put("categoryLargeList", categoryLargeList);
			}
			// 初始化行业中类
			if (StringUtils.isNotEmpty(custInfo.getCategoryMedium())) {
				List<Industry> categoryMediumList = industryService.findByParentCode(custInfo.getCategoryLarge());
				ajaxView.setSuccess().put("categoryMediumList", categoryMediumList);
			}
			// 初始化行业小类
			if (StringUtils.isNotEmpty(custInfo.getCategorySmall())) {
				List<Industry> categorySmallList = industryService.findByParentCode(custInfo.getCategoryMedium());
				ajaxView.setSuccess().put("categorySmallList", categorySmallList);
			}
			
			return ajaxView;
		}
}