package com.resoft.outinterface.SV.server.entry.request;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.utils.IdGen;

/**
 * 数据Entity类
 * 
 * @author wuxi01
 * @version 2016-04-22
 */
public abstract class SVBaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;
	protected String applyNo; // 申请编号
	protected Date createDate; // 创建日期
	protected String delFlag; // 删除标记（0：正常；1：删除；2：审核）

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert() {
		setId(IdGen.uuid());
		this.createDate = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	@Length(min = 1, max = 1)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}
