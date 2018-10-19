package com.resoft.accounting.taskCenter.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ActRuTask extends DataEntity<ActRuTask>{
	/**
	 * @reqno:H1511100047
	 * @date-designer:20151111-huangxuecheng
	 * @date-author:20151111-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.描述：act_ru_task实体类，在dao中会根据传入的procInstId查询ActRuTask实体类，封装了参数execId和proDefId
	 * 									         提供流程图、流程轨迹使用
	 */
	//act_ru_task实体类
	private static final long serialVersionUID = -8147440662501920687L;
	private String id;
	private String rev;
	private String executionId;
	private String procInstId;
	private String procDefId;
	private String name;
	private String parentTaskId;
	private String description;
	private String taskDefKey;
	private String owner;
	private String assignee;
	private String delegation;
	private String priority;
	private Date createTime;
	private Date dueDate;
	private String cateGory;
	private String suspensionState;
	private String tenantId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRev() {
		return rev;
	}
	public void setRev(String rev) {
		this.rev = rev;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getDelegation() {
		return delegation;
	}
	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getCateGory() {
		return cateGory;
	}
	public void setCateGory(String cateGory) {
		this.cateGory = cateGory;
	}
	public String getSuspensionState() {
		return suspensionState;
	}
	public void setSuspensionState(String suspensionState) {
		this.suspensionState = suspensionState;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
}
