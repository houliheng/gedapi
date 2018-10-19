package com.resoft.outinterface.rest.newged.entity;
/**
 * @description:
 * @Author:jiangmenglun
 * @E-mail:
 * @version:2018年1月25日 下午4:55:41
 */
public class GedRegisterRequest {
    private String type;//企业证件类型
    private String code;//企业证件编码
    private String mobile;//手机号
    private String userRole; //0借款人1自有担保人2自有担保企业3合作企业
    private String guaranteeAmount;//担保额度
    private String registerType;//注册类型 0主借人1法人2社会统一代码 ,担保人给，主借人给0 o r1
    private Integer userType;//0个人1企业
    private String  bankCode ;//银行开户卡号
    private Integer status;//老系统用户注册标示 1代表老系统用户

    public GedRegisterRequest() {
        super();
    }
    public GedRegisterRequest(String type, String code, String mobile,String userRole) {
        super();
        this.type = type;
        this.code = code;
        this.mobile = mobile;
        this.userRole = userRole;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public String getGuaranteeAmount() {
        return guaranteeAmount;
    }
    public void setGuaranteeAmount(String guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }
    public String getRegisterType() {
        return registerType;
    }
    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }


    public Integer getUserType() {
        return userType;
    }
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

}
