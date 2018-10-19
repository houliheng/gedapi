package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/1/19.
 */
@ApiModel
public class GedOrderUpdateReqForm implements Serializable {
    @ApiModelProperty(value = "订单编号")
    public String orderCode;;
    @ApiModelProperty(value = "借款金额")
    public BigDecimal loanAmount;
    @ApiModelProperty(value = "借款期限")
    public Integer loanTerm;
    @ApiModelProperty(value = "订单状态")
    public Integer status;


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
