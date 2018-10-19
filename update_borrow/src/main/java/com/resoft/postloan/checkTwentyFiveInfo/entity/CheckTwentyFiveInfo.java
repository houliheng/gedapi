package com.resoft.postloan.checkTwentyFiveInfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 25日复核信息Entity
 * @author admin
 * @version 2016-05-25
 */
public class CheckTwentyFiveInfo extends DataEntity<CheckTwentyFiveInfo> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String allocateId;		//分配id
	private String roleType;		// 角色类型(主借人，共借人，担保人，主借企业，担保企业)',
	private String checkBase;		// 基本信息是否核查（0否1是）
	private String checkBaseRemark;		// 基本信息备注
	private String checkWeb;		// 网查信息是否核查（0否1是）
	private String checkWebRemark;		// 网查信息备注
	private String checkFamily;		// 家庭状况信息是否核查（0否1是）
	private String checkFamilyRemark;		// 家庭状况信息备注
	private String checkFinancial;		// 财务状况信息是否核查（0否1是）
	private String checkFinancialRemark;		// 财务状况信息备注
	private String checkLoanPurpost;		// 借款用途信息是否核查
	private String checkLoanPurpostRemark;		// 借款用途信息备注
	private String checkBank;		// 银行流水信息是否核查
	private String checkBankRemark;		// 银行流水信息备注
	private String checkCredit;		// 企业征信是否核查
	private String checkCreditRemark;		// 企业征信备注
	private String checkProperty;		// 资产信息是否核查
	private String checkPropertyRemark;		// 资产信息是否核查备注
	private String checkMortgage;		// 抵质押品是否核查
	private String checkMortgageRemark;		// 抵质押品是否核查备注
	private String checkOperate;		// 经营情况是否核查
	private String checkOperateRemark;		// 经营情况是否核查备注
	private String checkArchive;		// 是否建档
	private String checkArchiveRemark;		// 是否建档备注
	private String checkProc;		// 流程是否合格
	private String checkProcRemark;		// 流程是否合格备注
	private String checkGetFee;		// 是否有信访咨询费
	private String checkGetFeeRemark;		// 是否有信访咨询费备注
	private String checkGetAddress;		// 是否实地外访
	private String checkGetAddressRemark;		// 是否实地外访备注
	private String checkSign;		// 核查是否执行签约条件
	private String checkSignRemark;		// 核查是否执行备注
	private String checkDebt;   //隐性负债
	private String checkDebtRemark;   //隐性负债备注
	
	
	public String getCheckBaseRemark() {
		return checkBaseRemark;
	}

	public void setCheckBaseRemark(String checkBaseRemark) {
		this.checkBaseRemark = checkBaseRemark;
	}

	public String getCheckWebRemark() {
		return checkWebRemark;
	}

	public void setCheckWebRemark(String checkWebRemark) {
		this.checkWebRemark = checkWebRemark;
	}

	public String getCheckFamilyRemark() {
		return checkFamilyRemark;
	}

	public void setCheckFamilyRemark(String checkFamilyRemark) {
		this.checkFamilyRemark = checkFamilyRemark;
	}

	public String getCheckFinancialRemark() {
		return checkFinancialRemark;
	}

	public void setCheckFinancialRemark(String checkFinancialRemark) {
		this.checkFinancialRemark = checkFinancialRemark;
	}

	public String getCheckLoanPurpostRemark() {
		return checkLoanPurpostRemark;
	}

	public void setCheckLoanPurpostRemark(String checkLoanPurpostRemark) {
		this.checkLoanPurpostRemark = checkLoanPurpostRemark;
	}

	public String getCheckBankRemark() {
		return checkBankRemark;
	}

	public void setCheckBankRemark(String checkBankRemark) {
		this.checkBankRemark = checkBankRemark;
	}

	public String getCheckCreditRemark() {
		return checkCreditRemark;
	}

	public void setCheckCreditRemark(String checkCreditRemark) {
		this.checkCreditRemark = checkCreditRemark;
	}

	public String getCheckPropertyRemark() {
		return checkPropertyRemark;
	}

	public void setCheckPropertyRemark(String checkPropertyRemark) {
		this.checkPropertyRemark = checkPropertyRemark;
	}

	public String getCheckMortgageRemark() {
		return checkMortgageRemark;
	}

	public void setCheckMortgageRemark(String checkMortgageRemark) {
		this.checkMortgageRemark = checkMortgageRemark;
	}

	public String getCheckOperateRemark() {
		return checkOperateRemark;
	}

	public void setCheckOperateRemark(String checkOperateRemark) {
		this.checkOperateRemark = checkOperateRemark;
	}

	public String getCheckArchiveRemark() {
		return checkArchiveRemark;
	}

	public void setCheckArchiveRemark(String checkArchiveRemark) {
		this.checkArchiveRemark = checkArchiveRemark;
	}

	public String getCheckProcRemark() {
		return checkProcRemark;
	}

	public void setCheckProcRemark(String checkProcRemark) {
		this.checkProcRemark = checkProcRemark;
	}

	public String getCheckGetFeeRemark() {
		return checkGetFeeRemark;
	}

	public void setCheckGetFeeRemark(String checkGetFeeRemark) {
		this.checkGetFeeRemark = checkGetFeeRemark;
	}

	public String getCheckGetAddressRemark() {
		return checkGetAddressRemark;
	}

	public void setCheckGetAddressRemark(String checkGetAddressRemark) {
		this.checkGetAddressRemark = checkGetAddressRemark;
	}

	public String getCheckSignRemark() {
		return checkSignRemark;
	}

	public void setCheckSignRemark(String checkSignRemark) {
		this.checkSignRemark = checkSignRemark;
	}

	public String getCheckDebtRemark() {
		return checkDebtRemark;
	}

	public void setCheckDebtRemark(String checkDebtRemark) {
		this.checkDebtRemark = checkDebtRemark;
	}

	public String getAllocateId() {
		return allocateId;
	}

	public void setAllocateId(String allocateId) {
		this.allocateId = allocateId;
	}

	public CheckTwentyFiveInfo() {
		super();
	}

	public CheckTwentyFiveInfo(String id){
		super(id);
	}

	@Length(min=1, max=50, message="合同编号长度必须介于 1 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=4, message="角色类型(主借人，共借人，担保人，主借企业，担保企业)',长度必须介于 0 和 4 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=0, max=1, message="基本信息是否核查（0否1是）长度必须介于 0 和 1 之间")
	public String getCheckBase() {
		return checkBase;
	}

	public void setCheckBase(String checkBase) {
		this.checkBase = checkBase;
	}
	
	@Length(min=0, max=1, message="网查信息是否核查（0否1是）长度必须介于 0 和 1 之间")
	public String getCheckWeb() {
		return checkWeb;
	}

	public void setCheckWeb(String checkWeb) {
		this.checkWeb = checkWeb;
	}
	
	@Length(min=0, max=1, message="家庭状况信息是否核查（0否1是）长度必须介于 0 和 1 之间")
	public String getCheckFamily() {
		return checkFamily;
	}

	public void setCheckFamily(String checkFamily) {
		this.checkFamily = checkFamily;
	}
	
	@Length(min=0, max=1, message="财务状况信息是否核查（0否1是）长度必须介于 0 和 1 之间")
	public String getCheckFinancial() {
		return checkFinancial;
	}

	public void setCheckFinancial(String checkFinancial) {
		this.checkFinancial = checkFinancial;
	}
	
	@Length(min=0, max=1, message="借款用途信息是否核查长度必须介于 0 和 1 之间")
	public String getCheckLoanPurpost() {
		return checkLoanPurpost;
	}

	public void setCheckLoanPurpost(String checkLoanPurpost) {
		this.checkLoanPurpost = checkLoanPurpost;
	}
	
	@Length(min=0, max=1, message="银行流水信息是否核查长度必须介于 0 和 1 之间")
	public String getCheckBank() {
		return checkBank;
	}

	public void setCheckBank(String checkBank) {
		this.checkBank = checkBank;
	}
	
	@Length(min=0, max=1, message="企业征信是否核查长度必须介于 0 和 1 之间")
	public String getCheckCredit() {
		return checkCredit;
	}

	public void setCheckCredit(String checkCredit) {
		this.checkCredit = checkCredit;
	}
	
	@Length(min=0, max=1, message="资产信息是否核查长度必须介于 0 和 1 之间")
	public String getCheckProperty() {
		return checkProperty;
	}

	public void setCheckProperty(String checkProperty) {
		this.checkProperty = checkProperty;
	}
	
	@Length(min=0, max=1, message="抵质押品是否核查长度必须介于 0 和 1 之间")
	public String getCheckMortgage() {
		return checkMortgage;
	}

	public void setCheckMortgage(String checkMortgage) {
		this.checkMortgage = checkMortgage;
	}
	
	@Length(min=0, max=1, message="经营情况是否核查长度必须介于 0 和 1 之间")
	public String getCheckOperate() {
		return checkOperate;
	}

	public void setCheckOperate(String checkOperate) {
		this.checkOperate = checkOperate;
	}
	
	@Length(min=0, max=1, message="是否建档长度必须介于 0 和 1 之间")
	public String getCheckArchive() {
		return checkArchive;
	}

	public void setCheckArchive(String checkArchive) {
		this.checkArchive = checkArchive;
	}
	
	@Length(min=0, max=1, message="流程是否合格长度必须介于 0 和 1 之间")
	public String getCheckProc() {
		return checkProc;
	}

	public void setCheckProc(String checkProc) {
		this.checkProc = checkProc;
	}
	
	@Length(min=0, max=1, message="是否有信访咨询费长度必须介于 0 和 1 之间")
	public String getCheckGetFee() {
		return checkGetFee;
	}

	public void setCheckGetFee(String checkGetFee) {
		this.checkGetFee = checkGetFee;
	}
	
	@Length(min=0, max=1, message="是否实地外访长度必须介于 0 和 1 之间")
	public String getCheckGetAddress() {
		return checkGetAddress;
	}

	public void setCheckGetAddress(String checkGetAddress) {
		this.checkGetAddress = checkGetAddress;
	}
	
	@Length(min=0, max=1, message="核查是否执行签约条件长度必须介于 0 和 1 之间")
	public String getCheckSign() {
		return checkSign;
	}

	public void setCheckSign(String checkSign) {
		this.checkSign = checkSign;
	}

	public String getCheckDebt() {
		return checkDebt;
	}

	public void setCheckDebt(String checkDebt) {
		this.checkDebt = checkDebt;
	}
	
	
	
}