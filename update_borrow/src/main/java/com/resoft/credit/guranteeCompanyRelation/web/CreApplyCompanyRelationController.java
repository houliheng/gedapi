package com.resoft.credit.guranteeCompanyRelation.web;

import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;

import javax.imageio.spi.RegisterableService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.google.common.collect.Maps;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;
import com.resoft.credit.custWorkInfo.service.CustWorkInfoService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.industry.entity.Industry;
import com.resoft.credit.industry.service.IndustryService;
import com.resoft.credit.loanApply.entity.CreApplyRegister;
import com.resoft.credit.loanApply.service.CreApplyRegisterService;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.velocity.runtime.directive.Foreach;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.credit.guranteeCompanyRelation.service.CreApplyCompanyRelationService;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批量借款企业关系表Controller
 * @author lb
 * @version 2018-04-25
 *
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/creApplyCompanyRelation")
public class CreApplyCompanyRelationController extends BaseController {

	@Autowired
	private CreApplyCompanyRelationService creApplyCompanyRelationService;

	@Autowired
	private CompanyInfoService companyInfoService;

	@Autowired
	private IndustryService industryService;

	@Autowired
	private ApplyRelationService applyRelationService;

	@Autowired
	private CreGuaranteeCompanyService creGuaranteeCompanyService;

	@Autowired
	private CustInfoService custInfoService;

	@Autowired
	private CustWorkInfoService custWorkInfoService;

	@Autowired
	private GuaranteeRelationService guaranteeRelationService;

	@Autowired
	private CreApplyRegisterService creApplyRegisterService;


	@ModelAttribute
	public CreApplyCompanyRelation get(@RequestParam(required = false) String id) {
		CreApplyCompanyRelation entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = creApplyCompanyRelationService.get(id);
		}
		if (entity == null) {
			entity = new CreApplyCompanyRelation();
		}
		return entity;
	}




	@RequiresPermissions("credit:creApplyCompanyRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(CreApplyCompanyRelation creApplyCompanyRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CreApplyCompanyRelation> page = creApplyCompanyRelationService.findCustomPage(new Page<CreApplyCompanyRelation>(request, response), creApplyCompanyRelation);
		model.addAttribute("page", page);
		return "app/credit/guranteeCompanyRelation/creApplyCompanyRelationList";
	}



	/**
	 * 查询批量借款企业
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/queryCompanyList")
	public String queryCompanyList(@RequestParam(required = true) String applyNo, Model model, String taskDefKey) {


		// 查询订单下的批量借款企业 type 类型为 9 的字段
		Map<String, Object> params = Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.CONTACT_RELATIONS_COMPANY); // 9 标示批量借款企业之间的关联
		List<CompanyInfo> companyInfoList = companyInfoService.findListByParams(params);
		model.addAttribute("companyInfoList", companyInfoList);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("taskDefKey", taskDefKey);

       if(!Constants.UTASK_SQLR.equals(taskDefKey)){
             model.addAttribute("readOnly",true);
	   } else {
		   model.addAttribute("readOnly",false);
	   }
		return "app/credit/guaranteeRelation/guaranteeRelationDetailList";
	}
	/**
	 * 删除批量借款企业信息
	 *
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:view")
	@ResponseBody
	@RequestMapping(value = "deleteCompanyList")
	public AjaxView banchDelete(String ids, String applyNo) {
		AjaxView ajaxView = new AjaxView();
		if (ids != null && !"".equals(ids)) {// 批量删除
			List<String> idList = Arrays.asList(ids.split(","));
			try {
				companyInfoService.banchDelete(idList, applyNo, Constants.ROLE_TYPE_PLJK);  // 这是批量借款企业
				ajaxView.setSuccess().setMessage("删除担保企业信息成功！");
			} catch (Exception e) {
				logger.error("删除担保企业信息失败！", e);
				ajaxView.setSuccess().setMessage("删除担保企业信息失败！");
			}
		}
		return ajaxView;
	}




	/**
	 * 借款企业申跳到担保人,担保公司,等页面
	 */
     @RequestMapping(value = "jump")
	 public String jump(String applyNo,Model model,String custId ,Boolean readOnly, String taskDefKey){
     	 model.addAttribute("applyNo",applyNo); //申请变好
     	 model.addAttribute("custId",custId);  // 批量借款企业主键
         model.addAttribute("readOnly",readOnly);
         model.addAttribute("taskDefKey", taskDefKey);
     	return "app/credit/guaranteeRelation/guaranteeRelationIndex";
	 }

	/**
	 * 新增借款企业
	 *
	 * @param companyId
	 * @param applyNo
	 * @param readOnly
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "compinySave")
	public String compinySave(String companyId, String applyNo, String readOnly,String taskDefKey, Model model) {
		// 1.新增
		CompanyInfo guarantorCompanyInfo = new CompanyInfo();
		if (StringUtils.isNotEmpty(applyNo) && StringUtils.isEmpty(companyId)) {
			readOnly = "1";
		}

		// 2.修改或详情
		if (StringUtils.isNotEmpty(companyId)) {
			guarantorCompanyInfo = companyInfoService.get(companyId);
			if (guarantorCompanyInfo == null) {
				guarantorCompanyInfo = new CompanyInfo();
			}
			ApplyRelation applyRelation = applyRelationService.getApplyRelation(applyNo, companyId);
			if (applyRelation != null) {
				guarantorCompanyInfo.setSvFlag(applyRelation.getSvFlag());
			}
		}
		guarantorCompanyInfo.setCurrApplyNo(applyNo);
		model.addAttribute("guarantorCompanyInfo", guarantorCompanyInfo);

		// 4.行业信息
		// 初始化行业门类
		List<Industry> categoryMainList = industryService.findByParentCode("root");
		model.addAttribute("categoryMainList", categoryMainList);
		// 初始化行业大类
		if (StringUtils.isNotEmpty(guarantorCompanyInfo.getCategoryLarge())) {
			List<Industry> categoryLargeList = industryService.findByParentCode(guarantorCompanyInfo.getCategoryMain());
			model.addAttribute("categoryLargeList", categoryLargeList);
		}
		// 初始化行业中类
		if (StringUtils.isNotEmpty(guarantorCompanyInfo.getCategoryMedium())) {
			List<Industry> categoryMediumList = industryService.findByParentCode(guarantorCompanyInfo.getCategoryLarge());
			model.addAttribute("categoryMediumList", categoryMediumList);
		}
		// 初始化行业小类
		if (StringUtils.isNotEmpty(guarantorCompanyInfo.getCategorySmall())) {
			List<Industry> categorySmallList = industryService
					.findByParentCode(guarantorCompanyInfo.getCategoryMedium());
			model.addAttribute("categorySmallList", categorySmallList);
		}
		model.addAttribute("readOnly", readOnly);
        model.addAttribute("taskDefKey","utask_sqlr");
		return "app/credit/guaranteeCompany/CompanyInfo";
	}

	/**
	 * 保存借款企业
	 *
	 * @param companyInfo
	 * @return
	 */
	@RequestMapping(value = "/saveCompany")
	@ResponseBody
	@RequiresPermissions("credit:guarantorInfo:view")
	public AjaxView saveCompany(CompanyInfo companyInfo) {
		AjaxView ajaxView = new AjaxView();
		Map<String, Object> params = Maps.newHashMap();
		String roleType = Constants.CONTACT_RELATIONS_COMPANY; // 9 录入的是批量借款企业
		try {
			params.put("applyNo", companyInfo.getCurrApplyNo());
			List<CompanyInfo> infos = companyInfoService.findListByParams(params);
			if (infos.size() != 0) {
				for (CompanyInfo info : infos) {
					if (companyInfo.getBusiRegName().equalsIgnoreCase(info.getBusiRegName()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId())))) {
						ajaxView.setFailed().setMessage("此企业已存在，请重新录入");
						return ajaxView;
					} else {
						if (!(StringUtils.isNull(companyInfo.getUnSocCreditNo()))) {// 统一社会信用贷码不为空
							if (companyInfo.getUnSocCreditNo().equalsIgnoreCase(info.getUnSocCreditNo()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId())))) {
								ajaxView.setFailed().setMessage("此企业(统一社会信用代码)已存在，请重新录入");
								return ajaxView;
							} else {
								if (!(StringUtils.isNull(companyInfo.getBusiLicRegNo()))) {
									if (companyInfo.getBusiLicRegNo().equalsIgnoreCase(info.getBusiLicRegNo()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId())))) {
										ajaxView.setFailed().setMessage("此企业(营业执照注册号)已存在，请重新录入");
										return ajaxView;
									}
								}
							}
						} else {
							if (!(StringUtils.isNull(companyInfo.getBusiLicRegNo()))) {
								if (companyInfo.getBusiLicRegNo().equalsIgnoreCase(info.getBusiLicRegNo()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId())))) {
									ajaxView.setFailed().setMessage("此企业(营业执照注册号)已存在，请重新录入");
									return ajaxView;
								}
							}
						}
					}
				}
			}
			companyInfoService.saveCompanyInfo(companyInfo, roleType);
			ajaxView.setSuccess().setMessage("保存担保企业信息成功！");
		} catch (Exception e) {
			logger.error("保存担保企业信息失败！", e);
			ajaxView.setFailed().setMessage("保存担保企业信息失败！");
		}

		return ajaxView;

	}

	/**
	 * 查询批量借款企业下的担保公司
	 */

     @RequestMapping(value = "selectCompany")
	 public String selectCompany(@RequestParam(required = false)String applyNo,
								 @RequestParam(required = false)String custId,
								 @RequestParam(required = false) Boolean readOnly,
								 @RequestParam(required = false) String taskDefKey,
								 Model model){


		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("applyNo", applyNo);
		paramMap.put("custId", custId);
		paramMap.put("RoleType", "3");
		paramMap.put("DelFlag", "0");

		model.addAttribute("taskDefKey", taskDefKey);
         model.addAttribute("readOnly",readOnly);
		//根据条件查询关系表中的数据
		CreGuaranteeCompany company = null;
		CreApplyCompanyRelation relation = null;
		relation = creApplyCompanyRelationService.queryCompanyRelationList(paramMap);

		if (null == relation) {
			model.addAttribute("applyNo", applyNo);
			model.addAttribute("custId", custId);
			model.addAttribute("flag", "1");
			model.addAttribute("relation", relation);

			return "app/credit/guranteeCompanyRelation/GuaranteeCompanyList";
		} else {
			String companyCustId = relation.getCustId(); //担保公司主键
			company = creGuaranteeCompanyService.get(companyCustId);

			if ("1".equals(relation.getIsConfirm())) {
				relation.setIsConfirm("已确认");
			} else if ("0".equals(relation.getIsConfirm())) {
				relation.setIsConfirm("已拒绝");
			} else {
				relation.setIsConfirm("未确认");
			}

			model.addAttribute("company", company);
			model.addAttribute("applyNo", applyNo);
			model.addAttribute("custId", custId);
			model.addAttribute("flag", "0");
			model.addAttribute("relation", relation);
			model.addAttribute("shanchu",100); // 只有在申请录入才能对担保公司做解除关系


			return "app/credit/guranteeCompanyRelation/GuaranteeCompanyList";
		}
	}

	/**
	 * 查询所有的担保公司  selectCompanyList
	 */
	@RequestMapping(value = "selectCompanyList")
	public String  selectCompanyList ( CreGuaranteeCompany creGuaranteeCompany,
								      Model model,
								      HttpServletRequest request){

		String flag = request.getParameter("companyFlag");

		String applyNo = request.getParameter("applyNo");
		String custId = request.getParameter("custId");

		List<CreGuaranteeCompany> companyList = creGuaranteeCompanyService.queryCreGuaranteeCompanyList(creGuaranteeCompany);
		model.addAttribute("companyList", companyList);
		model.addAttribute("custId", custId);
		model.addAttribute("flag", flag);
		model.addAttribute("applyNo", applyNo);

		return "app/credit/guranteeCompanyRelation/GuaranteeCompanyXianshiList";
	}


    /**
     * 保存   担保公司 与批量借款企业的关系
     * @return
     */
	@RequestMapping(value = "saveApplyCompanyRelation")
	@ResponseBody
	public AjaxView saveApplyCompanyRelation(@RequestParam(required = true) String applyNo,
											 @RequestParam(required = true) String companyId,
											 @RequestParam(required = true) String custId,
											 @RequestParam(required = true) String roleType) {
		AjaxView ajax = new AjaxView();


		CreApplyCompanyRelation relation = new CreApplyCompanyRelation();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("applyNo", applyNo);
		paramMap.put("custId", custId);
		paramMap.put("RoleType", "3");
		paramMap.put("DelFlag", "0");
		// 解除借款企业下的担保公司
		creApplyCompanyRelationService.deleteCompanyByApplyCompanyRelation(paramMap);

		relation.setApplyNo(applyNo);
		relation.setCompanyId(custId); // 这是批量借款企业id
		relation.setCustId(companyId);  // 这是担保** 的id
		relation.setRoleType(roleType); //担保公司

		try {
			creApplyCompanyRelationService.save(relation);
			ajax.setSuccess().setMessage("关联成功");
		} catch (Exception e) {
			logger.error("关联失败", e);
			ajax.setFailed().setMessage("稍后再试");
		}

		return ajax;
	}
	/**
	 * 删除担保公司信息
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteCompanysd")
	@ResponseBody
	public AjaxView deleteCompanysd(@RequestParam(required = false) String id) {
		AjaxView ajax = new AjaxView();
		CreApplyCompanyRelation relation = new CreApplyCompanyRelation();
		relation.setId(id);

		try {
			creApplyCompanyRelationService.delete(relation);
			ajax.setSuccess().setMessage("删除担保公司成功");
		} catch (Exception e) {
			logger.error("删除担保公司成功", e);
			ajax.setFailed().setMessage("删除担保公司成功");
		}
		return ajax;

	}
	/**
	 * 查询批量借款企业下的担保人
	 */
	@RequestMapping(value = "/selectCustInfo")
	public String selectCustInfo(@RequestParam(required = false) String applyNo,
								 @RequestParam(required = false) String custId,
								 @RequestParam(required = false) String taskDefKey,
								 Model model) {

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("applyNo", applyNo);
        paramMap.put("custId", custId);
        paramMap.put("RoleType", "1");
        paramMap.put("DelFlag", "0");


		List<CustInfo> custInfoList = new ArrayList<>();
		List<CustWorkInfo> guarantorWorkInfoList = new ArrayList<CustWorkInfo>();
		//根据条件查询关系表中的数据
		List<CreApplyCompanyRelation> relationList = creApplyCompanyRelationService.queryCompanyRElaList(paramMap);
		Map<String, GuaranteeRelation> confirmMap = new HashMap<>();
		if (Constants.UTASK_HTMQ.equals(taskDefKey)) {
			List<GuaranteeRelation> confirmList =
				guaranteeRelationService.listConfirmStatus(applyNo, Constants.ROLE_TYPE_PLJK, "1", custId);
			for (GuaranteeRelation confirm: confirmList) {
				confirmMap.put(confirm.getCustId(), confirm);
			}
		}
		//判断集合  没有值
		if (null == relationList && relationList.size() == 0) {
			model.addAttribute("applyNo", applyNo);
			model.addAttribute("custId", custId);
			model.addAttribute("flag", "1");

		} else {
			for (CreApplyCompanyRelation relation : relationList) {
				String custInfoId = relation.getCustId();
				CustInfo custInfo = custInfoService.get(custInfoId);
				if (Constants.UTASK_HTMQ.equals(taskDefKey)) {
					GuaranteeRelation confirm = confirmMap.get(relation.getCustId());
					if (confirm != null) {
						if ("1".equals(confirm.getIsConfirm())) {
							custInfo.setIsConfirm("已确认");
						} else if ("0".equals(confirm.getIsConfirm())) {
							custInfo.setIsConfirm("已拒绝");
						} else {
							custInfo.setIsConfirm("未确认");
						}
					}
				}
				custInfoList.add(custInfo);
				Map<String, String> map = Maps.newConcurrentMap();
				map.put("custId", relation.getCustId());
				CustWorkInfo coCustWorkInfo = custWorkInfoService.findCustWorkInfoByCustId(map);
				guarantorWorkInfoList.add(coCustWorkInfo);
			}

			model.addAttribute("guarantorRelationList", custInfoList);
			model.addAttribute("guarantorWorkInfoList", guarantorWorkInfoList);

			model.addAttribute("applyNo", applyNo);
			model.addAttribute("custId", custId);
			model.addAttribute("flag", "0");
			model.addAttribute("taskDefKey", taskDefKey);
		}
		return "app/credit/guranteeCompanyRelation/ContantsInfo";

	}


	/**
	 * 担保人新增、编辑、详情
	 *
	 * @param model
	 * @param applyNo
	 * @param custId
	 * @param readOnly
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "CustInfoForm")
	public String CustInfoForm(Model model, String applyNo, String custId, String readOnly, String piliId) {
		CustWorkInfo guarantorWorkInfo = new CustWorkInfo();
		CustInfo guarantorInfo = new CustInfo();
		try {
			if (StringUtils.isNotBlank(applyNo) && StringUtils.isBlank(custId)) {    // 新增
				readOnly = "1";// 只读属性置否
				guarantorInfo.setCurrApplyNo(applyNo);
				guarantorWorkInfo.setCustInfo(guarantorInfo);
				guarantorWorkInfo.setCurrApplyNo(applyNo);
				model.addAttribute("saveFlag", "1");
			} else if (StringUtils.isNotBlank(applyNo) && StringUtils.isNotBlank(custId)) {   // 修改
				// 1.查询担保人
				guarantorInfo = custInfoService.get(custId);
				ApplyRelation applyRela = applyRelationService.getApplyRelation(applyNo, custId);
				if (applyRela != null) {
					guarantorInfo.setSvFlag(applyRela.getSvFlag());
				}
				// 2.查询担保人工作信息
				Map<String, String> map = Maps.newConcurrentMap();
				map.put("custId", custId);
				CustWorkInfo guarantorWorkInfoTemp = custWorkInfoService.findCustWorkInfoByCustId(map);
				if (guarantorWorkInfoTemp != null) {
					guarantorWorkInfo = guarantorWorkInfoTemp;
				}
				// 3.查询与主借人关系
				ApplyRelation applyRelation = new ApplyRelation();
				applyRelation.setApplyNo(applyNo);
				applyRelation.setCustId(custId);
				List<ApplyRelation> applyRelationList = applyRelationService.findList(applyRelation);
				applyRelation = applyRelationList.get(0);
				String relationForApply = applyRelation.getRelationForApply();
				guarantorInfo.setRelationForApply(relationForApply);
			}
		} catch (Exception e) {
			logger.error("系统参数出现错误，请联系管理员！", e);
			model.addAttribute("message", "系统参数出现错误，请联系管理员！");
		}
		guarantorWorkInfo.setCustInfo(guarantorInfo);
		model.addAttribute("custId", custId);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("guarantorWorkInfo", guarantorWorkInfo);
		model.addAttribute("guarantorInfo", guarantorInfo);
		model.addAttribute("piliId", piliId);

		if (Constants.CONTACT_RELATIONS_MATE.equals(guarantorInfo.getRelationForApply())) {
			model.addAttribute("readOnly", readOnly);
		} else {
			model.addAttribute("readOnly", readOnly);
		}
		// 初始化行业门类
		List<Industry> categoryMainList = industryService.findByParentCode("root");
		model.addAttribute("categoryMainList", categoryMainList);
		// 初始化行业大类
		if (StringUtils.isNotEmpty(guarantorInfo.getCategoryLarge())) {
			List<Industry> categoryLargeList = industryService.findByParentCode(guarantorInfo.getCategoryMain());
			model.addAttribute("categoryLargeList", categoryLargeList);
		}
		// 初始化行业中类
		if (StringUtils.isNotEmpty(guarantorInfo.getCategoryMedium())) {
			List<Industry> categoryMediumList = industryService.findByParentCode(guarantorInfo.getCategoryLarge());
			model.addAttribute("categoryMediumList", categoryMediumList);
		}
		// 初始化行业小类
		if (StringUtils.isNotEmpty(guarantorInfo.getCategorySmall())) {
			List<Industry> categorySmallList = industryService.findByParentCode(guarantorInfo.getCategoryMedium());
			model.addAttribute("categorySmallList", categorySmallList);
		}
		return "app/credit/guranteeCompanyRelation/GuarantorCustInfoFrom";
	}

	/**
	 * 保存担保人信息
	 *
	 * @param
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:edit")
	@ResponseBody
	@RequestMapping(value = "saveCustInfo")
	public AjaxView saveCustInfo(CustWorkInfo guarantorWorkInfo, String piliId, String applyNo) {
		AjaxView ajaxView = new AjaxView();

		if (guarantorWorkInfo == null || guarantorWorkInfo.getCustInfo() == null) {
			logger.error("数据出现异常，保存担保人信息失败！");
			ajaxView.setFailed().setMessage("保存担保人信息失败！");
			return ajaxView;
		}
		CustInfo custInfo = guarantorWorkInfo.getCustInfo();
		String currApplyNo = custInfo.getCurrApplyNo();
		String relationForApply = custInfo.getRelationForApply();
		try {
			custInfoService.saveCustInfo(custInfo, currApplyNo, relationForApply,"3", guarantorWorkInfo);  // 就是这个原因导致的问题

			CreApplyCompanyRelation relation = new CreApplyCompanyRelation();
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("applyNo", applyNo);
			paramMap.put("custId", piliId);
			paramMap.put("custInfo",custInfo.getId());
			paramMap.put("RoleType", "1");
			paramMap.put("DelFlag", "0");
            CreApplyCompanyRelation relation1 = creApplyCompanyRelationService.queryCompanyRelationList(paramMap);
            if(null != relation1){
				ajaxView.setFailed().setMessage("已经关联担保人 不可重复关联！");
				return ajaxView;
			}
			relation.setApplyNo(applyNo);
			relation.setCompanyId(piliId); // 这是批量借款企业id
			relation.setCustId(custInfo.getId());  // 这是担保** 的id
			relation.setRoleType("1"); //担保公司
			creApplyCompanyRelationService.save(relation);   //保存批量借款企业与担保人的关系

			ajaxView.setSuccess().setMessage("保存担保人信息成功！");
		} catch (Exception e) {
			logger.error("保存担保人信息失败！", e);
			ajaxView.setFailed().setMessage("保存担保人信息失败！");
}
		return ajaxView;
}

	/**
	 * 批量删除担保人与批量借款企业关系
	 */
	@RequestMapping("deleteCustInfo")
	@ResponseBody
	public AjaxView deleteCustInfo(@RequestParam(required = false) String ids, String applyNo, String companyId) {

		AjaxView ajaxView = new AjaxView();


			List<String> idList = Arrays.asList(ids.split(","));
			try {
				creApplyCompanyRelationService.beachDelete(applyNo, companyId, idList);
				ajaxView.setSuccess().setMessage("删除担保企业信息成功！");
			} catch (Exception e) {
				logger.error("删除担保企业信息失败！", e);
				ajaxView.setSuccess().setMessage("删除担保企业信息失败！");
			}
			return ajaxView;
		}

	/*批量借款企业担保企业新增
	 * */
	@RequestMapping(value = "form")
	public String form(String guaranteeCompanyId,String companyId, String applyNo, String readOnly, Model model) {
		// 1.新增
		CompanyInfo guarantorCompanyInfo = new CompanyInfo();
		if (StringUtils.isNotEmpty(applyNo) && StringUtils.isEmpty(companyId)) {
			readOnly = "1";
		}
		// 2.修改或详情
		if (StringUtils.isNotEmpty(companyId)) {
			guarantorCompanyInfo = companyInfoService.get(companyId);
			if (guarantorCompanyInfo == null) {
				guarantorCompanyInfo = new CompanyInfo();
			}
			ApplyRelation applyRelation = applyRelationService.getApplyRelation(applyNo, companyId);
			if (applyRelation != null) {
				guarantorCompanyInfo.setSvFlag(applyRelation.getSvFlag());
			}
		}
		guarantorCompanyInfo.setCurrApplyNo(applyNo);
		model.addAttribute("guarantorCompanyInfo", guarantorCompanyInfo);

		// 4.行业信息
		// 初始化行业门类
		List<Industry> categoryMainList = industryService.findByParentCode("root");
		model.addAttribute("categoryMainList", categoryMainList);
		// 初始化行业大类
		if (StringUtils.isNotEmpty(guarantorCompanyInfo.getCategoryLarge())) {
			List<Industry> categoryLargeList = industryService.findByParentCode(guarantorCompanyInfo.getCategoryMain());
			model.addAttribute("categoryLargeList", categoryLargeList);
		}
		// 初始化行业中类
		if (StringUtils.isNotEmpty(guarantorCompanyInfo.getCategoryMedium())) {
			List<Industry> categoryMediumList = industryService.findByParentCode(guarantorCompanyInfo.getCategoryLarge());
			model.addAttribute("categoryMediumList", categoryMediumList);
		}
		// 初始化行业小类
		if (StringUtils.isNotEmpty(guarantorCompanyInfo.getCategorySmall())) {
			List<Industry> categorySmallList = industryService
					.findByParentCode(guarantorCompanyInfo.getCategoryMedium());
			model.addAttribute("categorySmallList", categorySmallList);
		}
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("guaranteeCompanyId", guaranteeCompanyId);
		return "app/credit/guaranteeRelation/newGuarantorCompanyInfoForm";
	}
	
	
	/**
	 * 保存担保企业信息Ajax
	 * 
	 * @param companyInfo
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "saveGuarantee")
	public AjaxView save(CompanyInfo companyInfo,String guaranteeCompanyId) {
		AjaxView ajaxView = new AjaxView();
		Map<String, Object> params = Maps.newHashMap();
		try {
			params.put("applyNo", companyInfo.getCurrApplyNo());
			List<CompanyInfo> infos = companyInfoService.findListByParams(params);
			if (infos.size() != 0) {
				for (CompanyInfo info : infos) {
					if (companyInfo.getBusiRegName().equalsIgnoreCase(info.getBusiRegName()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId())))) {
						ajaxView.setFailed().setMessage("此企业已存在，请重新录入");
						return ajaxView;
					} else {
						if (!(StringUtils.isNull(companyInfo.getUnSocCreditNo()))) {// 统一社会信用贷码不为空
							if (companyInfo.getUnSocCreditNo().equalsIgnoreCase(info.getUnSocCreditNo()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId()))) ) {
								ajaxView.setFailed().setMessage("此企业(统一社会信用代码)已存在，请重新录入");
								return ajaxView;
							} else {
								if (!(StringUtils.isNull(companyInfo.getBusiLicRegNo()))) {
									if (companyInfo.getBusiLicRegNo().equalsIgnoreCase(info.getBusiLicRegNo()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId()))) ) {
										ajaxView.setFailed().setMessage("此企业(营业执照注册号)已存在，请重新录入");
										return ajaxView;
									}
								}
							}
						} else {
							if (!(StringUtils.isNull(companyInfo.getBusiLicRegNo()))) {
								if (companyInfo.getBusiLicRegNo().equalsIgnoreCase(info.getBusiLicRegNo()) && (!(companyInfo.getId().equalsIgnoreCase(info.getId()))) ) {
									ajaxView.setFailed().setMessage("此企业(营业执照注册号)已存在，请重新录入");
									return ajaxView;
								}
							}
						}
					}
				}
			}
			companyInfoService.saveBatchCompanyInfo(companyInfo,guaranteeCompanyId);
			ajaxView.setSuccess().setMessage("保存担保企业信息成功！");
		} catch (Exception e) {
			logger.error("保存担保企业信息失败！", e);
			ajaxView.setFailed().setMessage("保存担保企业信息失败！");
		}

		return ajaxView;

	}
	
	/*
	 * 批量借款企业下的担保企业批量删除
	 * */
	@ResponseBody
	@RequestMapping(value = "banchDelete")
	public AjaxView banchDelete(String ids, String applyNo,String guaranteeCompanyId) {
		AjaxView ajaxView = new AjaxView();
		if (ids != null && !"".equals(ids)) {// 批量删除
			List<String> idList = Arrays.asList(ids.split(","));
			try {
				companyInfoService.banchGuaranteeDelete(idList, applyNo,guaranteeCompanyId);
				ajaxView.setSuccess().setMessage("删除担保企业信息成功！");
			} catch (Exception e) {
				logger.error("删除担保企业信息失败！", e);
				ajaxView.setSuccess().setMessage("删除担保企业信息失败！");
			}
		}
		return ajaxView;
	}

	/**
	 * 担保人列表
	 *
	 * @param applyNo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "CustInfoList111")
	public String sadfsadflist( String applyNo, String piliId,Model model ) {

		model.addAttribute("piliId",piliId);//借款企业的主键

		ApplyRelation coCustInfoRelation = new ApplyRelation();
		coCustInfoRelation.setApplyNo(applyNo);
		coCustInfoRelation.setRoleType(Constants.ROLE_TYPE_DBR);
		List<ApplyRelation> coCustRelations = applyRelationService.findList(coCustInfoRelation);
		List<ApplyRelation> guarantorRelationList = new ArrayList<ApplyRelation>();
		List<CustWorkInfo> guarantorWorkInfoList = new ArrayList<CustWorkInfo>();
		if (!coCustRelations.isEmpty()) {
			for (int i = 0; i < coCustRelations.size(); i++) {
				ApplyRelation coCustRelation = coCustRelations.get(i);
				if (coCustRelation != null && coCustRelation.getCustId() != null && StringUtils.isNotEmpty(coCustRelation.getCustId())) {
					CustInfo coCustInfo = custInfoService.get(coCustRelation.getCustId());

					coCustRelation.setCustInfo(coCustInfo);
					guarantorRelationList.add(coCustRelation);
					// 查询该担保人工作信息
					Map<String, String> map = Maps.newConcurrentMap();
					map.put("custId", coCustRelation.getCustId());
					CustWorkInfo coCustWorkInfo = custWorkInfoService.findCustWorkInfoByCustId(map);
					guarantorWorkInfoList.add(coCustWorkInfo);
				}
			}
		}
		model.addAttribute("guarantorRelationList", guarantorRelationList);
		model.addAttribute("guarantorWorkInfoList", guarantorWorkInfoList);
		model.addAttribute("applyNo", applyNo);

			return "app/credit/guranteeCompanyRelation/creApplyCustInfoList";
		}

	/**
	 * 批量保存借款企业下的担保人
	 */
     @RequestMapping(value = "saveCustInfoList")
	 @ResponseBody
	 public AjaxView saveCustInfoList(String ids,String applyNo,String piliId,Model model){

	  	 List<String> idList = Arrays.asList(ids.split(","));
		 AjaxView ajaxView = new AjaxView();
	  	 // 避免重复关联批量借款企业下的担保人信息
		 String roleType = "1";
		 List<CreApplyCompanyRelation> relationList = creApplyCompanyRelationService.findApplyCompanyRelationByXinxi(applyNo,piliId,roleType,idList);
         if(null != relationList && relationList.size()  > 0){
			 ajaxView.setSuccess().setMessage("已经关联该担保人 不可重复关联！");
			 return ajaxView;
		 }

		 try{
			 for(int i = 0;i<idList.size();i++){
				 CreApplyCompanyRelation relation = new CreApplyCompanyRelation();
				 relation.setApplyNo(applyNo);
				 relation.setCompanyId(piliId); // 这是批量借款企业id
				 relation.setCustId(idList.get(i));  // 这是担保** 的id
				 relation.setRoleType("1"); //担保公司
				 creApplyCompanyRelationService.save(relation);   //保存批量借款企业与担保人的关系
			 }

			 ajaxView.setSuccess().setMessage("保存担保人信息成功！");
		 } catch (Exception e) {
			 logger.error("保存担保人信息失败！", e);
			 ajaxView.setFailed().setMessage("保存担保人信息失败！");
		 }
		 return ajaxView;
	 }

	/**
	 * 担保企业选择
	 */
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "ComPanyInfoFromList111")
	public String ComPanyInfoFromList111(String applyNo,String piliId, Model model) {

		Map<String, Object> params = Maps.newConcurrentMap();
		params.put("applyNo", applyNo);
		params.put("roleType", Constants.ROLE_TYPE_DBQY);
		List<CompanyInfo> companyInfoList = companyInfoService.findListByParams(params);

		model.addAttribute("piliId",piliId);
		model.addAttribute("companyInfoList", companyInfoList);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/guranteeCompanyRelation/GuaranteeEnterpriseList";
	}

    /**
     * 批量保存借款企业下的担保企业选择
     */
    @RequestMapping(value = "saveCompanyFromList")
    @ResponseBody
    public AjaxView saveCompanyFromList(String ids,String applyNo,String piliId,Model model){

        List<String> idList = Arrays.asList(ids.split(","));
        AjaxView ajaxView = new AjaxView();
        // 避免重复关联批量借款企业下的担保企业选择
        String roleType = "2";
        List<CreApplyCompanyRelation> relationList = creApplyCompanyRelationService.findApplyCompanyRelationByXinxi(applyNo,piliId,roleType,idList);
        if(null != relationList && relationList.size()  > 0){
            ajaxView.setSuccess().setMessage("已经关联该担保企业 不可重复关联！");
            return ajaxView;
        }

        try{
            for(int i = 0;i<idList.size();i++){
                CreApplyCompanyRelation relation = new CreApplyCompanyRelation();
                relation.setApplyNo(applyNo);
                relation.setCompanyId(piliId); // 这是批量借款企业id
                relation.setCustId(idList.get(i));  // 这是担保** 的id
                relation.setRoleType("2"); //担保公司
                creApplyCompanyRelationService.save(relation);   //保存批量借款企业与担保人的关系
            }

            ajaxView.setSuccess().setMessage("保存担保企业信息成功！");
        } catch (Exception e) {
            logger.error("保存担保企业信息失败！", e);
            ajaxView.setFailed().setMessage("保存担保企业信息失败！");
        }
        return ajaxView;


    }

	/**
	 * 根据借款企业主键 查询信息
	 */
     @RequestMapping(value = "selectCompanyByCustId")
	 public   String selectCompanyByCustId(String id,Model model){
		 CompanyInfo  companyInfo = companyInfoService.get(id);
         model.addAttribute("companyInfo",companyInfo);

     	return "app/credit/guranteeCompanyRelation/borrowingCompanyInfo";
	 }



}
