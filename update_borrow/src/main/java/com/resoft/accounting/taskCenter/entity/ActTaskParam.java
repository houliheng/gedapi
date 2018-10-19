package com.resoft.accounting.taskCenter.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 代办事务传递的参数默认参数类
 * @author songmin
 */

public class ActTaskParam implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ActTaskParam.class);
	
	private String applyNo;
	private String taskId;
	private String taskDefKey;
	private String procDefId;
	private String status;//是否已办：0未办理1：已办理
	private String procInstId;
	private String  execId;
	private String title;
	private String canRedo;
    private String periodNum;
    
	public String getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		try {
			title = URLDecoder.decode(title, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("ActTaskParam-title 转码异常["+title+"]");
		}
		this.title = title;
	}
	
	public String getExecId() {
		return execId;
	}
	
	public void setExecId(String execId) {
		this.execId = execId;
	}
	
	public String getProcInstId() {
		return procInstId;
	}
	
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public String getApplyNo() {
		return applyNo;
	}
	
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	
	public String getTaskId() {
		return taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskDefKey() {
		return taskDefKey;
	}
	
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	
	public String getProcDefId() {
		return procDefId;
	}
	
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCanRedo() {
		return canRedo;
	}
	
	public void setCanRedo(String canRedo) {
		this.canRedo = canRedo;
	}
	
}
