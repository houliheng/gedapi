package com.resoft.credit.stockInfo.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 股权尽调业务表Entity
 * @author jml
 * @version 2017-12-04
 */
public class StockInfo extends DataEntity<StockInfo> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String targetCompany;		// 目标公司名称
	private String stockCompany;		// 持股公司名称
	private Double registeredCapital;		// 注册资本(单位：元)
	private Double paidInCapital;		// 实缴资本(单位：元)
	private Double businessValuation;		// 企业估值(单位：元)
	
	private Double stockServiceFee;		// 服务费金额（元）
	private Double additionMoney;		// 拟增资金额(单位：元)
	private Double oneTransferAmount;		// 一元转让金额（元）
	private Double quasiShareRatio;		// 拟占股比例(单位：%)
	private Double increaseProportion;		// 增资占股比例(单位：%)
	private Double oneTransferProportion;		// 一元转让占股比例(单位：%)
	private Double dividendAmount;		// 年度分红金额(单位：元)
	private Double addServiceFee;		// 增值服务费金额(单位：元)
	private String chairmanName;		// 提名董事名字
	private String contractClause;		// 股权端合同风控条款
	private String guaranteeMeasures;		// 担保措施
	private String otherControMeasures;		// 其他风控措施
	private String grade;
	
	
	private String suggestionBranch;		// 分公司意见
	private String branchName;// 分公司员工姓名
	private String branchNo;// 分公司编号
	
	private String suggestionArea;		// 区域公司意见
	private String areaName; // 区域员工姓名
	private String areaNo; // 区域员工编号
	
	private String suggestionLargeArea;		// 大区意见
	private String largeAreaName;    // 大区员工姓名
	private String largeAreaNo;		// 大区员工编号
	
	private String suggestionHead;		// 总公司意见
	private String headName;   // 总公司员工姓名
	private String headNo;     // 总公司员工编号
	
	public StockInfo() {
		super();
	}

	public StockInfo(String id){
		super(id);
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	public String getTargetCompany() {
		return targetCompany;
	}

	public void setTargetCompany(String targetCompany) {
		this.targetCompany = targetCompany;
	}
	
	public String getStockCompany() {
		return stockCompany;
	}

	public void setStockCompany(String stockCompany) {
		this.stockCompany = stockCompany;
	}
	
	public Double getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Double registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	
	public Double getPaidInCapital() {
		return paidInCapital;
	}

	public void setPaidInCapital(Double paidInCapital) {
		this.paidInCapital = paidInCapital;
	}
	
	public Double getBusinessValuation() {
		return businessValuation;
	}

	public void setBusinessValuation(Double businessValuation) {
		this.businessValuation = businessValuation;
	}
	
	public Double getStockServiceFee() {
		return stockServiceFee;
	}

	public void setStockServiceFee(Double stockServiceFee) {
		this.stockServiceFee = stockServiceFee;
	}
	
	public Double getAdditionMoney() {
		return additionMoney;
	}

	public void setAdditionMoney(Double additionMoney) {
		this.additionMoney = additionMoney;
	}
	
	public Double getOneTransferAmount() {
		return oneTransferAmount;
	}

	public void setOneTransferAmount(Double oneTransferAmount) {
		this.oneTransferAmount = oneTransferAmount;
	}
	
	public Double getQuasiShareRatio() {
		return quasiShareRatio;
	}

	public void setQuasiShareRatio(Double quasiShareRatio) {
		this.quasiShareRatio = quasiShareRatio;
	}
	
	public Double getIncreaseProportion() {
		return increaseProportion;
	}

	public void setIncreaseProportion(Double increaseProportion) {
		this.increaseProportion = increaseProportion;
	}
	
	public Double getOneTransferProportion() {
		return oneTransferProportion;
	}

	public void setOneTransferProportion(Double oneTransferProportion) {
		this.oneTransferProportion = oneTransferProportion;
	}
	
	public Double getDividendAmount() {
		return dividendAmount;
	}

	public void setDividendAmount(Double dividendAmount) {
		this.dividendAmount = dividendAmount;
	}
	
	public Double getAddServiceFee() {
		return addServiceFee;
	}

	public void setAddServiceFee(Double addServiceFee) {
		this.addServiceFee = addServiceFee;
	}
	
	public String getChairmanName() {
		return chairmanName;
	}

	public void setChairmanName(String chairmanName) {
		this.chairmanName = chairmanName;
	}
	
	public String getContractClause() {
		return contractClause;
	}

	public void setContractClause(String contractClause) {
		this.contractClause = contractClause;
	}
	
	public String getGuaranteeMeasures() {
		return guaranteeMeasures;
	}

	public void setGuaranteeMeasures(String guaranteeMeasures) {
		this.guaranteeMeasures = guaranteeMeasures;
	}
	
	public String getOtherControMeasures() {
		return otherControMeasures;
	}

	public void setOtherControMeasures(String otherControMeasures) {
		this.otherControMeasures = otherControMeasures;
	}

	public String getSuggestionBranch() {
		return suggestionBranch;
	}

	public void setSuggestionBranch(String suggestionBranch) {
		this.suggestionBranch = suggestionBranch;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getSuggestionArea() {
		return suggestionArea;
	}

	public void setSuggestionArea(String suggestionArea) {
		this.suggestionArea = suggestionArea;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getSuggestionLargeArea() {
		return suggestionLargeArea;
	}

	public void setSuggestionLargeArea(String suggestionLargeArea) {
		this.suggestionLargeArea = suggestionLargeArea;
	}

	public String getLargeAreaName() {
		return largeAreaName;
	}

	public void setLargeAreaName(String largeAreaName) {
		this.largeAreaName = largeAreaName;
	}

	public String getLargeAreaNo() {
		return largeAreaNo;
	}

	public void setLargeAreaNo(String largeAreaNo) {
		this.largeAreaNo = largeAreaNo;
	}

	public String getSuggestionHead() {
		return suggestionHead;
	}

	public void setSuggestionHead(String suggestionHead) {
		this.suggestionHead = suggestionHead;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getHeadNo() {
		return headNo;
	}

	public void setHeadNo(String headNo) {
		this.headNo = headNo;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}