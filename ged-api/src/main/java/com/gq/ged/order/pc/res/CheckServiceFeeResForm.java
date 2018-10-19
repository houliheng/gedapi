package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/3/27.
 */
public class CheckServiceFeeResForm {
    @ApiModelProperty("服务费缴费状态 0 未交费 1 已充值服务费 2充值失败 3 缴费完成")
    private String status;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("金额")
    private String amt;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
