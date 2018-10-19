package com.gq.ged.order.pc.res;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Levi on 2018/3/12.
 */
public class GeneratePayParas implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("存管系统分配给各合作商户的唯一识别码")
    private String mchnt_cd; //
    @ApiModelProperty("商户网站产生的一个唯一的流水号,资金定义的订单号")
    private String mchnt_txn_ssn;//
    @ApiModelProperty("存管系统个人用户登录名")
    private String login_id;//
    @ApiModelProperty("以分为单位 (无小数位)")
    private String amt;//
    @ApiModelProperty("支付类型")
    private String order_pay_type;//
    @ApiModelProperty("银行代码")
    private String iss_ins_cd;//
    @ApiModelProperty("存管系统以form表单post方式回调该地址，用户关闭界面，回调中断。  该地址实时同步返回交易请求结果")
    private String page_notify_url;//
    @ApiModelProperty("存管系统以httpclient方式post回调该地址。  该地址实时同步返回交易请求结果")
    private String back_notify_url;//
    @ApiModelProperty("按参数名字母排序后的值用“|”线连接起来的明文，然后用rsa签名的值")
    private String signature;//

    public String getOrder_pay_type() {
        return order_pay_type;
    }

    public void setOrder_pay_type(String order_pay_type) {
        this.order_pay_type = order_pay_type;
    }

    public String getIss_ins_cd() {
        return iss_ins_cd;
    }

    public void setIss_ins_cd(String iss_ins_cd) {
        this.iss_ins_cd = iss_ins_cd;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getPage_notify_url() {
        return page_notify_url;
    }

    public void setPage_notify_url(String page_notify_url) {
        this.page_notify_url = page_notify_url;
    }

    public String getBack_notify_url() {
        return back_notify_url;
    }

    public void setBack_notify_url(String back_notify_url) {
        this.back_notify_url = back_notify_url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
