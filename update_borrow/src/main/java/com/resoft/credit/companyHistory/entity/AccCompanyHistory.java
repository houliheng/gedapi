package com.resoft.credit.companyHistory.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 企业开户历史表Entity
 * @author weiruihong
 * @version 2018-06-26
 */
public class AccCompanyHistory extends DataEntity<AccCompanyHistory> {
	
	private static final long serialVersionUID = 1L;
	private String status;		// status
	private Date createTime;		// 操作时间
	private String userName;		// 操作人
	private String comment;		// comment
	private String accountcompanyId;		// accountcompany_id
	private String operate;		// 操作
	
	public AccCompanyHistory() {
		super();
	}

	public AccCompanyHistory(String id){
		super(id);
	}

	@Length(min=0, max=16, message="status长度必须介于 0 和 16 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=32, message="操作人长度必须介于 0 和 32 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=100, message="comment长度必须介于 0 和 100 之间")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Length(min=0, max=32, message="accountcompany_id长度必须介于 0 和 32 之间")
	public String getAccountcompanyId() {
		return accountcompanyId;
	}

	public void setAccountcompanyId(String accountcompanyId) {
		this.accountcompanyId = accountcompanyId;
	}
	
	@Length(min=0, max=32, message="操作长度必须介于 0 和 32 之间")
	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}
	
}