package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/1/19.
 */
@ApiModel
public class GedOrderStatusReqForm implements Serializable {
    @ApiModelProperty(value = "订单编号")
    public String orderNo;
    @ApiModelProperty(value = "订单状态")
    public Integer status;
    @ApiModelProperty(value = "担保费")
    private BigDecimal guaranteeFee;
    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceFee;
    @ApiModelProperty(value = "保证金")
    private BigDecimal cashDeposit;
    @ApiModelProperty(value = "服务方所在地")
    private Integer serviceProvinceId;
    @ApiModelProperty(value = "还款方式")
    private String repaymentStyle;
    @ApiModelProperty(value = "合同号")
    private String contractNo;
    @ApiModelProperty(value = "合同金额")
    public BigDecimal contractAmount;
    @ApiModelProperty(value = "借款期限")
    public Integer loanTerm;
    @ApiModelProperty(value = "放款额度")
    public BigDecimal loanAmount;
    @ApiModelProperty(value = "子订单编号")
    private  String applyIdChild;
    @ApiModelProperty(value = "收取方式")
    private Integer serviceFeeWay;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getGuaranteeFee() {
        return guaranteeFee;
    }

    public void setGuaranteeFee(BigDecimal guaranteeFee) {
        this.guaranteeFee = guaranteeFee;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(BigDecimal cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public Integer getServiceProvinceId() {
        return serviceProvinceId;
    }

    public void setServiceProvinceId(Integer serviceProvinceId) {
        this.serviceProvinceId = serviceProvinceId;
    }

    public String getRepaymentStyle() {
        return repaymentStyle;
    }

    public void setRepaymentStyle(String repaymentStyle) {
        this.repaymentStyle = repaymentStyle;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getApplyIdChild() {
        return applyIdChild;
    }

    public void setApplyIdChild(String applyIdChild) {
        this.applyIdChild = applyIdChild;
    }

    public Integer getServiceFeeWay() {
        return serviceFeeWay;
    }

    public void setServiceFeeWay(Integer serviceFeeWay) {
        this.serviceFeeWay = serviceFeeWay;
    }
}
