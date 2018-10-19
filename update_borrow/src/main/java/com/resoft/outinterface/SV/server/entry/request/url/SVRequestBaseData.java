package com.resoft.outinterface.SV.server.entry.request.url;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SVRequestBaseData {
	@XmlElement(required=true,name="FILE_NAME")
	private String fileName;
	@XmlElement(required=true,name="FILE_DIR")
	private String fileDir;
	@XmlElement(required=true,name="PACKAGE_PATH")
	private String packagePath;
	@XmlElement(required=true,name="THUMBNAIL_PATH")
	private String thumbnailUrl;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
}
