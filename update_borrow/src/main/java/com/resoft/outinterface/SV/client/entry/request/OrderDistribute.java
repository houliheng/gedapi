package com.resoft.outinterface.SV.client.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="orderDistribute")
@XmlType(name="orderDistribute",propOrder={"arg0"})
public class OrderDistribute {
	@XmlElement(name="arg0")
	protected WorkOrderDto arg0;

	public WorkOrderDto getArg0() {
		return arg0;
	}

	public void setArg0(WorkOrderDto arg0) {
		this.arg0 = arg0;
	}
	
}
