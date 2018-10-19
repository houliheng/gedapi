package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2017/9/8.
 */
@ApiModel
public class GedOrderStatusResForm implements Serializable {
    @ApiModelProperty(value = "状态码")
    private String code;
    @ApiModelProperty(value = "url路径")
    private String configUrl;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConfigUrl() {
        return configUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.configUrl = configUrl;
    }
}
