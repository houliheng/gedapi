package com.gq.ged.user.api.res;

/**
 * Created by yxh on 2018/5/28.
 */
public class WithdrawalLetter {
  // 授权提现函
  // 委托人姓名 wtName
  private String wtName;
  // 身份证号码 wtCertNo
  private String wtCertNo;
  // 冠易贷注册ID gedCustName
  private String gedCustName;
  // 借款人 jkCustName
  private String jkCustName;
  // 授权代表人 representName
  private String representName;
    // 签署日期 signDate
    private String signDate;

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    private String txContractType ="205";
  private String txVersion  ="v1.0";

    public String getTxContractType() {
        return txContractType;
    }

    public void setTxContractType(String txContractType) {
        this.txContractType = txContractType;
    }

    public String getTxVersion() {
        return txVersion;
    }

    public void setTxVersion(String txVersion) {
        this.txVersion = txVersion;
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
}
