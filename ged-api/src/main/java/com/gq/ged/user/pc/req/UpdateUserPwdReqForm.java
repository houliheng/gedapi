package com.gq.ged.user.pc.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2018/4/18.
 */
public class UpdateUserPwdReqForm {

    @ApiModelProperty(value = "旧密码")
    private String pwd;

    @ApiModelProperty(value = "新密码")
    private String newPwd;
    @ApiModelProperty(value = "新密码2")
    private String newPwd2;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getNewPwd2() {
        return newPwd2;
    }

    public void setNewPwd2(String newPwd2) {
        this.newPwd2 = newPwd2;
    }
}
