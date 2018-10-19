package com.resoft.credit.interfaceinfo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 接口日志记录Entity
 * @author wanghong
 * @version 2016-10-31
 */
public class InterfaceInfo extends DataEntity<InterfaceInfo> {

	private static final long serialVersionUID = 1L;
	private String applyNo;		// 申请编号
	private String interfaceName;		// 接口名称
	private String message;		// 返回信息
	private Date sendDate;		// 发送时间
	private String json;

	public InterfaceInfo(String applyNo, String interfaceName, String message, Date sendDate){
		this.applyNo = applyNo;
		this.interfaceName = interfaceName;
		this.message = message;
		this.sendDate = sendDate;
	}

	public InterfaceInfo(String applyNo, String interfaceName, String message, Date sendDate, String json){
		this.applyNo = applyNo;
		this.interfaceName = interfaceName;
		this.message = message;
		this.sendDate = sendDate;
		this.json = json;
	}

	public InterfaceInfo() {
		super();
	}

	public InterfaceInfo(String id){
		super(id);
	}


	@Length(min=0, max=32, message="申请编号长度必须介于 0 和 32 之间")
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Length(min=0, max=100, message="接口名称长度必须介于 0 和 100 之间")
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@Length(min=0, max=1000, message="返回信息长度必须介于 0 和 1000 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}