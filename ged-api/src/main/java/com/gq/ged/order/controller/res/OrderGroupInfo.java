package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by Levi on 2018/5/30.
 */
public class OrderGroupInfo {
    /**
     * 借款企业数量
     */
    @ApiModelProperty(value = "借款企业数量")
    private Integer companyCount;
    /**
     * 借款金额
     */
    @ApiModelProperty(value = "借款金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "主订单编号")
    private BigDecimal masterOrderCode;

    public BigDecimal getMasterOrderCode() {
        return masterOrderCode;
    }

    public void setMasterOrderCode(BigDecimal masterOrderCode) {
        this.masterOrderCode = masterOrderCode;
    }

    public Integer getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(Integer companyCount) {
        this.companyCount = companyCount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
