package com.gq.ged.user.api.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gq.ged.user.api.res.RepaymentInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * create by yxh with 2018/5/31
 * 合同立即签署请求参数Form
 */
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalImmediateSignReqFrom implements Serializable {
    // 借款服务协议
    // 合同编号 contractNo
    private String contractNo;
    // 合同签订地 省province 市city 县county
    // private String ;
    // 借款人 jkCustName
    private String jkCustName;
    // 冠易贷注册ID gedCustName
    private String jkgedCustName;
    // 法定代表人 jkCustNameDb
    private String jkCustNameDb;
    // 身份证号 certNo
    private String jkcertNo;
    // 联系地址 custAddress
    private String custAddress;
    // 手机号码 custPhone
    private String custPhone;
    // 电子邮箱 custEmail
    private String custEmail;
    // 借款金额 loanAmt
    private String loanAmt;
    // 借款用途 loanReason
    private String loanReason;
    // 平台服务费 小写serviceFee 大写serviceFeeUp
    private String serviceFee;
    private String serviceFeeUpper;//服务费大写金额
    //咨询服务费
    private String consultServiceFee;
    private String consultServiceFeeUpper;//咨询服务费大写
    // 账户管理费 小写manageFee 大写managerFeeUp
    private String manageFee;
    private String accountManageFeeUpper;//账户管理费大写金额
    // 支付账户管理费方式 payType
    private String payType;
    // 支付管理费期数 periodCount
    private String periodCount;
    // 还款计划
    private List<RepaymentInfo> list;
    // 账户 account
    private String account;
    // 开户银行 bankName
    private String bankName;
    // 账号 bankNo
    private String bankNo;
    // 签订日期 signDate
    private String jksignDate;
    private String province;//省
    private String city;//市
    private String area;//区


    // 授权提现函
    // 委托人姓名 wtName
    private String wtName;
    // 身份证号码 wtCertNo
    private String wtCertNo;
    // 冠易贷注册ID gedCustName
    private String gedCustName;
    // 借款人 jkCustName
    private String sqjkCustName;
    // 签署日期 signDate
    private String sqSignDate;


    // 电子签名数字证书用户申请确认函
    // 用户名称 custName
    private String custName;
    // 身份证号码 certNo
    private String dzcertNo;
    // 借款人 jkCustName
    private String dzjkCustName;
    // 签署日期 signDate
    private String dzsignDate;


    //个人信息采集
    //个人名称
    private String personName;
    //个人身份证号码
    private String personIdentityNo;
    //个人注册id
    private String personRegisterId;
    //授权人
    private String personAuthorizer;
    //证件类型
    private String personCertificateType;
    //证件号码
    private String personcertificateNo;
    //证件日期
    private String personDate;


    //网络借贷承诺书
    //自然人姓名
    private String netName;
    //法人姓名
    private String netLegalPerson;
    //日期
    private String netDate;
    //其他网络借贷平台借款总额
    private String loanSumAmt;
    //本人拟在冠e通平台借款
    private String getLoanAmt;

    public String getLoanSumAmt() {
        return loanSumAmt;
    }

    public void setLoanSumAmt(String loanSumAmt) {
        this.loanSumAmt = loanSumAmt;
    }

    public String getGetLoanAmt() {
        return getLoanAmt;
    }

    public void setGetLoanAmt(String getLoanAmt) {
        this.getLoanAmt = getLoanAmt;
    }

    private String jkfwContractType = "201";
    private String qysqContractType = "202";
    private String grsqContractType = "203";
    private String dbContractType = "204";
    private String txContractType = " 205";
    private String signContractType = "206";
    private String netContractType = "207";

    private String netVersion = "v1.2";
    private String jkfwVersion = "v1.2";
    private String qysqVersion = "v1.2";
    private String grsqVersion = "v1.2";
    private String dbVersion = "v1.2";
    private String txVersion = "v1.2";
    private String signVersion = "v1.2";
    private String portal = "2";

    public String getConsultServiceFee() {
        return consultServiceFee;
    }

    public void setConsultServiceFee(String consultServiceFee) {
        this.consultServiceFee = consultServiceFee;
    }

    public String getConsultServiceFeeUpper() {
        return consultServiceFeeUpper;
    }

    public void setConsultServiceFeeUpper(String consultServiceFeeUpper) {
        this.consultServiceFeeUpper = consultServiceFeeUpper;
    }

    public String getNetContractType() {
        return netContractType;
    }

    public void setNetContractType(String netContractType) {
        this.netContractType = netContractType;
    }

    public String getNetVersion() {
        return netVersion;
    }

    public void setNetVersion(String netVersion) {
        this.netVersion = netVersion;
    }

    //用户标识
    private String userTypeFlag;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUserTypeFlag() {
        return userTypeFlag;
    }

    public void setUserTypeFlag(String userTypeFlag) {
        this.userTypeFlag = userTypeFlag;
    }


    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getJkCustName() {
        return jkCustName;
    }

    public void setJkCustName(String jkCustName) {
        this.jkCustName = jkCustName;
    }

    public String getJkgedCustName() {
        return jkgedCustName;
    }

    public void setJkgedCustName(String jkgedCustName) {
        this.jkgedCustName = jkgedCustName;
    }

    public String getJkCustNameDb() {
        return jkCustNameDb;
    }

    public void setJkCustNameDb(String jkCustNameDb) {
        this.jkCustNameDb = jkCustNameDb;
    }

    public String getJkcertNo() {
        return jkcertNo;
    }

    public void setJkcertNo(String jkcertNo) {
        this.jkcertNo = jkcertNo;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(String loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(String loanReason) {
        this.loanReason = loanReason;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getServiceFeeUpper() {
        return serviceFeeUpper;
    }

    public void setServiceFeeUpper(String serviceFeeUpper) {
        this.serviceFeeUpper = serviceFeeUpper;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public String getAccountManageFeeUpper() {
        return accountManageFeeUpper;
    }

    public void setAccountManageFeeUpper(String accountManageFeeUpper) {
        this.accountManageFeeUpper = accountManageFeeUpper;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPeriodCount() {
        return periodCount;
    }

    public void setPeriodCount(String periodCount) {
        this.periodCount = periodCount;
    }

    public List<RepaymentInfo> getList() {
        return list;
    }

    public void setList(List<RepaymentInfo> list) {
        this.list = list;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }


    public String getJksignDate() {
        return jksignDate;
    }

    public void setJksignDate(String jksignDate) {
        this.jksignDate = jksignDate;
    }



    public String getWtName() {
        return wtName;
    }

    public void setWtName(String wtName) {
        this.wtName = wtName;
    }

    public String getWtCertNo() {
        return wtCertNo;
    }

    public void setWtCertNo(String wtCertNo) {
        this.wtCertNo = wtCertNo;
    }

    public String getGedCustName() {
        return gedCustName;
    }

    public void setGedCustName(String gedCustName) {
        this.gedCustName = gedCustName;
    }

    public String getSqjkCustName() {
        return sqjkCustName;
    }

    public void setSqjkCustName(String sqjkCustName) {
        this.sqjkCustName = sqjkCustName;
    }


    public String getSqSignDate() {
        return sqSignDate;
    }

    public void setSqSignDate(String sqSignDate) {
        this.sqSignDate = sqSignDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDzcertNo() {
        return dzcertNo;
    }

    public void setDzcertNo(String dzcertNo) {
        this.dzcertNo = dzcertNo;
    }

    public String getDzjkCustName() {
        return dzjkCustName;
    }

    public void setDzjkCustName(String dzjkCustName) {
        this.dzjkCustName = dzjkCustName;
    }


    public String getDzsignDate() {
        return dzsignDate;
    }

    public void setDzsignDate(String dzsignDate) {
        this.dzsignDate = dzsignDate;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonIdentityNo() {
        return personIdentityNo;
    }

    public void setPersonIdentityNo(String personIdentityNo) {
        this.personIdentityNo = personIdentityNo;
    }

    public String getPersonRegisterId() {
        return personRegisterId;
    }

    public void setPersonRegisterId(String personRegisterId) {
        this.personRegisterId = personRegisterId;
    }

    public String getPersonAuthorizer() {
        return personAuthorizer;
    }

    public void setPersonAuthorizer(String personAuthorizer) {
        this.personAuthorizer = personAuthorizer;
    }

    public String getPersonCertificateType() {
        return personCertificateType;
    }

    public void setPersonCertificateType(String personCertificateType) {
        this.personCertificateType = personCertificateType;
    }

    public String getPersoncertificateNo() {
        return personcertificateNo;
    }

    public void setPersoncertificateNo(String personcertificateNo) {
        this.personcertificateNo = personcertificateNo;
    }

    public String getPersonDate() {
        return personDate;
    }

    public void setPersonDate(String personDate) {
        this.personDate = personDate;
    }


    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getNetLegalPerson() {
        return netLegalPerson;
    }

    public void setNetLegalPerson(String netLegalPerson) {
        this.netLegalPerson = netLegalPerson;
    }

    public String getNetDate() {
        return netDate;
    }

    public void setNetDate(String netDate) {
        this.netDate = netDate;
    }

    public String getJkfwContractType() {
        return jkfwContractType;
    }

    public void setJkfwContractType(String jkfwContractType) {
        this.jkfwContractType = jkfwContractType;
    }

    public String getQysqContractType() {
        return qysqContractType;
    }

    public void setQysqContractType(String qysqContractType) {
        this.qysqContractType = qysqContractType;
    }

    public String getGrsqContractType() {
        return grsqContractType;
    }

    public void setGrsqContractType(String grsqContractType) {
        this.grsqContractType = grsqContractType;
    }

    public String getDbContractType() {
        return dbContractType;
    }

    public void setDbContractType(String dbContractType) {
        this.dbContractType = dbContractType;
    }

    public String getTxContractType() {
        return txContractType;
    }

    public void setTxContractType(String txContractType) {
        this.txContractType = txContractType;
    }

    public String getSignContractType() {
        return signContractType;
    }

    public void setSignContractType(String signContractType) {
        this.signContractType = signContractType;
    }

    public String getJkfwVersion() {
        return jkfwVersion;
    }

    public void setJkfwVersion(String jkfwVersion) {
        this.jkfwVersion = jkfwVersion;
    }

    public String getQysqVersion() {
        return qysqVersion;
    }

    public void setQysqVersion(String qysqVersion) {
        this.qysqVersion = qysqVersion;
    }

    public String getGrsqVersion() {
        return grsqVersion;
    }

    public void setGrsqVersion(String grsqVersion) {
        this.grsqVersion = grsqVersion;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public String getTxVersion() {
        return txVersion;
    }

    public void setTxVersion(String txVersion) {
        this.txVersion = txVersion;
    }

    public String getSignVersion() {
        return signVersion;
    }

    public void setSignVersion(String signVersion) {
        this.signVersion = signVersion;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }


    public PersonalImmediateSignReqFrom() {
    }

    public PersonalImmediateSignReqFrom(String contractNo, String jkCustName, String jkgedCustName, String jkCustNameDb, String jkcertNo, String custAddress, String custPhone, String custEmail, String loanAmt, String loanReason, String serviceFee, String serviceFeeUpper, String manageFee, String accountManageFeeUpper, String payType, String periodCount, List<RepaymentInfo> list, String account, String bankName, String bankNo,  String jksignDate, String wtName, String wtCertNo, String gedCustName, String sqjkCustName, String sqSignDate, String custName, String dzcertNo, String dzjkCustName,  String dzsignDate, String personName, String personIdentityNo, String personRegisterId, String personAuthorizer, String personCertificateType, String personcertificateNo, String personDate, String netName, String netLegalPerson, String netDate, String jkfwContractType, String qysqContractType, String grsqContractType, String dbContractType, String txContractType, String signContractType, String jkfwVersion, String qysqVersion, String grsqVersion, String dbVersion, String txVersion, String signVersion, String portal) {
        this.contractNo = contractNo;
        this.jkCustName = jkCustName;
        this.jkgedCustName = jkgedCustName;
        this.jkCustNameDb = jkCustNameDb;
        this.jkcertNo = jkcertNo;
        this.custAddress = custAddress;
        this.custPhone = custPhone;
        this.custEmail = custEmail;
        this.loanAmt = loanAmt;
        this.loanReason = loanReason;
        this.serviceFee = serviceFee;
        this.serviceFeeUpper = serviceFeeUpper;
        this.manageFee = manageFee;
        this.accountManageFeeUpper = accountManageFeeUpper;
        this.payType = payType;
        this.periodCount = periodCount;
        this.list = list;
        this.account = account;
        this.bankName = bankName;
        this.bankNo = bankNo;
        this.jksignDate = jksignDate;
        this.wtName = wtName;
        this.wtCertNo = wtCertNo;
        this.gedCustName = gedCustName;
        this.sqjkCustName = sqjkCustName;
        this.sqSignDate = sqSignDate;
        this.custName = custName;
        this.dzcertNo = dzcertNo;
        this.dzjkCustName = dzjkCustName;
        this.dzsignDate = dzsignDate;
        this.personName = personName;
        this.personIdentityNo = personIdentityNo;
        this.personRegisterId = personRegisterId;
        this.personAuthorizer = personAuthorizer;
        this.personCertificateType = personCertificateType;
        this.personcertificateNo = personcertificateNo;
        this.personDate = personDate;

        this.netName = netName;
        this.netLegalPerson = netLegalPerson;
        this.netDate = netDate;
        this.jkfwContractType = jkfwContractType;
        this.qysqContractType = qysqContractType;
        this.grsqContractType = grsqContractType;
        this.dbContractType = dbContractType;
        this.txContractType = txContractType;
        this.signContractType = signContractType;
        this.jkfwVersion = jkfwVersion;
        this.qysqVersion = qysqVersion;
        this.grsqVersion = grsqVersion;
        this.dbVersion = dbVersion;
        this.txVersion = txVersion;
        this.signVersion = signVersion;
        this.portal = portal;
    }
}
