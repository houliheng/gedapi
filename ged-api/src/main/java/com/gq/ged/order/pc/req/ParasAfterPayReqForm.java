package com.gq.ged.order.pc.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Levi on 2018/3/12.
 */
public class ParasAfterPayReqForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "第三方返回码")
    private String resp_code;
    @ApiModelProperty(value = "商户号")
    private String mchnt_cd;
    @ApiModelProperty(value = "订单号")
    private String mchnt_txn_ssn;
    @ApiModelProperty(value = "登录手机号")
    private String login_id;
    @ApiModelProperty(value = "充值金额")
    private String amt;
    @ApiModelProperty(value = "备注")
    private String rem;
    @ApiModelProperty(value = "签名")
    private String signature;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getResp_code() {
        return resp_code;
    }

    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }

    public String getMchnt_cd() {
        return mchnt_cd;
    }

    public void setMchnt_cd(String mchnt_cd) {
        this.mchnt_cd = mchnt_cd;
    }

    public String getMchnt_txn_ssn() {
        return mchnt_txn_ssn;
    }

    public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
        this.mchnt_txn_ssn = mchnt_txn_ssn;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
