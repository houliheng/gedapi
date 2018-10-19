package com.resoft.credit.reviewedRule.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 借款审核表Entity
 * @author zhaohuakui
 * @version 2016-12-08
 */
public class ReviewedRule extends DataEntity<ReviewedRule> {

	private static final long serialVersionUID = 1L;
	private String reviewedSheet;		// 审核标准
	private String reviewedBook;		// 审核科目
	private String reviewedTarget;		// 审核对象
	private String reviewedTool;		// 审核工具
	private String dataRequied;		// 材料要求
	private String banRule;		// 禁入标准
	private String replaceMeans;		// 替换手段
	private String remark;		// 备注
	private String passFlag;    // 非表字段
	private String applyNo;    // 非表字段
	private String taskDefKey;    // 非表字段
	private String processSequence;    // 非表字段   PROCESS_SEQUENCE
	private String range;


	public ReviewedRule() {
		super();
	}

	public ReviewedRule(String id){
		super(id);
	}

	@Length(min=0, max=3, message="审核标准长度必须介于 0 和 3 之间")
	public String getReviewedSheet() {
		return reviewedSheet;
	}

	public void setReviewedSheet(String reviewedSheet) {
		this.reviewedSheet = reviewedSheet;
	}

	@Length(min=0, max=32, message="审核科目长度必须介于 0 和 32 之间")
	public String getReviewedBook() {
		return reviewedBook;
	}

	public void setReviewedBook(String reviewedBook) {
		this.reviewedBook = reviewedBook;
	}

	@Length(min=0, max=30, message="审核对象长度必须介于 0 和 30 之间")
	public String getReviewedTarget() {
		return reviewedTarget;
	}

	public void setReviewedTarget(String reviewedTarget) {
		this.reviewedTarget = reviewedTarget;
	}

	@Length(min=0, max=32, message="审核工具长度必须介于 0 和 32 之间")
	public String getReviewedTool() {
		return reviewedTool;
	}

	public void setReviewedTool(String reviewedTool) {
		this.reviewedTool = reviewedTool;
	}

	@Length(min=0, max=30, message="材料要求长度必须介于 0 和 30 之间")
	public String getDataRequied() {
		return dataRequied;
	}

	public void setDataRequied(String dataRequied) {
		this.dataRequied = dataRequied;
	}

	@Length(min=0, max=32, message="禁入标准长度必须介于 0 和 32 之间")
	public String getBanRule() {
		return banRule;
	}

	public void setBanRule(String banRule) {
		this.banRule = banRule;
	}

	@Length(min=0, max=32, message="替换手段长度必须介于 0 和 32 之间")
	public String getReplaceMeans() {
		return replaceMeans;
	}

	public void setReplaceMeans(String replaceMeans) {
		this.replaceMeans = replaceMeans;
	}

	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

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

	public String getProcessSequence() {
		return processSequence;
	}

	public void setProcessSequence(String processSequence) {
		this.processSequence = processSequence;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}


}