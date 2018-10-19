package com.resoft.credit.creditViewBook.entity.CreditViewBook;

import org.hibernate.validator.constraints.Length;

import com.resoft.credit.creditAndLine.entity.creditAnalysis.CreditAnalysis;
import com.resoft.credit.creditAndLine.entity.creditAnalysisExtend.CreditAnalysisExtend;
import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtend.CreditAnalysisMostExtend;
import com.resoft.credit.creditAndLine.entity.creditAnalysisMostExtends.CreditAnalysisMostExtends;
import com.resoft.credit.creditViewBook.entity.creditOtherInfo.CreditOtherInfo;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 征信意见书其他信息Entity
 * @author wuxi01
 * @version 2016-02-29
 */
public class CreditViewBook extends DataEntity<CreditViewBook> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String taskDefKey;		// 流程编号
	
	private String suggestionBranch;		// 分公司意见
	private String suggestionArea;		// 区域公司意见
	private String suggestionLargeArea;		// 大区意见
	private String suggestionHead;		// 总公司意见
	
	private CreditOtherInfo creditOtherInfo;
	
	private CreditAnalysis creditAnalysis;
	
	private CreditAnalysisExtend creditAnalysisExtend;
	
	private CreditAnalysisMostExtend creditAnalysisMostExtend;
	
	private CreditAnalysisMostExtends creditAnalysisMostExtends;
	public CreditViewBook() {
		super();
	}

	public CreditViewBook(String id){
		super(id);
	}

	@Length(min=1, max=32, message="申请编号长度必须介于 1 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getSuggestionBranch() {
		return suggestionBranch;
	}

	public void setSuggestionBranch(String suggestionBranch) {
		this.suggestionBranch = suggestionBranch;
	}

	public String getSuggestionArea() {
		return suggestionArea;
	}

	public void setSuggestionArea(String suggestionArea) {
		this.suggestionArea = suggestionArea;
	}

	public String getSuggestionLargeArea() {
		return suggestionLargeArea;
	}

	public void setSuggestionLargeArea(String suggestionLargeArea) {
		this.suggestionLargeArea = suggestionLargeArea;
	}

	public String getSuggestionHead() {
		return suggestionHead;
	}

	public void setSuggestionHead(String suggestionHead) {
		this.suggestionHead = suggestionHead;
	}

	public CreditOtherInfo getCreditOtherInfo() {
		return creditOtherInfo;
	}

	public void setCreditOtherInfo(CreditOtherInfo creditOtherInfo) {
		this.creditOtherInfo = creditOtherInfo;
	}

	public CreditAnalysis getCreditAnalysis() {
		return creditAnalysis;
	}

	public void setCreditAnalysis(CreditAnalysis creditAnalysis) {
		this.creditAnalysis = creditAnalysis;
	}

	public CreditAnalysisExtend getCreditAnalysisExtend() {
		return creditAnalysisExtend;
	}

	public void setCreditAnalysisExtend(CreditAnalysisExtend creditAnalysisExtend) {
		this.creditAnalysisExtend = creditAnalysisExtend;
	}

	public CreditAnalysisMostExtend getCreditAnalysisMostExtend() {
		return creditAnalysisMostExtend;
	}

	public void setCreditAnalysisMostExtend(CreditAnalysisMostExtend creditAnalysisMostExtend) {
		this.creditAnalysisMostExtend = creditAnalysisMostExtend;
	}

	public CreditAnalysisMostExtends getCreditAnalysisMostExtends() {
		return creditAnalysisMostExtends;
	}

	public void setCreditAnalysisMostExtends(CreditAnalysisMostExtends creditAnalysisMostExtends) {
		this.creditAnalysisMostExtends = creditAnalysisMostExtends;
	}
	
	
    
	
}