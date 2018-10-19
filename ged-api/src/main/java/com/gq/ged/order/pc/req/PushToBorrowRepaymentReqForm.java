package com.gq.ged.order.pc.req;

/**
 * Created by Levi on 2018/3/30.
 */
public class PushToBorrowRepaymentReqForm {

    private static final long serialVersionUID = 1L;
    private String custId;//客户id在冠E通存在的账户id
    private String contractNo;//合同号
    private String periodNum;//期数
    private String deductCustName; // 扣款人Name   扣款人姓名
    private String deductApplyNo; // 扣款申请序列号  （你们产生）
    private String mobileNum;// 手机号  扣款人
    private String bankcardNo;// 银行卡号    扣款人
    private String bank; // 开户银行（字典类型：BANKS） 扣款人
    private String deductAmount; //账户余额
    private String orderNo; //第三方流水号
    private String advanceRepayMoney;//提前结清违约金
    private String advanceFlag;//提前结清标

    public String getAdvanceRepayMoney() {
        return advanceRepayMoney;
    }

    public void setAdvanceRepayMoney(String advanceRepayMoney) {
        this.advanceRepayMoney = advanceRepayMoney;
    }

    public String getAdvanceFlag() {
        return advanceFlag;
    }

    public void setAdvanceFlag(String advanceFlag) {
        this.advanceFlag = advanceFlag;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(String deductAmount) {
        this.deductAmount = deductAmount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPeriodNum() {
        return periodNum;
    }

    public void setPeriodNum(String periodNum) {
        this.periodNum = periodNum;
    }

    public String getDeductCustName() {
        return deductCustName;
    }

    public void setDeductCustName(String deductCustName) {
        this.deductCustName = deductCustName;
    }

    public String getDeductApplyNo() {
        return deductApplyNo;
    }

    public void setDeductApplyNo(String deductApplyNo) {
        this.deductApplyNo = deductApplyNo;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getBankcardNo() {
        return bankcardNo;
    }

    public void setBankcardNo(String bankcardNo) {
        this.bankcardNo = bankcardNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
