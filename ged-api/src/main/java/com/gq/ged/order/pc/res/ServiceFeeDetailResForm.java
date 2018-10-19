package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/4/18.
 */
public class ServiceFeeDetailResForm extends BorrowOrderBaseInfo{
//    @ApiModelProperty("借款金额")
//    private String loanAmount;
//
//    @ApiModelProperty(" 期限")
//    private String period;
//
//    @ApiModelProperty("利率")
//    private String rate;
//
//    @ApiModelProperty("借款类型")
//    private String loanType;
//
//    @ApiModelProperty("起止时间")
//    private String date;
//
//    @ApiModelProperty("状   态")
//    private String status;
//
//    @ApiModelProperty("借款用途")
//    private String loanPurpose;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("服务费金额")
    private String serviceFee;
    @ApiModelProperty("服务费缴费状态 -1禁止操作 0 未交费 1 已充值服务费 2充值失败 3 缴费完成  5 充值中")
    private String isPay;

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }
}
