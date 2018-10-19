package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.resoft.credit.creditAndLine.entity.creditLineBank.CreditLineBank;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 流水信息列表Entity
 * 
 * @author wuxi01
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCreditLineBankDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private String remarks; // 备注
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	private CreditLineBank creditLineBank; // 流水银行卡ID 父类
	// 生成xsd字段
	@XmlElement(required = false, name = "BANKCARD_NO")
	private String bankcardNo;// 银行卡号
	@XmlElement(required = false, name = "LINE_MONTH")
	private String lineMonth; // 流水月份
	@XmlElement(required = false, name = "CURRE_VALID_LINE_AMOUNT")
	private String curreValidLineAmount; // 当月有效流水
	@XmlElement(required = false, name = "INCOME_AMOUNT")
	private String incomeAmount; // 进项额
	@XmlElement(required = false, name = "EXPENSE_AMOUNT")
	private String expenseAmount; // 出项额

	public SVCreditLineBankDetail(String id, String applyNo, String remarks, User createBy, Date createDate, User updateBy, Date updateDate, String delFlag, CreditLineBank creditLineBank, String bankcardNo, String lineMonth, String curreValidLineAmount, String incomeAmount, String expenseAmount) {
		super();
		this.id = id;
		this.applyNo = applyNo;
		this.remarks = remarks;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.delFlag = delFlag;
		this.creditLineBank = creditLineBank;
		this.bankcardNo = bankcardNo;
		this.lineMonth = lineMonth;
		this.curreValidLineAmount = curreValidLineAmount;
		this.incomeAmount = incomeAmount;
		this.expenseAmount = expenseAmount;
	}

	public SVCreditLineBankDetail() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

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

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}

	public CreditLineBank getCreditLineBank() {
		return creditLineBank;
	}

	public void setCreditLineBank(CreditLineBank creditLineBank) {
		this.creditLineBank = creditLineBank;
	}

}