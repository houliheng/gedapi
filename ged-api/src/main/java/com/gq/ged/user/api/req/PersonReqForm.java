package com.gq.ged.user.api.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * create by 2018/5/31
 *
 * @author yxh
 */
public class PersonReqForm implements Serializable {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "短信验证码")
    private String smCode;
    @ApiModelProperty(value = "其他网络借贷平台借款总额")
    private String otherLoanAmt;

    public String getOtherLoanAmt() {
        return otherLoanAmt;
    }

    public void setOtherLoanAmt(String otherLoanAmt) {
        this.otherLoanAmt = otherLoanAmt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmCode() {
        return smCode;
    }

    public void setSmCode(String smCode) {
        this.smCode = smCode;
    }
}
