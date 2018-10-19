package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/3/14.
 */
public class RechargeReForm {
    @ApiModelProperty("fuiou登录名")
    private String userName;
    @ApiModelProperty("get返回的交易码")
    private String orderNo;
    @ApiModelProperty("订单编号")
    private String seqNum;

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
