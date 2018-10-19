package com.resoft.accounting.checkAccount.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 待查账/已查账实体.
 *
 * @author SeppSong
 */
public class CheckAccountVerify extends DataEntity<CheckAccountVerify> {

    private static final long serialVersionUID = -5437835776540148998L;

    private Long applyId;

    private String contractNo;

    private String borrowName;
    /**
     * 汇款人姓名
     */
    private String remittanceName;
    /**
     * 还款日期（最近一期）
     */
    private Date repayDate;
    /**
     * 申请查账日期(创建日期)
     */
    private Date applyDate;
    /**
     * 交易日期
     */
    private Date tradeDate;
    /**
     * 还款金额
     */
    private BigDecimal repayAmount;

    private String checkAmount;

    private String repayStatus;

    private String region;

    private Date startDate;

    private Date endDate;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getRemittanceName() {
        return remittanceName;
    }

    public void setRemittanceName(String remittanceName) {
        this.remittanceName = remittanceName;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
    }

    public String getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(String checkAmount) {
        this.checkAmount = checkAmount;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
