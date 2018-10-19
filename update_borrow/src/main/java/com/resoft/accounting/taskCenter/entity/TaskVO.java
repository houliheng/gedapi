package com.resoft.accounting.taskCenter.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.CustomOffice;

public class TaskVO extends DataEntity<TaskVO> {

	private static final long serialVersionUID = 1L;
	private String proDefKey;		//流程key
	private String processName;		//流程名称
	private String taskDefKey;     
	private String taskName;		//任务名称
	private String custName;		//客户名称
	private String registOrg;		//登记门店
	private String areaName;		//区域			
	private String largeAreaName;	//大区
	private Date createStartTime;	//创建开始时间
	private Date createEndTime;		//创建结束时间
	private Date applyStartTime;	//申请开始时间
	private Date applyEndTime;		//申请结束时间
	private String dataScope;		//数据范围
	private String orgLevel;		//数据范围机构层级
	private CustomOffice office;
	
	
	
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getProDefKey() {
		return proDefKey;
	}
	public void setProDefKey(String proDefKey) {
		this.proDefKey = proDefKey;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getRegistOrg() {
		return registOrg;
	}
	public void setRegistOrg(String registOrg) {
		this.registOrg = registOrg;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getLargeAreaName() {
		return largeAreaName;
	}
	public void setLargeAreaName(String largeAreaName) {
		this.largeAreaName = largeAreaName;
	}
	public String getDataScope() {
		return dataScope;
	}
	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}
	public Date getCreateStartTime() {
		return createStartTime;
	}
	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}
	public Date getCreateEndTime() {
		return createEndTime;
	}
	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public Date getApplyStartTime() {
		return applyStartTime;
	}
	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}
	public Date getApplyEndTime() {
		return applyEndTime;
	}
	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	public CustomOffice getOffice() {
		return office;
	}
	public void setOffice(CustomOffice office) {
		this.office = office;
	}
	
}
