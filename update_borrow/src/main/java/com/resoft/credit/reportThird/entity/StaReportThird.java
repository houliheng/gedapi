package com.resoft.credit.reportThird.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 统计报表3Entity
 * @author wangguodong
 * @version 2017-03-07
 */
public class StaReportThird extends DataEntity<StaReportThird> {
	
	private static final long serialVersionUID = 1L;
	private String reviewedBook;// REVIEWED_BOOK 审核科目
	private String reviewedTarget;// REVIEWED_TARGET 审核对象
	private String reviewedTool;//REVIEWED_TOOL 审核工具
	private String dataRequied;//DATA_REQUIED 材料要求
	private String banRule;//BAN_RULE 禁入标准
	private String replaceMeans;//REPLACE_MEANS 替换手段
	private String bookCount;//统计数量
	public String getReviewedBook() {
		return reviewedBook;
	}
	public void setReviewedBook(String reviewedBook) {
		this.reviewedBook = reviewedBook;
	}
	public String getReviewedTarget() {
		return reviewedTarget;
	}
	public void setReviewedTarget(String reviewedTarget) {
		this.reviewedTarget = reviewedTarget;
	}
	public String getReviewedTool() {
		return reviewedTool;
	}
	public void setReviewedTool(String reviewedTool) {
		this.reviewedTool = reviewedTool;
	}
	public String getDataRequied() {
		return dataRequied;
	}
	public void setDataRequied(String dataRequied) {
		this.dataRequied = dataRequied;
	}
	public String getBanRule() {
		return banRule;
	}
	public void setBanRule(String banRule) {
		this.banRule = banRule;
	}
	public String getReplaceMeans() {
		return replaceMeans;
	}
	public void setReplaceMeans(String replaceMeans) {
		this.replaceMeans = replaceMeans;
	}
	public String getBookCount() {
		return bookCount;
	}
	public void setBookCount(String bookCount) {
		this.bookCount = bookCount;
	}
	
}