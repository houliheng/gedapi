package com.resoft.credit.stockAssesseTarget.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 年度考核时点及指标Entity
 * @author jml
 * @version 2017-12-04
 */
public class StockAssesseTarget extends DataEntity<StockAssesseTarget> {
	
	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private Date assesseTime;		// 考核时间
	private String assesseProject;		// 考核项目
	private String assesseContent;		// 考核内容
	private String grade;
	public StockAssesseTarget() {
		super();
	}

	public StockAssesseTarget(String id){
		super(id);
	}

	@Length(min=1, max=50, message="申请编号长度必须介于 1 和 50 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="考核时间不能为空")
	public Date getAssesseTime() {
		return assesseTime;
	}

	public void setAssesseTime(Date assesseTime) {
		this.assesseTime = assesseTime;
	}
	
	@Length(min=1, max=50, message="考核项目长度必须介于 1 和 50 之间")
	public String getAssesseProject() {
		return assesseProject;
	}

	public void setAssesseProject(String assesseProject) {
		this.assesseProject = assesseProject;
	}
	
	@Length(min=1, max=100, message="考核内容长度必须介于 1 和 100 之间")
	public String getAssesseContent() {
		return assesseContent;
	}

	public void setAssesseContent(String assesseContent) {
		this.assesseContent = assesseContent;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}