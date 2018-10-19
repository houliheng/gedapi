package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wrh on 2018/3/7.
 */
@ApiModel
public class WithDrawCapitalResForm implements Serializable {
    @ApiModelProperty(value = "data")
    private String data;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "已提现金额")
    private BigDecimal withDrawAmount;
    @ApiModelProperty(value = "判断是否按钮是否置灰标识(0否1是)")
    private Integer withDrawFlag;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getWithDrawAmount() {
        return withDrawAmount;
    }

    public void setWithDrawAmount(BigDecimal withDrawAmount) {
        this.withDrawAmount = withDrawAmount;
    }

    public Integer getWithDrawFlag() {
        return withDrawFlag;
    }

    public void setWithDrawFlag(Integer withDrawFlag) {
        this.withDrawFlag = withDrawFlag;
    }
}
