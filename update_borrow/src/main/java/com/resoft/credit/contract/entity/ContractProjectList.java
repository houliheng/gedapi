package com.resoft.credit.contract.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * ClassName:ContractProjectList
 *
 *  合同批量信息
 * @Date 2018/5/22 11:10
 * @Author liangbin
 */
public class ContractProjectList  extends DataEntity<ContractProjectList> {

         private static final long serialVersionUID = 1L;
         private String contractNo;//合同编号
         private String approProductName;//产品名称
         private String borrowName;//借款人名称
         private String borrowType;//借款人类型
         private String custId;//借款人主键
         private String applyNo; //申请编号
         private String unSocCreditNo;//社会统一信用代码
         private String custName;//主借人名字
         private String mobileNum;//主借人手机号
         private String corporaTionName;//法人名称
         private String corporaTionMobile;//法人手机号
         private String repayContractStatus;//合同借款状态
         private String guanyiFlag ; //冠易贷账号
         private String isFlag;//企业跟个人判断标记位 1:个人 2 企业
         private String gedApiSave;//是否数据是否推送订单  0是未保存 1 是已保存
         private String  recBankcareNo;;//收款银行卡号


    public String getRecBankcareNo() {
        return recBankcareNo;
    }

    public void setRecBankcareNo(String recBankcareNo) {
        this.recBankcareNo = recBankcareNo;
    }

    public String getGedApiSave() {
        return gedApiSave;
    }

    public void setGedApiSave(String gedApiSave) {
        this.gedApiSave = gedApiSave;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getApproProductName() {
        return approProductName;
    }

    public void setApproProductName(String approProductName) {
        this.approProductName = approProductName;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getUnSocCreditNo() {
        return unSocCreditNo;
    }

    public void setUnSocCreditNo(String unSocCreditNo) {
        this.unSocCreditNo = unSocCreditNo;
    }


    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getCorporaTionName() {
        return corporaTionName;
    }

    public void setCorporaTionName(String corporaTionName) {
        this.corporaTionName = corporaTionName;
    }

    public String getCorporaTionMobile() {
        return corporaTionMobile;
    }

    public void setCorporaTionMobile(String corporaTionMobile) {
        this.corporaTionMobile = corporaTionMobile;
    }

    public String getRepayContractStatus() {
        return repayContractStatus;
    }

    public void setRepayContractStatus(String repayContractStatus) {
        this.repayContractStatus = repayContractStatus;
    }

    public String getGuanyiFlag() {
        return guanyiFlag;
    }

    public void setGuanyiFlag(String guanyiFlag) {
        this.guanyiFlag = guanyiFlag;
    }
}
