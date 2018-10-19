package com.resoft.credit.contract.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resoft.accounting.contract.dao.AccContractDao;
import com.resoft.accounting.discount.dao.AccDiscountDao;
import com.resoft.accounting.discount.entity.AccDiscount;
import com.resoft.accounting.discount.service.AccDiscountService;
import com.resoft.credit.checkApprove.dao.CheckApproveDao;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.checkApproveUnion.dao.CheckApproveUnionDao;
import com.resoft.credit.loanApply.dao.CreApplyRegisterDao;
import com.resoft.postloan.accountClean.service.AccountCleanService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.accounting.accContract.AccContract;
import com.resoft.common.utils.CalculatorFormulasUtils;
import com.resoft.common.utils.Constants;
import com.resoft.credit.CreGedBorrowStatus.service.CreGedBorrowStatusService;
import com.resoft.credit.applyLoanStatus.dao.ApplyLoanStatusDao;
import com.resoft.credit.applyLoanStatus.entity.ApplyLoanStatus;
import com.resoft.credit.applyRegister.dao.ApplyRegisterDao;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRelation.entity.ApplyRelation;
import com.resoft.credit.applyRelation.service.ApplyRelationService;
import com.resoft.credit.checkApproveUnion.entity.CheckApproveUnion;
import com.resoft.credit.checkApproveUnion.service.CheckApproveUnionService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contract.dao.ContractDao;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.entity.RepayPlanData;
import com.resoft.credit.guaranteeCompany.entity.CreGuaranteeCompany;
import com.resoft.credit.guaranteeCompany.service.CreGuaranteeCompanyService;
import com.resoft.credit.guaranteeRelation.entity.GuaranteeRelation;
import com.resoft.credit.guaranteeRelation.service.GuaranteeRelationService;
import com.resoft.credit.guranteeCompanyRelation.entity.CreApplyCompanyRelation;
import com.resoft.credit.guranteeCompanyRelation.service.CreApplyCompanyRelationService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.resoft.credit.processSuggestionInfo.service.ProcessSuggestionInfoService;
import com.resoft.credit.rateCapital.entity.RateCapital;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.credit.repayPlan.service.RepayPlanService;
import com.resoft.credit.repayPlanUnion.dao.RepayPlanUnionDao;
import com.resoft.credit.repayPlanUnion.entity.RepayPlanUnion;
import com.resoft.credit.repayPlanUnion.service.RepayPlanUnionService;
import com.resoft.credit.videoDir.dao.VideoUploadDao;
import com.resoft.outinterface.rest.ged.GedClient;
import com.resoft.outinterface.rest.newged.entity.AddOrderResponse;
import com.resoft.outinterface.rest.newged.entity.OrderContractReqForm;
import com.resoft.outinterface.rest.newged.entity.OrderContractReqFormLists;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 合同信息Service
 *
 * @author yanwanmei
 * @version 2016-03-02
 */
@Service("creContractService")
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class ContractService extends CrudService<ContractDao, Contract> {
	private static final Logger logger = LoggerFactory.getLogger(ContractService.class);
	@Autowired
	private CreGedBorrowStatusService creGedBorrowStatusService;
	@Autowired
	private RepayPlanService repayPlanService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private VideoUploadDao videoUploadDao;
	@Autowired
	private ProcessSuggestionInfoService processSuggestionInfoService;
	@Autowired
	private RepayPlanUnionDao repayPlanUnionDao;
	@Autowired
	private ApplyLoanStatusDao applyLoanStatusDao;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private CheckApproveUnionService checkApproveUnionService;
	@Autowired
	private CreApplyCompanyRelationService applyCompanyRelationService;
	@Autowired
	private ApplyRelationService applyRelationService;
	@Autowired
	private GuaranteeRelationService guaranteeRelationService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private CreGuaranteeCompanyService guaranteeCompanyService;
	@Autowired
	private RepayPlanUnionService repayPlanUnionService;
    @Autowired
	private AccDiscountDao accDiscountDao;

    @Autowired
	private CheckApproveDao checkApproveDao;

    @Autowired
	private CreApplyRegisterDao creApplyRegisterDao;

    @Autowired
	private ContractDao contractDao;
    @Autowired
	private CheckApproveUnionDao checkApproveUnionDao;
    @Autowired
    private ApplyRegisterDao applyRegisterDao;


	public Contract get(String id) {
		return super.get(id);
	}

	public List<Contract> findList(Contract contract) {
		return super.findList(contract);
	}

	public Page<Contract> findPage(Page<Contract> page, Contract contract) {
		return super.findPage(page, contract);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void save(Contract contract) {
		super.save(contract);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void delete(Contract contract) {
		super.delete(contract);
	}

	public Contract getContractByApplyNo(String applyNo) {
		return super.dao.getContractByApplyNo(applyNo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void updateByApplyNo(Contract contract) {
		super.dao.updateByApplyNo(contract);
	}

	public Map<String, String> findOrgLevelByOrgId(String orgId) {
		Map<String, String> orgLevelsMap = new HashMap<String, String>();

		// 根据客户的orgId，先去sys_office表中查找levelnum,来判断这个客户所处的机构位于哪一层
		Map<String, Object> orgLevelMap = super.dao.findLevelNumByOrgId(orgId);
		String levelNum = orgLevelMap.get("levelNum") + "";

		// 如果levelNum，表示为四级机构，要查找它的父机构以及父机构的父机构，一级机构为为总公司，不需要保存
		if (Constants.OFFICE_LEVEL_MD.equals(levelNum)) {
			String orgLevel4 = orgId;
			String orgLevel3 = (String) orgLevelMap.get("parentId");
			orgLevelsMap.put("orgLevel3", orgLevel3);
			orgLevelsMap.put("orgLevel4", orgLevel4);

			String orgLevel2 = dao.findParentIdById(orgLevel3);
			orgLevelsMap.put("orgLevel2", orgLevel2);
		} else if (Constants.OFFICE_LEVEL_QY.equals(levelNum)) {
			String orgLevel3 = orgId;
			String orgLevel2 = (String) orgLevelMap.get("parentId");
			orgLevelsMap.put("orgLevel3", orgLevel3);
			orgLevelsMap.put("orgLevel2", orgLevel2);
		} else if (Constants.OFFICE_LEVEL_DQ.equals(levelNum)) {
			orgLevelsMap.put("orgLevel2", orgId);
		}

		return orgLevelsMap;
	}

	public Map<String, Object> findLevelNumByOrgId(String orgId) {
		return super.dao.findLevelNumByOrgId(orgId);
	}

	public List<RateCapital> getRateCapitalCurr(Map<String, String> params) {
		return super.dao.getRateCapitalCurr(params);
	}

	public Contract getContractByApplyNoCustId(Map<String, String> params) {
		return super.dao.getContractByApplyNoCustId(params);
	}

	public Contract getContractByApproveId(Map<String, String> params) {
		return super.dao.getContractByApproveId(params);
	}

	public String getCurrContractNo(Map<String, String> params) {
		return super.dao.getCurrContractNo(params);
	}

	public List<Contract> findListByApplyNo(Map<String, String> params) {
		return super.dao.findListByApplyNo(params);
	}

	/**
	 * 根据冠e通返回的计算还款计划
	 * @param repayPlanData
	 * @return
	 * @throws Exception
	 */
	// 生成还款计划表中的数据
	public List<RepayPlan> calculateRepayPlan(RepayPlanData repayPlanData) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();

		Map<String, String> params = Maps.newConcurrentMap();
		params.put("loanRepayType", repayPlanData.getApproLoanRepayType());
		params.put("periodValue", repayPlanData.getApproPeriodValue());
		params.put("approProductTypeCode", repayPlanData.getApproProductTypeCode());

		List<RateCapital> rateCapitalList = super.dao.getRateCapitalCurr(params);

		// 合同金额
		BigDecimal contractAmount = repayPlanData.getContractAmount();
		// 期数
		int approPeriodValue = Integer.parseInt(repayPlanData.getApproPeriodValue());
		// 当期本金
		BigDecimal capitalAmount = new BigDecimal("0");
		/*
		 * // 当期利息 BigDecimal interestAmount = new BigDecimal("0"); //储存合同金额/期数
		 * BigDecimal contractAmountPeriodTotal = new BigDecimal("0");
		 */

		// *************************************************************//
		// 调用工具类计算还款方式
		BigDecimal contractRate = repayPlanData.getInterest();
		List<Map<String, BigDecimal>> exportRepayPlan = new ArrayList<Map<String, BigDecimal>>();

		if ("5".equals(repayPlanData.getApproLoanRepayType())) {
			exportRepayPlan = CalculatorFormulasUtils.getACPIMonthPaymentAmount(contractAmount, new BigDecimal(rateCapitalList.get(0).getRateInterest().getRateInterest()), approPeriodValue, contractRate);
		} else {
			exportRepayPlan = CalculatorFormulasUtils.getLadderMonthPaymentAmount(contractAmount, new BigDecimal(rateCapitalList.get(0).getRateInterest().getRateInterest()), approPeriodValue, contractRate, rateCapitalList);
		}

		// ************************************************************//
		Date firstDeductDate = null;
		for (int i = 0; i < approPeriodValue; i++) {
			RepayPlan repayPlan = new RepayPlan();
			repayPlan.preInsert();
			repayPlan.setApplyNo(repayPlanData.getApplyNo());
			repayPlan.setContractNo(repayPlanData.getContractNo());

			// ******************//
			// 增加合同还款本金、利息、合计以及差额本金、利息
			repayPlan.setBidCapitalAmount(exportRepayPlan.get(i).get("htMonthBaseAmount"));
			repayPlan.setBidInterestAmount(exportRepayPlan.get(i).get("htMonthIrrAmount"));
			repayPlan.setBidRepayAmount(exportRepayPlan.get(i).get("htMonthSumAmount"));
			repayPlan.setDifCapitalAmount(exportRepayPlan.get(i).get("ceMonthBaseAmount"));
			repayPlan.setDifInterestAmount(exportRepayPlan.get(i).get("ceMonthIrrAmount"));
			// ******************//

			if (StringUtils.isEmpty(repayPlanData.getIsAcc()) && repayPlanData.getDeductDate() != null) {
				/*
				 * if (i == 0) {
				 * repayPlan.setDeductDate(repayPlanData.getDeductDate()); }
				 * else { // 每次还款日推迟一个月 Calendar cal = Calendar.getInstance();
				 * cal.setTime(repayPlanData.getDeductDate());
				 * cal.add(Calendar.DATE, 0); cal.add(Calendar.MONTH, 1); Date
				 * deductDate = cal.getTime();
				 * repayPlan.setDeductDate(deductDate);
				 * repayPlanData.setDeductDate(deductDate); }
				 */
				Calendar cal = Calendar.getInstance();
				cal.setTime(repayPlanData.getDeductDate());
				if (cal.get(Calendar.MONTH) == 2 && cal.get(Calendar.DAY_OF_MONTH) == 1) {// 特殊情况：如果是3月1号提现回盘，第一期还款应该是3月30，2月没有30号，Calendar日期减一会导致所有期数还款日为28号或29号
					if (i == 0) {
						cal.add(Calendar.MONTH, 1);
						cal.add(Calendar.DATE, -2);// 04-01改到03-30,减2天
						Date deductDate = cal.getTime();
						repayPlan.setDeductDate(deductDate);
						firstDeductDate = deductDate;
					} else {
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(firstDeductDate);
						cal2.add(Calendar.DATE, 0);// 肯定不是31号
						cal2.add(Calendar.MONTH, i);
						Date deductDate = cal2.getTime();
						repayPlan.setDeductDate(deductDate);
					}
				} else {
					cal.add(Calendar.DATE, -1);
					if (cal.get(Calendar.DAY_OF_MONTH) == 31) {// 如果还款日是31号，则改为30号
						cal.add(Calendar.DATE, -1);
					}
					cal.add(Calendar.MONTH, i + 1);
					Date deductDate = cal.getTime();
					repayPlan.setDeductDate(deductDate);
				}
			}

			if (StringUtils.isNotEmpty(repayPlanData.getIsAcc()) && StringUtils.isNotEmpty(repayPlanData.getDeductDateStr())) {
				/*
				 * if (i == 0) {
				 * repayPlan.setDeductDateStr(repayPlanData.getDeductDateStr());
				 * } else { // 每次还款日推迟一个月 Calendar cal = Calendar.getInstance();
				 * cal.setTime(repayPlanData.getDeductDate());
				 * cal.add(Calendar.DATE, 0); cal.add(Calendar.MONTH, 1); Date
				 * deductDate = cal.getTime();
				 * repayPlan.setDeductDateStr(sdf.format(deductDate));
				 * repayPlanData.setDeductDate(deductDate); }
				 */
				Calendar cal = Calendar.getInstance();
				cal.setTime(repayPlanData.getDeductDate());
				if (cal.get(Calendar.MONTH) == 2 && cal.get(Calendar.DAY_OF_MONTH) == 1) {
					if (i == 0) {
						cal.add(Calendar.MONTH, 1);
						cal.add(Calendar.DATE, -2);// 04-01改到03-30,减2天
						Date deductDate = cal.getTime();
						repayPlan.setDeductDateStr(sdf.format(deductDate));
						firstDeductDate = deductDate;
						repayPlan.setDeductDate(deductDate);
					} else {
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(firstDeductDate);
						cal2.add(Calendar.DATE, 0);// 肯定不是31号
						cal2.add(Calendar.MONTH, i);
						Date deductDate = cal2.getTime();
						repayPlan.setDeductDateStr(sdf.format(deductDate));
						repayPlan.setDeductDate(deductDate);
					}
				} else {
					cal.add(Calendar.DATE, -1);
					if (cal.get(Calendar.DAY_OF_MONTH) == 31) {// 如果还款日是31号，则改为30号
						cal.add(Calendar.DATE, -1);
					}
					cal.add(Calendar.MONTH, i + 1);
					Date deductDate = cal.getTime();
					repayPlan.setDeductDate(deductDate);
					repayPlan.setDeductDateStr(sdf.format(deductDate));
				}

			}

			repayPlan.setPeriodNum(rateCapitalList.get(i).getPeriodNum());

			// 计算当期本金
			/*
			 * BigDecimal rateCapitalCurr = new
			 * BigDecimal(rateCapitalList.get(i).getRateCapitalCurr());
			 * if("5".equals(repayPlanData.getApproLoanRepayType())){ if (i ==
			 * (approPeriodValue - 1)){ capitalAmount =
			 * contractAmount.subtract((contractAmount.divide(new
			 * BigDecimal(approPeriodValue), 2,
			 * BigDecimal.ROUND_HALF_UP)).multiply(new
			 * BigDecimal(approPeriodValue-1))); }else{ capitalAmount =
			 * contractAmount.divide(new BigDecimal(approPeriodValue), 2,
			 * BigDecimal.ROUND_HALF_UP); } }else { capitalAmount =
			 * contractAmount.multiply(rateCapitalCurr); }
			 */
			// 运用工具类计算的当期本金
			repayPlan.setCapitalAmount(exportRepayPlan.get(i).get("monthBaseAmount"));

			// 计算当期利息
			// 参数表中当期剩余应还本金比例
			/*
			 * BigDecimal rateCapitalRemain = new
			 * BigDecimal(rateCapitalList.get(i).getRateCapitalRemain()); //
			 * 利息月利率(月利率是直接从数据库中读的，应该是不用除以100，但是库里的数据是年利率，数据不对) BigDecimal
			 * rateInterest = new
			 * BigDecimal(rateCapitalList.get(i).getRateInterest
			 * ().getRateInterest()).divide(new BigDecimal(100));
			 *
			 * if("5".equals(repayPlanData.getApproLoanRepayType())){ //标的利息
			 * interestAmount =
			 * ((contractAmount.multiply(rateInterest).multiply(new
			 * BigDecimal(Math
			 * .pow((BigDecimal.ONE.add(rateInterest)).doubleValue(),
			 * approPeriodValue)))).divide(new
			 * BigDecimal(Math.pow((BigDecimal.ONE
			 * .add(rateInterest)).doubleValue(), approPeriodValue) - 1), 2,
			 * BigDecimal.ROUND_UP)).subtract(capitalAmount);
			 * logger.info("标的利息 ======"+interestAmount); }else { 合同金额*
			 * 参数表中当期剩余应还本金比例 * 参数表中利息月利率 BigDecimal.valueOf(approPeriodValue)
			 * interestAmount =
			 * contractAmount.multiply(rateCapitalRemain).multiply
			 * (rateInterest); }
			 */
			// 运用工具类计算的标的利息
			repayPlan.setInterestAmount(exportRepayPlan.get(i).get("monthIrrAmount"));

			// 计算当期服务费
			BigDecimal serviceFee = new BigDecimal("0");
			// 放款时扣除服务费
			BigDecimal serviceFeeRate = repayPlanData.getServiceFeeRate().divide(new BigDecimal("100"));// 服务费率
			if (Constants.SERVICE_FEE_TYPE_FKSKCFWF.equals(repayPlanData.getServiceFeeType())) {
				serviceFee = new BigDecimal("0");
			} else if (Constants.SERVICE_FEE_TYPE_HKZJTDMQ.equals(repayPlanData.getServiceFeeType())) {// 还款中均摊到每期扣除服务费
				// 合同金额*服务费率
				 serviceFee = contractAmount.multiply(serviceFeeRate);
			}
			repayPlan.setServiceFee(serviceFee);
			
				
			// 计算账户管理费
			BigDecimal managementFee = new BigDecimal("0");

			/*
			 * // 非等额本息的账户管理费算法:合同金额*利息(根据还款方式和还款期限从cre_rate_interest中读取) -
			 * 还款计划表中的 当期利息 managementFee =
			 * contractAmount.multiply(repayPlanData
			 * .getInterest()).subtract(interestAmount);
			 */
			// 运用工具类计算账户管理费（差额本息）
			repayPlan.setManagementFee(exportRepayPlan.get(i).get("ceMonthSumAmount"));

			/*
			 * // 计算逾期违约金费用 BigDecimal overduePenalty = new BigDecimal("0");
			 * List<Map<String, String>> overduePenaltyList =
			 * repayPlanService.getOverduePenalty(); overduePenalty =
			 * serviceFee.add(managementFee).add(capitalAmount); if
			 * (overduePenalty.compareTo(new
			 * BigDecimal(overduePenaltyList.get(0).get("label"))) <= 0) {
			 * overduePenalty = overduePenalty.multiply(new
			 * BigDecimal(overduePenaltyList.get(0).get("value"))); } else if
			 * (overduePenalty.compareTo(new
			 * BigDecimal(overduePenaltyList.get(overduePenaltyList.size() -
			 * 2).get("label"))) > 0) { overduePenalty =
			 * overduePenalty.multiply(new
			 * BigDecimal(overduePenaltyList.get(overduePenaltyList.size() -
			 * 1).get("value"))); } else { for (int j = 0; j <
			 * overduePenaltyList.size(); j++) { if
			 * (overduePenalty.compareTo(new
			 * BigDecimal(overduePenaltyList.get(j).get("label"))) > 0 &&
			 * overduePenalty.compareTo(new BigDecimal(overduePenaltyList.get(j
			 * + 1).get("label"))) <= 0) { overduePenalty =
			 * overduePenalty.multiply(new BigDecimal(overduePenaltyList.get(j +
			 * 1).get("value"))); break; } } }
			 * repayPlan.setOverduePenalty(overduePenalty);
			 */

			// 计算月还款
			BigDecimal repayAmount = new BigDecimal("0");

			/*
			 * if(Constants.LOAN_REPAY_TYPE_DEBX.equals(repayPlanData.
			 * getApproLoanRepayType())){ repayAmount =
			 * serviceFee.add(capitalAmount
			 * ).add(contractAmount.multiply(repayPlanData.getInterest()));
			 * }else { 合同金额* 参数表中当期剩余应还本金比例 * 参数表中利息月利率
			 * BigDecimal.valueOf(approPeriodValue) repayAmount =
			 * serviceFee.add(
			 * managementFee).add(capitalAmount).add(interestAmount); }
			 */

			// （运用工具类计算合同还款本息）+计算当期服务费

			repayAmount = serviceFee.add(exportRepayPlan.get(i).get("htMonthSumAmount"));

			repayPlan.setRepayAmount(repayAmount);

			// 计算逾期违约金费用
			BigDecimal overduePenalty = new BigDecimal("0");
			List<Map<String, String>> overduePenaltyList = repayPlanService.getOverduePenalty();
			if (repayAmount.compareTo(new BigDecimal(overduePenaltyList.get(0).get("label"))) <= 0) {
				overduePenalty = repayAmount.multiply(new BigDecimal(overduePenaltyList.get(0).get("value")));
			} else if (repayAmount.compareTo(new BigDecimal(overduePenaltyList.get(overduePenaltyList.size() - 2).get("label"))) > 0) {
				overduePenalty = repayAmount.multiply(new BigDecimal(overduePenaltyList.get(overduePenaltyList.size() - 1).get("value")));
			} else {
				for (int j = 0; j < overduePenaltyList.size(); j++) {
					if (repayAmount.compareTo(new BigDecimal(overduePenaltyList.get(j).get("label"))) > 0 && repayAmount.compareTo(new BigDecimal(overduePenaltyList.get(j + 1).get("label"))) <= 0) {
						overduePenalty = repayAmount.multiply(new BigDecimal(overduePenaltyList.get(j + 1).get("value")));
						break;
					}
				}
			}
			//新增数据还款计划违约金为0
			if (StringUtils.isNotBlank(repayPlan.getApplyNo())) {
				ApplyRegister ap = applyRegisterDao.getApplyRegisterByApplyNo(repayPlan.getApplyNo());
					if ("0".equals(ap.getNewOld())) {
						repayPlan.setOverduePenalty(new BigDecimal("0"));
					}else{
						repayPlan.setOverduePenalty(serviceFee);
					}
			}else{
				repayPlan.setOverduePenalty(overduePenalty);
			}
			

			if (StringUtils.isNotEmpty(repayPlanData.getIsAcc())) {
				// 机构号
				repayPlan.setOrgLevel2(repayPlanData.getOrgLevel2());
				repayPlan.setOrgLevel3(repayPlanData.getOrgLevel3());
				repayPlan.setOrgLevel4(repayPlanData.getOrgLevel4());

				// 总期数
				repayPlan.setPeriodValue(Integer.parseInt(rateCapitalList.get(i).getPeriodValue()));

				// 客户名称
				repayPlan.setCustName(repayPlanData.getCustName());
				// 生成日期
				repayPlan.setCreateDateStr(sdf.format(new Date()));
				// 资金平台账号
				repayPlan.setCapitalTerraceNo(repayPlanData.getCapitalTerraceNo());
			}

			if (StringUtils.isEmpty(repayPlanData.getIsAcc())) {
				// 流程ID
				repayPlan.setTaskDefKey(repayPlanData.getTaskDefKey());
			}

			repayPlanList.add(i, repayPlan);
		}
		return repayPlanList;
	}

	public Contract getContractByContractNo(String contractNo) {
		return super.dao.getContractByContractNo(contractNo);
	}

	public String getInterest(Map<String, String> params) {
		return super.dao.getInterest(params);
	}

	@Transactional(value = "CRE", readOnly = false)
	public String insertContractAndRepayPlan(Contract contract) {
		String message = null;
		List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();
		List<RepayPlan> repayPlanListTmp = new ArrayList<RepayPlan>();
		try {
			Map<String, String> param = Maps.newConcurrentMap();
			param.put("applyNo", contract.getApplyNo());
			param.put("taskDefKey", Constants.UTASK_HTMQ);
			repayPlanService.deleteRepayPlanByApplyNo(param);
			// 生成还款计划表
			RepayPlanData repayPlanData = new RepayPlanData();
			repayPlanData.setApproProductTypeCode(contract.getApproProductTypeCode());
			repayPlanData.setApplyNo(contract.getApplyNo());
			repayPlanData.setContractNo(contract.getContractNo());
			repayPlanData.setApproLoanRepayType(contract.getApproLoanRepayType());
			repayPlanData.setApproPeriodValue(contract.getApproPeriodValue());
			repayPlanData.setContractAmount(contract.getContractAmount());
			repayPlanData.setServiceFeeType(contract.getServiceFeeType());
			repayPlanData.setServiceFeeRate(contract.getServiceFeeRate());
			// repayPlanData.setDeductDate(DateUtils.getNextMonth(contract.getConStartDate()));//1月30号提现回盘第一个月还款日是2月28，如果直接传合同起始日期的下个月，后面所有期数的还款日都会是28
			repayPlanData.setDeductDate(contract.getConStartDate());
			// 查询利率
			BigDecimal interest = contract.getApproYearRate();
			if (interest != null) {
				interest = interest.multiply(new BigDecimal("0.01"));
			}
			repayPlanData.setInterest(interest);
			repayPlanData.setTaskDefKey(Constants.UTASK_HTMQ);
			repayPlanListTmp = calculateRepayPlan(repayPlanData);
			
			// 判断 如果是一次性付清本息 则进行数据处理
			if (Constants.LOAN_REPAY_TYPE_YCXHBFX.equals(contract.getApproLoanRepayType())) {
				repayPlanList = recountData(repayPlanListTmp, contract.getApproPeriodValue());
			} else {
				repayPlanList = repayPlanListTmp;
			}
			// 保存合同信息
			// 合同结束日期设置为还款计划表最后一期日期
			contract.setConEndDate(repayPlanList.get(repayPlanList.size() - 1).getDeductDate());
			//-----------------------------------------------------------
			ApplyRelation byApplyNoAndRoleType = applyRelationService.getByApplyNoAndRoleType(contract.getApplyNo(), "5");
			ApplyRelation byApplyNoAndRoleType2 = applyRelationService.getByApplyNoAndRoleType(contract.getApplyNo(), "8");
			contract.setLoanCompanyName(byApplyNoAndRoleType.getCustName());
			contract.setLoanCompanyID(byApplyNoAndRoleType.getCustId());
			contract.setGuaranteeCompanyName(byApplyNoAndRoleType2.getCustName());
			contract.setGuaranteeCompanyId(byApplyNoAndRoleType2.getCustId());
			//---------------------------------
			if (getContractByApplyNo(contract.getApplyNo()) != null) {
				contract.preUpdate();
				dao.update(contract);
			} else {
				contract.preInsert();
				dao.insert(contract);
			}
			repayPlanService.insertRepayPlanList(repayPlanList);
			message = "success";
		} catch (Exception e1) {
			message = "fail";
			logger.error("保存合同信息或生成还款计划出现异常！", e1);
		}
		return message;
	}

	@Transactional(value = "CRE", readOnly = false)
	public String insertContractAndRepayPlanUnion(Contract contract, String approId) {
		String message = null;
		//--------------------------------------------------------------------------------------------------------------
		//企业信息
		CompanyInfo companyInfo = companyInfoService.getCompanyNameByApproId(contract.getApproId());
		//担保信息
		CreGuaranteeCompany guaranteeCompany = guaranteeCompanyService.getGuaranByCompanyIdAndType(contract.getApplyNo() ,companyInfo.getId(),"3");
		if(guaranteeCompany==null) {//主借企业担保公司
			guaranteeCompany = guaranteeCompanyService.getByApplyNoAndRoleType(contract.getApplyNo(), "8");
		}

		contract.setLoanCompanyName(companyInfo.getBusiRegName());
		contract.setLoanCompanyID(companyInfo.getId());
		contract.setGuaranteeCompanyName(guaranteeCompany.getGuaranteeCompanyName());
		contract.setGuaranteeCompanyId(guaranteeCompany.getId());
		//--------------------------------------------------------------------------------------------------------------
		List<RepayPlan> repayPlanList = new ArrayList<RepayPlan>();
		List<RepayPlan> repayPlanListTmp = new ArrayList<RepayPlan>();
		try {

			Map<String, String> param = Maps.newConcurrentMap();
			param.put("applyNo", contract.getApplyNo());
			param.put("taskDefKey", Constants.UTASK_HTMQ);
			param.put("approId", approId);
			repayPlanUnionDao.deleteRepayPlanUnion(param);
			// 生成还款计划表
			RepayPlanData repayPlanData = new RepayPlanData();
			repayPlanData.setApproProductTypeCode(contract.getApproProductTypeCode());
			repayPlanData.setApplyNo(contract.getApplyNo());
			repayPlanData.setContractNo(contract.getContractNo());
			repayPlanData.setApproLoanRepayType(contract.getApproLoanRepayType());
			repayPlanData.setApproPeriodValue(contract.getApproPeriodValue());
			repayPlanData.setContractAmount(contract.getContractAmount());
			repayPlanData.setServiceFeeType(contract.getServiceFeeType());
			repayPlanData.setServiceFeeRate(contract.getServiceFeeRate());
			// repayPlanData.setDeductDate(DateUtils.getNextMonth(contract.getConStartDate()));//1月30号提现回盘第一个月还款日是2月28，如果直接传合同起始日期的下个月，后面所有期数的还款日都会是28
			repayPlanData.setDeductDate(contract.getConStartDate());
			// 查询利率
			BigDecimal interest = contract.getApproYearRate();
			if (interest != null) {
				interest = interest.multiply(new BigDecimal("0.01"));
			}
			repayPlanData.setInterest(interest);
			repayPlanData.setTaskDefKey(Constants.UTASK_HTMQ);
			repayPlanListTmp = calculateRepayPlan(repayPlanData);

			// 判断 如果是一次性付清本息 则进行数据处理
			if (Constants.LOAN_REPAY_TYPE_YCXHBFX.equals(contract.getApproLoanRepayType())) {
				repayPlanList = recountData(repayPlanListTmp, contract.getApproPeriodValue());
			} else {
				repayPlanList = repayPlanListTmp;
			}

			// 保存合同信息
			// 合同结束日期设置为还款计划表最后一期日期
			contract.setConEndDate(repayPlanList.get(repayPlanList.size() - 1).getDeductDate());

			Map<String, String> paramContract = Maps.newConcurrentMap();
			paramContract.put("applyNo", contract.getApplyNo());
			paramContract.put("custId", contract.getCustId());
			paramContract.put("approveId", approId);
			Contract contract1 = getContractByApproveId(paramContract);
			//得到借款企业名称



			if (contract1 != null) {
				contract.preUpdate();
				dao.update(contract);
			} else {
				contract.preInsert();
				dao.insert(contract);
			}

			List<RepayPlanUnion> repayPlanUnions = Lists.newArrayList();
			for (int j = 0; j < repayPlanList.size(); j++) {
				RepayPlanUnion repayPlanUnion = new RepayPlanUnion();
				repayPlanUnion.setId(repayPlanList.get(j).getId());
				repayPlanUnion.setApplyNo(repayPlanList.get(j).getApplyNo());
				repayPlanUnion.setApproveId(approId);
				repayPlanUnion.setContractNo(repayPlanList.get(j).getContractNo());
				repayPlanUnion.setDuebillId(repayPlanList.get(j).getDuebillId());
				repayPlanUnion.setPeriodNum(repayPlanList.get(j).getPeriodNum());
				repayPlanUnion.setDeductDate(repayPlanList.get(j).getDeductDate());
				repayPlanUnion.setRepayAmount(repayPlanList.get(j).getRepayAmount());
				repayPlanUnion.setServiceFee(repayPlanList.get(j).getServiceFee());
				repayPlanUnion.setManagementFee(repayPlanList.get(j).getManagementFee());
				repayPlanUnion.setCapitalAmount(repayPlanList.get(j).getCapitalAmount());
				repayPlanUnion.setInterestAmount(repayPlanList.get(j).getInterestAmount());
				repayPlanUnion.setBidCapitalAmount(repayPlanList.get(j).getBidCapitalAmount());
				repayPlanUnion.setBidInterestAmount(repayPlanList.get(j).getBidInterestAmount());
				repayPlanUnion.setBidRepayAmount(repayPlanList.get(j).getBidRepayAmount());
				repayPlanUnion.setDifCapitalAmount(repayPlanList.get(j).getDifCapitalAmount());
				repayPlanUnion.setDifInterestAmount(repayPlanList.get(j).getDifInterestAmount());
				repayPlanUnion.setOverduePenalty(repayPlanList.get(j).getOverduePenalty());
				repayPlanUnion.setTaskDefKey(repayPlanList.get(j).getTaskDefKey());
				repayPlanUnions.add(repayPlanUnion);
			}
			repayPlanUnionDao.saveRepayPlanUnionList(repayPlanUnions);

		/*
			在合同生成之后 操作
			新增根据申请编号查询批复信息表查询
            interest_rate_diff保证金月息差andinterest_monthly_spread
            利息月息差的和作为贴息总金额存储到acc_discount贴息表中*/
			insertDiscount(contract);
			message = "success";
		} catch (Exception e1) {
			message = "fail";
			logger.error("保存合同信息或生成还款计划出现异常！", e1);
		}
		return message;
	}

	/**
	 * 通过合同号查询-cre库
	 * @param contractNo
	 * @return
	 */
	public AccContract getCreContractByContractNo(String contractNo) {
		return super.dao.getCreContractByContractNo(contractNo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public String saveSuggestion(ActTaskParam actTaskParam, String passFlag, Contract contract, ProcessSuggestionInfo processSuggestionInfo) {
		String checkFlag = null;
		try {
			if ("yes".equals(passFlag)) {// 提交
				//传递冠易贷合同关系
				if(Constants.UTASK_HTMQ.equalsIgnoreCase(actTaskParam.getTaskDefKey())){
					ApplyRegister ar = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
					List<Contract> contractByApplyNoList = dao.getContractDataByApplyNo(actTaskParam.getApplyNo());
					List<OrderContractReqForm> orderContractReqFormList = new ArrayList<OrderContractReqForm>();
					OrderContractReqFormLists orderContractReqFormLists =new OrderContractReqFormLists();
					for (Contract contractByApplyNo: contractByApplyNoList) {
						//String managerFee = dao.queryContractAccountManagerFeeByCon(contractByApplyNo.getContractNo());
						OrderContractReqForm orderContractReqForm = new OrderContractReqForm();
						orderContractReqForm.setContractNo(contractByApplyNo.getContractNo());
						orderContractReqForm.setOrderNo(contractByApplyNo.getApplyNo());
						//orderContractReqForm.setAccountManageFee(new BigDecimal(managerFee));
						if(Constants.PROC_DEF_KEY_LHSX.equalsIgnoreCase(ar.getProcDefKey())){
							orderContractReqForm.setApplyIdChild(contractByApplyNo.getApproId());
							OrderContractReqForm temp1 = repayPlanUnionService.getRepayAndContract(actTaskParam.getApplyNo(),"utask_htmq",contractByApplyNo.getApproId());
							orderContractReqForm.setAccountManageFee(temp1.getAccountManageFee());

							orderContractReqForm.setContractProvince(temp1.getContractProvince());
							orderContractReqForm.setContractCity(temp1.getContractCity());
							orderContractReqForm.setContractDistinct(temp1.getContractDistinct());
						}else {
							OrderContractReqForm temp1 = repayPlanService.getRepayAndContract(actTaskParam.getApplyNo(),"utask_htmq");
							orderContractReqForm.setAccountManageFee(temp1.getAccountManageFee());

							orderContractReqForm.setContractProvince(temp1.getContractProvince());
							orderContractReqForm.setContractCity(temp1.getContractCity());
							orderContractReqForm.setContractDistinct(temp1.getContractDistinct());
						}
						BigDecimal bigDecimal = new BigDecimal("0.00");
						BigDecimal bigDecimal2 = new BigDecimal("0.00");
						if(!(contractByApplyNo.getServiceFee()==null)) {
							bigDecimal= new BigDecimal(contractByApplyNo.getServiceFee().toString());
						}
						if(!(contractByApplyNo.getSpecialServiceFee()==null)) {
							bigDecimal2= new BigDecimal(contractByApplyNo.getSpecialServiceFee().toString());
						}
						orderContractReqForm.setServiceFee(bigDecimal.add(bigDecimal2).toString());
						orderContractReqFormList.add(orderContractReqForm);
					}
					orderContractReqFormLists.setList(orderContractReqFormList);
					AddOrderResponse addOrderResponse = Facade.facade.sendConytractRelation(orderContractReqFormLists);
				}
				Map<String, Object> params = Maps.newHashMap();
				params.put("applyNo", actTaskParam.getApplyNo());
				params.put("taskDefKey", actTaskParam.getTaskDefKey());
				params.put("lockFlag", "1");
				videoUploadDao.lockVideoMessageByApplyNoAndTaskDefKey(params);
				// 将applyNo和suggestion保存到processSuggestionInfo中，主要是为了利用之前做抵质押物评估中的方法。
				processSuggestionInfo.setApplyNo(actTaskParam.getApplyNo());
				processSuggestionInfo.setTaskDefKey(actTaskParam.getTaskDefKey());
				processSuggestionInfo.setIsAbnormal("0");
				processSuggestionInfoService.saveVisit(processSuggestionInfo);
				BigDecimal contractAmount = contract.getContractAmount();
				if (contractAmount != null) {
					contractAmount = BigDecimal.ZERO;
				}
				if (Constants.UTASK_HTMQ.equalsIgnoreCase(actTaskParam.getTaskDefKey())) {
					// 流转条件参数map
					Map<String, Object> processParams = Maps.newHashMap();
					processParams.put("contractAmount", contractAmount);// 合同放款金额
					processParams.put("contractAmountConfig", DictUtils.getDictValue(Constants.WORKFLOW_CONTRACT_AMOUNT_DESC, Constants.WORKFLOW_CONTRACT_AMOUNT, "5000000"));// 流程流转条件配置的合同金额
					List<Dict> dicts = DictUtils.getDictList(Constants.IS_UTASK_DQSH_NEED);
					if(dicts != null && dicts.size() != 0){
						for(int i = 0;i<dicts.size();i++){
							String id = super.dao.getOfficeIdByCode(dicts.get(i).getValue());
							if(StringUtils.isNull(id) || StringUtils.isNull(contract.getOrgLevel2())){
								processParams.put("is_utask_dqsh_need", "0");//不进行大区审核
							}else{
								if(contract.getOrgLevel2().equalsIgnoreCase(id)){
									processParams.put("is_utask_dqsh_need", "1");//进行大区审核
									break;
								}else{
									processParams.put("is_utask_dqsh_need", "0");//不进行大区审核
								}
							}
						}
					}else{
						processParams.put("is_utask_dqsh_need", "0");
					}
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + processSuggestionInfo.getSuggestionDesc(), "提交", processParams);
				} else {
					actTaskService.complete(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【提交】" + processSuggestionInfo.getSuggestionDesc(), "提交", null);
				}
				checkFlag = "pass";
				if(Constants.UTASK_HTSH.equalsIgnoreCase(actTaskParam.getTaskDefKey())){
					ApplyRegister ar = applyRegisterService.getApplyRegisterByApplyNo(actTaskParam.getApplyNo());
					if(!(ar.getProcDefKey()!=null && ar.getProcDefKey().contains("union"))){
						creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(),null, GedClient.ged_shtg,0,null);
					}else {
						List<CheckApproveUnion> checkApproveUnionByApplyNo = checkApproveUnionService.getCheckApproveUnionByApplyNo(actTaskParam.getApplyNo());
						for (CheckApproveUnion checkApproveUnion2 : checkApproveUnionByApplyNo) {
							creGedBorrowStatusService.saveGedBorrowStatusByApplyNo(actTaskParam.getApplyNo(),checkApproveUnion2.getId(), GedClient.ged_shtg,1,null);
						}
					}
				}
			} else {
				checkFlag = "back";
				actTaskService.backOnANode(actTaskParam.getTaskId(), actTaskParam.getProcInstId(), "【打回】" + processSuggestionInfo.getSuggestionDesc());
			}
		} catch (Exception e) {
			logger.error("保存" + actTaskParam.getTaskDefKey() + "意见报错！", e);
			checkFlag = "error";
		}
		return checkFlag;
	}

	public List<Contract> getContractDataByApplyNo(String applyNo) {
		return this.dao.getContractDataByApplyNo(applyNo);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void updateContractNoById(Contract contract,ApplyLoanStatus applyLoanStatus) {
		this.dao.updateContractNoById(contract);
		applyLoanStatusDao.updateContractNo(applyLoanStatus);
	}

	@Transactional(value = "CRE", readOnly = false)
	public void updateContractDataByApproveId(Map<String, String> params) {
		this.dao.updateContractDataByApproveId(params);
	}

	public List<RepayPlan> recountData(List<RepayPlan> repayPlanList, String periodNum) {
		BigDecimal repayAmount = new BigDecimal(0);
		BigDecimal interestAmount = new BigDecimal(0);
		BigDecimal serviceFee = new BigDecimal(0);
		BigDecimal managementFee = new BigDecimal(0);
		BigDecimal capitalAmount = new BigDecimal(0);
		BigDecimal overduePenalty = new BigDecimal(0);
		BigDecimal bidCapitalAmount = new BigDecimal(0);
		BigDecimal bidInterestAmount = new BigDecimal(0);
		BigDecimal bidRepayAmount = new BigDecimal(0);
		BigDecimal difCapitalAmount = new BigDecimal(0);
		BigDecimal difInterestAmount = new BigDecimal(0);
		for (int i = 0; i < repayPlanList.size(); i++) {
			repayAmount = repayAmount.add(repayPlanList.get(i).getRepayAmount());
			interestAmount = interestAmount.add(repayPlanList.get(i).getInterestAmount());
			serviceFee = serviceFee.add(repayPlanList.get(i).getServiceFee());
			managementFee = managementFee.add(repayPlanList.get(i).getManagementFee());
			capitalAmount = capitalAmount.add(repayPlanList.get(i).getCapitalAmount());
			overduePenalty = overduePenalty.add(repayPlanList.get(i).getOverduePenalty());
			bidCapitalAmount = bidCapitalAmount.add(repayPlanList.get(i).getBidCapitalAmount());
			bidInterestAmount = bidInterestAmount.add(repayPlanList.get(i).getBidInterestAmount());
			bidRepayAmount = bidRepayAmount.add(repayPlanList.get(i).getBidRepayAmount());
			difCapitalAmount = difCapitalAmount.add(repayPlanList.get(i).getDifCapitalAmount());
			difInterestAmount = difInterestAmount.add(repayPlanList.get(i).getDifInterestAmount());
			repayPlanList.get(i).setRepayAmount(new BigDecimal(0));
			repayPlanList.get(i).setInterestAmount(new BigDecimal(0));
			repayPlanList.get(i).setServiceFee(new BigDecimal(0));
			repayPlanList.get(i).setManagementFee(new BigDecimal(0));
			repayPlanList.get(i).setCapitalAmount(new BigDecimal(0));
			repayPlanList.get(i).setOverduePenalty(new BigDecimal(0));
			repayPlanList.get(i).setBidCapitalAmount(new BigDecimal(0));
			repayPlanList.get(i).setBidInterestAmount(new BigDecimal(0));
			repayPlanList.get(i).setBidRepayAmount(new BigDecimal(0));
			repayPlanList.get(i).setDifCapitalAmount(new BigDecimal(0));
			repayPlanList.get(i).setDifInterestAmount(new BigDecimal(0));
		}
		for (int i = 0; i < repayPlanList.size(); i++) {
			int value = Integer.valueOf(repayPlanList.get(i).getPeriodNum());
			int num = Integer.valueOf(periodNum);
			if (num == value) {
				repayPlanList.get(i).setRepayAmount(repayAmount);
				repayPlanList.get(i).setInterestAmount(interestAmount);
				repayPlanList.get(i).setServiceFee(serviceFee);
				repayPlanList.get(i).setManagementFee(managementFee);
				repayPlanList.get(i).setCapitalAmount(capitalAmount);
				repayPlanList.get(i).setOverduePenalty(overduePenalty);
				repayPlanList.get(i).setBidCapitalAmount(bidCapitalAmount);
				repayPlanList.get(i).setBidInterestAmount(bidInterestAmount);
				repayPlanList.get(i).setBidRepayAmount(bidRepayAmount);
				repayPlanList.get(i).setDifCapitalAmount(difCapitalAmount);
				repayPlanList.get(i).setDifInterestAmount(difInterestAmount);
			}
		}
		return repayPlanList;
	}

	public Map<String, Object> findContractAndApplyLoanStatus(String applyNo, String contractNo) {
		return dao.findContractAndApplyLoanStatus(applyNo, contractNo);
	}
	@Transactional(value = "CRE", readOnly = false)
	public void updateFactGuarantee(String contractNo, BigDecimal factGuaranteeFee, BigDecimal factGuaranteeGold,BigDecimal factServiceFee) {
		dao.updateFactGuarantee( contractNo,  factGuaranteeFee,  factGuaranteeGold,factServiceFee);
	}

	public String checkIsConfig(String applyNo) {
		//批量借款  担保公司
		List<CreApplyCompanyRelation>  applyCompanyRelationList= applyCompanyRelationService.getByApplyNoRoleType(applyNo,"3");
		for (CreApplyCompanyRelation creApplyCompanyRelation : applyCompanyRelationList) {
			if(StringUtils.isEmpty(creApplyCompanyRelation.getIsConfirm())||"0".equals(creApplyCompanyRelation.getIsConfirm())) {
				return "批量借款企业的担保公司未确认担保关系或者已经拒绝担保关系！";
			}
		}
		ApplyRelation applyRelation=applyRelationService.getByApplyNoAndRoleType(applyNo,"8");
		if(StringUtils.isEmpty(applyRelation.getIsConfirm())||"0".equals(applyRelation.getIsConfirm())) {
			return "主借企业的担保公司未确认担保关系或者已经拒绝担保关系！";
		}
		List<CheckApproveUnion> CheckApproveUnionList = checkApproveUnionService.getCheckApproveUnionByApplyNo(applyNo);
		for (CheckApproveUnion checkApproveUnion : CheckApproveUnionList) {
			String flag = "0";
			List<GuaranteeRelation> guaranteeRelationList= guaranteeRelationService.getRelationByApplyNoAndCompanyId(checkApproveUnion.getCustId(), applyNo);
			for (GuaranteeRelation guaranteeRelation : guaranteeRelationList) {
				if("1".equals(guaranteeRelation.getIsConfirm())) {//至少有一个
					flag="1";
					break;
				}
			}
			if("0".equals(flag)) {
				return "借款企业未确认担保关系或者已经拒绝担保关系！";
			}
		}
		return null;
	}

	@Transactional(value = "CRE", readOnly = false)
	public void updateGuranteeRelation(String applyNo,String custId,String contractNo){
		updateContractNo(applyNo, custId, contractNo);
		CreApplyCompanyRelation companyRelation = applyCompanyRelationService.findApplyRelationByCustAndRole(applyNo, custId);
		if (companyRelation != null && StringUtils.isNotBlank(companyRelation.getRoleType())  && "3".equals(companyRelation.getRoleType())) {
			confirmRelation(custId,applyNo,contractNo);
		}
	}


	private void updateContractNo(String applyNo,String custId,String contractNo){
		ApplyRelation applyRelation = applyRelationService.getByApplyNoAndRoleType(applyNo,"8");
		if (applyRelation != null) {
			confirmRelation(custId,applyNo,contractNo);
		}
	}

	private void confirmRelation(String custId,String applyNo,String contractNO){
		List<GuaranteeRelation> guaranteeRelations = guaranteeRelationService.getRelationByApplyNoAndCompanyId(custId, applyNo);
		Boolean flag = false;
		for(GuaranteeRelation guaranteeRelation:guaranteeRelations){
			if (guaranteeRelation != null) {
				flag = true;
				break;
			}
		}
		if(flag){
			dao.confirmGuranteeRelation(contractNO);
		}

	}

	@Transactional(value = "CRE", readOnly = false)
	public int updateContractGedApiSave(String contractNo){

		return super.dao.updateContractGedApiSave(contractNo);
	}




	public Page<MyMap> showAllContract(Page<MyMap> page, MyMap paramMap) {
		paramMap.setPage(page);
		page.setList(dao.showAllContract(paramMap));
		return page;
	}

	public List<Contract> getContractListByApplyNo(String applyNo) {
		return dao.getContractListByApplyNo(applyNo);
	}

	public String checkIsSignContract(String applyNo) {
		List<Contract> contract = dao.getContractListByApplyNo(applyNo);
		for (Contract contractSigle : contract) {
			if("0".equals(contractSigle.getSignFlag())||StringUtils.isEmpty(contractSigle.getSignFlag())) {
				return contractSigle.getContractNo()+"未签约！";
			}
		}
		return null;
	}

	/*
    在合同生成之后 操作
    新增根据申请编号查询批复信息表查询
    interest_rate_diff保证金月息差andinterest_monthly_spread
    利息月息差的和作为贴息总金额存储到acc_discount贴息表中*/
	public void insertDiscount(Contract contract){
		Map<String,String>paramMap=new HashMap<>();
		paramMap.put("applyNo",contract.getApplyNo());
		String procDefKey = applyRegisterService.queryRegisterByApplyNo(contract.getApplyNo());
		if ("gqcc_loan_union".equals(procDefKey)) {
			//这是联合授信
			CheckApproveUnion checkApprove = checkApproveUnionDao.queryUnionByContract(contract.getContractNo());
			if(null!=checkApprove){

				if(null == checkApprove.getInterestMonthlySpread()){
					checkApprove.setInterestMonthlySpread(new BigDecimal("0.00"));
				}
				if(null == checkApprove.getInterestRateDiff()){
					checkApprove.setInterestRateDiff(new BigDecimal("0.00"));
				}
				System.out.println("利后月系差====="+checkApprove.getInterestMonthlySpread());
				System.out.println("月息差========"+checkApprove.getInterestRateDiff());
				BigDecimal SumDiscountFee11=checkApprove.getInterestMonthlySpread().add(checkApprove.getInterestRateDiff());//总贴息金额=保证金月息差+利息月息差
				System.out.println("总贴息金额====="+SumDiscountFee11);//总贴息金额
				Double SumDiscountFee=SumDiscountFee11.doubleValue();
				if(SumDiscountFee>0){
					accDiscountDao.deleteAccDiscount(contract.getContractNo()); //先删除贴息表的数据 再导入数据
					String processSquence1=checkApprove.getApproPeriodValue(); //修改期数
					int processSquence=Integer.parseInt(processSquence1);
					Double discountFee1=SumDiscountFee/processSquence;//贴息金额=总贴息金额/期数
					System.out.println("贴息金额====="+discountFee1);//贴息金额
					DecimalFormat df=new DecimalFormat("#.00");
					String discountFee=df.format(discountFee1);
					System.out.println("约分小数点后两位的贴息金额====="+discountFee);

					for(int i=1;i<=processSquence;i++){
						AccDiscount discount=new AccDiscount();
						discount.setContractNo(contract.getContractNo());
						discount.setPeriodNum(String.valueOf(i));
						discount.setDiscountFee(discountFee);
						discount.setOperateName(UserUtils.getUser().getLoginName());
						discount.setOperateOrgName(UserUtils.getUser().getName());
						discount.setCreateBy(UserUtils.getUser().getCreateBy());
						discount.setCreateDate(new Date());
						discount.setId(IdGen.uuid());
						accDiscountDao.insertAccDiscount(discount);
					}
					//在合同表中增加状态标示新老数据贴息
					Contract  discontract = new Contract();
					discontract.setContractNo(contract.getContractNo());
					discontract.setDiscountSave("1");//新老数据区分贴息金额导入
					contractDao.updateDiscountSaveByContract(discontract);
				}
			}

		}else{
			//这是非联合授信
			List<CheckApprove>checkApproveList=checkApproveDao.getCheckApproveByApplyNo(paramMap);
			if(checkApproveList!=null&&checkApproveList.size()>0){
				CheckApprove checkApprove=checkApproveList.get(0);//获取最近的批复信息
				if(null!=checkApprove){

					if(null == checkApprove.getInterestMonthlySpread()){
						checkApprove.setInterestMonthlySpread(new BigDecimal("0.00"));
					}
					if(null == checkApprove.getInterestRateDiff()){
						checkApprove.setInterestRateDiff(new BigDecimal("0.00"));
					}
					System.out.println("利后月系差====="+checkApprove.getInterestMonthlySpread());
					System.out.println("月息差========"+checkApprove.getInterestRateDiff());
					BigDecimal SumDiscountFee11=checkApprove.getInterestMonthlySpread().add(checkApprove.getInterestRateDiff());//总贴息金额=保证金月息差+利息月息差
					System.out.println("总贴息金额====="+SumDiscountFee11);//总贴息金额
					Double SumDiscountFee=SumDiscountFee11.doubleValue();
					if(SumDiscountFee>0){
						accDiscountDao.deleteAccDiscount(contract.getContractNo()); //先删除贴息表的数据 再导入数据
						String processSquence1=checkApprove.getApproPeriodValue(); //修改期数
						int processSquence=Integer.parseInt(processSquence1);
						Double discountFee1=SumDiscountFee/processSquence;//贴息金额=总贴息金额/期数
						System.out.println("贴息金额====="+discountFee1);//贴息金额
						DecimalFormat df=new DecimalFormat("#.00");
						String discountFee=df.format(discountFee1);
						System.out.println("约分小数点后两位的贴息金额====="+discountFee);

						for(int i=1;i<=processSquence;i++){
							AccDiscount discount=new AccDiscount();
							discount.setContractNo(contract.getContractNo());
							discount.setPeriodNum(String.valueOf(i));
							discount.setDiscountFee(discountFee);
							discount.setOperateName(UserUtils.getUser().getLoginName());
							discount.setOperateOrgName(UserUtils.getUser().getName());
							discount.setCreateBy(UserUtils.getUser().getCreateBy());
							discount.setCreateDate(new Date());
							discount.setId(IdGen.uuid());
							accDiscountDao.insertAccDiscount(discount);
						}
						//在合同表中增加状态标示新老数据贴息
						Contract  discontract = new Contract();
						discontract.setContractNo(contract.getContractNo());
						discontract.setDiscountSave("1");//新老数据区分贴息金额导入
						contractDao.updateDiscountSaveByContract(discontract);
					}
				}
			}
		}

	}

}