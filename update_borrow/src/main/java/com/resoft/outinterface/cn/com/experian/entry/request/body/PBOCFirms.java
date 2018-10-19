package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PBOC_FirmsInfo")
public class PBOCFirms {
	@XmlElement(name="PBOC_Firm")
	protected List<PBOCFirmInfo> pbocFirmInfo;

	public List<PBOCFirmInfo> getPbocFirmInfo() {
		return pbocFirmInfo;
	}

	public void setPbocFirmInfo(List<PBOCFirmInfo> pbocFirmInfo) {
		this.pbocFirmInfo = pbocFirmInfo;
	}
	
}
