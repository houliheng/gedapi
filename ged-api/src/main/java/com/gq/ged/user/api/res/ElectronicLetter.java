package com.gq.ged.user.api.res;

/**
 * Created by yxh on 2018/5/28.
 */
public class ElectronicLetter {

  // 电子签名数字证书用户申请确认函
  // 用户名称 custName
  private String custName;
  // 身份证号码 certNo
  private String certNo;
  // 借款人 jkCustName
  private String jkCustName;
  // 授权代表人 representName
  private String representName;
  // 签署日期 signDate
  private String signDate;

  private String signContractType = "206";

  private String signVersion = "v1.0";

  public String getSignContractType() {
    return signContractType;
  }

  public void setSignContractType(String signContractType) {
    this.signContractType = signContractType;
  }

  public String getSignVersion() {
    return signVersion;
  }

  public void setSignVersion(String signVersion) {
    this.signVersion = signVersion;
  }

  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
  }

  public String getCertNo() {
    return certNo;
  }

  public void setCertNo(String certNo) {
    this.certNo = certNo;
  }

  public String getJkCustName() {
    return jkCustName;
  }

  public void setJkCustName(String jkCustName) {
    this.jkCustName = jkCustName;
  }

  public String getRepresentName() {
    return representName;
  }

  public void setRepresentName(String representName) {
    this.representName = representName;
  }

  public String getSignDate() {
    return signDate;
  }

  public void setSignDate(String signDate) {
    this.signDate = signDate;
  }
}
