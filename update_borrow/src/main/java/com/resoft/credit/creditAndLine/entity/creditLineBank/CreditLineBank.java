package com.resoft.credit.creditAndLine.entity.creditLineBank;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 流水信息列表Entity
 * 
 * @author wuxi01
 * @version 2016-02-26
 */
public class CreditLineBank extends DataEntity<CreditLineBank> {

	private static final long serialVersionUID = 1L;
	private String applyNo; // 申请编号
	private String roleType; // 角色类型(主借人，共借人，担保人，配偶)
	private String custId; // 客户ID
	private String custName; // 名称
	private String idNum; // 证件号
	private String idType; // 证件类型
	private String bankName; // 开户行名称
	private String bankcardNo; // 银行卡号
	private String balanceAmount; // 余额
	private String avgCurreValidLineAmount;// 月均流水
	private String sumIncomeAmount; // 进项总额
	private String sumExpenseAmount; // 出项总额
	private String printingDate; // 打印日期
	private String averageCycleEndAmount; // 平均期末余额

	public CreditLineBank() {
		super();
	}

	public CreditLineBank(String id) {
		super(id);
	}

	@Length(min = 1, max = 32, message = "申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Length(min = 0, max = 4, message = "角色类型(主借人，共借人，担保人，配偶)长度必须介于 0 和 4 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Length(min = 1, max = 32, message = "客户ID长度必须介于 1 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Length(min = 0, max = 20, message = "名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Length(min = 0, max = 18, message = "证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	@Length(min = 0, max = 10, message = "证件类型长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Length(min = 0, max = 20, message = "开户行名称长度必须介于 0 和 20 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Length(min = 0, max = 30, message = "银行卡号长度必须介于 0 和 30 之间")
	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getSumIncomeAmount() {
		return sumIncomeAmount;
	}

	public void setSumIncomeAmount(String sumIncomeAmount) {
		this.sumIncomeAmount = sumIncomeAmount;
	}

	public String getAvgCurreValidLineAmount() {
		return avgCurreValidLineAmount;
	}

	public void setAvgCurreValidLineAmount(String avgCurreValidLineAmount) {
		this.avgCurreValidLineAmount = avgCurreValidLineAmount;
	}

	public String getSumExpenseAmount() {
		return sumExpenseAmount;
	}

	public void setSumExpenseAmount(String sumExpenseAmount) {
		this.sumExpenseAmount = sumExpenseAmount;
	}

	public String getPrintingDate() {
		return printingDate;
	}

	public void setPrintingDate(String printingDate) {
		this.printingDate = printingDate;
	}

	public String getAverageCycleEndAmount() {
		return averageCycleEndAmount;
	}

	public void setAverageCycleEndAmount(String averageCycleEndAmount) {
		this.averageCycleEndAmount = averageCycleEndAmount;
	}

}