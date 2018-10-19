package com.resoft.credit.contract.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同信息Entity
 * 
 * @author yanwanmei
 * @version 2016-03-02
 */
public class Contract extends DataEntity<Contract> {

	private static final long serialVersionUID = 1L;
	private String contractNo; // 合同编号
	private String newContractNo;//更改合同编号用
	private String orgLevel2; // 2级公司
	private String orgLevel3; // 3级公司
	private String orgLevel4; // 4级公司
	private BigDecimal contractAmount; // 合同金额
	private String applyNo; // 申请编号
	private String custId; // 客户编号
	private BigDecimal factLoanAmount; // 实际放款金额
	private String custType; // 客户类型
	private String mianContract; // 主合同类型值
	private String signContracts; // 签订合同集合
	private String custName; // 借款人姓名
	private String idType; // 证件类型（字典类型：CUSTOMER_P_ID_TYPE）
	private String idNum; // 证件号码
	private String operateOrgId; // 经办机构ID
	private BigDecimal applyServiceFeeRate; // 申请服务费率
	private String approId; // 批复ID
	private String approProductTypeCode; // 批借产品类型ID(保留字段)
	private String approProductTypeName; // 批借产品类型名称
	private String approProductId; // 批借产品ID
	private String approProductName; // 批借产品名称
	private BigDecimal approAmount; // 批借金额
	private String approPeriodId; // 批借期限ID
	private String approPeriodValue; // 批借期限值
	private BigDecimal applyAmount; // 申请金额
	private String applyPeriodId; // 申请期限ID
	private BigDecimal approYearRate; // 批借年利率
	private String applyPeriodValue; // 申请期限值
	private BigDecimal approIntegratedRate; // 批借综合费率
	private String approLoanRepayType; // 批借还款方式（字典类型：LOAN_REPAY_TYPE）
	private BigDecimal approMonthRepayAmount; // 批借月还款金额
	private String applyProductId; // 申请产品ID
	private BigDecimal loanAmount; // 放款金额
	private String applyProductName; // 申请产品名称
	private String applyProductTypeCode; // 申请产品类型编码
	private BigDecimal serviceFeeRate; // 服务费率
	private String applyProductTypeName; // 申请产品类型名称
	private BigDecimal specialServiceFeeRate; // 特殊服务费率
	private String serviceFeeType; // 服务费收取方式
	private BigDecimal applyYearRate; // 申请年利率
	private BigDecimal serviceFee; // 服务费
	private BigDecimal specialServiceFee; // 特殊服务费
	private String applyLoanRepayType; // 申请还款方式（字典类型：LOAN_REPAY_TYPE）
	private BigDecimal applyMonthRepayAmount; // 申请月还款金额
	private BigDecimal allServiceFee; // 服务费总计
	private BigDecimal marginRate; // 保证金率
	private BigDecimal marginAmount; // 保证金
	private BigDecimal checkFee; // 外访费
	private String loanModel; // 借款模式
	private String approResult; // 总公司批复信息
	private Date conStartDate; // 合同起始日期
	private Date conEndDate; // 合同结束日期
	private BigDecimal receiveAmount; // 收到现金金额（保证金和服务费）
	private String operateId; // 经办人ID
	private String recBank; // 收款银行（字典类型：BANKS）
	private String operateName; // 经办人名称
	private String operateOrgName; // 经办机构名称
	private String operateOtherDesc; // 其他归属说明
	private String recBankProvince; // 收款银行地址//省Province
	private String contractProvince; // 合同签署地：省
	private String recBankCity; // 收款银行地址//市city
	private String contractCity; // 合同签署地：市
	private String description; // 补充合同说明
	private String recBankDistinct; // 收款银行地址//区district
	private String isAccord; // 收款、还款账户是否一致（字典类型：yes_no）
	private String contractDistinct; // 合同签署地：区
	private String repBank; // 还款银行（字典类型：BANKS）
	private String mortgageeId; // 抵押权人ID
	private String mortgageeName; // 抵押权限人名称
	private String mortgageeIdNum; // 证件号
	private String mortgageeMobileNum; // 手机号
	private String bankcardNo; // 银行卡号
	private String repBankProvince; // 还款银行地址//省Province
	private String bankNo; // 开户行代码
	private String repBankCity; // 还款银行地址//市city
	private String repBankDistinct; // 还款银行地址//区district
	private String bankDetailName; // 开户行详细名称
	private String contProvince; // 开户行地址省
	private Date occurDate; // 合同签订日期
	private String contCity; // 开户行地址市
	private String contDistinct; // 开户行地址区
	private String contDetails; // 开户行地址详细
	private String mortgageeCapTerNo; // 抵押权人资金平台账号
	private String recBankcardNo; // 收款银行卡号z
	private String recBankcardId;
	private String recBankcardName; // 收款银行卡账户名称
	private String recBankName; // 收款银行网点名称
	private String recBankMobile; // 收款移动电话
	private String recBankIdNum; // 收款人身份证号
	private String recBankDetail; // 收款银行地址//详细
	private String capitalTerraceNo; // 资金平台账号
	private String repBankcardNo; // 还款银行卡号
	private String repBankcardName; // 还款银行卡账户名称
	private String repBankName; // 还款银行网点名称
	private String repBankMobile; // 还款移动电话
	private String repBankIdNum; // 还款人身份证号
	private String repBankDetail; // 还款银行地址//详细
	private String contractOrgPlatform; // 借款平台
	private String openAccountStatus;// 开户状态
	private String remark;// 备注
	private String isOrNoMIddleMan;//是否有中间人
	private String middlemanId; // 中间人ID
	private String middlemanName; // 中间人名称
	private String middlemanIdNum; // 中间人证件号
	private String middlemanMobileNum; // 中间人手机号
	private String middlemanBankcardNo; // 中间人银行卡号
	private String middlemanBankNo; // 中间人开户行代码
	private String middlemanBankDetailName; // 中间人开户行详细名称
	private String middlemanContProvince; // 中间人开户行地址省
	private String middlemanContCity; // 中间人开户行地址市
	private String middlemanContDistinct; // 中间人开户行地址区
	private String middlemanContDetails; // 中间人开户行地址详细
	private String middlemanCapTerNo; // 中间人资金平台账号
	private BigDecimal qualityServiceMarginAmount;//质量服务保证金 QUALITY_SERVICE_MARGIN_AMOUNT
	private BigDecimal qualityServiceMarginRate;//质量服务保证金率 QUALITY_SERVICE_MARGIN_RATE
	private String guaranteeRelation;//确认担保关系
	private String discountSave;//区分新老数据贴息标识
	private String loanCompanyName;
	private String loanCompanyID;
	private String guaranteeCompanyName;
	private String guaranteeCompanyId;
	
	private String signFlag;//签约标识
	public String getRecBankcardId() {
		return recBankcardId;
	}

	public void setRecBankcardId(String recBankcardId) {
		this.recBankcardId = recBankcardId;
	}

	public String getNewContractNo() {
		return newContractNo;
	}

	public String getDiscountSave() {
		return discountSave;
	}

	public void setDiscountSave(String discountSave) {
		this.discountSave = discountSave;
	}

	public void setNewContractNo(String newContractNo) {
		this.newContractNo = newContractNo;
	}
	
	public BigDecimal getQualityServiceMarginAmount() {
		return qualityServiceMarginAmount;
	}

	public void setQualityServiceMarginAmount(BigDecimal qualityServiceMarginAmount) {
		this.qualityServiceMarginAmount = qualityServiceMarginAmount;
	}

	public BigDecimal getQualityServiceMarginRate() {
		return qualityServiceMarginRate;
	}

	public void setQualityServiceMarginRate(BigDecimal qualityServiceMarginRate) {
		this.qualityServiceMarginRate = qualityServiceMarginRate;
	}
	public String getMiddlemanId() {
		return middlemanId;
	}

	public void setMiddlemanId(String middlemanId) {
		this.middlemanId = middlemanId;
	}

	public String getMiddlemanName() {
		return middlemanName;
	}

	public void setMiddlemanName(String middlemanName) {
		this.middlemanName = middlemanName;
	}

	public String getMiddlemanIdNum() {
		return middlemanIdNum;
	}

	public void setMiddlemanIdNum(String middlemanIdNum) {
		this.middlemanIdNum = middlemanIdNum;
	}

	public String getMiddlemanMobileNum() {
		return middlemanMobileNum;
	}

	public void setMiddlemanMobileNum(String middlemanMobileNum) {
		this.middlemanMobileNum = middlemanMobileNum;
	}

	public String getMiddlemanBankcardNo() {
		return middlemanBankcardNo;
	}

	public void setMiddlemanBankcardNo(String middlemanBankcardNo) {
		this.middlemanBankcardNo = middlemanBankcardNo;
	}

	public String getMiddlemanBankNo() {
		return middlemanBankNo;
	}

	public void setMiddlemanBankNo(String middlemanBankNo) {
		this.middlemanBankNo = middlemanBankNo;
	}

	public String getMiddlemanBankDetailName() {
		return middlemanBankDetailName;
	}

	public void setMiddlemanBankDetailName(String middlemanBankDetailName) {
		this.middlemanBankDetailName = middlemanBankDetailName;
	}

	public String getMiddlemanContProvince() {
		return middlemanContProvince;
	}

	public void setMiddlemanContProvince(String middlemanContProvince) {
		this.middlemanContProvince = middlemanContProvince;
	}

	public String getMiddlemanContCity() {
		return middlemanContCity;
	}

	public void setMiddlemanContCity(String middlemanContCity) {
		this.middlemanContCity = middlemanContCity;
	}

	public String getMiddlemanContDistinct() {
		return middlemanContDistinct;
	}

	public void setMiddlemanContDistinct(String middlemanContDistinct) {
		this.middlemanContDistinct = middlemanContDistinct;
	}

	public String getMiddlemanContDetails() {
		return middlemanContDetails;
	}

	public void setMiddlemanContDetails(String middlemanContDetails) {
		this.middlemanContDetails = middlemanContDetails;
	}

	public String getMiddlemanCapTerNo() {
		return middlemanCapTerNo;
	}

	public void setMiddlemanCapTerNo(String middlemanCapTerNo) {
		this.middlemanCapTerNo = middlemanCapTerNo;
	}

	public String getIsOrNoMIddleMan() {
		return isOrNoMIddleMan;
	}

	public void setIsOrNoMIddleMan(String isOrNoMIddleMan) {
		this.isOrNoMIddleMan = isOrNoMIddleMan;
	}

	public Contract() {
		super();
	}

	public Contract(String id) {
		super(id);
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
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

	public BigDecimal getFactLoanAmount() {
		return factLoanAmount;
	}

	public void setFactLoanAmount(BigDecimal factLoanAmount) {
		this.factLoanAmount = factLoanAmount;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getMianContract() {
		return mianContract;
	}

	public void setMianContract(String mianContract) {
		this.mianContract = mianContract;
	}

	public String getSignContracts() {
		return signContracts;
	}

	public void setSignContracts(String signContracts) {
		this.signContracts = signContracts;
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

	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	public String getApproId() {
		return approId;
	}

	public void setApproId(String approId) {
		this.approId = approId;
	}

	public String getApproProductTypeCode() {
		return approProductTypeCode;
	}

	public void setApproProductTypeCode(String approProductTypeCode) {
		this.approProductTypeCode = approProductTypeCode;
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

	public BigDecimal getApproAmount() {
		return approAmount;
	}

	public void setApproAmount(BigDecimal approAmount) {
		this.approAmount = approAmount;
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

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getApplyPeriodId() {
		return applyPeriodId;
	}

	public void setApplyPeriodId(String applyPeriodId) {
		this.applyPeriodId = applyPeriodId;
	}


	public String getApplyPeriodValue() {
		return applyPeriodValue;
	}

	public void setApplyPeriodValue(String applyPeriodValue) {
		this.applyPeriodValue = applyPeriodValue;
	}


	public String getApproLoanRepayType() {
		return approLoanRepayType;
	}

	public void setApproLoanRepayType(String approLoanRepayType) {
		this.approLoanRepayType = approLoanRepayType;
	}

	public BigDecimal getApproMonthRepayAmount() {
		return approMonthRepayAmount;
	}

	public void setApproMonthRepayAmount(BigDecimal approMonthRepayAmount) {
		this.approMonthRepayAmount = approMonthRepayAmount;
	}

	public String getApplyProductId() {
		return applyProductId;
	}

	public void setApplyProductId(String applyProductId) {
		this.applyProductId = applyProductId;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getApplyProductName() {
		return applyProductName;
	}

	public void setApplyProductName(String applyProductName) {
		this.applyProductName = applyProductName;
	}

	public String getApplyProductTypeCode() {
		return applyProductTypeCode;
	}

	public void setApplyProductTypeCode(String applyProductTypeCode) {
		this.applyProductTypeCode = applyProductTypeCode;
	}


	public String getApplyProductTypeName() {
		return applyProductTypeName;
	}

	public void setApplyProductTypeName(String applyProductTypeName) {
		this.applyProductTypeName = applyProductTypeName;
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

	public String getApplyLoanRepayType() {
		return applyLoanRepayType;
	}

	public void setApplyLoanRepayType(String applyLoanRepayType) {
		this.applyLoanRepayType = applyLoanRepayType;
	}

	public BigDecimal getApplyMonthRepayAmount() {
		return applyMonthRepayAmount;
	}

	public void setApplyMonthRepayAmount(BigDecimal applyMonthRepayAmount) {
		this.applyMonthRepayAmount = applyMonthRepayAmount;
	}

	public BigDecimal getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(BigDecimal marginAmount) {
		this.marginAmount = marginAmount;
	}

	public BigDecimal getCheckFee() {
		return checkFee;
	}

	public void setCheckFee(BigDecimal checkFee) {
		this.checkFee = checkFee;
	}

	public String getLoanModel() {
		return loanModel;
	}

	public void setLoanModel(String loanModel) {
		this.loanModel = loanModel;
	}

	public String getApproResult() {
		return approResult;
	}

	public void setApproResult(String approResult) {
		this.approResult = approResult;
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

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	public String getRecBank() {
		return recBank;
	}

	public void setRecBank(String recBank) {
		this.recBank = recBank;
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

	public String getOperateOtherDesc() {
		return operateOtherDesc;
	}

	public void setOperateOtherDesc(String operateOtherDesc) {
		this.operateOtherDesc = operateOtherDesc;
	}

	public String getRecBankProvince() {
		return recBankProvince;
	}

	public void setRecBankProvince(String recBankProvince) {
		this.recBankProvince = recBankProvince;
	}

	public String getContractProvince() {
		return contractProvince;
	}

	public void setContractProvince(String contractProvince) {
		this.contractProvince = contractProvince;
	}

	public String getRecBankCity() {
		return recBankCity;
	}

	public void setRecBankCity(String recBankCity) {
		this.recBankCity = recBankCity;
	}

	public String getContractCity() {
		return contractCity;
	}

	public void setContractCity(String contractCity) {
		this.contractCity = contractCity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRecBankDistinct() {
		return recBankDistinct;
	}

	public void setRecBankDistinct(String recBankDistinct) {
		this.recBankDistinct = recBankDistinct;
	}

	public String getIsAccord() {
		return isAccord;
	}

	public void setIsAccord(String isAccord) {
		this.isAccord = isAccord;
	}

	public String getContractDistinct() {
		return contractDistinct;
	}

	public void setContractDistinct(String contractDistinct) {
		this.contractDistinct = contractDistinct;
	}

	public String getRepBank() {
		return repBank;
	}

	public void setRepBank(String repBank) {
		this.repBank = repBank;
	}

	public String getMortgageeId() {
		return mortgageeId;
	}

	public void setMortgageeId(String mortgageeId) {
		this.mortgageeId = mortgageeId;
	}

	public String getMortgageeName() {
		return mortgageeName;
	}

	public void setMortgageeName(String mortgageeName) {
		this.mortgageeName = mortgageeName;
	}

	public String getMortgageeIdNum() {
		return mortgageeIdNum;
	}

	public void setMortgageeIdNum(String mortgageeIdNum) {
		this.mortgageeIdNum = mortgageeIdNum;
	}

	public String getMortgageeMobileNum() {
		return mortgageeMobileNum;
	}

	public void setMortgageeMobileNum(String mortgageeMobileNum) {
		this.mortgageeMobileNum = mortgageeMobileNum;
	}

	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}

	public String getRepBankProvince() {
		return repBankProvince;
	}

	public void setRepBankProvince(String repBankProvince) {
		this.repBankProvince = repBankProvince;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
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

	public String getBankDetailName() {
		return bankDetailName;
	}

	public void setBankDetailName(String bankDetailName) {
		this.bankDetailName = bankDetailName;
	}

	public String getContProvince() {
		return contProvince;
	}

	public void setContProvince(String contProvince) {
		this.contProvince = contProvince;
	}

	public Date getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}

	public String getContCity() {
		return contCity;
	}

	public void setContCity(String contCity) {
		this.contCity = contCity;
	}

	public String getContDistinct() {
		return contDistinct;
	}

	public void setContDistinct(String contDistinct) {
		this.contDistinct = contDistinct;
	}

	public String getContDetails() {
		return contDetails;
	}

	public void setContDetails(String contDetails) {
		this.contDetails = contDetails;
	}

	public String getMortgageeCapTerNo() {
		return mortgageeCapTerNo;
	}

	public void setMortgageeCapTerNo(String mortgageeCapTerNo) {
		this.mortgageeCapTerNo = mortgageeCapTerNo;
	}

	public String getRecBankcardNo() {
		return recBankcardNo;
	}

	public void setRecBankcardNo(String recBankcardNo) {
		this.recBankcardNo = recBankcardNo;
	}

	public String getRecBankcardName() {
		return recBankcardName;
	}

	public void setRecBankcardName(String recBankcardName) {
		this.recBankcardName = recBankcardName;
	}

	public String getRecBankName() {
		return recBankName;
	}

	public void setRecBankName(String recBankName) {
		this.recBankName = recBankName;
	}

	public String getRecBankMobile() {
		return recBankMobile;
	}

	public void setRecBankMobile(String recBankMobile) {
		this.recBankMobile = recBankMobile;
	}

	public String getRecBankIdNum() {
		return recBankIdNum;
	}

	public void setRecBankIdNum(String recBankIdNum) {
		this.recBankIdNum = recBankIdNum;
	}

	public String getRecBankDetail() {
		return recBankDetail;
	}

	public void setRecBankDetail(String recBankDetail) {
		this.recBankDetail = recBankDetail;
	}

	public String getCapitalTerraceNo() {
		return capitalTerraceNo;
	}

	public void setCapitalTerraceNo(String capitalTerraceNo) {
		this.capitalTerraceNo = capitalTerraceNo;
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

	public String getRepBankDetail() {
		return repBankDetail;
	}

	public void setRepBankDetail(String repBankDetail) {
		this.repBankDetail = repBankDetail;
	}

	public String getContractOrgPlatform() {
		return contractOrgPlatform;
	}

	public void setContractOrgPlatform(String contractOrgPlatform) {
		this.contractOrgPlatform = contractOrgPlatform;
	}

	public void setOrgLevel4(String orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}

	public BigDecimal getApplyServiceFeeRate() {
		return applyServiceFeeRate;
	}

	public void setApplyServiceFeeRate(BigDecimal applyServiceFeeRate) {
		this.applyServiceFeeRate = applyServiceFeeRate;
	}

	public BigDecimal getApproYearRate() {
		return approYearRate;
	}

	public void setApproYearRate(BigDecimal approYearRate) {
		this.approYearRate = approYearRate;
	}

	public BigDecimal getApproIntegratedRate() {
		return approIntegratedRate;
	}

	public void setApproIntegratedRate(BigDecimal approIntegratedRate) {
		this.approIntegratedRate = approIntegratedRate;
	}

	public BigDecimal getServiceFeeRate() {
		return serviceFeeRate;
	}

	public void setServiceFeeRate(BigDecimal serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	public BigDecimal getSpecialServiceFeeRate() {
		return specialServiceFeeRate;
	}

	public void setSpecialServiceFeeRate(BigDecimal specialServiceFeeRate) {
		this.specialServiceFeeRate = specialServiceFeeRate;
	}

	public BigDecimal getApplyYearRate() {
		return applyYearRate;
	}

	public void setApplyYearRate(BigDecimal applyYearRate) {
		this.applyYearRate = applyYearRate;
	}

	public BigDecimal getAllServiceFee() {
		return allServiceFee;
	}

	public void setAllServiceFee(BigDecimal allServiceFee) {
		this.allServiceFee = allServiceFee;
	}

	public BigDecimal getMarginRate() {
		return marginRate;
	}

	public void setMarginRate(BigDecimal marginRate) {
		this.marginRate = marginRate;
	}

	public String getOpenAccountStatus() {
		return openAccountStatus;
	}

	public void setOpenAccountStatus(String openAccountStatus) {
		this.openAccountStatus = openAccountStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGuaranteeRelation() {
		return guaranteeRelation;
	}

	public void setGuaranteeRelation(String guaranteeRelation) {
		this.guaranteeRelation = guaranteeRelation;
	}

	public String getLoanCompanyName() {
		return loanCompanyName;
	}

	public void setLoanCompanyName(String loanCompanyName) {
		this.loanCompanyName = loanCompanyName;
	}

	public String getLoanCompanyID() {
		return loanCompanyID;
	}

	public void setLoanCompanyID(String loanCompanyID) {
		this.loanCompanyID = loanCompanyID;
	}

	public String getGuaranteeCompanyName() {
		return guaranteeCompanyName;
	}

	public void setGuaranteeCompanyName(String guaranteeCompanyName) {
		this.guaranteeCompanyName = guaranteeCompanyName;
	}

	public String getGuaranteeCompanyId() {
		return guaranteeCompanyId;
	}

	public void setGuaranteeCompanyId(String guaranteeCompanyId) {
		this.guaranteeCompanyId = guaranteeCompanyId;
	}

	public String getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}
	
	
}