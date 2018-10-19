package com.gq.ged.order.pc.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 查询充值是否成功实体
 * Created by Levi on 2018/3/8.
 */
public class RechargeCallBackForm implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @ApiModelProperty(value = "系统代码")
    private String mchn;
    //
    @ApiModelProperty(value = "交易流水号")
    private String seq_no;
    //
    @ApiModelProperty(value = "交易类型")
    private String trade_type;
    //
    @ApiModelProperty(value = "客户编号")
    private String cust_no;
    //
    @ApiModelProperty(value = "用户编号")
    private String user_no;
    //
    @ApiModelProperty(value = "第三方交易流水号")
    private String order_no;
    //
    @ApiModelProperty(value = "第三方交易日期")
    private String tradeDate;
    //
    @ApiModelProperty(value = "第三方交易返回码")
    private String respCode;
    //
    @ApiModelProperty(value = "第三方交易返回手机号")
    private String mobile_no;
    //
    @ApiModelProperty(value = "充值金额")
    private String amt;
    //
    @ApiModelProperty(value = "银行类型")
    private String bank_id;
    //
    @ApiModelProperty(value = "签名")
    private String signature;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMchn() {
        return mchn;
    }

    public void setMchn(String mchn) {
        this.mchn = mchn;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(String seq_no) {
        this.seq_no = seq_no;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getCust_no() {
        return cust_no;
    }

    public void setCust_no(String cust_no) {
        this.cust_no = cust_no;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
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
}
