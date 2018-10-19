package com.resoft.credit.custinfo.web;

import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.custWorkInfo.entity.CustWorkInfo;
import com.resoft.credit.custWorkInfo.service.CustWorkInfoService;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.resoft.credit.custinfo.service.CustInfoService;
import com.resoft.credit.industry.entity.Industry;
import com.resoft.credit.industry.service.IndustryService;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterRequest;
import com.resoft.outinterface.rest.newged.entity.FindIsRegisterResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/credit/guarantorInfo")
public class GuarantorInfoFormController extends BaseController {
	@Autowired
	private CustInfoService custInfoService;

	@Autowired
	private ApplyRelationService applyRelationService;

	@Autowired
	private IndustryService industryService;// 行业分类service

	@Autowired
	private CustWorkInfoService custWorkInfoService;

	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	
	@Autowired 
	private ApplyRegisterService applyRegisterService;

	@Autowired
	private GuaranteeRelationService guaranteeRelationService;
	
	/**
	 * 担保人列表
	 * 
	 * @param applyNo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "list")
	public String list(String taskDefKey,String applyNo, String gqgetFlag, Model model) {
		ApplyRegister applyRegister = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		int showGedRegister=0;
		if (applyRegister != null) {
			if(Constants.PROC_DEF_KEY_GR.equalsIgnoreCase(applyRegister.getProcDefKey())&&Constants.UTASK_HTYY.equals(taskDefKey)){
				showGedRegister=1;
				model.addAttribute("showGedRegister", showGedRegister);
				model.addAttribute("taskDefKey", taskDefKey);
				//			model.addAttribute("applyNo", applyNo);
			}
		}

		if (Constants.UNDER_DQGLR.equals(taskDefKey)) {
			//为了跳转到guarantorInfoList页面
			gqgetFlag = Constants.UNDER_DQGLR;
			showGedRegister=1;
			model.addAttribute("showGedRegister", showGedRegister);
			model.addAttribute("taskDefKey", taskDefKey);
		}
		ApplyRelation coCustInfoRelation = new ApplyRelation();
		coCustInfoRelation.setApplyNo(applyNo);
		coCustInfoRelation.setRoleType(Constants.ROLE_TYPE_DBR);
		List<ApplyRelation> coCustRelations = applyRelationService.findList(coCustInfoRelation);
		//担保关系
		Map<String, GuaranteeRelation> confirmMap = new HashMap<>();
		if (Constants.UTASK_HTMQ.equals(taskDefKey)) {
			List<GuaranteeRelation> confirmList =
				guaranteeRelationService.listMainConfirmStatus(applyNo, Constants.ROLE_TYPE_ZJQY, "1");
			for (GuaranteeRelation confirm: confirmList) {
				confirmMap.put(confirm.getCustId(), confirm);
			}
		}
		List<ApplyRelation> guarantorRelationList = new ArrayList<ApplyRelation>();
		List<CustWorkInfo> guarantorWorkInfoList = new ArrayList<CustWorkInfo>();
		if (!coCustRelations.isEmpty()) {
			for (int i = 0; i < coCustRelations.size(); i++) {
				ApplyRelation coCustRelation = coCustRelations.get(i);
				if (coCustRelation != null && coCustRelation.getCustId() != null && StringUtils.isNotEmpty(coCustRelation.getCustId())) {
					CustInfo coCustInfo = custInfoService.get(coCustRelation.getCustId());
					if(1==showGedRegister) {
						FindIsRegisterRequest findIsRegister = new FindIsRegisterRequest();
						findIsRegister.setUserRole("1");
						findIsRegister.setMobile(coCustInfo.getMobileNum());
						FindIsRegisterResponse findIsRegisterResponse = Facade.facade.findGedRegisterInterface(findIsRegister , applyNo);
						if(findIsRegisterResponse!=null){
							String data=null;
							if(findIsRegisterResponse.getData()!=null){
								data = findIsRegisterResponse.getData().getMobile();
								coCustInfo.setGedAccount(data);
							}
						}
					}
					if (Constants.UTASK_HTMQ.equals(taskDefKey)) {
						GuaranteeRelation confirm = confirmMap.get(coCustRelation.getCustId());
						if (confirm != null) {
							if ("1".equals(confirm.getIsConfirm())) {
								coCustRelation.setGuaranteeConfirm("已确认");
							} else if ("0".equals(confirm.getIsConfirm())) {
								coCustRelation.setGuaranteeConfirm("已拒绝");
							} else {
								coCustRelation.setGuaranteeConfirm("未确认");
							}
						}
					}

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
        model.addAttribute("taskDefKey", taskDefKey);
		if ("2".equals(gqgetFlag)) {
			return "app/credit/guarantorInfo/gqgetGuarantorInfoList";
		} else {
			return "app/credit/guarantorInfo/guarantorInfoList";
		}
	}

	/**
	 * 保存担保人信息
	 * 
	 * @param
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxView saveGuarantor(CustWorkInfo guarantorWorkInfo) {
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
			custInfoService.saveCoCustInfo(custInfo, currApplyNo, relationForApply, Constants.ROLE_TYPE_DBR, guarantorWorkInfo);
			ajaxView.setSuccess().setMessage("保存担保人信息成功！");
		} catch (Exception e) {
			logger.error("保存担保人信息失败！", e);
			ajaxView.setFailed().setMessage("保存担保人信息失败！");
		}
		return ajaxView;
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
	@RequestMapping(value = "form")
	public String form(Model model, String applyNo, String custId, String readOnly) {
		CustWorkInfo guarantorWorkInfo = new CustWorkInfo();
		CustInfo guarantorInfo = new CustInfo();
		try {
			if (StringUtils.isNotBlank(applyNo) && StringUtils.isBlank(custId)) {// 新增
				readOnly = "1";// 只读属性置否
				guarantorInfo.setCurrApplyNo(applyNo);
				guarantorWorkInfo.setCustInfo(guarantorInfo);
				guarantorWorkInfo.setCurrApplyNo(applyNo);
				model.addAttribute("saveFlag", "1");
			} else if (StringUtils.isNotBlank(applyNo) && StringUtils.isNotBlank(custId)) {// 修改
				// 1.查询担保人
				guarantorInfo = custInfoService.get(custId);
				ApplyRelation applyRela  =  applyRelationService.getApplyRelation(applyNo, custId);
				if(applyRela!=null){
					guarantorInfo.setSvFlag(applyRela.getSvFlag());
				}
				// 2.查询担保人工作信息
				Map<String, String> map = Maps.newConcurrentMap();
				map.put("custId", custId);
				CustWorkInfo guarantorWorkInfoTemp = custWorkInfoService.findCustWorkInfoByCustId(map);
				if(guarantorWorkInfoTemp != null){
					guarantorWorkInfo = guarantorWorkInfoTemp;
				}
				// 3.查询与主借人关系
				ApplyRelation applyRelation = new ApplyRelation();
				applyRelation.setApplyNo(applyNo);
				applyRelation.setCustId(custId);
				List<ApplyRelation> applyRelationList = applyRelationService.findList(applyRelation);
				if(applyRelationList != null){
					applyRelation = applyRelationList.get(0);
					String relationForApply = applyRelation.getRelationForApply();
					guarantorInfo.setRelationForApply(relationForApply);
				}
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
		if(Constants.CONTACT_RELATIONS_MATE.equals(guarantorInfo.getRelationForApply())){
			model.addAttribute("readOnly", readOnly);
		}else{
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
		return "app/credit/guarantorInfo/guarantorInfoForm";
	}

	/**
	 * 删除担保人信息
	 * 
	 * @param
	 * @return
	 */
	@RequiresPermissions("credit:guarantorInfo:delete")
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxView banchDelete(String ids, String applyNo) {
		AjaxView ajaxView = new AjaxView();
		if (ids != null && !"".equals(ids)) {// 批量删除
			List<String> idList = Arrays.asList(ids.split(","));
			try {
				custInfoService.banchDelete(idList, applyNo, Constants.ROLE_TYPE_DBR);
				ajaxView.setSuccess().setMessage("删除担保人信息成功！");
			} catch (Exception e) {
				logger.error("删除担保人信息失败！", e);
				ajaxView.setSuccess().setMessage("删除担保人信息失败！");
			}
		}
		return ajaxView;
	}
	/*
	 * 原来是逻辑删除用的是banchDelete方法，现在该为物理删除。
	 * */
	@RequiresPermissions("credit:guarantorInfo:delete")
	@ResponseBody
	@RequestMapping(value = "newDelete")
	public AjaxView newBanchDelete(String ids, String applyNo) {
		AjaxView ajaxView = new AjaxView();
		if (ids != null && !"".equals(ids)) {// 批量删除
			List<String> idList = Arrays.asList(ids.split(","));
			try {
				custInfoService.newBanchDelete(idList, applyNo, Constants.ROLE_TYPE_DBR);
				ajaxView.setSuccess().setMessage("删除担保人信息成功！");
			} catch (Exception e) {
				logger.error("删除担保人信息失败！", e);
				ajaxView.setSuccess().setMessage("删除担保人信息失败！");
			}
		}
		return ajaxView;
	}
	
	
	
	
	@RequiresPermissions("credit:guarantorInfo:view")
	@RequestMapping(value = "listUnion")
	public String listUnion(String applyNo, String approId, Model model) {
		CheckApproveUnion check = new CheckApproveUnion();
		check = checkApproveUnionService.get(approId);
		String custId = check.getCustId();
		
		ApplyRelation coCustInfoRelation = new ApplyRelation();
		Map<String, String> param = new HashMap<String, String>();
		param.put("applyNo", applyNo);
		param.put("custId", custId);
		List<ApplyRelation> coCustRelations = applyRelationService.findUnionList(param);
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
		return "app/credit/guarantorInfo/gqgetGuarantorInfoList";
	}
}
