package com.resoft.accounting.contract.entity;

/**
 * 合同信息Entity
 * 
 * @author wuxi01
 * @version 2016-03-03
 */
public class ContractImport  {

	
	
	private String tmpId;
	private String orgLevel2; // 大区
	private String orgLevel3; // 区域
	private String orgLevel4; // 登记门店
	private String contractNo; // 合同编号
	private String applyNo; // 申请编号
	private String custId; // 客户号
	private String custName; // 客户名称
	private String idType; // 证件类型
	private String idNum; // 证件号
	private String contractAmount; // 合同金额
	private String loanDate;// 放款日期
	private String loanAmount; // 放款金额
	private String approProductTypeId; // 批借产品类型ID(保留字段)
	private String approProductTypeName; // 批借产品类型名称
	private String approProductId; // 批借产品ID
	private String approProductName; // 批借产品名称
	private String approPeriodId; // 批借期限ID
	private String approPeriodValue; // 批借期限值
	private String approYearRate; // 批借年利率
	private String loanModel; // 借款模式
	private String serviceFeeType; // 服务费收取方式
	private String serviceFee; // 服务费
	private String specialServiceFee; // 特殊服务费
	private String serviceFeeRate; // 合同状态（字典类型：CONTRACT_STATE）
	private String marginRate; // 合同状态（字典类型：CONTRACT_STATE）
	private String marginAmount; // 保证金
	private String approLoanRepayType; // 批借还款方式（字典类型：LOAN_REPAY_TYPE）
	private String repBankcardNo; // 还款银行卡号
	private String repBankcardName; // 还款银行卡账户名称
	private String repBank; // 还款银行（字典类型：BANKS）
	private String repBankName; // 还款银行网点名称
	private String repBankMobile; // 收款移动电话
	private String repBankIdNum; // 收款人身份证号
	private String capitalTerraceNo; // 资金平台账号 CAPITAL_TERRACE_NO
	private String mortgageeCapTerNo;// 抵押权人资金平台账号
	private String loanPlatform;// 借款平台
	private String repBankProvince; // 还款银行地址//省Province
	private String repBankCity; // 还款银行地址//市city
	private String repBankDistinct; // 还款银行地址//区district
	private String repBankDetail; // 还款银行地址//详细
	private String operateName; // 经办人名称
	private String operateOrgName; // 经办机构名称
	private String operateId; // 经办人ID
	private String operateOrgId; // 经办机构ID
	private String conStartDate; // 合同起始日期
	private String conEndDate; // 合同结束日期
	private String occurDate; // 合同签订日期
	private String repBankProvinceName; // 省对应名称
	private String repBankCityName; // 市对应名称
	private String repBankDistinctName; // 区对应名称
	private String company;
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRepBankProvinceName() {
		return repBankProvinceName;
	}

	public void setRepBankProvinceName(String repBankProvinceName) {
		this.repBankProvinceName = repBankProvinceName;
	}

	public String getRepBankCityName() {
		return repBankCityName;
	}

	public void setRepBankCityName(String repBankCityName) {
		this.repBankCityName = repBankCityName;
	}

	public String getRepBankDistinctName() {
		return repBankDistinctName;
	}

	public void setRepBankDistinctName(String repBankDistinctName) {
		this.repBankDistinctName = repBankDistinctName;
	}

	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}

	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}

	public ContractImport() {
		super();
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getApproProductTypeId() {
		return approProductTypeId;
	}

	public void setApproProductTypeId(String approProductTypeId) {
		this.approProductTypeId = approProductTypeId;
	}

	public String getApproProductTypeName() {
		return approProductTypeName;
	}

	public void setApproProductTypeName(String approProductTypeName) {
		this.approProductTypeName = approProductTypeName;
	}

	public String getApproProductId() {
		return approProductId;
	}

	public void setApproProductId(String approProductId) {
		this.approProductId = approProductId;
	}

	public String getApproProductName() {
		return approProductName;
	}

	public void setApproProductName(String approProductName) {
		this.approProductName = approProductName;
	}

	public String getApproPeriodId() {
		return approPeriodId;
	}

	public void setApproPeriodId(String approPeriodId) {
		this.approPeriodId = approPeriodId;
	}

	public String getApproPeriodValue() {
		return approPeriodValue;
	}

	public void setApproPeriodValue(String approPeriodValue) {
		this.approPeriodValue = approPeriodValue;
	}

	public String getLoanModel() {
		return loanModel;
	}

	public void setLoanModel(String loanModel) {
		this.loanModel = loanModel;
	}

	public String getServiceFeeType() {
		return serviceFeeType;
	}

	public void setServiceFeeType(String serviceFeeType) {
		this.serviceFeeType = serviceFeeType;
	}

	public String getApproLoanRepayType() {
		return approLoanRepayType;
	}

	public void setApproLoanRepayType(String approLoanRepayType) {
		this.approLoanRepayType = approLoanRepayType;
	}

	public String getRepBankcardNo() {
		return repBankcardNo;
	}

	public void setRepBankcardNo(String repBankcardNo) {
		this.repBankcardNo = repBankcardNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRepBankcardName() {
		return repBankcardName;
	}

	public void setRepBankcardName(String repBankcardName) {
		this.repBankcardName = repBankcardName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getRepBankName() {
		return repBankName;
	}

	public void setRepBankName(String repBankName) {
		this.repBankName = repBankName;
	}

	public String getRepBankMobile() {
		return repBankMobile;
	}

	public void setRepBankMobile(String repBankMobile) {
		this.repBankMobile = repBankMobile;
	}

	public String getRepBankIdNum() {
		return repBankIdNum;
	}

	public void setRepBankIdNum(String repBankIdNum) {
		this.repBankIdNum = repBankIdNum;
	}

	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	public String getRepBankDetail() {
		return repBankDetail;
	}

	public void setRepBankDetail(String repBankDetail) {
		this.repBankDetail = repBankDetail;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getApproYearRate() {
		return approYearRate;
	}

	public void setApproYearRate(String approYearRate) {
		this.approYearRate = approYearRate;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getSpecialServiceFee() {
		return specialServiceFee;
	}

	public void setSpecialServiceFee(String specialServiceFee) {
		this.specialServiceFee = specialServiceFee;
	}

	public String getMarginRate() {
		return marginRate;
	}

	public void setMarginRate(String marginRate) {
		this.marginRate = marginRate;
	}

	public String getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(String marginAmount) {
		this.marginAmount = marginAmount;
	}

	public String getConStartDate() {
		return conStartDate;
	}

	public void setConStartDate(String conStartDate) {
		this.conStartDate = conStartDate;
	}

	public String getConEndDate() {
		return conEndDate;
	}

	public void setConEndDate(String conEndDate) {
		this.conEndDate = conEndDate;
	}

	public String getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}

	public String getRepBank() {
		return repBank;
	}

	public void setRepBank(String repBank) {
		this.repBank = repBank;
	}

	public String getRepBankProvince() {
		return repBankProvince;
	}

	public void setRepBankProvince(String repBankProvince) {
		this.repBankProvince = repBankProvince;
	}

	public String getRepBankCity() {
		return repBankCity;
	}

	public void setRepBankCity(String repBankCity) {
		this.repBankCity = repBankCity;
	}

	public String getRepBankDistinct() {
		return repBankDistinct;
	}

	public void setRepBankDistinct(String repBankDistinct) {
		this.repBankDistinct = repBankDistinct;
	}

	public String getServiceFeeRate() {
		return serviceFeeRate;
	}

	public void setServiceFeeRate(String serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	public String getMortgageeCapTerNo() {
		return mortgageeCapTerNo;
	}

	public void setMortgageeCapTerNo(String mortgageeCapTerNo) {
		this.mortgageeCapTerNo = mortgageeCapTerNo;
	}

	public String getTmpId() {
		return tmpId;
	}

	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public String getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(String orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	public String getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(String orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	public String getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(String orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLoanPlatform() {
		return loanPlatform;
	}

	public void setLoanPlatform(String loanPlatform) {
		this.loanPlatform = loanPlatform;
	}

	@Override
	public String toString() {
		return "ContractTmp [id=" + tmpId + ", orgLevel2=" + orgLevel2 + ", orgLevel3=" + orgLevel3 + ", orgLevel4=" + orgLevel4 + ", contractNo=" + contractNo + ", applyNo=" + applyNo + ", custId=" + custId + ", custName=" + custName + ", idType=" + idType + ", idNum=" + idNum + ", contractAmount=" + contractAmount + ", loanDate=" + loanDate + ", loanAmount=" + loanAmount + ", approProductTypeId=" + approProductTypeId + ", approProductTypeName=" + approProductTypeName + ", approProductId=" + approProductId + ", approProductName=" + approProductName + ", approPeriodId=" + approPeriodId + ", approPeriodValue=" + approPeriodValue + ", approYearRate=" + approYearRate + ", loanModel=" + loanModel + ", serviceFeeType=" + serviceFeeType + ", serviceFee=" + serviceFee + ", specialServiceFee=" + specialServiceFee + ", serviceFeeRate=" + serviceFeeRate + ", marginRate=" + marginRate + ", marginAmount=" + marginAmount + ", approLoanRepayType=" + approLoanRepayType + ", repBankcardNo=" + repBankcardNo + ", repBankcardName=" + repBankcardName + ", repBank=" + repBank + ", repBankName=" + repBankName + ", repBankMobile=" + repBankMobile + ", repBankIdNum=" + repBankIdNum + ", capitalTerraceNo=" + capitalTerraceNo + ", mortgageeCapTerNo=" + mortgageeCapTerNo + ", loanPlatform=" + loanPlatform + ", repBankProvince=" + repBankProvince + ", repBankCity=" + repBankCity + ", repBankDistinct=" + repBankDistinct + ", repBankDetail=" + repBankDetail + ", operateName=" + operateName + ", operateOrgName=" + operateOrgName + ", operateId=" + operateId + ", operateOrgId=" + operateOrgId + ", conStartDate=" + conStartDate + ", conEndDate=" + conEndDate + ", occurDate=" + occurDate + ", repBankProvinceName=" + repBankProvinceName + ", repBankCityName=" + repBankCityName + ", repBankDistinctName=" + repBankDistinctName + ", company=" + company + "]";
	}


}