package com.resoft.outinterface.cn.com.experian.entry.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Resinfo {
	@XmlElement(name = "AppDataCheckDecisionCategory", required = true)
	private String appDataCheckDecisionCategory;
	@XmlElement(name = "AppDataCheckDecisionTable", required = true)
	private String appDataCheckDecisionTable;
	@XmlElement(name = "AppDataCheckDecisionText", required = true)
	private String appDataCheckDecisionText;
	@XmlElement(name = "AppDataCheckReasonCode", required = true)
	private String appDataCheckReasonCode;
	@XmlElement(name = "AppDataCheckRulesName", required = true)
	private String appDataCheckRulesName;
	@XmlElement(name = "CreditPolicyCheckDecisionCategory", required = true)
	private String creditPolicyCheckDecisionCategory;
	@XmlElement(name = "CreditPolicyCheckDecisionTable", required = true)
	private String creditPolicyCheckDecisionTable;
	@XmlElement(name = "CreditPolicyCheckDecisionText", required = true)
	private String creditPolicyCheckDecisionText;
	@XmlElement(name = "CreditPolicyCheckReasonCode", required = true)
	private String creditPolicyCheckReasonCode;
	@XmlElement(name = "CreditPolicyCheckRulesName", required = true)
	private String creditPolicyCheckRulesName;
	@XmlElement(name = "ScoringIndex", required = true)
	private String scoringIndex;
	@XmlElement(name = "ScoringRAReasonCodeTable", required = true)
	private String scoringRAReasonCodeTable;
	@XmlElement(name = "ScoringRSDeviationTable", required = true)
	private String scoringRSDeviationTable;
	@XmlElement(name = "ScoringRiskGrade", required = true)
	private String scoringRiskGrade;
	@XmlElement(name = "ScoringScore", required = true)
	private String scoringScore;
	@XmlElement(name = "ScoringScorecardID", required = true)
	private String scoringScorecardID;
	@XmlElement(name = "ScoringScorecardName", required = true)
	private String scoringScorecardName;
	@XmlElement(name = "ScoringScorecardResultsTable", required = true)
	private String scoringScorecardResultsTable;
	
	private String headId;
	public String getAppDataCheckDecisionCategory() {
		return appDataCheckDecisionCategory;
	}
	public void setAppDataCheckDecisionCategory(String appDataCheckDecisionCategory) {
		this.appDataCheckDecisionCategory = appDataCheckDecisionCategory;
	}
	public String getAppDataCheckDecisionTable() {
		return appDataCheckDecisionTable;
	}
	public void setAppDataCheckDecisionTable(String appDataCheckDecisionTable) {
		this.appDataCheckDecisionTable = appDataCheckDecisionTable;
	}
	public String getAppDataCheckDecisionText() {
		return appDataCheckDecisionText;
	}
	public void setAppDataCheckDecisionText(String appDataCheckDecisionText) {
		this.appDataCheckDecisionText = appDataCheckDecisionText;
	}
	public String getAppDataCheckReasonCode() {
		return appDataCheckReasonCode;
	}
	public void setAppDataCheckReasonCode(String appDataCheckReasonCode) {
		this.appDataCheckReasonCode = appDataCheckReasonCode;
	}
	public String getAppDataCheckRulesName() {
		return appDataCheckRulesName;
	}
	public void setAppDataCheckRulesName(String appDataCheckRulesName) {
		this.appDataCheckRulesName = appDataCheckRulesName;
	}
	public String getCreditPolicyCheckDecisionCategory() {
		return creditPolicyCheckDecisionCategory;
	}
	public void setCreditPolicyCheckDecisionCategory(
			String creditPolicyCheckDecisionCategory) {
		this.creditPolicyCheckDecisionCategory = creditPolicyCheckDecisionCategory;
	}
	public String getCreditPolicyCheckDecisionTable() {
		return creditPolicyCheckDecisionTable;
	}
	public void setCreditPolicyCheckDecisionTable(
			String creditPolicyCheckDecisionTable) {
		this.creditPolicyCheckDecisionTable = creditPolicyCheckDecisionTable;
	}
	public String getCreditPolicyCheckDecisionText() {
		return creditPolicyCheckDecisionText;
	}
	public void setCreditPolicyCheckDecisionText(
			String creditPolicyCheckDecisionText) {
		this.creditPolicyCheckDecisionText = creditPolicyCheckDecisionText;
	}
	public String getCreditPolicyCheckReasonCode() {
		return creditPolicyCheckReasonCode;
	}
	public void setCreditPolicyCheckReasonCode(String creditPolicyCheckReasonCode) {
		this.creditPolicyCheckReasonCode = creditPolicyCheckReasonCode;
	}
	public String getCreditPolicyCheckRulesName() {
		return creditPolicyCheckRulesName;
	}
	public void setCreditPolicyCheckRulesName(String creditPolicyCheckRulesName) {
		this.creditPolicyCheckRulesName = creditPolicyCheckRulesName;
	}
	public String getScoringIndex() {
		return scoringIndex;
	}
	public void setScoringIndex(String scoringIndex) {
		this.scoringIndex = scoringIndex;
	}
	public String getScoringRAReasonCodeTable() {
		return scoringRAReasonCodeTable;
	}
	public void setScoringRAReasonCodeTable(String scoringRAReasonCodeTable) {
		this.scoringRAReasonCodeTable = scoringRAReasonCodeTable;
	}
	public String getScoringRSDeviationTable() {
		return scoringRSDeviationTable;
	}
	public void setScoringRSDeviationTable(String scoringRSDeviationTable) {
		this.scoringRSDeviationTable = scoringRSDeviationTable;
	}
	public String getScoringRiskGrade() {
		return scoringRiskGrade;
	}
	public void setScoringRiskGrade(String scoringRiskGrade) {
		this.scoringRiskGrade = scoringRiskGrade;
	}
	public String getScoringScore() {
		return scoringScore;
	}
	public void setScoringScore(String scoringScore) {
		this.scoringScore = scoringScore;
	}
	public String getScoringScorecardID() {
		return scoringScorecardID;
	}
	public void setScoringScorecardID(String scoringScorecardID) {
		this.scoringScorecardID = scoringScorecardID;
	}
	public String getScoringScorecardName() {
		return scoringScorecardName;
	}
	public void setScoringScorecardName(String scoringScorecardName) {
		this.scoringScorecardName = scoringScorecardName;
	}
	public String getScoringScorecardResultsTable() {
		return scoringScorecardResultsTable;
	}
	public void setScoringScorecardResultsTable(String scoringScorecardResultsTable) {
		this.scoringScorecardResultsTable = scoringScorecardResultsTable;
	}
	@Override
	public String toString() {
		return "Resinfo [appDataCheckDecisionCategory="
				+ appDataCheckDecisionCategory + ", appDataCheckDecisionTable="
				+ appDataCheckDecisionTable + ", appDataCheckDecisionText="
				+ appDataCheckDecisionText + ", appDataCheckReasonCode="
				+ appDataCheckReasonCode + ", appDataCheckRulesName="
				+ appDataCheckRulesName
				+ ", creditPolicyCheckDecisionCategory="
				+ creditPolicyCheckDecisionCategory
				+ ", creditPolicyCheckDecisionTable="
				+ creditPolicyCheckDecisionTable
				+ ", creditPolicyCheckDecisionText="
				+ creditPolicyCheckDecisionText
				+ ", creditPolicyCheckReasonCode="
				+ creditPolicyCheckReasonCode + ", creditPolicyCheckRulesName="
				+ creditPolicyCheckRulesName + ", scoringIndex=" + scoringIndex
				+ ", scoringRAReasonCodeTable=" + scoringRAReasonCodeTable
				+ ", scoringRSDeviationTable=" + scoringRSDeviationTable
				+ ", scoringRiskGrade=" + scoringRiskGrade + ", scoringScore="
				+ scoringScore + ", scoringScorecardID=" + scoringScorecardID
				+ ", scoringScorecardName=" + scoringScorecardName
				+ ", scoringScorecardResultsTable="
				+ scoringScorecardResultsTable + "]";
	}
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	
}
