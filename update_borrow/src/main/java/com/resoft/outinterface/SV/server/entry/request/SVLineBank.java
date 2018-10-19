package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.custinfo.entity.CustInfo;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * SV接口流水信息列表Entity
 * 
 * @author wuxi01
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVLineBank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String applyNo; // 申请编号
	private Date createDate; // 创建日期
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	private CustInfo custInfo;// 客户基本信息
	private CompanyInfo companyInfo;// 企业基本信息
	// 生成xsd字段
	@XmlElement(required = false, name = "ROLE_TYPE")
	private String roleType; // 角色类型(主借人，共借人，担保人，配偶)
	@XmlElement(required = false, name = "ID_NUM")
	private String idNum; // 证件号(企业法人证件号)
	@XmlElement(required = false, name = "ID_TYPE")
	private String idType; // 证件类型(企业法人证件类型)
	@XmlElement(required = false, name = "BANK_NAME")
	private String bankName; // 开户行名称
	@XmlElement(required = false, name = "BANKCARD_NO")
	private String bankcardNo; // 银行卡号
	@XmlElement(required = false, name = "BALANCE_AMOUNT")
	private String balanceAmount; // 余额
	@XmlElement(required = false, name = "AVG_CURRE_VALID_LINE_AMOUNT")
	private String avgCurreValidLineAmount;// 月均流水
	@XmlElement(required = false, name = "SUM_INCOME_AMOUNT")
	private String sumIncomeAmount; // 进项总额
	@XmlElement(required = false, name = "SUM_EXPENSE_AMOUNT")
	private String sumExpenseAmount; // 出项总额
	@XmlElement(required = false, name = "PRINTING_DATE")
	private String printingDate; // 打印日期
	@XmlElement(required = false, name = "LINE_BANK_DETAIL")
	private List<SVCreditLineBankDetail> creditLineBankDetail;

	public SVLineBank(String id, String applyNo, String remarks, User createBy, Date createDate, User updateBy, Date updateDate, String delFlag, CustInfo custInfo, String roleType, String idNum, String idType, String bankName, String bankcardNo, String balanceAmount, String avgCurreValidLineAmount, String sumIncomeAmount, String sumExpenseAmount, String printingDate) {
		super();
		this.id = id;
		this.applyNo = applyNo;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.delFlag = delFlag;
		this.roleType = roleType;
		this.idNum = idNum;
		this.idType = idType;
		this.bankName = bankName;
		this.bankcardNo = bankcardNo;
		this.balanceAmount = balanceAmount;
		this.avgCurreValidLineAmount = avgCurreValidLineAmount;
		this.sumIncomeAmount = sumIncomeAmount;
		this.sumExpenseAmount = sumExpenseAmount;
		this.printingDate = printingDate;
	}

	public SVLineBank() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

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

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

	public List<SVCreditLineBankDetail> getCreditLineBankDetail() {
		return creditLineBankDetail;
	}

	public void setCreditLineBankDetail(List<SVCreditLineBankDetail> creditLineBankDetail) {
		this.creditLineBankDetail = creditLineBankDetail;
	}

	public CustInfo getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

}