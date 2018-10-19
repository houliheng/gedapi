package com.resoft.credit.creditAndLine.entity.creditLineBank;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 流水信息列表Entity
 * 
 * @author wuxi01
 * @version 2016-02-26
 */
public class CreditLineBankDetail extends DataEntity<CreditLineBankDetail> {

	private static final long serialVersionUID = 1L;
	private CreditLineBank creditLineBank; // 流水银行卡ID 父类
	private String lineMonth; // 流水月份
	private String curreValidLineAmount; // 当月有效流水
	private String incomeAmount; // 进项额
	private String expenseAmount; // 出项额
	private String lineBankId; // 流水银行卡id
	private String cycleEndAmount; // 期末余额

	public CreditLineBankDetail() {
		super();
	}

	public CreditLineBankDetail(String id) {
		super(id);
	}

	@Length(min = 0, max = 10, message = "流水月份长度必须介于 0 和 10 之间")
	public String getLineMonth() {
		return lineMonth;
	}

	public void setLineMonth(String lineMonth) {
		this.lineMonth = lineMonth;
	}

	public String getCurreValidLineAmount() {
		return curreValidLineAmount;
	}

	public void setCurreValidLineAmount(String curreValidLineAmount) {
		this.curreValidLineAmount = curreValidLineAmount;
	}

	public String getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(String incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public String getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(String expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public CreditLineBank getCreditLineBank() {
		return creditLineBank;
	}

	public void setCreditLineBank(CreditLineBank creditLineBank) {
		this.creditLineBank = creditLineBank;
	}

	public String getLineBankId() {
		return lineBankId;
	}

	public void setLineBankId(String lineBankId) {
		this.lineBankId = lineBankId;
	}

	public String getCycleEndAmount() {
		return cycleEndAmount;
	}

	public void setCycleEndAmount(String cycleEndAmount) {
		this.cycleEndAmount = cycleEndAmount;
	}

}