package com.resoft.credit.contract.entity;


import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 *  合同模板上传记录entity
 * 
 */
public class ContractUploadFiles extends DataEntity<ContractUploadFiles> {
	
	private static final long serialVersionUID = 1L;
	private String  fileName;//文件名称
	private String  filePath;//文件路径
	private String  versionNo;//版本号
	private String 	moduleId;//模块ID
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
	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	
}