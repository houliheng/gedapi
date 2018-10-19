package com.resoft.credit.checkApprove.utils;

import com.resoft.common.utils.Constants;
import com.resoft.credit.checkApprove.entity.CheckApprove;
import com.resoft.credit.fdfs.Constant;
import java.math.BigDecimal;

/**
 * 批复信息工具类.
 *
 * @author SeppSong
 */
public class CheckApproveUtils {

    public static String validateMonthlySpreadStatus(String taskDefKey) {
        if (Constants.UTASK_FGSFKSH.equals(taskDefKey) || Constants.UTASK_FGSJLSH.equals(taskDefKey) ||
            Constants.UTASK_QYFKSH.equals(taskDefKey) || Constants.UTASK_QYJLSH.equals(taskDefKey) ||
            Constants.UTASK_DQFKSH.equals(taskDefKey) || Constants.UTASK_DQFKJLSH.equals(taskDefKey) ||
            Constants.UTASK_ZGSFKSH.equals(taskDefKey) || Constants.UTASK_ZGSJLSH.equals(taskDefKey)) {
            return "1";
        }
        return "0";
    }

    public static String validateFlowCode(String productTypeCode, String taskDefKey) {
        if (Constants.UTASK_FGSSX.equals(taskDefKey) || Constants.UTASK_DQSXQR.equals(taskDefKey) ||
            Constants.UTASK_ZGSSXQR.equals(taskDefKey) || Constants.UTASK_HTYY.equals(taskDefKey) ||
            Constants.UTASK_HTMQ.equals(taskDefKey) || Constants.UTASK_HTSH.equals(taskDefKey) ||
            Constants.UTASK_CWFK.equals(taskDefKey)) {
            if (Constants.PRODUCT_TYPE_ZGJH.equals(productTypeCode)) {
                return "1";
            } else if (Constants.PRODUCT_TYPE_CG.equals(productTypeCode)) {
                return "2";
            }
        }
        return "0";
    }

    public static void calcApproveYearRate(CheckApprove approve) {
        BigDecimal approveYearRate = approve.getContractYearRate().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
        approve.setApproYearRate(approveYearRate);
    }
}
