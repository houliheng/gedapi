package com.gq.ged.order.pc.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/4/8.
 */
public class UpdateFinishRepayReqForm {
    @ApiModelProperty("订单编号")
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
