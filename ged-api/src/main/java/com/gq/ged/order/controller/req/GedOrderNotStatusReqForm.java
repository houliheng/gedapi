package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2018/5/17.
 */
@ApiModel
public class GedOrderNotStatusReqForm implements Serializable {
    @ApiModelProperty(value = "订单编号")
    public String orderNo;
    @ApiModelProperty(value = "订单状态")
    public Integer status;
    @ApiModelProperty(value = "0非联1联合")
    private String flag;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
