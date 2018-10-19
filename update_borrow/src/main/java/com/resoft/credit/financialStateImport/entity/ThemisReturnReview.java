package com.resoft.credit.financialStateImport.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * Themis返回评价Entity
 * @author caoyinglong
 * @version 2016-03-14
 */
public class ThemisReturnReview extends DataEntity<ThemisReturnReview> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;			//进件号
	private String companycode;		// 企业编号
	private String reportYearMonth;		// 财报年月
	private String returnOrderCol;		// 指标存储序号
	private String returnIndexCode;		// 返回指标编号
	private String returnIndexName;		// 返回指标名称
	private String review;		// 评语
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public ThemisReturnReview() {
		super();
	}

	public ThemisReturnReview(String id){
		super(id);
	}

	@Length(min=1, max=255, message="企业编号长度必须介于 1 和 255 之间")
	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	
	@Length(min=0, max=255, message="财报年月长度必须介于 0 和 255 之间")
	public String getReportYearMonth() {
		return reportYearMonth;
	}

	public void setReportYearMonth(String reportYearMonth) {
		this.reportYearMonth = reportYearMonth;
	}
	
	@Length(min=0, max=255, message="指标存储序号长度必须介于 0 和 255 之间")
	public String getReturnOrderCol() {
		return returnOrderCol;
	}

	public void setReturnOrderCol(String returnOrderCol) {
		this.returnOrderCol = returnOrderCol;
	}
	
	@Length(min=0, max=255, message="返回指标编号长度必须介于 0 和 255 之间")
	public String getReturnIndexCode() {
		return returnIndexCode;
	}

	public void setReturnIndexCode(String returnIndexCode) {
		this.returnIndexCode = returnIndexCode;
	}
	
	@Length(min=0, max=255, message="返回指标名称长度必须介于 0 和 255 之间")
	public String getReturnIndexName() {
		return returnIndexName;
	}

	public void setReturnIndexName(String returnIndexName) {
		this.returnIndexName = returnIndexName;
	}
	
	@Length(min=0, max=255, message="评语长度必须介于 0 和 255 之间")
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
}