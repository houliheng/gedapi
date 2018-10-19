package com.resoft.credit.compensatory.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 代偿资金账户Entity
 * @author jml
 * @version 2018-03-19
 */
public class CompensatoryAccount extends DataEntity<CompensatoryAccount> {
	
	private static final long serialVersionUID = 1L;
	private String compensatoryAccount;		// compensatory_account
	private String custId;
	private String priopityLevel;		// 优先级
	private String accountStatus;		// 状态0:开启，1：禁止
	private String accountAmount;
	
	//非表--------------------------------
	private String compensatoryType;//代偿类型    1自有担保人   2担保公司   3平台保证金账户
	private String compensatoryName;//代偿账户名称
	
	public CompensatoryAccount() {
		super();
	}

	public CompensatoryAccount(String id){
		super(id);
	}

	@Length(min=0, max=32, message="compensatory_account长度必须介于 0 和 32 之间")
	public String getCompensatoryAccount() {
		return compensatoryAccount;
	}

	public void setCompensatoryAccount(String compensatoryAccount) {
		this.compensatoryAccount = compensatoryAccount;
	}
	
	@Length(min=0, max=1, message="优先级长度必须介于 0 和 1 之间")
	public String getPriopityLevel() {
		return priopityLevel;
	}

	public void setPriopityLevel(String priopityLevel) {
		this.priopityLevel = priopityLevel;
	}
	
	@Length(min=0, max=1, message="状态0:开启，1：禁止长度必须介于 0 和 1 之间")
	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(String accountAmount) {
		this.accountAmount = accountAmount;
	}

	public String getCompensatoryType() {
		return compensatoryType;
	}

	public void setCompensatoryType(String compensatoryType) {
		this.compensatoryType = compensatoryType;
	}

	public String getCompensatoryName() {
		return compensatoryName;
	}

	public void setCompensatoryName(String compensatoryName) {
		this.compensatoryName = compensatoryName;
	}
	
}