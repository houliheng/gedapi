package com.gq.ged.user.api.req;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

public class ProcurationSignatureReqForm {
    //签约代表人姓名
    @ApiModelProperty(value = "签约代表人姓名")
    private String signatoryName;
    //签约代表人身份证号码
    @ApiModelProperty(value = "签约代表人身份证号码")
    private String signatoryIdentity;
    @ApiModelProperty(value = "企业电子签名委托书")
    private String fileId;
    //手机号
    @ApiModelProperty(value = "手机号")
    private String mobile;
    //短信验证码
    @ApiModelProperty(value = "短信验证码")
    private String smCode;
    //其他网络借贷平台借款总额
    @ApiModelProperty(value = "其他网络借贷平台借款总额")
    private String otherLoanAmt;

    public String getOtherLoanAmt() {
        return otherLoanAmt;
    }

    public void setOtherLoanAmt(String otherLoanAmt) {
        this.otherLoanAmt = otherLoanAmt;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getSmCode() {
        return smCode;
    }

    public void setSmCode(String smCode) {
        this.smCode = smCode;
    }

    public String getSignatoryName() {
        return signatoryName;
    }

    public void setSignatoryName(String signatoryName) {
        this.signatoryName = signatoryName;
    }

    public String getSignatoryIdentity() {
        return signatoryIdentity;
    }

    public void setSignatoryIdentity(String signatoryIdentity) {
        this.signatoryIdentity = signatoryIdentity;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
