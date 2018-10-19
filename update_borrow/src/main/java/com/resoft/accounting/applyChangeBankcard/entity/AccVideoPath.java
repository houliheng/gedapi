package com.resoft.accounting.applyChangeBankcard.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class AccVideoPath extends DataEntity<AccVideoPath> {
	private static final long serialVersionUID = -2106745811393190866L;
	private String registerDate;
	private String applyNo;
	private String fileDir;
	private String fileName;
	private String fileStoragePath;
	private String thumbnailStoragePath;
	private String createBy1;

	public String getCreateBy1() {
		return createBy1;
	}

	public void setCreateBy1(String createBy1) {
		this.createBy1 = createBy1;
	}

	public String getCreateDate1() {
		return createDate1;
	}

	public void setCreateDate1(String createDate1) {
		this.createDate1 = createDate1;
	}

	private String createDate1;

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileStoragePath() {
		return fileStoragePath;
	}

	public void setFileStoragePath(String fileStoragePath) {
		this.fileStoragePath = fileStoragePath;
	}

	public String getThumbnailStoragePath() {
		return thumbnailStoragePath;
	}

	public void setThumbnailStoragePath(String thumbnailStoragePath) {
		this.thumbnailStoragePath = thumbnailStoragePath;
	}
}
