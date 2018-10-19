package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Levi on 2018/3/5.
 */
public class OrderManageResForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("flag == 1 转向费用支付页面 2 转向无此订单页面  3 转向订单查看页面")
    private Integer  flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
