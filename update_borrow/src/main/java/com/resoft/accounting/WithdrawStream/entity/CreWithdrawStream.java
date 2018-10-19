package com.resoft.accounting.WithdrawStream.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 冠E贷充值流水记录Entity
 * @author gsh
 * @version 2018-04-02
 */
public class CreWithdrawStream extends DataEntity<CreWithdrawStream> {
	
	private static final long serialVersionUID = 1L;
	private String contractNo;		// 合同编号
	private String applyNo;		// 申请编号
	private String withdrawAmount;		// 提现金额
	private String accountBalance;		// 账户余额
	private String flag;		// 提现标记
	private String seqno;		// 交易流水好
	private String mchn;		// 系统代码
	
	public CreWithdrawStream() {
		super();
	}

	public CreWithdrawStream(String id){
		super(id);
	}

	@Length(min=0, max=50, message="合同编号长度必须介于 0 和 50 之间")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Length(min=0, max=50, message="申请编号长度必须介于 0 和 50 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	public String getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(String withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	
	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	@Length(min=0, max=255, message="提现标记长度必须介于 0 和 255 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Length(min=0, max=50, message="交易流水好长度必须介于 0 和 50 之间")
	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	
	@Length(min=0, max=50, message="系统代码长度必须介于 0 和 50 之间")
	public String getMchn() {
		return mchn;
	}

	public void setMchn(String mchn) {
		this.mchn = mchn;
	}
	
}