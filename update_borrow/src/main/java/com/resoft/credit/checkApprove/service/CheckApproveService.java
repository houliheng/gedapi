package com.resoft.credit.checkApprove.service;

import com.resoft.credit.checkApprove.utils.CheckApproveUtils;
import com.resoft.credit.repayPlan.utils.RepayPlanUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyInfo.entity.ApplyInfo;
import com.resoft.credit.applyInfo.service.ApplyInfoService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApprove.dao.CheckApproveDao;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApproveUnion.dao.CheckApproveUnionDao;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.contract.entity.RepayPlanData;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.creditAndLine.entity.creditAnalysis.CreditAnalysis;
import com.resoft.credit.creditAndLine.entity.creditCust.CreditCust;
import com.resoft.credit.creditAndLine.service.creditAnalysis.CreditAnalysisService;
import com.resoft.credit.creditAndLine.service.creditCust.CreditCustService;
import com.resoft.credit.creditAndLine.service.creditLineBank.CreditLineBankDetailService;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageCarEvaluateInfo.service.MortgageCarEvaluateInfoService;
import com.resoft.credit.mortgageCarInfo.entity.MortgageCarInfo;
import com.resoft.credit.mortgageCarInfo.service.MortgageCarInfoService;
import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.credit.mortgageHouseInfo.service.MortgageHouseInfoService;
import com.resoft.credit.mortgageOtherInfo.entity.MortgageOtherInfo;
import com.resoft.credit.mortgageOtherInfo.service.MortgageOtherInfoService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.credit.repayPlan.service.RepayPlanService;
import com.resoft.credit.stockTaskReceive.entity.StockTaskReceive;
import com.resoft.credit.stockTaskReceive.service.StockTaskReceiveService;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequest;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequestList;
import com.resoft.outinterface.rest.newged.entity.AddOrderResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 批复信息Service
 * 
 * @author wuxi01
 * @version 2016-02-29
 */
@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class CheckApproveService extends CrudService<CheckApproveDao, CheckApprove> {

	private static final Logger logger = LoggerFactory.getLogger(CheckApproveService.class);

	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;

	@Autowired
	private MortgageCarInfoService mortgageCarInfoService;

	@Autowired
	private MortgageHouseInfoService mortgageHouseInfoService;

	@Autowired
	private MortgageOtherInfoService mortgageOtherInfoService;

	@Autowired
	private MortgageCarEvaluateInfoService mortgageCarEvaluateInfoService;

	@Autowired
	private CreditAnalysisService creditAnalysisService;

	@Autowired
	private CreditLineBankDetailService creditLineBankDetailService;

	@Autowired
	private CreditCustService creditCustService;

	@Autowired
	private ApplyInfoService applyInfoService;

	@Autowired
	private RepayPlanService repayPlanService;

	@Autowired
	private ContractService contractService;
	
	@Autowired
	private StockTaskReceiveService stockTaskReceiveService;
	
	@Autowired
	private CheckApproveUnionDao checkApproveUnionDao;
	
	@Autowired
	private CreGuaranteeCompanyService guaranteeCompanyService;
	
	@Autowired
	private ApplyRelationService applyRelationService;

	public CheckApprove get(String id) {
		return super.get(id);
	}

	public List<CheckApprove> findList(CheckApprove checkApprove) {
		return super.findList(checkApprove);
	}

	public Page<CheckApprove> findPage(Page<CheckApprove> page, CheckApprove checkApprove) {
		return super.findPage(page, checkApprove);
	}

	@Transactional(value = "CRE", readOnly = false)
	public String saveCheckApprove(CheckApprove checkApprove) {
		try {
			// 保存批复信息
			Map<String, String> param = Maps.newConcurrentMap();
			param.put("applyNo", checkApprove.getApplyNo());
			param.put("taskDefKeyV", checkApprove.getTaskDefKey());
			List<CheckApprove> hasCheckApprove = getCheckApproveByApplyNo(param);
			if (hasCheckApprove.size() > 0) {
//				if(Constants.UTASK_ZGSFKSH.equalsIgnoreCase(checkApprove.getTaskDefKey())){
//					dealChekApproveBackUp(checkApprove.getApplyNo());
//				}
				checkApprove.preUpdate();
				this.dao.update(checkApprove);
			} else {
				checkApprove.preInsert();
				this.dao.insert(checkApprove);
			}
			// 先删除之前的还款计划表数据
			param.put("taskDefKey", checkApprove.getTaskDefKey());
			repayPlanService.deleteRepayPlanByApplyNo(param);
			// 生成还款计划表
			RepayPlanData repayPlanData = RepayPlanUtils.packRepayPlan(checkApprove);
			repayPlanData.setDeductDate(null);
			List<RepayPlan> repayPlanList = Lists.newArrayList();
			List<RepayPlan> repayPlanListTmp = Lists.newArrayList();
			repayPlanListTmp = contractService.calculateRepayPlan(repayPlanData);
			// 判断 如果是一次性付清本息 则进行数据处理
			if (Constants.LOAN_REPAY_TYPE_YCXHBFX.equals(checkApprove.getApproLoanRepayType())) {
				repayPlanList = contractService.recountData(repayPlanListTmp, checkApprove.getApproPeriodValue());
			} else {
				repayPlanList = repayPlanListTmp;
			}
			repayPlanService.insertRepayPlanList(repayPlanList);
			// 更新借款申请信息表中的合同金额，用于待办任务列表显示
			ApplyInfo applyInfo = new ApplyInfo();
			applyInfo.setApplyNo(checkApprove.getApplyNo());
			applyInfo.setContractAmount(checkApprove.getContractAmount());
			applyInfoService.updateContractAmount(applyInfo);
			return "true";
		} catch (Exception e) {
			logger.error("保存批复信息，或生成还款计划，或更新借款申请信息表发生异常！", e);
			return "false";
		}
	}

	@Transactional(value = "CRE", readOnly = false)
	public String saveUnderCheckApprove(CheckApprove checkApprove) {
		try {
			CheckApproveUtils.calcApproveYearRate(checkApprove);
			Map<String, String> params = Maps.newConcurrentMap();
			params.put("applyNo", checkApprove.getApplyNo());
			params.put("taskDefKeyV", checkApprove.getTaskDefKey());
			List<CheckApprove> hasCheckApprove = getCheckApproveByApplyNo(params);
			if (hasCheckApprove.size() > 0) {
				checkApprove.preUpdate();
				this.dao.update(checkApprove);
			} else {
				checkApprove.preInsert();
				this.dao.insert(checkApprove);
			}
			return "true";
		} catch (Exception e) {
			logger.error("保存借款信息失败", e);
			return "false";
		}
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(CheckApprove checkApprove) {
		super.delete(checkApprove);
	}

	public List<CheckApprove> getCheckApproveByApplyNo(Map<String, String> param) {
		return super.dao.getCheckApproveByApplyNo(param);
	}

	/**
	 * 检验抵质押物信息完整性
	 * 
	 * @param params
	 * @param ajaxView
	 * @return
	 */
	public boolean isMortagageCompleted(Map<String, String> params, AjaxView ajaxView) {
		// 抵质押物车辆信息
		List<MortgageCarInfo> mortgageCarInfoList = mortgageCarInfoService.getPageByApplyNo(params.get("applyNo"));
		// 抵质押物房产信息
		List<MortgageHouseInfo> mortgageHouseInfoList = mortgageHouseInfoService.getPageByApplyNo(params.get("applyNo"));
		// 抵质押物其他信息
		List<MortgageOtherInfo> mortgageOtherInfoList = mortgageOtherInfoService.getPageByApplyNo(params.get("applyNo"));
		// 无抵押物情况下，直接返回，提示信息
		if ((mortgageCarInfoList == null || mortgageCarInfoList.size() == 0) && (mortgageHouseInfoList == null || mortgageHouseInfoList.size() == 0) && (mortgageOtherInfoList == null || mortgageOtherInfoList.size() == 0)) {
			ajaxView.setFailed().setMessage("抵质押物信息页面，请完善抵/质押物信息后再进行操作！");
			return false;
		}
		// 车辆抵质押物评估信息
		for (int i = 0; i < mortgageCarInfoList.size(); i++) {
			MortgageCarEvaluateInfo mortgageCarEvaluateInfo = mortgageCarEvaluateInfoService.findListByCarId(mortgageCarInfoList.get(i).getId());
			// 抵质押车辆未填写评估信息
			if (mortgageCarEvaluateInfo == null) {
				ajaxView.setFailed().setMessage("抵质押物信息页面，请完善车辆评估信息后再进行操作！");
				return false;
			}
		}
		// 房产抵质押物评估信息
		for (int j = 0; j < mortgageHouseInfoList.size(); j++) {
			if (mortgageHouseInfoList.get(j) == null || StringUtils.isBlank(mortgageHouseInfoList.get(j).getEvaluatePrice())) {
				ajaxView.setFailed().setMessage("抵质押物信息页面，请完善房产评估信息后再进行操作！");
				return false;
			}
		}
		// 其他抵质押物评估信息
		for (int k = 0; k < mortgageOtherInfoList.size(); k++) {
			if (mortgageOtherInfoList.get(k) == null || StringUtils.isBlank(mortgageOtherInfoList.get(k).getValueNum())) {
				ajaxView.setFailed().setMessage("抵质押物信息页面，请完善其他抵质押物评估信息后再进行操作！");
				return false;
			}
		}
		// 抵质押物分析信息
		ProcessSuggestionInfo processSuggestionInfo = processSuggestionInfoService.findByApplyNo(params);
		if (processSuggestionInfo == null || StringUtils.isBlank(processSuggestionInfo.getAnalysisDesc())) {
			ajaxView.setFailed().setMessage("抵质押物信息页面，请完善抵质押物分析信息后再进行操作！");
			return false;
		}
		ajaxView.setSuccess();
		return true;
	}

	/**
	 * 校验信审意见书数据完整性
	 * 
	 * @param params
	 * @param ajaxView
	 * @return
	 */
	public boolean isCreditCompleted(Map<String, String> params, AjaxView ajaxView) {
		// 单张银行卡流水明细必须在6条以上
		boolean isBankDetailCompleted = creditLineBankDetailService.isBankDetailCompleted(params, ajaxView);
		if (!isBankDetailCompleted) {
			return false;
		}
		// 个人征信
		CreditCust creditCust = new CreditCust();
		creditCust.setApplyNo(params.get("applyNo"));
		List<CreditCust> creditCustList = creditCustService.findList(creditCust);
		if (creditCustList == null || creditCustList.size() == 0) {
			ajaxView.setFailed().setMessage("征信及流水页面，请完善个人征信信息后再进行操作！");
			return false;
		}

		// 校验征信及流水分析意见是否存在
		CreditAnalysis creditAnalysis = creditAnalysisService.getCreditAnalysisByApplyNo(params.get("applyNo"));
		if (creditAnalysis == null || StringUtils.isBlank(creditAnalysis.getLineDesc())) {
			ajaxView.setFailed().setMessage("征信及流水页面，请完善分析信息后再进行操作！");
			return false;
		}
		ajaxView.setSuccess();
		return true;
	}
	
	@Transactional(value = "CRE", readOnly = false)
	public void dealChekApproveBackUp(String applyNo){
		this.dao.deleteChekApproveBackUp(applyNo);
		this.dao.insertCheckApproveBackUp(applyNo);
	}
	
	public CheckApprove getCheckApproveBackUp(String applyNo){
		return this.dao.getCheckApproveBackUp(applyNo);
	}
	
	public List<String> contrastObj(Object obj1, Object obj2) {
		List<String> textList = Lists.newArrayList();
		if (obj1 instanceof CheckApprove && obj2 instanceof CheckApprove) {
			 Map<String, Object> param = Maps.newHashMap();
			  param.put("applyNo" , "申请编号");
			  param.put ("approProductTypeName", "批贷产品类型名称");
			  param.put ("approProductName", "批贷产品名称");
			  param.put ("contractAmount", "合同金额");
			  param.put ("approAmount", "批贷金额");
			  param.put ("loanAmount", "放款金额");
			  param.put ("approYearRate", "利率");
			  param.put ("guanetongRate", "冠E通利率");
			  param.put ("serviceFeeRate", "服务费率");
			  param.put ("specialServiceFeeRate","特殊服务费率");
			  param.put ("serviceFeeType", "服务费收取方式");
			  param.put ("serviceFee", "服务费");
			  param.put ("specialServiceFee", "特殊服务费");
			  param.put ("allServiceFee", "服务费总计");
			  param.put ("approPeriodValue", "批贷期限值");
			  param.put ("approLoanRepayType", "还款方式");
			  param.put ("marginRate", "保证金率");
			  param.put ("marginAmount", "保证金");
			  param.put ("checkFee", "外访费");
			  param.put ("loanModel", "借款模式");
			  param.put ("isUrgent", "是否加急");
			  param.put ("contractType", "建议签订合同类型");
			  param.put ("approDate", "批复日期");
			  param.put ("processSequence", "批复信息流程顺序");
			  param.put ("pricedRisk", "风险定价费率");
			  param.put ("qualityServiceMarginAmount", "质量服务保证金");
			  param.put ("qualityServiceMarginRate", "质量服务保证金率");
			CheckApprove pojo1 = (CheckApprove) obj1;
			CheckApprove pojo2 = (CheckApprove) obj2;
			try {
				Class clazz = pojo1.getClass();
				Field[] fields = pojo1.getClass().getDeclaredFields();
				for (Field field : fields) {
					if (!("serialVersionUID".equalsIgnoreCase(field.getName())) && !("remark".equalsIgnoreCase(field.getName())) ) {
						PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
						Method getMethod = pd.getReadMethod();
						Object o1 = getMethod.invoke(pojo1);
						Object o2 = getMethod.invoke(pojo2);
						String s1 = o1 == null ? "" : o1.toString();// 避免空指针异常
						String s2 = o2 == null ? "" : o2.toString();// 避免空指针异常
						// 思考下面注释的这一行：会bug的，虽然被try catch了，程序没报错，但是结果不是我们想要的
						// if (!o1.toString().equals(o2.toString())) {
						if (!s1.equals(s2) && param.get(field.getName()) != null ) {
							if("serviceFeeType".equalsIgnoreCase(field.getName())){
								textList.add("变更字段：" +param.get(field.getName()) + "; 变更过程：[由 " + DictUtils.getDictLabel(s1, Constants.SERVICE_FEE_TYPE, "") + " 更改为 " + DictUtils.getDictLabel(s2, Constants.SERVICE_FEE_TYPE, "") + " ]");
							}else if("approLoanRepayType".equalsIgnoreCase(field.getName())){
								textList.add("变更字段：" +param.get(field.getName()) + "; 变更过程：[由 " + DictUtils.getDictLabel(s1, Constants.LOAN_REPAY_TYPE, "") + " 更改为 " + DictUtils.getDictLabel(s2, Constants.LOAN_REPAY_TYPE, "") + " ]");
							}else if("loanModel".equalsIgnoreCase(field.getName())){
								textList.add("变更字段：" +param.get(field.getName()) + "; 变更过程：[由 " + DictUtils.getDictLabel(s1, Constants.LOAN_MODEL, "") + " 更改为 " + DictUtils.getDictLabel(s2, Constants.LOAN_MODEL, "") + " ]");
							}else{
								textList.add("变更字段：" +param.get(field.getName()) + "; 变更过程：[由 " + s1 + " 更改为 " + s2 + " ]");
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("对比字段数据异常",e);
			}
		}
		return textList;
	}
	
	/**
	 * 查询各层级审核是否审批过
	 * @param param
	 * @return
	 */
	public StockTaskReceive findCreStockTaskReceiveByParam(Map<String,String> param){
		return stockTaskReceiveService.findCreStockOpinionByParam(param);
	}
	@Transactional(value = "CRE", readOnly = false)
	public AddOrderResponse pushUnionOrderToGED(String applyNo) {
		List<AddOrderRequest> addOrderRequestList = checkApproveUnionDao.findOrderApproveListByApplyNo(applyNo);
		AddOrderRequestList list =new AddOrderRequestList();
		for (AddOrderRequest addOrderRequest : addOrderRequestList) {
			DictUtils.getDictLabel("", "LOAN_REPAY_TYPE", null);
			addOrderRequest.setRepaymentStyle(DictUtils.getDictLabel(addOrderRequest.getRepaymentStyle(), "LOAN_REPAY_TYPE", null));
			CreGuaranteeCompany guaranteeCompany = guaranteeCompanyService.getBycheckAndRelation(addOrderRequest.getApplyIdChild());
			if(guaranteeCompany == null) {
				CheckApproveUnion checkApproveUnion = checkApproveUnionDao.get(addOrderRequest.getApplyIdChild());
				ApplyRelation relationByCustId = applyRelationService.getBatchRelationByCustIdAndRoleType(applyNo, checkApproveUnion.getCustId(),"5");
				if(relationByCustId!=null) {
					ApplyRelation byApplyNoAndRoleType = applyRelationService.getByApplyNoAndRoleType(applyNo, "8");
					guaranteeCompany = guaranteeCompanyService.get(byApplyNoAndRoleType.getCustId());
				}
			}
			/*if(guaranteeCompany!=null) {
				BigDecimal costRate = new BigDecimal(guaranteeCompany.getCostRate());
				BigDecimal multiply = addOrderRequest.getApplyAmount().multiply(costRate);
				BigDecimal multiply2 = multiply.multiply(new BigDecimal("0.01"));
				addOrderRequest.setReceivableGuaranteeFee(multiply2.toString());
			}*/
			addOrderRequest.setReceivableGuaranteeFee("1000");
		}
		list.setList(addOrderRequestList);
		AddOrderResponse addOrderToGEDInterface = Facade.facade.addOrderToGEDInterface(list,applyNo);
		return addOrderToGEDInterface;
	}

	public CheckApprove getByApplyNo(String applyNo, String taskDefKey) {
		return dao.getByApplyNo(applyNo,taskDefKey);
	}

    public CheckApprove getByUnderContract(String contract_no) {
		return  dao.getByUnderContract(contract_no);
    }
}