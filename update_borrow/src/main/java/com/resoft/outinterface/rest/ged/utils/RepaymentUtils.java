package com.resoft.outinterface.rest.ged.utils;

import com.resoft.Accoutinterface.utils.AccFinancialPlatformUtils;
import com.resoft.accounting.contract.entity.Contract;
import com.resoft.accounting.staRepayPlanStatus.entity.StaRepayPlanStatus;
import com.resoft.common.utils.DateUtils;
import com.resoft.credit.repayPlan.entity.RepayPlan;
import com.resoft.outinterface.rest.ged.entity.GedRepayment;
import com.resoft.outinterface.rest.ged.entity.AdvanceFullRepayment;
import com.thinkgem.jeesite.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提前还款工具类.
 *
 * @author SeppSong
 */
public class RepaymentUtils {

    /**
     * 提前结清计算金额
     * @param contract 合同
     * @param list 还款计划列表
     * @param fullRepayment 提前结清实体
     * @return 提前结清总额
     */
    public static BigDecimal calcRepaymentMoney(Contract contract, List<RepayPlan> list, AdvanceFullRepayment fullRepayment) {
        Map<String, RepayPlan> repayPlanMap = new HashMap<>();
        for (RepayPlan repayPlan : list) {
            repayPlanMap.put(repayPlan.getPeriodNum(), repayPlan);
        }
        RepayPlan currentRepayPlan = repayPlanMap.get(fullRepayment.getPeriodNum());

        //管理费 + 利息 + 服务费 + 提前还款违约金
        if (currentRepayPlan != null) {
            fullRepayment.setCurrentManageFee(currentRepayPlan.getManagementFee());
            fullRepayment.setCurrentInterest(currentRepayPlan.getInterestAmount());
            fullRepayment.setCurrentServiceFee(currentRepayPlan.getServiceFee());
            fullRepayment.setCurrentOverDuePenalty(currentRepayPlan.getOverduePenalty());
        }

        //未还期数本金
        for (int num = Integer.valueOf(fullRepayment.getPeriodNum()); num <= list.size(); num ++) {
            RepayPlan repayPlan = repayPlanMap.get(String.valueOf(num));
            fullRepayment.setRepaymentAmount(new BigDecimal("0.00"));
            if (repayPlan != null) {
                fullRepayment.setRepaymentAmount(fullRepayment.getRepaymentAmount().add(repayPlan.getCapitalAmount()));
            }
        }

        BigDecimal repaymentAmount = new BigDecimal("0.00");
        //提前还款违约金
        BigDecimal repaymentPenalty = contract.getContractAmount().multiply(new BigDecimal("0.03"))
            .setScale(2, BigDecimal.ROUND_HALF_UP);
        fullRepayment.setRepaymentPenalty(repaymentPenalty);
        repaymentAmount = repaymentAmount.add(repaymentPenalty);
        repaymentAmount = repaymentAmount.add(fullRepayment.getRepaymentAmount());
        repaymentAmount = repaymentAmount.add(fullRepayment.getCurrentServiceFee());
        repaymentAmount = repaymentAmount.add(fullRepayment.getCurrentInterest());
        repaymentAmount = repaymentAmount.add(fullRepayment.getCurrentManageFee());
        repaymentAmount = repaymentAmount.add(fullRepayment.getCurrentOverDuePenalty());
        return repaymentAmount;
    }

    /**
     * 组装查询还款计划列表的查询条件
     * @param contractNo 合同编号
     * @return 还款计划（查询条件）
     */
    public static RepayPlan listRepayPlanByContractNo(String contractNo) {
        RepayPlan repayPlan = new RepayPlan();
        repayPlan.setContractNo(contractNo);
        return repayPlan;
    }

    /**
     * 组装还款信息
     * @param repayment 还款信息
     */
    public static void packGedRepayment(AdvanceFullRepayment repayment) {
        String seqNo = AccFinancialPlatformUtils.makeSeqNo();
        String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
        String deductTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        repayment.setSeqNo(seqNo);
        repayment.setDataDt(date);
        repayment.setDeductTime(deductTime);
    }

    /**
     * 更新当前期数
     * @param repayment 还款实体
     * @param list 状态列表
     */
    public static void updatePeriodNum(GedRepayment repayment, List<StaRepayPlanStatus> list) {
        if (list.size() > 0) {
            for (StaRepayPlanStatus staRepayPlanStatus : list) {
                if (staRepayPlanStatus != null) {
                    if (StringUtils.isNotBlank(staRepayPlanStatus.getRepayPeriodStatus()) && (
                        "0200".equals(staRepayPlanStatus.getRepayPeriodStatus()) ||
                        "0300".equals(staRepayPlanStatus.getRepayPeriodStatus()))) {
                        repayment.setPeriodNum(staRepayPlanStatus.getPeriodNum());
                    } else {
                        int perNum = Integer.parseInt(staRepayPlanStatus.getPeriodNum()) + 1;
                        repayment.setPeriodNum(String.valueOf(perNum));
                    }
                } else {
                    repayment.setPeriodNum("1");
                }
            }
        } else {
            repayment.setPeriodNum("1");
        }
    }
}
