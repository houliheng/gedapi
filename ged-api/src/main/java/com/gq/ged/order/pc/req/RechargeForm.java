package com.gq.ged.order.pc.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 充值实体
 * Created by Levi on 2018/3/8.
 */
public class RechargeForm implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @ApiModelProperty(value = "充值金额")
    private String  amt;
    //
    @ApiModelProperty(value = "交易流水号")
    private String seq_no;
    //
    @ApiModelProperty(value = "客户编号")
    private String cust_no;
    //
    @ApiModelProperty(value = "银行类型")
    private String bank_id;
    //
    @ApiModelProperty(value = "签名")
    private String signature;
    //
    @ApiModelProperty(value = "用户编号")
    private String user_no;
    //
    @ApiModelProperty(value = "交易类型")
    private String trade_type;
    //
    @ApiModelProperty(value = "系统代码")
    private String mchn;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(String seq_no) {
        this.seq_no = seq_no;
    }

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getMchn() {
        return mchn;
    }

    public void setMchn(String mchn) {
        this.mchn = mchn;
    }
}
