package com.gq.ged.order.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by wrh on 2018/5/30.
 */

@ApiModel
public class ContractSignResult implements Serializable {

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "合同签署生成的PDF")
    private String url;
    @ApiModelProperty(value = "类型")
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
