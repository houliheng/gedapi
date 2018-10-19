package com.resoft.credit.markNorm.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 股权加减分项Entity
 * 
 * @author zcl
 * @version 2017-10-13
 */
public class CreStockMarkNorm extends DataEntity<CreStockMarkNorm> {

	private static final long serialVersionUID = 1L;
	private String description; // 分数描述
	private String score; // 分数
	private String markType; // 打分类型（1加分，2减分）

	/**
	 * -----------------------------------
	 * 非数据库相关属性
	 */
	private String isChecked; // 1选中0未选中

	public CreStockMarkNorm() {
		super();
	}

	public CreStockMarkNorm(String id) {
		super(id);
	}

	@Length(min = 0, max = 500, message = "分数描述长度必须介于 0 和 500 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Length(min = 0, max = 1, message = "打分类型（1加分，2减分）长度必须介于 0 和 1 之间")
	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

}