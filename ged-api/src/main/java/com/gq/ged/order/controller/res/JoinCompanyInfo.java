package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/5/30.
 */
public class JoinCompanyInfo {

    @ApiModelProperty(value = "企业名称")
    private String companyName;
    @ApiModelProperty(value = "开户银行")
    private String companyBank;
    @ApiModelProperty(value = "开户账户名称")
    private String companyAccountName;
    @ApiModelProperty(value = "账户")
    private String companyAccount;
    @ApiModelProperty(value = "是否开户 1 是  0否")
    private String isOpenAccount;

    public String getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(String isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyBank() {
        return companyBank;
    }

    public void setCompanyBank(String companyBank) {
        this.companyBank = companyBank;
    }

    public String getCompanyAccountName() {
        return companyAccountName;
    }

    public void setCompanyAccountName(String companyAccountName) {
        this.companyAccountName = companyAccountName;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }
}
