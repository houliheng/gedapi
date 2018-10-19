package com.resoft.credit.processSuggestionInfo.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 流程意见Entity
 * @author wuxi01
 * @version 2016-03-02
 */
public class ProcessSuggestionInfo extends DataEntity<ProcessSuggestionInfo> {
	
	private static final long serialVersionUID = 1L;
	private String taskDefKey;		// 流程ID
	private String applyNo;		// 申请编号
	private String isAbnormal;		// 是否异常
	private String isAbnormal2;		// 是否异常2（电核）
	private String suggestionDesc;		// 审批意见
	private String reserveTime;		// 预留时间（预约时间）
	private String analysisDesc ;// 分析意见  ANALYSIS_DESC
	private String creditDesc;//信审意见书意见（各级综合意见）
	
	//非表字段，用于标识是否通过的状态
	private String passFlag;
	
	//非表字段，用于标识是保存还是提交
	private String sta;
	
	
	public String getAnalysisDesc() {
		return analysisDesc;
	}

	public void setAnalysisDesc(String analysisDesc) {
		this.analysisDesc = analysisDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ProcessSuggestionInfo() {
		super();
	}

	public ProcessSuggestionInfo(String id){
		super(id);
	}

	
	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@Length(min=0, max=1, message="是否异常长度必须介于 0 和 1 之间")
	public String getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}
	
	@Length(min=0, max=1, message="是否异常2（电核）长度必须介于 0 和 1 之间")
	public String getIsAbnormal2() {
		return isAbnormal2;
	}

	public void setIsAbnormal2(String isAbnormal2) {
		this.isAbnormal2 = isAbnormal2;
	}
	
	@Length(min=0, max=1000, message="审批意见长度必须介于 0 和 1000 之间")
	public String getSuggestionDesc() {
		return suggestionDesc;
	}

	public void setSuggestionDesc(String suggestionDesc) {
		this.suggestionDesc = suggestionDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getSta() {
		return sta;
	}

	public void setSta(String sta) {
		this.sta = sta;
	}

	public String getCreditDesc() {
		return creditDesc;
	}

	public void setCreditDesc(String creditDesc) {
		this.creditDesc = creditDesc;
	}
	
}