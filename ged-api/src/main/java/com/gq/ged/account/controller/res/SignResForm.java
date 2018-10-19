package com.gq.ged.account.controller.res;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Levi on 2017/9/19.
 */
public class SignResForm {
    @ApiModelProperty(value = "是否成功  true：成功 | false：失败")
    private boolean successMark;
    @ApiModelProperty(value = "状态码")
    private String code;
    @ApiModelProperty(value = "描述")
    private String descrip;
    @ApiModelProperty(value = "签约url")
    private String  signUrl;

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public boolean isSuccessMark() {
        return successMark;
    }

    public void setSuccessMark(boolean successMark) {
        this.successMark = successMark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
