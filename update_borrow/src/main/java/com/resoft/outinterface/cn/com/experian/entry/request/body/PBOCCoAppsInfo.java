package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PBOC_CoAppsInfo")
public class PBOCCoAppsInfo {
	@XmlElement(name="PBOC_CoApp")
	protected List<PBOCCoAppInfo> pbocCoAppInfo;

	public List<PBOCCoAppInfo> getPbocCoAppInfo() {
		return pbocCoAppInfo;
	}

	public void setPbocCoAppInfo(List<PBOCCoAppInfo> pbocCoAppInfo) {
		this.pbocCoAppInfo = pbocCoAppInfo;
	}
}
