package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Levi on 2018/4/18.
 */
public class OrderDetailResForm  extends  BorrowOrderBaseInfo{



    @ApiModelProperty("借款合同")
    private String contractUrl;

    @ApiModelProperty("五天内是否有还款")
    private String isFiveDays;

    @ApiModelProperty("还款计划")
    private List<RepaymentItem> repaymentPlans;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("是否能够还款 1 是 0 否")
    private String isCanRepay;

    public String getIsFiveDays() {
        return isFiveDays;
    }

    public void setIsFiveDays(String isFiveDays) {
        this.isFiveDays = isFiveDays;
    }

    public String getIsCanRepay() {
        return isCanRepay;
    }

    public void setIsCanRepay(String isCanRepay) {
        this.isCanRepay = isCanRepay;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public List<RepaymentItem> getRepaymentPlans() {
        return repaymentPlans;
    }

    public void setRepaymentPlans(List<RepaymentItem> repaymentPlans) {
        this.repaymentPlans = repaymentPlans;
    }
}
