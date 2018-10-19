package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmsInfo {
@XmlElement(name="Firm")
protected List<FirmInfo>firmInfo;

public List<FirmInfo> getFirmInfo() {
	return firmInfo;
}

public void setFirmInfo(List<FirmInfo> firmInfo) {
	this.firmInfo = firmInfo;
}

}
