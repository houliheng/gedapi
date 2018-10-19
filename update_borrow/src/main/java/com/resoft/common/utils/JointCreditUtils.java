package com.resoft.common.utils;

import com.resoft.credit.checkApprove.entity.CheckApprove;
import java.math.BigDecimal;

/**
 * .
 *
 * @author SeppSong
 */
public class JointCreditUtils {

    /**
     * 计算利息月息差
     * 债股结合 ->（0.8-让利后月利率） * 放款金额
     *
     * @param checkApprove 批复信息
     * @param loanAmount 放款金额
     * @return 利息月息差
     */
    public static BigDecimal calcInterestMonthlySpread(CheckApprove checkApprove, BigDecimal loanAmount) {
        //产品类型编码
        String productTypeCode = checkApprove.getApproProductTypeCode();
        //让利后月利率
        BigDecimal discountInterestRate = checkApprove.getDiscountInterestRate();

        BigDecimal interestMonthlySpread = new BigDecimal(0);
        if (Constants.PRODUCT_TYPE_ZGJH.equals(productTypeCode)) {
            interestMonthlySpread = loanAmount.multiply(new BigDecimal(0.8).subtract(discountInterestRate))
                .multiply(new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return interestMonthlySpread;
    }

    /**
     * 算保证金月息差
     * 债股结合 -> 保证金 * 0.8%  // 改成  保证金 * 批复月利率
     * 采购贷 保证金率 >= 30% -> 保证金 * 批复月利率
     *
     * @param checkApprove 批复信息
     * @param marginRate 保证金率
     * @param marginAmount 保证金
     * @return 保证金月息差
     */
    public static BigDecimal calcInterestRateDiff(CheckApprove checkApprove, BigDecimal marginRate,
        BigDecimal marginAmount) {
        //批复月利率
        BigDecimal approYearRate = checkApprove.getApproYearRate();

        BigDecimal interestRateDiff = new BigDecimal(0);
        if (Constants.PRODUCT_TYPE_ZGJH.equals(checkApprove.getApproProductTypeCode())) {
            //interestRateDiff = marginAmount.multiply(new BigDecimal(0.008)).setScale(2, BigDecimal.ROUND_HALF_UP);
            interestRateDiff = marginAmount.multiply(approYearRate).multiply(new BigDecimal(0.01))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (Constants.PRODUCT_TYPE_CG.equals(checkApprove.getApproProductTypeCode())
            && marginRate.compareTo(new BigDecimal(30)) >= 0) {
            interestRateDiff = marginAmount.multiply(approYearRate).multiply(new BigDecimal(0.01))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return interestRateDiff;
    }
}
