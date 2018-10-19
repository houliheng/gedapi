package com.resoft.multds.accounting.PLContract.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Office;

public class PLContract extends DataEntity<PLContract> {
	private static final long serialVersionUID = 1L;
	private Office orgLevel2; // 大区
	private Office orgLevel3; // 区域
	private Office orgLevel4; // 登记门店
	private String contractNo; // 合同编号
	private String applyNo; // 申请编号
	private String custId; // 客户号
	private String custName; // 客户名称
	private String idType; // 证件类型
	private String idNum; // 证件号
	private BigDecimal contractAmount; // 合同金额
	private Date loanDate;// 放款日期
	private BigDecimal loanAmount; // 放款金额
	private String approProductTypeId; // 批借产品类型ID(保留字段)
	private String approProductTypeName; // 批借产品类型名称
	private String approProductId; // 批借产品ID
	private String approProductName; // 批借产品名称
	private String approPeriodId; // 批借期限ID
	private String approPeriodValue; // 批借期限值
	private BigDecimal approYearRate; // 批借年利率
	private String loanModel; // 借款模式
	private String serviceFeeType; // 服务费收取方式
	private BigDecimal serviceFee; // 服务费
	private BigDecimal specialServiceFee; // 特殊服务费
	private String serviceFeeRate; // 合同状态（字典类型：CONTRACT_STATE）
	private BigDecimal marginRate; // 合同状态（字典类型：CONTRACT_STATE）
	private BigDecimal marginAmount; // 保证金
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
	private Date conStartDate; // 合同起始日期
	private Date conEndDate; // 合同结束日期
	private Date occurDate; // 合同签订日期
	private String repBankProvinceName; // 省对应名称
	private String repBankCityName; // 市对应名称
	private String repBankDistinctName; // 区对应名称

	
	private String monthlyRepaymentDate;//非本表数据，每月还款日

	//非ACC表字段，用于前台展示
	private String repayDate;//每月还款日
	private String extendPeriod;//合同展期期限值
	
	public String getExtendPeriod() {
		return extendPeriod;
	}

	public void setExtendPeriod(String extendPeriod) {
		this.extendPeriod = extendPeriod;
	}

	public Office getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(Office orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}

	public Office getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(Office orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}

	public Office getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(Office orgLevel4) {
		this.orgLevel4 = orgLevel4;
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

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
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

	public BigDecimal getApproYearRate() {
		return approYearRate;
	}

	public void setApproYearRate(BigDecimal approYearRate) {
		this.approYearRate = approYearRate;
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

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getSpecialServiceFee() {
		return specialServiceFee;
	}

	public void setSpecialServiceFee(BigDecimal specialServiceFee) {
		this.specialServiceFee = specialServiceFee;
	}

	public String getServiceFeeRate() {
		return serviceFeeRate;
	}

	public void setServiceFeeRate(String serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	public BigDecimal getMarginRate() {
		return marginRate;
	}

	public void setMarginRate(BigDecimal marginRate) {
		this.marginRate = marginRate;
	}

	public BigDecimal getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(BigDecimal marginAmount) {
		this.marginAmount = marginAmount;
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

	public String getRepBankcardName() {
		return repBankcardName;
	}

	public void setRepBankcardName(String repBankcardName) {
		this.repBankcardName = repBankcardName;
	}

	public String getRepBank() {
		return repBank;
	}

	public void setRepBank(String repBank) {
		this.repBank = repBank;
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

	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}

	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
	}

	public String getMortgageeCapTerNo() {
		return mortgageeCapTerNo;
	}

	public void setMortgageeCapTerNo(String mortgageeCapTerNo) {
		this.mortgageeCapTerNo = mortgageeCapTerNo;
	}

	public String getLoanPlatform() {
		return loanPlatform;
	}

	public void setLoanPlatform(String loanPlatform) {
		this.loanPlatform = loanPlatform;
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

	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	public Date getConStartDate() {
		return conStartDate;
	}

	public void setConStartDate(Date conStartDate) {
		this.conStartDate = conStartDate;
	}

	public Date getConEndDate() {
		return conEndDate;
	}

	public void setConEndDate(Date conEndDate) {
		this.conEndDate = conEndDate;
	}

	public Date getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
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


	public String getMonthlyRepaymentDate() {
		return monthlyRepaymentDate;
	}

	public void setMonthlyRepaymentDate(String monthlyRepaymentDate) {
		this.monthlyRepaymentDate = monthlyRepaymentDate;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
}
