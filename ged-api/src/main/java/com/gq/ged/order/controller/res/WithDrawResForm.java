package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/3/5.
 */
@ApiModel
public class WithDrawResForm implements Serializable {
    @ApiModelProperty(value = "法人开户名称")
    private String corporationName;
    @ApiModelProperty(value = "法人开户行")
    private String corporationBankOfDeposit;
    @ApiModelProperty(value = "法人银行卡号")
    private String corporationAccount;
    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceFee;
    @ApiModelProperty(value = "账户余额")
    private BigDecimal accountBalance;
    @ApiModelProperty(value = "提现金额")
    private BigDecimal withDraw;
    @ApiModelProperty(value = "服务费标识")
    private Integer serviceFeeFlag;
    @ApiModelProperty(value = "判断提现按钮是否置灰")
    private Integer withDrawFlag;

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public String getCorporationBankOfDeposit() {
        return corporationBankOfDeposit;
    }

    public void setCorporationBankOfDeposit(String corporationBankOfDeposit) {
        this.corporationBankOfDeposit = corporationBankOfDeposit;
    }

    public String getCorporationAccount() {
        return corporationAccount;
    }

    public void setCorporationAccount(String corporationAccount) {
        this.corporationAccount = corporationAccount;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getWithDraw() {
        return withDraw;
    }

    public void setWithDraw(BigDecimal withDraw) {
        this.withDraw = withDraw;
    }

    public Integer getServiceFeeFlag() {
        return serviceFeeFlag;
    }

    public void setServiceFeeFlag(Integer serviceFeeFlag) {
        this.serviceFeeFlag = serviceFeeFlag;
    }

    public Integer getWithDrawFlag() {
        return withDrawFlag;
    }

    public void setWithDrawFlag(Integer withDrawFlag) {
        this.withDrawFlag = withDrawFlag;
    }
}
