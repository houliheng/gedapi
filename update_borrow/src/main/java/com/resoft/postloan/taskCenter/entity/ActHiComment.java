package com.resoft.postloan.taskCenter.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @author huangxuecheng ACT_HI_COMMENT实体类
 *
 */
public class ActHiComment extends DataEntity<ActHiComment>{
	/**
	 * 
	 */
	/**
	 * @reqno:H1511100047
	 * @date-designer:20151117-huangxuecheng
	 * @date-author:20151117-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹.开发说明：建立ACT_HI_COMMENT表实体类废弃时插入comment					        
	 */
	private static final long serialVersionUID = 2426820997086830478L;
	private String id;
	private String type;
	private Date time;
	private String userId;
	private String taskId;
	private String procInstId;
	private String action;
	private String message;
	private String fullMsg;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFullMsg() {
		return fullMsg;
	}
	public void setFullMsg(String fullMsg) {
		this.fullMsg = fullMsg;
	}
	
}
