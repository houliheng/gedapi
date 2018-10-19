package com.resoft.credit.industry.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 行业分类信息表Entity
 * @author songmin
 * @version 2016-01-06
 */
/**
 * @reqno:H1512210027
 * @date-designer:2016年1月6日-songmin
 * @date-author:2016年1月6日-songmin:行业分类信息表Entity
 */
public class Industry extends DataEntity<Industry> {
	
	private static final long serialVersionUID = 1L;
	private String induCode;		// 国民经济行业编码
	private String induName;		// 国民经济行业名称
	private String parentInduCode;		// 上级国民经济行业编码
	private String induLevel;		// 国民经济行业级别
	private String induOrder;		// 排序号
	private String induDesc;		// 国民经济行业描述
	private String isEdited;		// 是否可编辑
	private Date startTime;		// 有效开始日期
	private Date endTime;		// 有效结束日期
	private Date createTime;		// 时间戳
	
	public Industry() {
		super();
	}

	public Industry(String id){
		super(id);
	}

	@Length(min=0, max=10, message="国民经济行业编码长度必须介于 0 和 10 之间")
	public String getInduCode() {
		return induCode;
	}

	public void setInduCode(String induCode) {
		this.induCode = induCode;
	}
	
	@Length(min=0, max=300, message="国民经济行业名称长度必须介于 0 和 300 之间")
	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}
	
	@Length(min=0, max=500, message="上级国民经济行业编码长度必须介于 0 和 500 之间")
	public String getParentInduCode() {
		return parentInduCode;
	}

	public void setParentInduCode(String parentInduCode) {
		this.parentInduCode = parentInduCode;
	}
	
	@Length(min=0, max=11, message="国民经济行业级别长度必须介于 0 和 11 之间")
	public String getInduLevel() {
		return induLevel;
	}

	public void setInduLevel(String induLevel) {
		this.induLevel = induLevel;
	}
	
	@Length(min=0, max=11, message="排序号长度必须介于 0 和 11 之间")
	public String getInduOrder() {
		return induOrder;
	}

	public void setInduOrder(String induOrder) {
		this.induOrder = induOrder;
	}
	
	@Length(min=0, max=600, message="国民经济行业描述长度必须介于 0 和 600 之间")
	public String getInduDesc() {
		return induDesc;
	}

	public void setInduDesc(String induDesc) {
		this.induDesc = induDesc;
	}
	
	@Length(min=0, max=11, message="是否可编辑长度必须介于 0 和 11 之间")
	public String getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(String isEdited) {
		this.isEdited = isEdited;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}