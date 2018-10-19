package com.resoft.credit.custinfo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contactInfo.entity.ContactInfo;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;
import com.resoft.credit.custWorkInfo.service.CustWorkInfoService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.industry.entity.Industry;
import com.resoft.credit.industry.service.IndustryService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/custinfo/custInfoIndex")
public class CustInfoIndexController extends BaseController {
	@Autowired
	private CustInfoService custInfoService;// 客户基本信息service

	@Autowired
	private CustWorkInfoService custWorkInfoService;// 客户工作信息service

	@Autowired
	private CompanyInfoService companyInfoService;// 企业信息service

	@Autowired
	private IndustryService industryService;// 行业分类service

	@Autowired
	private ApplyRelationService applyRelationService;// 关系表service

	@Autowired
	private ApplyInfoService applyInfoService;

	@RequiresPermissions("credit:custinfo:view")
	@RequestMapping(value = "index")
	public String index(ActTaskParam actTaskParam, Model model, String readOnly) {
		// 1.流程信息
		String applyNo = actTaskParam.getApplyNo();
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);

		// 2.查询主借人关系表数据
		ApplyRelation zjrRelation = new ApplyRelation();
		zjrRelation.setApplyNo(applyNo);
		zjrRelation.setRoleType(Constants.ROLE_TYPE_ZJR);
		List<ApplyRelation> relations = applyRelationService.findList(zjrRelation);
		if (!relations.isEmpty()) {
			zjrRelation = relations.get(0);
		}
		model.addAttribute("zjrRelation", zjrRelation);

		// 3.查询主借人信息
		List<CustInfo> custInfoList = custInfoService.findMainBorrowerByApplyNo(applyNo);

		CustInfo custInfo = new CustInfo();
		try {
			custInfo = custInfoList.get(0);
		} catch (Exception e) {
			logger.error("该客户不存在，可能是客户登记环节出现异常或数据库数据异常", e);
			model.addAttribute("message", "该客户不存在，请联系管理员！");
			custInfo = new CustInfo();
			model.addAttribute("custInfo", custInfo);
			return "app/credit/custinfo/custInfoIndex";
		}
		if (custInfo == null) {
			custInfo = new CustInfo();
		}
		// 根据主借人查询申请信息：
		ApplyInfo applyInfo = applyInfoService.findApplyInfoByApplyNo(applyNo);
		try {
			if (applyInfo != null) {
				custInfo.setIsHaveAssure(applyInfo.getIsHaveAssure());
				custInfo.setIsHaveComLoan(applyInfo.getIsHaveComLoan());
			}
		} catch (Exception e) {
			logger.error("借款申请信息不存在！", e);
		}
		model.addAttribute("custInfo", custInfo);

		// 主借人存在情况下，查询其他相关信息
		CustWorkInfo custWorkInfo = new CustWorkInfo();
		CustInfo custMateInfo = new CustInfo();
		List<ContactInfo> contactList = new ArrayList<ContactInfo>();
		List<ApplyRelation> coCustRelations = new ArrayList<ApplyRelation>();
		if (custInfo != null && StringUtils.isNotEmpty(custInfo.getId())) {
			// 4.查询主借人工作信息
			Map<String, String> map = Maps.newConcurrentMap();
			map.put("custId", custInfo.getId());
			custWorkInfo = custWorkInfoService.findCustWorkInfoByCustId(map);
			if (custWorkInfo == null) {
				custWorkInfo = new CustWorkInfo();
			}
			custWorkInfo.setCurrApplyNo(applyNo);
			// 5.查询主借人配偶信息
			ApplyRelation mateRelation = new ApplyRelation();
			mateRelation.setApplyNo(applyNo);
			mateRelation.setRoleType(Constants.ROLE_TYPE_MATE);
			mateRelation.setRelationForApply(Constants.CONTACT_RELATIONS_MATE);
			List<ApplyRelation> mateRelations = applyRelationService.findList(mateRelation);
			if (!mateRelations.isEmpty()) {
				mateRelation = mateRelations.get(0);
				try {
					String custMateId = mateRelation.getCustId();
					if (custMateId == null) {
						custMateId = mateRelation.getCustInfo().getId();
					}
					if (custMateInfo != null) {
						if (custMateId != null && StringUtils.isNotEmpty(custMateId)) {
							custMateInfo = custInfoService.get(custMateId);
							custMateInfo.setCurrApplyNo(applyNo);
							custMateInfo.setMateToGuarantor(mateRelation.getMateToGuarantor());
							custMateInfo.setSvFlag(mateRelation.getSvFlag());
						}
					}
				} catch (Exception e) {
					logger.error("无配偶客户信息", e);
				}
			}
			if (custMateInfo == null) {
				custMateInfo = new CustInfo();
				custMateInfo.setCurrApplyNo(applyNo);
			}
			// 8.查询企业信息

		}
		CompanyInfo companyInfo = new CompanyInfo();
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_ZJQY);
		List<CompanyInfo> comapnyList = companyInfoService.findListByParams(params);
		if (!comapnyList.isEmpty()) {
			companyInfo = comapnyList.get(0);
		}
		companyInfo.setCurrApplyNo(applyNo);
		model.addAttribute("custWorkInfo", custWorkInfo);
		model.addAttribute("custMateInfo", custMateInfo);
		model.addAttribute("contactList", contactList);
		model.addAttribute("coCustRelations", coCustRelations);
		model.addAttribute("companyInfo", companyInfo);
		model.addAttribute("mateFlag", Constants.WED_STATUS_YH.equals(custInfo.getWedStatus()));
		model.addAttribute("wedStatusYh", Constants.WED_STATUS_YH);
		// 10.行业信息级联选择
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
		// 初始化行业门类
		List<Industry> categoryMainList2 = industryService.findByParentCode("root");
		model.addAttribute("categoryMainList2", categoryMainList2);
		// 初始化行业大类
		if (StringUtils.isNotEmpty(custMateInfo.getCategoryLarge())) {
			List<Industry> categoryLargeList2 = industryService.findByParentCode(custMateInfo.getCategoryMain());
			model.addAttribute("categoryLargeList2", categoryLargeList2);
		}
		// 初始化行业中类
		if (StringUtils.isNotEmpty(custMateInfo.getCategoryMedium())) {
			List<Industry> categoryMediumList2 = industryService.findByParentCode(custMateInfo.getCategoryLarge());
			model.addAttribute("categoryMediumList2", categoryMediumList2);
		}
		// 初始化行业小类
		if (StringUtils.isNotEmpty(custMateInfo.getCategorySmall())) {
			List<Industry> categorySmallList2 = industryService.findByParentCode(custMateInfo.getCategoryMedium());
			model.addAttribute("categorySmallList2", categorySmallList2);
		}
		// 初始化行业门类
		List<Industry> categoryMainList3 = industryService.findByParentCode("root");
		model.addAttribute("categoryMainList3", categoryMainList3);
		// 初始化行业大类
		if (StringUtils.isNotEmpty(companyInfo.getCategoryLarge())) {
			List<Industry> categoryLargeList3 = industryService.findByParentCode(companyInfo.getCategoryMain());
			model.addAttribute("categoryLargeList3", categoryLargeList3);
		}
		// 初始化行业中类
		if (StringUtils.isNotEmpty(companyInfo.getCategoryMedium())) {
			List<Industry> categoryMediumList3 = industryService.findByParentCode(companyInfo.getCategoryLarge());
			model.addAttribute("categoryMediumList3", categoryMediumList3);
		}
		// 初始化行业小类
		if (StringUtils.isNotEmpty(companyInfo.getCategorySmall())) {
			List<Industry> categorySmallList3 = industryService.findByParentCode(companyInfo.getCategoryMedium());
			model.addAttribute("categorySmallList3", categorySmallList3);
		}
		// 11.返回index页面
		return "app/credit/custinfo/custInfoIndex";
	}

}
