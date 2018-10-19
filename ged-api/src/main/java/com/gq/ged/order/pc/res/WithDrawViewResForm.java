package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Levi on 2018/3/14.
 */
public class WithDrawViewResForm implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单来自")
    private String source;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("账户余额")
    private String accountAmt;
    @ApiModelProperty("可提现金额")
    private String canWithdrawAmt;
    @ApiModelProperty("提现金额")
    private String withDrawAmt;
    @ApiModelProperty("实际到账金额")
    private String actualAmt;
    @ApiModelProperty("开户名称")
    private String corporationName; //
    @ApiModelProperty("银行名称")
    private String bankName;
    @ApiModelProperty("银行卡尾号")
    private String bankNum;
    @ApiModelProperty("预计到账日期")
    private String withDrawDate;

    public String getCanWithdrawAmt() {
        return canWithdrawAmt;
    }

    public void setCanWithdrawAmt(String canWithdrawAmt) {
        this.canWithdrawAmt = canWithdrawAmt;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(String actualAmt) {
        this.actualAmt = actualAmt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAccountAmt() {
        return accountAmt;
    }

    public void setAccountAmt(String accountAmt) {
        this.accountAmt = accountAmt;
    }

    public String getWithDrawAmt() {
        return withDrawAmt;
    }

    public void setWithDrawAmt(String withDrawAmt) {
        this.withDrawAmt = withDrawAmt;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getWithDrawDate() {
        return withDrawDate;
    }

    public void setWithDrawDate(String withDrawDate) {
        this.withDrawDate = withDrawDate;
    }
}
