package com.gq.ged.order.pc.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 * 缴费实体
 * Created by Levi on 2018/3/8.
 */
public class ChargeAmountForm implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @ApiModelProperty(value = "收费金额")
    private String amt;
    //
    @ApiModelProperty(value = "交易流水号")
    private String seq_no;
    //
    @ApiModelProperty(value = "客户编号")
    private String cust_no;
    //
    @ApiModelProperty(value = "交易编号")
    private String trade_no;
    //
    @ApiModelProperty(value = "所属分公司")
    private String filiale;
    //
    @ApiModelProperty(value = "所属大区")
    private String 	region;
    //
    @ApiModelProperty(value = "签名")
    private String signature;
    //
    @ApiModelProperty(value = "备注")
    private String 	memo;
    //
    @ApiModelProperty(value = "账户类型")
    private String busi_type;
    //
    @ApiModelProperty(value = "用户编号")
    private String 	user_no;
    //
    @ApiModelProperty(value = "交易类型")
    private String trade_type;
    //
    @ApiModelProperty(value = "所属平台")
    private String platform;
    //
    @ApiModelProperty(value = "账务类型")
    private String accounts_type;
    //
    @ApiModelProperty(value = "系统代码")
    private String mchn;
    //
    @ApiModelProperty(value = "业务编号")
    private String busi_no;

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

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getFiliale() {
        return filiale;
    }

    public void setFiliale(String filiale) {
        this.filiale = filiale;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBusi_type() {
        return busi_type;
    }

    public void setBusi_type(String busi_type) {
        this.busi_type = busi_type;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAccounts_type() {
        return accounts_type;
    }

    public void setAccounts_type(String accounts_type) {
        this.accounts_type = accounts_type;
    }

    public String getMchn() {
        return mchn;
    }

    public void setMchn(String mchn) {
        this.mchn = mchn;
    }

    public String getBusi_no() {
        return busi_no;
    }

    public void setBusi_no(String busi_no) {
        this.busi_no = busi_no;
    }
}
