package com.gq.ged.order.pc.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 * 资金管理-订单查看
 * Created by Levi on 2018/3/5.
 */
public class OrderViewsReqForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单号")
    private String  orderCode;

    @ApiModelProperty("借款金额") //批复了就指批复金额 为批复就指申请金额
    private String  loanAmount;

    @ApiModelProperty("借款期限")
    private String  loanTerm;

    @ApiModelProperty("借款状态")
    private String  status;

    @ApiModelProperty("企业开户行")
    private String  companyBankOfDeposit;

    @ApiModelProperty("公司名称  -- 20180305日由 收款户名 修改为公司名称")//20180305日由 收款户名 修改为公司名称
    private String  companyName;

    @ApiModelProperty("账号")
    private String  companyAccount;

    @ApiModelProperty("是否开户")
    private String  isOpenAccount;

    public String getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(String isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyBankOfDeposit() {
        return companyBankOfDeposit;
    }

    public void setCompanyBankOfDeposit(String companyBankOfDeposit) {
        this.companyBankOfDeposit = companyBankOfDeposit;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }
}
