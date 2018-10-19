package com.gq.ged.user.api.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 信息采集授权书-个人.
 *
 * @author SeppSong
 */
@ApiModel
public class PersonalInfoProtocolResForm implements Serializable{

    private static final long serialVersionUID = 1240687460612010585L;

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "身份证号")
    private String identityNo;
    @ApiModelProperty(value = "注册ID")
    private String registerId;
    @ApiModelProperty(value = "授权人")
    private String authorizer;
    @ApiModelProperty(value = "证件类型")
    private String certificateType;
    @ApiModelProperty(value = "证件号码")
    private String certificateNo;
    @ApiModelProperty(value = "日期")
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
