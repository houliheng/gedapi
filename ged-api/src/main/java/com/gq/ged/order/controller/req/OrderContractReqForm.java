package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/5/28.
 */
@ApiModel
public class OrderContractReqForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    public String orderNo;
    @ApiModelProperty(value = "合同号")
    private String contractNo;
    @ApiModelProperty(value = "子订单编号")
    private  String applyIdChild;
    @ApiModelProperty(value = "账户管理费")
    private BigDecimal accountManageFee;
    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceFee;
    @ApiModelProperty(value = "合同签署省")
    private String  contractProvince;
    @ApiModelProperty(value = "合同签署市")
    private String contractCity;
    @ApiModelProperty(value = "合同签署区")
    private String contractDistinct;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getApplyIdChild() {
        return applyIdChild;
    }

    public void setApplyIdChild(String applyIdChild) {
        this.applyIdChild = applyIdChild;
    }

    public BigDecimal getAccountManageFee() {
        return accountManageFee;
    }

    public void setAccountManageFee(BigDecimal accountManageFee) {
        this.accountManageFee = accountManageFee;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getContractProvince() {
        return contractProvince;
    }

    public void setContractProvince(String contractProvince) {
        this.contractProvince = contractProvince;
    }

    public String getContractCity() {
        return contractCity;
    }

    public void setContractCity(String contractCity) {
        this.contractCity = contractCity;
    }

    public String getContractDistinct() {
        return contractDistinct;
    }

    public void setContractDistinct(String contractDistinct) {
        this.contractDistinct = contractDistinct;
    }
}
