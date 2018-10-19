package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PBOC_GuAppsInfo")
public class PBOCGuApps {
	@XmlElement(name="PBOC_GuApp")
	protected List<PBOCGuAppInfo> pbocGuAppInfo;

	public List<PBOCGuAppInfo> getPbocGuAppInfo() {
		return pbocGuAppInfo;
	}

	public void setPbocGuAppInfo(List<PBOCGuAppInfo> pbocGuAppInfo) {
		this.pbocGuAppInfo = pbocGuAppInfo;
	}
	
}
