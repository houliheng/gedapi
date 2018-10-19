package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/4/19.
 */
public class GuarantorIndexResForm {


//    第一版面
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("统一社会信用代码")
    private String socialCreditCode;
    @ApiModelProperty("账户  1是  0否")
    private String isOpenAccount;

//    第二版面

    @ApiModelProperty("总担保额度")
    private String guaranteeFeeAount;
    @ApiModelProperty("剩余担保额度")
    private String remainGuaranteeFeeAount;
    @ApiModelProperty("账户担保余额")
    private String accountGuaranteeFeeAount;
    @ApiModelProperty("担保笔数")
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getGuaranteeFeeAount() {
        return guaranteeFeeAount;
    }

    public void setGuaranteeFeeAount(String guaranteeFeeAount) {
        this.guaranteeFeeAount = guaranteeFeeAount;
    }

    public String getRemainGuaranteeFeeAount() {
        return remainGuaranteeFeeAount;
    }

    public void setRemainGuaranteeFeeAount(String remainGuaranteeFeeAount) {
        this.remainGuaranteeFeeAount = remainGuaranteeFeeAount;
    }

    public String getAccountGuaranteeFeeAount() {
        return accountGuaranteeFeeAount;
    }

    public void setAccountGuaranteeFeeAount(String accountGuaranteeFeeAount) {
        this.accountGuaranteeFeeAount = accountGuaranteeFeeAount;
    }
}
