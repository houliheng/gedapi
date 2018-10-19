package com.resoft.credit.repayPlan.utils;

import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.contract.entity.RepayPlanData;
import java.math.BigDecimal;

/**
 * 还款计划工具类.
 *
 * @author SeppSong
 */
public class RepayPlanUtils {

    public static RepayPlanData packRepayPlan(CheckApprove checkApprove) {
        RepayPlanData repayPlanData = new RepayPlanData();
        repayPlanData.setApproProductTypeCode(checkApprove.getApproProductTypeCode());
        repayPlanData.setApplyNo(checkApprove.getApplyNo());
        repayPlanData.setApproLoanRepayType(checkApprove.getApproLoanRepayType());
        repayPlanData.setApproPeriodValue(checkApprove.getApproPeriodValue());
        repayPlanData.setContractAmount(checkApprove.getContractAmount());
        repayPlanData.setServiceFeeRate(checkApprove.getServiceFeeRate());
        repayPlanData.setServiceFeeType(checkApprove.getServiceFeeType());
        repayPlanData.setTaskDefKey(checkApprove.getTaskDefKey());
        // 查询利率
        BigDecimal interest = checkApprove.getApproYearRate();
        if (interest != null) {
            interest = interest.multiply(new BigDecimal("0.01"));
        }
        repayPlanData.setInterest(interest);
        repayPlanData.setContractNo("");
        return repayPlanData;
    }
}
