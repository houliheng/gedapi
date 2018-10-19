package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by Levi on 2018/4/23.
 */
public class GuaranteeRecordListResForm extends GuaranteeBaseForm {
//    保证金(万元)      担保服务费（元）


    @ApiModelProperty("保证金")
    private String cashDeposit;
    @ApiModelProperty("担保服务费")
    private String guaranteeFee;
    @ApiModelProperty("担保状态")
    private String guaranteeStatus;

    public String getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(String cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public String getGuaranteeFee() {
        return guaranteeFee;
    }

    public void setGuaranteeFee(String guaranteeFee) {
        this.guaranteeFee = guaranteeFee;
    }

    public String getGuaranteeStatus() {
        return guaranteeStatus;
    }

    public void setGuaranteeStatus(String guaranteeStatus) {
        this.guaranteeStatus = guaranteeStatus;
    }
}
