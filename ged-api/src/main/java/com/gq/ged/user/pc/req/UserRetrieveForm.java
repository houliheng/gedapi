package com.gq.ged.user.pc.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/3/5.
 */
public class UserRetrieveForm {

    @ApiModelProperty("手机号码")
    private String mobile;
    @ApiModelProperty("短信验证码")
    private String mobileCode;
    @ApiModelProperty("新密码")
    private String pwd;
    @ApiModelProperty("确认密码")
    private String pwdSure;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwdSure() {
        return pwdSure;
    }

    public void setPwdSure(String pwdSure) {
        this.pwdSure = pwdSure;
    }
}
