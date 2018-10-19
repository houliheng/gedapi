package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/1/23.
 */

@ApiModel
public class GedLoanRecordResForm implements Serializable {

    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "借款金额")
    private BigDecimal loanAmount;
    @ApiModelProperty(value = "订单编号")
    private  String orderNo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
