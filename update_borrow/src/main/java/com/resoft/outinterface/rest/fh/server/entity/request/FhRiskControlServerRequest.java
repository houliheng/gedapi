package com.resoft.outinterface.rest.fh.server.entity.request;

import java.io.Serializable;

public class FhRiskControlServerRequest implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String applyNo;// 申请编号
	private String custId;// 客户Id
	private String fileName;// 报告文件名称
	private String filePath;// 存放路径

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
