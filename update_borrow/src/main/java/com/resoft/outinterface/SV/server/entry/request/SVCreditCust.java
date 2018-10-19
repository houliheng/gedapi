package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.resoft.credit.custinfo.entity.CustInfo;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * SV接口个人征信列表Entity
 * 
 * @author wuxi01
 * @version 2016-04-21
 */
@XmlAccessorType(XmlAccessType.NONE)
public class SVCreditCust implements Serializable {

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
	private CustInfo custInfo;// 本系统中客户基本信息对象，便于入库时数据完整，未生成在xsd接口文件中
	// 生成xsd字段
	@XmlElement(required = true, name = "ROLE_TYPE")
	private String roleType; // 角色类型(主借人，共借人，担保人，配偶)
	@XmlElement(required = true, name = "ID_TYPE")
	private String idType; // 证件类型
	@XmlElement(required = true, name = "ID_NUM")
	private String idNum; // 证件号
	@XmlElement(required = true, name = "PRINTING_DATE")
	private Date printingDate; // 打印日期
	@XmlElement(required = true, name = "QUERY_NUM_YEAR")
	private String queryNumYear; // 本人年内查询次数
	@XmlElement(required = true, name = "QUERY_NUM_SEASON")
	private String queryNumSeason; // 本人季内查询次数
	@XmlElement(required = true, name = "QUERY_NUM_MONTH")
	private String queryNumMonth; // 本人月内查询次数
	@XmlElement(required = true, name = "QUERY_REPORT_DATE_MINUS")
	private String queryReportDateMinus; // 查询时间与报告时间间隔
	@XmlElement(required = true, name = "CREDIT_MONTHS")
	private String creditMonths; // 征信信用历史月数
	@XmlElement(required = true, name = "MOBILE_NO")
	private String mobileNo; // 征信中手机号码
	@XmlElement(required = true, name = "COMPANY_PHONE_NO")
	private String companyPhoneNo; // 征信中单位号码
	@XmlElement(required = true, name = "ALL_CREDIT_CARD_NUM")
	private String allCreditCardNum; // 信用卡总数量
	@XmlElement(required = true, name = "OVERDUE_CARD_NUM")
	private String overdueCardNum; // 逾期卡数
	@XmlElement(required = true, name = "UNUSED_CARD_NUM")
	private String unusedCardNum; // 未使用卡数量（不含美元账户）
	@XmlElement(required = true, name = "ABNORMAL_CARD_NUM")
	private String abnormalCardNum; // 异常卡数量
	@XmlElement(required = true, name = "ALL_CREDIT_AMOUNT")
	private BigDecimal allCreditAmount; // 总授信额度
	@XmlElement(required = true, name = "ALL_USED_AMOUNT")
	private BigDecimal allUsedAmount; // 总使用额度
	@XmlElement(required = true, name = "ALL_BALANCE_AMOUNT")
	private BigDecimal allBalanceAmount; // 总余额
	@XmlElement(required = true, name = "ALL_OVERDUE_AMOUNT")
	private BigDecimal allOverdueAmount; // 逾期总额
	@XmlElement(required = true, name = "USED_CREDIT_CARD_RATE")
	private String usedCreditCardRate; // 信用卡使用占比
	@XmlElement(required = true, name = "MAX_CREDIT_AMOUNT")
	private BigDecimal maxCreditAmount; // 最高授信额度
	@XmlElement(required = true, name = "MAX_USED_AMOUNT")
	private BigDecimal maxUsedAmount; // 最高使用额度
	@XmlElement(required = true, name = "CONTINUE_OVERDUE_NUM")
	private String continueOverdueNum; // 连续逾期次数
	@XmlElement(required = true, name = "ALL_OVERDUE_NUM")
	private String allOverdueNum; // 累计逾期次数
	@XmlElement(required = true, name = "CREDIT_CARD_STATUS")
	private String creditCardStatus; // 信用卡状态
	@XmlElement(required = true, name = "SUM_LOAN_AMOUNT")
	private BigDecimal sumLoanAmount; // 贷款总额
	@XmlElement(required = true, name = "SUM_BALANCE_AMOUNT")
	private BigDecimal sumBalanceAmount; // 贷款余额
	@XmlElement(required = true, name = "SUM_OVERDUE_AMOUNT")
	private BigDecimal sumOverdueAmount; // 贷款逾期总额
	@XmlElement(required = true, name = "IS_WHITE")
	private String isWhite; // 是否白户
	@XmlElement(required = true, name = "MORTGAGE_HOUSE_NUM")
	private String mortgageHouseNum;// 房贷笔数
	@XmlElement(required = true, name = "CREDIT_CUST_LOAN")
	private List<SVCreditCustLoan> creditCustLoan;// 个人贷款明细

	public SVCreditCust(String id, String applyNo, String remarks, User createBy, Date createDate, User updateBy, Date updateDate, String delFlag, CustInfo custInfo, String roleType, String idType, String idNum, Date printingDate, String queryNumYear, String queryNumSeason, String queryNumMonth, String queryReportDateMinus, String creditMonths, String mobileNo, String companyPhoneNo, String allCreditCardNum, String overdueCardNum, String unusedCardNum, String abnormalCardNum, BigDecimal allCreditAmount, BigDecimal allUsedAmount, BigDecimal allBalanceAmount, BigDecimal allOverdueAmount, String usedCreditCardRate, BigDecimal maxCreditAmount, BigDecimal maxUsedAmount, String continueOverdueNum, String allOverdueNum, String creditCardStatus, BigDecimal sumLoanAmount, BigDecimal sumBalanceAmount, BigDecimal sumOverdueAmount, String isWhite, String mortgageHouseNum) {
		super();
		this.id = id;
		this.applyNo = applyNo;
		this.remarks = remarks;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.delFlag = delFlag;
		this.roleType = roleType;
		this.idType = idType;
		this.idNum = idNum;
		this.printingDate = printingDate;
		this.queryNumYear = queryNumYear;
		this.queryNumSeason = queryNumSeason;
		this.queryNumMonth = queryNumMonth;
		this.queryReportDateMinus = queryReportDateMinus;
		this.creditMonths = creditMonths;
		this.mobileNo = mobileNo;
		this.companyPhoneNo = companyPhoneNo;
		this.allCreditCardNum = allCreditCardNum;
		this.overdueCardNum = overdueCardNum;
		this.unusedCardNum = unusedCardNum;
		this.abnormalCardNum = abnormalCardNum;
		this.allCreditAmount = allCreditAmount;
		this.allUsedAmount = allUsedAmount;
		this.allBalanceAmount = allBalanceAmount;
		this.allOverdueAmount = allOverdueAmount;
		this.usedCreditCardRate = usedCreditCardRate;
		this.maxCreditAmount = maxCreditAmount;
		this.maxUsedAmount = maxUsedAmount;
		this.continueOverdueNum = continueOverdueNum;
		this.allOverdueNum = allOverdueNum;
		this.creditCardStatus = creditCardStatus;
		this.sumLoanAmount = sumLoanAmount;
		this.sumBalanceAmount = sumBalanceAmount;
		this.sumOverdueAmount = sumOverdueAmount;
		this.isWhite = isWhite;
		this.mortgageHouseNum = mortgageHouseNum;
	}

	public SVCreditCust() {
		super();
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

	public String getQueryNumYear() {
		return queryNumYear;
	}

	public void setQueryNumYear(String queryNumYear) {
		this.queryNumYear = queryNumYear;
	}

	public String getQueryNumSeason() {
		return queryNumSeason;
	}

	public void setQueryNumSeason(String queryNumSeason) {
		this.queryNumSeason = queryNumSeason;
	}

	public String getQueryNumMonth() {
		return queryNumMonth;
	}

	public void setQueryNumMonth(String queryNumMonth) {
		this.queryNumMonth = queryNumMonth;
	}

	public String getQueryReportDateMinus() {
		return queryReportDateMinus;
	}

	public void setQueryReportDateMinus(String queryReportDateMinus) {
		this.queryReportDateMinus = queryReportDateMinus;
	}

	public String getCreditMonths() {
		return creditMonths;
	}

	public void setCreditMonths(String creditMonths) {
		this.creditMonths = creditMonths;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCompanyPhoneNo() {
		return companyPhoneNo;
	}

	public void setCompanyPhoneNo(String companyPhoneNo) {
		this.companyPhoneNo = companyPhoneNo;
	}

	public String getAllCreditCardNum() {
		return allCreditCardNum;
	}

	public void setAllCreditCardNum(String allCreditCardNum) {
		this.allCreditCardNum = allCreditCardNum;
	}

	public String getOverdueCardNum() {
		return overdueCardNum;
	}

	public void setOverdueCardNum(String overdueCardNum) {
		this.overdueCardNum = overdueCardNum;
	}

	public String getUnusedCardNum() {
		return unusedCardNum;
	}

	public void setUnusedCardNum(String unusedCardNum) {
		this.unusedCardNum = unusedCardNum;
	}

	public String getAbnormalCardNum() {
		return abnormalCardNum;
	}

	public void setAbnormalCardNum(String abnormalCardNum) {
		this.abnormalCardNum = abnormalCardNum;
	}

	public Date getPrintingDate() {
		return printingDate;
	}

	public void setPrintingDate(Date printingDate) {
		this.printingDate = printingDate;
	}

	public BigDecimal getAllCreditAmount() {
		return allCreditAmount;
	}

	public void setAllCreditAmount(BigDecimal allCreditAmount) {
		this.allCreditAmount = allCreditAmount;
	}

	public BigDecimal getAllUsedAmount() {
		return allUsedAmount;
	}

	public void setAllUsedAmount(BigDecimal allUsedAmount) {
		this.allUsedAmount = allUsedAmount;
	}

	public BigDecimal getAllBalanceAmount() {
		return allBalanceAmount;
	}

	public void setAllBalanceAmount(BigDecimal allBalanceAmount) {
		this.allBalanceAmount = allBalanceAmount;
	}

	public BigDecimal getAllOverdueAmount() {
		return allOverdueAmount;
	}

	public void setAllOverdueAmount(BigDecimal allOverdueAmount) {
		this.allOverdueAmount = allOverdueAmount;
	}

	public String getUsedCreditCardRate() {
		return usedCreditCardRate;
	}

	public void setUsedCreditCardRate(String usedCreditCardRate) {
		this.usedCreditCardRate = usedCreditCardRate;
	}

	public BigDecimal getMaxCreditAmount() {
		return maxCreditAmount;
	}

	public void setMaxCreditAmount(BigDecimal maxCreditAmount) {
		this.maxCreditAmount = maxCreditAmount;
	}

	public BigDecimal getMaxUsedAmount() {
		return maxUsedAmount;
	}

	public void setMaxUsedAmount(BigDecimal maxUsedAmount) {
		this.maxUsedAmount = maxUsedAmount;
	}

	public String getContinueOverdueNum() {
		return continueOverdueNum;
	}

	public void setContinueOverdueNum(String continueOverdueNum) {
		this.continueOverdueNum = continueOverdueNum;
	}

	public String getAllOverdueNum() {
		return allOverdueNum;
	}

	public void setAllOverdueNum(String allOverdueNum) {
		this.allOverdueNum = allOverdueNum;
	}

	public String getCreditCardStatus() {
		return creditCardStatus;
	}

	public void setCreditCardStatus(String creditCardStatus) {
		this.creditCardStatus = creditCardStatus;
	}

	public BigDecimal getSumLoanAmount() {
		return sumLoanAmount;
	}

	public void setSumLoanAmount(BigDecimal sumLoanAmount) {
		this.sumLoanAmount = sumLoanAmount;
	}

	public BigDecimal getSumBalanceAmount() {
		return sumBalanceAmount;
	}

	public void setSumBalanceAmount(BigDecimal sumBalanceAmount) {
		this.sumBalanceAmount = sumBalanceAmount;
	}

	public BigDecimal getSumOverdueAmount() {
		return sumOverdueAmount;
	}

	public void setSumOverdueAmount(BigDecimal sumOverdueAmount) {
		this.sumOverdueAmount = sumOverdueAmount;
	}

	public String getIsWhite() {
		return isWhite;
	}

	public void setIsWhite(String isWhite) {
		this.isWhite = isWhite;
	}

	public String getMortgageHouseNum() {
		return mortgageHouseNum;
	}

	public void setMortgageHouseNum(String mortgageHouseNum) {
		this.mortgageHouseNum = mortgageHouseNum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

	public List<SVCreditCustLoan> getCreditCustLoan() {
		return creditCustLoan;
	}

	public void setCreditCustLoan(List<SVCreditCustLoan> creditCustLoan) {
		this.creditCustLoan = creditCustLoan;
	}

	public CustInfo getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}

}