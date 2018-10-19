package com.resoft.accounting.accContract;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同信息Entity：和acc中的contract实体类不一样
 * @author wuxi01
 * @version 2016-03-03
 */
public class AccContract extends DataEntity<AccContract> {

	private static final long serialVersionUID = 1L;    
	private String orgLevel2;		// 大区 
	private String orgLevel3;		// 区域    
	private String orgLevel4;			// 登记门店
	private String contractNo;		// 合同编号
	private String applyNo;		// 申请编号
	private String custId;		// 客户号
	private String custName;		// 客户名称
	private String idType;		// 证件类型
	private String idNum;		// 证件号
	private BigDecimal contractAmount;		// 合同金额  
	private Date loanDate;//放款日期
	private BigDecimal loanAmount;		// 放款金额
	private String approProductTypeId;		// 批借产品类型ID(保留字段)
	private String approProductTypeName;		// 批借产品类型名称
	private String approProductId;		// 批借产品ID
	private String approProductName;		// 批借产品名称
	private String approPeriodId;		// 批借期限ID
	private String approPeriodValue;		// 批借期限值
	private BigDecimal approYearRate;		// 批借年利率
	private String loanModel;		// 借款模式
	private String serviceFeeType;		// 服务费收取方式
	private BigDecimal serviceFee;		// 服务费
	private BigDecimal specialServiceFee;		// 特殊服务费
	private String serviceFeeRate;		// 合同状态（字典类型：CONTRACT_STATE）
	private BigDecimal marginRate;		// 合同状态（字典类型：CONTRACT_STATE）
	private BigDecimal marginAmount;		// 保证金
	private String approLoanRepayType;		// 批借还款方式（字典类型：LOAN_REPAY_TYPE）
	private String repBankcardNo;		// 还款银行卡号
	private String repBankcardName;		// 还款银行卡账户名称
	private String repBank;		// 还款银行（字典类型：BANKS）
	private String repBankName;		// 还款银行网点名称
	private String repBankMobile;		// 收款移动电话
	private String repBankIdNum;		// 收款人身份证号
	private String capitalTerraceNo;  // 资金平台账号 CAPITAL_TERRACE_NO
	private String mortgageeCapTerNo;// 抵押权人资金平台账号
	private String loanPlatform;//借款平台
	private String repBankProvince;		// 还款银行地址//省Province
	private String repBankCity;		// 还款银行地址//市city
	private String repBankDistinct;		// 还款银行地址//区district
	private String repBankDetail;		// 还款银行地址//详细
	private String operateName;		// 经办人名称
	private String operateOrgName;		// 经办机构名称
	private String operateId;		// 经办人ID
	private String operateOrgId;		// 经办机构ID
	private Date conStartDate;		// 合同起始日期
	private Date conEndDate;		// 合同结束日期
	private Date occurDate;		// 合同签订日期
	private String remark;//备注

//------------------------------------------------
	private BigDecimal factGuaranteeFee;
	private BigDecimal factGuaranteeGold;
	private BigDecimal factServiceFee;
	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}

	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AccContract() {
		super();
	}

	public AccContract(String id){
		super(id);
	}

	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	
	@Length(min=0, max=10, message="证件类型长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	
	@Length(min=0, max=18, message="证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	@Length(min=0, max=32, message="批借产品类型ID(保留字段)长度必须介于 0 和 32 之间")
	public String getApproProductTypeId() {
		return approProductTypeId;
	}

	public void setApproProductTypeId(String approProductTypeId) {
		this.approProductTypeId = approProductTypeId;
	}
	
	@Length(min=0, max=30, message="批借产品类型名称长度必须介于 0 和 30 之间")
	public String getApproProductTypeName() {
		return approProductTypeName;
	}

	public void setApproProductTypeName(String approProductTypeName) {
		this.approProductTypeName = approProductTypeName;
	}
	
	@Length(min=0, max=32, message="批借产品ID长度必须介于 0 和 32 之间")
	public String getApproProductId() {
		return approProductId;
	}

	public void setApproProductId(String approProductId) {
		this.approProductId = approProductId;
	}
	
	@Length(min=0, max=30, message="批借产品名称长度必须介于 0 和 30 之间")
	public String getApproProductName() {
		return approProductName;
	}

	public void setApproProductName(String approProductName) {
		this.approProductName = approProductName;
	}
	
	@Length(min=0, max=1, message="批借期限ID长度必须介于 0 和 1 之间")
	public String getApproPeriodId() {
		return approPeriodId;
	}

	public void setApproPeriodId(String approPeriodId) {
		this.approPeriodId = approPeriodId;
	}
	
	@Length(min=0, max=4, message="批借期限值长度必须介于 0 和 4 之间")
	public String getApproPeriodValue() {
		return approPeriodValue;
	}

	public void setApproPeriodValue(String approPeriodValue) {
		this.approPeriodValue = approPeriodValue;
	}
	
	
	@Length(min=0, max=4, message="借款模式长度必须介于 0 和 4 之间")
	public String getLoanModel() {
		return loanModel;
	}

	public void setLoanModel(String loanModel) {
		this.loanModel = loanModel;
	}
	
	@Length(min=0, max=4, message="服务费收取方式长度必须介于 0 和 4 之间")
	public String getServiceFeeType() {
		return serviceFeeType;
	}

	public void setServiceFeeType(String serviceFeeType) {
		this.serviceFeeType = serviceFeeType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}
	
	
	
	
	@Length(min=0, max=4, message="批借还款方式（字典类型：LOAN_REPAY_TYPE）长度必须介于 0 和 4 之间")
	public String getApproLoanRepayType() {
		return approLoanRepayType;
	}

	public void setApproLoanRepayType(String approLoanRepayType) {
		this.approLoanRepayType = approLoanRepayType;
	}
	
	@Length(min=0, max=30, message="还款银行卡号长度必须介于 0 和 30 之间")
	public String getRepBankcardNo() {
		return repBankcardNo;
	}

	public void setRepBankcardNo(String repBankcardNo) {
		this.repBankcardNo = repBankcardNo;
	}
	
	@Length(min=0, max=32, message="客户号长度必须介于 0 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=20, message="还款银行卡账户名称长度必须介于 0 和 20 之间")
	public String getRepBankcardName() {
		return repBankcardName;
	}

	public void setRepBankcardName(String repBankcardName) {
		this.repBankcardName = repBankcardName;
	}
	
	@Length(min=0, max=200, message="客户名称长度必须介于 0 和 200 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=20, message="还款银行网点名称长度必须介于 0 和 20 之间")
	public String getRepBankName() {
		return repBankName;
	}

	public void setRepBankName(String repBankName) {
		this.repBankName = repBankName;
	}
	
	@Length(min=0, max=15, message="收款移动电话长度必须介于 0 和 15 之间")
	public String getRepBankMobile() {
		return repBankMobile;
	}

	public void setRepBankMobile(String repBankMobile) {
		this.repBankMobile = repBankMobile;
	}
	
	@Length(min=0, max=32, message="收款人身份证号长度必须介于 0 和 32 之间")
	public String getRepBankIdNum() {
		return repBankIdNum;
	}

	public void setRepBankIdNum(String repBankIdNum) {
		this.repBankIdNum = repBankIdNum;
	}
	
	@Length(min=0, max=32, message="经办机构ID长度必须介于 0 和 32 之间")
	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}
	
	@Length(min=0, max=300, message="还款银行地址//详细长度必须介于 0 和 300 之间")
	public String getRepBankDetail() {
		return repBankDetail;
	}

	public void setRepBankDetail(String repBankDetail) {
		this.repBankDetail = repBankDetail;
	}
	
	@Length(min=0, max=20, message="经办人名称长度必须介于 0 和 20 之间")
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	
	@Length(min=0, max=300, message="经办机构名称长度必须介于 0 和 300 之间")
	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}
	
	@Length(min=0, max=32, message="经办人ID长度必须介于 0 和 32 之间")
	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getConStartDate() {
		return conStartDate;
	}

	public void setConStartDate(Date conStartDate) {
		this.conStartDate = conStartDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getConEndDate() {
		return conEndDate;
	}

	public void setConEndDate(Date conEndDate) {
		this.conEndDate = conEndDate;
	}
	
	@Length(min=0, max=200, message="还款银行（字典类型：BANKS）长度必须介于 0 和 200 之间")
	public String getRepBank() {
		return repBank;
	}

	public void setRepBank(String repBank) {
		this.repBank = repBank;
	}
	
	@Length(min=0, max=100, message="还款银行地址//省Province长度必须介于 0 和 100 之间")
	public String getRepBankProvince() {
		return repBankProvince;
	}

	public void setRepBankProvince(String repBankProvince) {
		this.repBankProvince = repBankProvince;
	}
	
	@Length(min=0, max=100, message="还款银行地址//市city长度必须介于 0 和 100 之间")
	public String getRepBankCity() {
		return repBankCity;
	}

	public void setRepBankCity(String repBankCity) {
		this.repBankCity = repBankCity;
	}
	
	@Length(min=0, max=100, message="还款银行地址//区district长度必须介于 0 和 100 之间")
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



	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getApproYearRate() {
		return approYearRate;
	}

	public void setApproYearRate(BigDecimal approYearRate) {
		this.approYearRate = approYearRate;
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

	public BigDecimal getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(BigDecimal marginAmount) {
		this.marginAmount = marginAmount;
	}

	public BigDecimal getMarginRate() {
		return marginRate;
	}

	public void setMarginRate(BigDecimal marginRate) {
		this.marginRate = marginRate;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getFactGuaranteeFee() {
		return factGuaranteeFee;
	}

	public void setFactGuaranteeFee(BigDecimal factGuaranteeFee) {
		this.factGuaranteeFee = factGuaranteeFee;
	}

	public BigDecimal getFactGuaranteeGold() {
		return factGuaranteeGold;
	}

	public void setFactGuaranteeGold(BigDecimal factGuaranteeGold) {
		this.factGuaranteeGold = factGuaranteeGold;
	}

	public BigDecimal getFactServiceFee() {
		return factServiceFee;
	}

	public void setFactServiceFee(BigDecimal factServiceFee) {
		this.factServiceFee = factServiceFee;
	}
	
	
}