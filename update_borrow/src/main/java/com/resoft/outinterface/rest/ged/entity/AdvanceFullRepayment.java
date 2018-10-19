package com.resoft.outinterface.rest.ged.entity;

import com.resoft.outinterface.rest.ged.entity.GedRepayment;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 提前结清.
 *
 * @author SeppSong
 */
public class AdvanceFullRepayment extends GedRepayment {

    private static final long serialVersionUID = 4734379336543013539L;

    /**
     * 提前结清所有本金
     */
    private BigDecimal repaymentAmount;

    /**
     * 当期应还服务费
     */
    private BigDecimal currentServiceFee;

    /**
     * 当期应还利息
     */
    private BigDecimal currentInterest;

    /**
     * 当期管理费
     */
    private BigDecimal currentManageFee;

    /**
     * 逾期违约金
     */
    private BigDecimal currentOverDuePenalty;

    /**
     * 提前结清违约金
     */
    private BigDecimal repaymentPenalty;

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public BigDecimal getCurrentServiceFee() {
        return currentServiceFee;
    }

    public void setCurrentServiceFee(BigDecimal currentServiceFee) {
        this.currentServiceFee = currentServiceFee;
    }

    public BigDecimal getCurrentInterest() {
        return currentInterest;
    }

    public void setCurrentInterest(BigDecimal currentInterest) {
        this.currentInterest = currentInterest;
    }

    public BigDecimal getCurrentManageFee() {
        return currentManageFee;
    }

    public void setCurrentManageFee(BigDecimal currentManageFee) {
        this.currentManageFee = currentManageFee;
    }

    public BigDecimal getRepaymentPenalty() {
        return repaymentPenalty;
    }

    public void setRepaymentPenalty(BigDecimal repaymentPenalty) {
        this.repaymentPenalty = repaymentPenalty;
    }

    public BigDecimal getCurrentOverDuePenalty() {
        return currentOverDuePenalty;
    }

    public void setCurrentOverDuePenalty(BigDecimal currentOverDuePenalty) {
        this.currentOverDuePenalty = currentOverDuePenalty;
    }
}
