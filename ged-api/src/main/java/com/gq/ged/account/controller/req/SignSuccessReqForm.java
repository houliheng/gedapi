package com.gq.ged.account.controller.req;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2017/9/19.
 */
public class SignSuccessReqForm {
    @ApiModelProperty(value = "data")
    @NotNull
    private String data;
    @ApiModelProperty(value = "code")
    @NotBlank
    private String code;
    @ApiModelProperty(value = "msg")
    @NotBlank
    private String msg;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
