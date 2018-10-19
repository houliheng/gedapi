package com.gq.ged.user.pc.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Levi on 2018/3/5.
 */
public class LoginReqForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    private String  userName;
    @ApiModelProperty("密码")
    private String pwd;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
