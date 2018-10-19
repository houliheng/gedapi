package com.resoft.credit.fhRiskControl.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 法海风控信息Entity
 * 
 * @author wangguodong
 * @version 2017-02-17
 */
public class FhRiskControl extends DataEntity<FhRiskControl> {

	private static final long serialVersionUID = 1L;
	private String applyNo; // 申请编号
	private String roleType; // 角色类型(主借人，共借人，担保人，配偶)
	private String custId; // 客户ID
	private String custName; // 客户名称
	private String checkNum; // 核查次数
	private String ktggCount;// 开庭公告条数
	private String ajlcCount;// 案件流程条数
	private String newsCount;// 新闻媒体条数
	private String cpwsCount;// 裁判文书条数
	private String zxggCount;// 执行公告条数
	private String sxggCount;// 失信公告条数
	private String fyggCount;// 法院公告条数
	private String bgtCount;// 曝光台条数
	private String totalCount;// 总条数
	private String reportStatus; // 报告状态
	private String idNum; // 身份证号
	private String filePath;// 文件路径
	private String fileName;//文件名称
	private String realFilePath;//影像服务器路径
	
	public String getFileName() {
		return fileName;
	}

	public String getRealFilePath() {
		return realFilePath;
	}

	public void setRealFilePath(String realFilePath) {
		this.realFilePath = realFilePath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FhRiskControl() {
		super();
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
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

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	public String getKtggCount() {
		return ktggCount;
	}

	public void setKtggCount(String ktggCount) {
		this.ktggCount = ktggCount;
	}

	public String getAjlcCount() {
		return ajlcCount;
	}

	public void setAjlcCount(String ajlcCount) {
		this.ajlcCount = ajlcCount;
	}

	public String getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(String newsCount) {
		this.newsCount = newsCount;
	}

	public String getCpwsCount() {
		return cpwsCount;
	}

	public void setCpwsCount(String cpwsCount) {
		this.cpwsCount = cpwsCount;
	}

	public String getZxggCount() {
		return zxggCount;
	}

	public void setZxggCount(String zxggCount) {
		this.zxggCount = zxggCount;
	}

	public String getSxggCount() {
		return sxggCount;
	}

	public void setSxggCount(String sxggCount) {
		this.sxggCount = sxggCount;
	}

	public String getFyggCount() {
		return fyggCount;
	}

	public void setFyggCount(String fyggCount) {
		this.fyggCount = fyggCount;
	}

	public String getBgtCount() {
		return bgtCount;
	}

	public void setBgtCount(String bgtCount) {
		this.bgtCount = bgtCount;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}