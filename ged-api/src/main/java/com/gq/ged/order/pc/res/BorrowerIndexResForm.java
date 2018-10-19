package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Levi on 2018/4/18.
 */
public class BorrowerIndexResForm {

//    第一版面
    @ApiModelProperty("用户名")
    private String userName;
//    @ApiModelProperty("邮箱")
//    private String email;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("统一社会信用代码")
    private String socialCreditCode;
    @ApiModelProperty("账户  1是  0否")
    private String isOpenAccount;

//      第二版面
    List<LoanRecordResForm> orders;

//    第三版面
    @ApiModelProperty("借款笔数")
    private String orderCount;
    @ApiModelProperty("在途借款总额")
    private String orderingLoanAmount;
    @ApiModelProperty("当前待还金额")
    private String nowRepayAmount;
    @ApiModelProperty("账户余额")
    private String accountBalance;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(String isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }

    public List<LoanRecordResForm> getOrders() {
        return orders;
    }

    public void setOrders(List<LoanRecordResForm> orders) {
        this.orders = orders;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderingLoanAmount() {
        return orderingLoanAmount;
    }

    public void setOrderingLoanAmount(String orderingLoanAmount) {
        this.orderingLoanAmount = orderingLoanAmount;
    }

    public String getNowRepayAmount() {
        return nowRepayAmount;
    }

    public void setNowRepayAmount(String nowRepayAmount) {
        this.nowRepayAmount = nowRepayAmount;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }
}
