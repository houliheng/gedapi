package com.resoft.outinterface.cn.com.experian.entry.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
	@XmlRootElement(name="root")
	@XmlAccessorType(XmlAccessType.FIELD)
public class ExperianResponse {
	@XmlElement(name = "Header", required = true)
    protected ExperianResponseHeader head;
	@XmlElement(name = "Resinfo", required = true)
    protected Resinfo body;
	public ExperianResponseHeader getHead() {
		return head;
	}
	public void setHead(ExperianResponseHeader head) {
		this.head = head;
	}
	public Resinfo getBody() {
		return body;
	}
	public void setBody(Resinfo body) {
		this.body = body;
	}
		
}

