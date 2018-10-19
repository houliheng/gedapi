package com.resoft.credit.applyLoanStatus.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 财务放款Entity
 * @author wuxi01
 * @version 2016-03-03
 */
public class ApplyLoanStatus extends DataEntity<ApplyLoanStatus> {
	
	private static final long serialVersionUID = 1L;
	private String orgLevel2;		// 2级公司
	private String orgLevel3;		// 3级公司
	private String orgLevel4;		// 4级公司
	private String applyNo;		// 申请编号
	private String custName;		// 客户名称
	private String contractNo;		// 合同编号
	private String contractAmount;		// 合同金额
	private String factLoanAmount;		// 实际放款金额（转到银行卡金额）
	private String loanModel;		// 借款模式
	private String loanStatus;		// 放款状态
	private Date orderLoanDate;		// 预约到账日期
	private String contractState;		// 合同状态（字典类型：CONTRACT_STATE）
	private String ServiceFeeStatus;
	private String send;//是否推解冻
	public ApplyLoanStatus() {
		super();
	}

	public ApplyLoanStatus(String id){
		super(id);
	}

	@Length(min=0, max=32, message="2级公司长度必须介于 0 和 32 之间")
	public String getOrgLevel2() {
		return orgLevel2;
	}

	public void setOrgLevel2(String orgLevel2) {
		this.orgLevel2 = orgLevel2;
	}
	
	@Length(min=0, max=32, message="3级公司长度必须介于 0 和 32 之间")
	public String getOrgLevel3() {
		return orgLevel3;
	}

	public void setOrgLevel3(String orgLevel3) {
		this.orgLevel3 = orgLevel3;
	}
	
	@Length(min=0, max=32, message="4级公司长度必须介于 0 和 32 之间")
	public String getOrgLevel4() {
		return orgLevel4;
	}

	public void setOrgLevel4(String orgLevel4) {
		this.orgLevel4 = orgLevel4;
	}
	
	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=20, message="客户名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	public String getFactLoanAmount() {
		return factLoanAmount;
	}

	public void setFactLoanAmount(String factLoanAmount) {
		this.factLoanAmount = factLoanAmount;
	}
	
	@Length(min=0, max=4, message="借款模式长度必须介于 0 和 4 之间")
	public String getLoanModel() {
		return loanModel;
	}

	public void setLoanModel(String loanModel) {
		this.loanModel = loanModel;
	}
	
	@Length(min=0, max=4, message="放款状态长度必须介于 0 和 4 之间")
	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderLoanDate() {
		return orderLoanDate;
	}

	public void setOrderLoanDate(Date orderLoanDate) {
		this.orderLoanDate = orderLoanDate;
	}
	
	@Length(min=0, max=4, message="合同状态（字典类型：CONTRACT_STATE）长度必须介于 0 和 4 之间")
	public String getContractState() {
		return contractState;
	}

	public void setContractState(String contractState) {
		this.contractState = contractState;
	}

	public String getServiceFeeStatus() {
		return ServiceFeeStatus;
	}

	public void setServiceFeeStatus(String serviceFeeStatus) {
		ServiceFeeStatus = serviceFeeStatus;
	}

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	

	
	
	
}