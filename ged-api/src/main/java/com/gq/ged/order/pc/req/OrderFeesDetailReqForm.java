package com.gq.ged.order.pc.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 资金管理-费用支付详情
 * Created by Levi on 2018/3/5.
 */
public class OrderFeesDetailReqForm implements Serializable{

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("合同地址")
    private String contractUrl;

    @ApiModelProperty("1 费用支付 2无此订单 3订单详细")
    private Integer pageNum;
    @ApiModelProperty("费用支付页面展示的提示信息")
    private String alertInfo;

    @ApiModelProperty("服务费缴费状态 0 未交费 1 已充值服务费 2充值失败 3 缴费完成  5 充值中")
    private String isPay;

    @ApiModelProperty("是否提现 0 不能提现   1可以提现")
    private String isWithdraw;
    @ApiModelProperty("是否开户 1 是  0否")
    private String isOpenAccount;

    @ApiModelProperty("1 提60%  2提现100%")
    private String withdrawPercent;

    @ApiModelProperty("已缴费金额")
    private String feePaidAmount = "";
    @ApiModelProperty("缴费日期")
    private String feePaidDate = "";
    @ApiModelProperty("已提现金额")
    private String feeAwithdrawAmount = "";
    @ApiModelProperty("提现日期")
    private String feeAwithdrawDate = "";

    //第一版面
    @ApiModelProperty("订单号")
    private String  orderCode;
    @ApiModelProperty("企业开户行")
    private String  companyBankOfDeposit;
    @ApiModelProperty("借款金额--批复金额")
    private String  replyAmount;
    @ApiModelProperty("公司名称  -- 20180305日由 收款户名 修改为公司名称")//20180305日由 收款户名 修改为公司名称
    private String  companyName;
    @ApiModelProperty("借款期限")
    private String  loanTerm;
    @ApiModelProperty("账号")
    private String  companyAccount;
    @ApiModelProperty("借款状态")
    private String  status;

    //第二版面
    @ApiModelProperty("服务费")
    private String  serviceFee;
    @ApiModelProperty("担保费")
    private String  guaranteeFee;
    @ApiModelProperty("保证金")
    private String  cashDeposit;

    @ApiModelProperty("应支付总金额")
    private String  allPayAmount;

    //第三版面
    @ApiModelProperty("放款日期")
    private Date lendingDate;
    @ApiModelProperty("放款金额")
    private String  lendingAmount;
    @ApiModelProperty("资金账户余额")
    private String moneyAccountBalance;
    @ApiModelProperty("可提余额")
    private String usefullBalance;

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public String getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(String isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }

    public String getWithdrawPercent() {
        return withdrawPercent;
    }

    public void setWithdrawPercent(String withdrawPercent) {
        this.withdrawPercent = withdrawPercent;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFeePaidAmount() {
        return feePaidAmount;
    }

    public void setFeePaidAmount(String feePaidAmount) {
        this.feePaidAmount = feePaidAmount;
    }

    public String getFeePaidDate() {
        return feePaidDate;
    }

    public void setFeePaidDate(String feePaidDate) {
        this.feePaidDate = feePaidDate;
    }

    public String getFeeAwithdrawAmount() {
        return feeAwithdrawAmount;
    }

    public void setFeeAwithdrawAmount(String feeAwithdrawAmount) {
        this.feeAwithdrawAmount = feeAwithdrawAmount;
    }

    public String getFeeAwithdrawDate() {
        return feeAwithdrawDate;
    }

    public void setFeeAwithdrawDate(String feeAwithdrawDate) {
        this.feeAwithdrawDate = feeAwithdrawDate;
    }

    public String getIsWithdraw() {
        return isWithdraw;
    }

    public void setIsWithdraw(String isWithdraw) {
        this.isWithdraw = isWithdraw;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getAlertInfo() {
        return alertInfo;
    }

    public void setAlertInfo(String alertInfo) {
        this.alertInfo = alertInfo;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCompanyBankOfDeposit() {
        return companyBankOfDeposit;
    }

    public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
        this.companyBankOfDeposit = companyBankOfDeposit;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getReplyAmount() {
        return replyAmount;
    }

    public void setReplyAmount(String replyAmount) {
        this.replyAmount = replyAmount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getGuaranteeFee() {
        return guaranteeFee;
    }

    public void setGuaranteeFee(String guaranteeFee) {
        this.guaranteeFee = guaranteeFee;
    }

    public String getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(String cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public String getAllPayAmount() {
        return allPayAmount;
    }

    public void setAllPayAmount(String allPayAmount) {
        this.allPayAmount = allPayAmount;
    }

    public Date getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(Date lendingDate) {
        this.lendingDate = lendingDate;
    }

    public String getLendingAmount() {
        return lendingAmount;
    }

    public void setLendingAmount(String lendingAmount) {
        this.lendingAmount = lendingAmount;
    }

    public String getMoneyAccountBalance() {
        return moneyAccountBalance;
    }

    public void setMoneyAccountBalance(String moneyAccountBalance) {
        this.moneyAccountBalance = moneyAccountBalance;
    }

    public String getUsefullBalance() {
        return usefullBalance;
    }

    public void setUsefullBalance(String usefullBalance) {
        this.usefullBalance = usefullBalance;
    }
}
