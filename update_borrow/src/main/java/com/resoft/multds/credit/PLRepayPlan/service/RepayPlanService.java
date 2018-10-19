package com.resoft.multds.credit.PLRepayPlan.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.resoft.multds.credit.PLRepayPlan.dao.RepayPlanDao;
import com.resoft.multds.credit.PLRepayPlan.entity.PLRateCapital;
import com.resoft.multds.credit.PLRepayPlan.entity.PLRepayPlan;
import com.resoft.multds.credit.PLRepayPlan.entity.PLRepayPlanData;
import com.thinkgem.jeesite.common.persistence.annotation.DbType;
import com.thinkgem.jeesite.common.utils.StringUtils;

@Service
@DbType("cre.dbType")
@Transactional(value = "CRE", readOnly = true)
public class RepayPlanService {

	@Autowired
	private RepayPlanDao repayPlanDao;

	// 生成还款计划表中的数据
	@Transactional(value = "CRE", readOnly = false)
	public List<PLRepayPlan> calculateRepayPlan(PLRepayPlanData repayPlanData) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<PLRepayPlan> repayPlanList = new ArrayList<PLRepayPlan>();

		Map<String, String> params = Maps.newConcurrentMap();
		params.put("loanRepayType", repayPlanData.getApproLoanRepayType());
		params.put("periodValue", repayPlanData.getApproPeriodValue());
		params.put("approProductTypeCode", repayPlanData.getApproProductTypeCode());
		
		List<PLRateCapital> rateCapitalList = repayPlanDao.getRateCapitalCurr(params);

		// 合同金额
		BigDecimal contractAmount = repayPlanData.getContractAmount();
		// 期数
		int approPeriodValue = Integer.parseInt(repayPlanData.getApproPeriodValue());
		// 当期本金
		BigDecimal capitalAmount = new BigDecimal("0");
		// 当期利息
		BigDecimal interestAmount = new BigDecimal("0");
		// 储存合同金额/期数
		//BigDecimal contractAmountPeriodTotal = new BigDecimal("0");

		// 查询利率
		String interestStr = repayPlanDao.getInterestByApplyNo(repayPlanData.getApplyNo());
		BigDecimal interest = new BigDecimal("0");
		if (interestStr != null && StringUtils.isNotEmpty(interestStr)) {
			interest = new BigDecimal(interestStr).multiply(new BigDecimal("0.01"));
		}
		for (int i = 0; i < approPeriodValue; i++) {
			PLRepayPlan repayPlan = new PLRepayPlan();
			repayPlan.preInsert();
			repayPlan.setApplyNo(repayPlanData.getApplyNo());
			repayPlan.setContractNo(repayPlanData.getContractNo());
			if (StringUtils.isEmpty(repayPlanData.getIsAcc()) && repayPlanData.getDeductDate() != null) {
				if (i == 0) {
					repayPlan.setDeductDate(repayPlanData.getDeductDate());
				} else {
					// 每次还款日推迟一个月
					Calendar cal = Calendar.getInstance();
					cal.setTime(repayPlanData.getDeductDate());
					cal.add(Calendar.DATE, 0);
					cal.add(Calendar.MONTH, 1);
					Date deductDate = cal.getTime();
					repayPlan.setDeductDate(deductDate);
					repayPlanData.setDeductDate(deductDate);
				}
			}

			if (StringUtils.isNotEmpty(repayPlanData.getIsAcc()) && StringUtils.isNotEmpty(repayPlanData.getDeductDateStr())) {
				if (i == 0) {
					repayPlan.setDeductDateStr(repayPlanData.getDeductDateStr());
				} else {
					// 每次还款日推迟一个月
					Calendar cal = Calendar.getInstance();
					cal.setTime(repayPlanData.getDeductDate());
					cal.add(Calendar.DATE, 0);
					cal.add(Calendar.MONTH, 1);
					Date deductDate = cal.getTime();
					repayPlan.setDeductDateStr(sdf.format(deductDate));
					repayPlanData.setDeductDate(deductDate);
				}
			}

			repayPlan.setPeriodNum(rateCapitalList.get(i).getPeriodNum());

			// 计算当期本金
			BigDecimal rateCapitalCurr = new BigDecimal(rateCapitalList.get(i).getRateCapitalCurr());

			capitalAmount = contractAmount.multiply(rateCapitalCurr);
			repayPlan.setCapitalAmount(capitalAmount);

			// 计算当期利息
			// 参数表中当期剩余应还本金比例
			BigDecimal rateCapitalRemain = new BigDecimal(rateCapitalList.get(i).getRateCapitalRemain());
			// 利息月利率(月利率是直接从数据库中读的，应该是不用除以100，但是库里的数据是年利率，数据不对)
			BigDecimal rateInterest = new BigDecimal(rateCapitalList.get(i).getPlRateInterest().getRateInterest()).divide(new BigDecimal(100));
			/* 合同金额* 参数表中当期剩余应还本金比例 * 参数表中利息月利率 */
			interestAmount = contractAmount.multiply(rateCapitalRemain).multiply(rateInterest);
			repayPlan.setInterestAmount(interestAmount);

			// 计算当期服务费
			BigDecimal serviceFee = new BigDecimal("0");
			// 放款前先扣除服务费
			BigDecimal serviceFeeRate = repayPlanData.getServiceFeeRate().divide(new BigDecimal("100"));// 服务费率
			if ("2".equals(repayPlanData.getServiceFeeType())) {
				serviceFee = new BigDecimal("0");
			} else if ("1".equals(repayPlanData.getServiceFeeType())) {// 放完款后再扣服务费:全部服务费放到第一期中
				if (!"1".equals(repayPlan.getPeriodNum())) {
					serviceFee = new BigDecimal("0");
				} else {
					serviceFee = contractAmount.multiply(serviceFeeRate);
					serviceFee = serviceFee.multiply(new BigDecimal(approPeriodValue));
				}
			} else if ("3".equals(repayPlanData.getServiceFeeType())) {// 服务费再还款过程中还
				// 合同金额*服务费率
				serviceFee = contractAmount.multiply(serviceFeeRate);
			}
			repayPlan.setServiceFee(serviceFee);

			// 计算账户管理费
			BigDecimal managementFee = new BigDecimal("0");

			// 非等额本息的账户管理费算法:合同金额*利息(根据还款方式和还款期限从cre_rate_interest中读取) - 还款计划表中的
			// 当期利息
			managementFee = contractAmount.multiply(interest).subtract(interestAmount);
			repayPlan.setManagementFee(managementFee);

			// 计算逾期违约金费用
			BigDecimal overduePenalty = new BigDecimal("0");
			List<Map<String, String>> overduePenaltyList = repayPlanDao.getOverduePenalty();
			overduePenalty = serviceFee.add(managementFee).add(capitalAmount);
			if (overduePenalty.compareTo(new BigDecimal(overduePenaltyList.get(0).get("label"))) <= 0) {
				overduePenalty = overduePenalty.multiply(new BigDecimal(overduePenaltyList.get(0).get("value")));
			} else if (overduePenalty.compareTo(new BigDecimal(overduePenaltyList.get(overduePenaltyList.size() - 2).get("label"))) > 0) {
				overduePenalty = overduePenalty.multiply(new BigDecimal(overduePenaltyList.get(overduePenaltyList.size() - 1).get("value")));
			} else {
				for (int j = 0; j < overduePenaltyList.size(); j++) {
					if (overduePenalty.compareTo(new BigDecimal(overduePenaltyList.get(j).get("label"))) > 0 && overduePenalty.compareTo(new BigDecimal(overduePenaltyList.get(j + 1).get("label"))) <= 0) {
						overduePenalty = overduePenalty.multiply(new BigDecimal(overduePenaltyList.get(j + 1).get("value")));
						break;
					}
				}
			}
			repayPlan.setOverduePenalty(overduePenalty);

			// 计算月还款
			BigDecimal repayAmount = new BigDecimal("0");
			repayAmount = serviceFee.add(managementFee).add(capitalAmount).add(interestAmount);
			repayPlan.setRepayAmount(repayAmount);

			if (StringUtils.isNotEmpty(repayPlanData.getIsAcc())) {
				// // 机构号
				// repayPlan.setOrgLevel2(repayPlanData.getOrgLevel2());
				// repayPlan.setOrgLevel3(repayPlanData.getOrgLevel3());
				// repayPlan.setOrgLevel4(repayPlanData.getOrgLevel4());

				// 总期数
				repayPlan.setPeriodValue(Integer.parseInt(rateCapitalList.get(i).getPeriodValue()));

				// 客户名称
				// repayPlan.setCustName(repayPlanData.getCustName());
				// // 生成日期
				// repayPlan.setCreateDateStr(sdf.format(new Date()));
				// // 资金平台账号
				// repayPlan.setCapitalTerraceNo(repayPlanData.getCapitalTerraceNo());
			}

			repayPlanList.add(i, repayPlan);
		}
		return repayPlanList;
	}
}
