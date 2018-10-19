package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2018/1/19.
 */
@ApiModel
public class CertificationReqForm implements Serializable {
    @ApiModelProperty(value = "用户姓名")
    private String username;
    @ApiModelProperty(value = "身份证号")
    private String idCardNum;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
}
