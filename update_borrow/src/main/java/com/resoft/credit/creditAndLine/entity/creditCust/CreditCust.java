package com.resoft.credit.creditAndLine.entity.creditCust;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.resoft.credit.custinfo.entity.CustInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 个人征信列表Entity
 * @author wuxi01
 * @version 2016-03-17
 */
public class CreditCust extends DataEntity<CreditCust> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String roleType;		// 角色类型(主借人，共借人，担保人，配偶)
	private CustInfo custInfo;		//客户对象
	private String custId;		// 客户ID
	private String custName;		// 名称
	private String idType;		// 证件类型
	private String idNum;		// 证件号
	private Date printingDate;		// 打印日期
	private Integer queryNumYear;		// 本人年内查询次数
	private Integer queryNumSeason;		// 本人季内查询次数
	private Integer queryNumMonth;		// 本人月内查询次数
	private String queryReportDateMinus;		// 查询时间与报告时间间隔
	private String creditMonths;		// 征信信用历史月数
	private String mobileNo;		// 征信中手机号码
	private String companyPhoneNo;		// 征信中单位号码
	private Integer allCreditCardNum;		// 信用卡总数量
	private Integer overdueCardNum;		// 逾期卡数
	private Integer unusedCardNum;		// 未使用卡数量（不含美元账户）
	private Integer abnormalCardNum;		// 异常卡数量
	private BigDecimal allCreditAmount;		// 总授信额度
	private BigDecimal allUsedAmount;		// 总使用额度
	private BigDecimal allBalanceAmount;		// 总余额
	private BigDecimal allOverdueAmount;		// 逾期总额
	private String usedCreditCardRate;		// 信用卡使用占比
	private BigDecimal maxCreditAmount;		// 最高授信额度
	private BigDecimal maxUsedAmount;		// 最高使用额度
	private Integer continueOverdueNum;		// 连续逾期次数
	private Integer allOverdueNum;		// 累计逾期次数
	private String creditCardStatus;		// 信用卡状态
	private BigDecimal sumLoanAmount;		// 贷款总额
	private BigDecimal sumBalanceAmount;		// 贷款余额
	private BigDecimal sumOverdueAmount;		// 贷款逾期总额
	private String isWhite; //是否白户
	private Integer mortgageHouseNum;//房贷笔数
	private Integer bankLegalNum; //发卡法人机构数
	private Integer debitCardsLimit;//贷记卡逾期月份数
	private Date firstDebitCardsMonth;//首张贷记卡发卡月份
	private BigDecimal recentSixMonthLines;//最近6个月平均使用额度
	private Integer debitLongLimit;//贷记卡最长逾期月数
	private Integer personHouseLoadNum;//个人住房贷款笔数
	private Integer personCommerHouseLoan;//个人商用（包括商住两用）房贷款笔数
	private Integer otherLoanNum;//其他贷款笔数
	private Integer loanLegalOrg;//贷款法人机构数
	private Date firstLoanMonth;//首笔贷款发放月份
	private Integer loanLimitMonth;//贷款逾期月份数
	private Integer loanLongLimitMonth;//贷款最长逾期月份数
	private BigDecimal recentSixMonthMoney;//最近6个月平均还款金额
	private Integer guaranteeNum;//担保笔数
	private BigDecimal guaranteeCapitalBal;//担保本金余额
	public CreditCust() {
		super();
	}

	public CreditCust(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=4, message="角色类型(主借人，共借人，担保人，配偶)长度必须介于 0 和 4 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Length(min=1, max=32, message="客户ID长度必须介于 1 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=20, message="名称长度必须介于 0 和 20 之间")
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Length(min=0, max=10, message="证件类型长度必须介于 0 和 10 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=18, message="证件号长度必须介于 0 和 18 之间")
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	public Integer getQueryNumYear() {
		return queryNumYear;
	}

	public void setQueryNumYear(Integer queryNumYear) {
		this.queryNumYear = queryNumYear;
	}
	
	public Integer getQueryNumSeason() {
		return queryNumSeason;
	}

	public void setQueryNumSeason(Integer queryNumSeason) {
		this.queryNumSeason = queryNumSeason;
	}
	
	public Integer getQueryNumMonth() {
		return queryNumMonth;
	}

	public void setQueryNumMonth(Integer queryNumMonth) {
		this.queryNumMonth = queryNumMonth;
	}
	
	@Length(min=0, max=10, message="查询时间与报告时间间隔长度必须介于 0 和 10 之间")
	public String getQueryReportDateMinus() {
		return queryReportDateMinus;
	}

	public void setQueryReportDateMinus(String queryReportDateMinus) {
		this.queryReportDateMinus = queryReportDateMinus;
	}
	
	@Length(min=0, max=10, message="征信信用历史月数长度必须介于 0 和 10 之间")
	public String getCreditMonths() {
		return creditMonths;
	}

	public void setCreditMonths(String creditMonths) {
		this.creditMonths = creditMonths;
	}
	
	@Length(min=0, max=15, message="征信中手机号码长度必须介于 0 和 15 之间")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@Length(min=0, max=15, message="征信中单位号码长度必须介于 0 和 15 之间")
	public String getCompanyPhoneNo() {
		return companyPhoneNo;
	}

	public void setCompanyPhoneNo(String companyPhoneNo) {
		this.companyPhoneNo = companyPhoneNo;
	}
	
	public Integer getAllCreditCardNum() {
		return allCreditCardNum;
	}

	public void setAllCreditCardNum(Integer allCreditCardNum) {
		this.allCreditCardNum = allCreditCardNum;
	}
	
	public Integer getOverdueCardNum() {
		return overdueCardNum;
	}

	public void setOverdueCardNum(Integer overdueCardNum) {
		this.overdueCardNum = overdueCardNum;
	}
	
	public Integer getUnusedCardNum() {
		return unusedCardNum;
	}

	public void setUnusedCardNum(Integer unusedCardNum) {
		this.unusedCardNum = unusedCardNum;
	}
	
	public Integer getAbnormalCardNum() {
		return abnormalCardNum;
	}

	public void setAbnormalCardNum(Integer abnormalCardNum) {
		this.abnormalCardNum = abnormalCardNum;
	}

	public CustInfo getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
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

	public Integer getContinueOverdueNum() {
		return continueOverdueNum;
	}

	public void setContinueOverdueNum(Integer continueOverdueNum) {
		this.continueOverdueNum = continueOverdueNum;
	}

	public Integer getAllOverdueNum() {
		return allOverdueNum;
	}

	public void setAllOverdueNum(Integer allOverdueNum) {
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

	public Integer getMortgageHouseNum() {
		return mortgageHouseNum;
	}

	public void setMortgageHouseNum(Integer mortgageHouseNum) {
		this.mortgageHouseNum = mortgageHouseNum;
	}

	public Integer getBankLegalNum() {
		return bankLegalNum;
	}

	public void setBankLegalNum(Integer bankLegalNum) {
		this.bankLegalNum = bankLegalNum;
	}

	public Integer getDebitCardsLimit() {
		return debitCardsLimit;
	}

	public void setDebitCardsLimit(Integer debitCardsLimit) {
		this.debitCardsLimit = debitCardsLimit;
	}

	

	public Date getFirstDebitCardsMonth() {
		return firstDebitCardsMonth;
	}

	public void setFirstDebitCardsMonth(Date firstDebitCardsMonth) {
		this.firstDebitCardsMonth = firstDebitCardsMonth;
	}

	public void setFirstLoanMonth(Date firstLoanMonth) {
		this.firstLoanMonth = firstLoanMonth;
	}

	public BigDecimal getRecentSixMonthLines() {
		return recentSixMonthLines;
	}

	public void setRecentSixMonthLines(BigDecimal recentSixMonthLines) {
		this.recentSixMonthLines = recentSixMonthLines;
	}

	public Integer getDebitLongLimit() {
		return debitLongLimit;
	}

	public void setDebitLongLimit(Integer debitLongLimit) {
		this.debitLongLimit = debitLongLimit;
	}

	public Integer getPersonHouseLoadNum() {
		return personHouseLoadNum;
	}

	public void setPersonHouseLoadNum(Integer personHouseLoadNum) {
		this.personHouseLoadNum = personHouseLoadNum;
	}

	public Integer getPersonCommerHouseLoan() {
		return personCommerHouseLoan;
	}

	public void setPersonCommerHouseLoan(Integer personCommerHouseLoan) {
		this.personCommerHouseLoan = personCommerHouseLoan;
	}

	public Integer getOtherLoanNum() {
		return otherLoanNum;
	}

	public void setOtherLoanNum(Integer otherLoanNum) {
		this.otherLoanNum = otherLoanNum;
	}

	public Integer getLoanLegalOrg() {
		return loanLegalOrg;
	}

	public void setLoanLegalOrg(Integer loanLegalOrg) {
		this.loanLegalOrg = loanLegalOrg;
	}

	public Integer getLoanLimitMonth() {
		return loanLimitMonth;
	}

	public void setLoanLimitMonth(Integer loanLimitMonth) {
		this.loanLimitMonth = loanLimitMonth;
	}

	public Integer getLoanLongLimitMonth() {
		return loanLongLimitMonth;
	}

	public void setLoanLongLimitMonth(Integer loanLongLimitMonth) {
		this.loanLongLimitMonth = loanLongLimitMonth;
	}

	public BigDecimal getRecentSixMonthMoney() {
		return recentSixMonthMoney;
	}

	public void setRecentSixMonthMoney(BigDecimal recentSixMonthMoney) {
		this.recentSixMonthMoney = recentSixMonthMoney;
	}

	public Integer getGuaranteeNum() {
		return guaranteeNum;
	}

	public void setGuaranteeNum(Integer guaranteeNum) {
		this.guaranteeNum = guaranteeNum;
	}

	public BigDecimal getGuaranteeCapitalBal() {
		return guaranteeCapitalBal;
	}

	public void setGuaranteeCapitalBal(BigDecimal guaranteeCapitalBal) {
		this.guaranteeCapitalBal = guaranteeCapitalBal;
	}

	public Date getFirstLoanMonth() {
		return firstLoanMonth;
	}

	
	
	
}