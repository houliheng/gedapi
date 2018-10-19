package com.resoft.outinterface.cn.com.experian.entry.request;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.resoft.outinterface.cn.com.experian.entry.request.body.RequestInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.head.ExperianRequestHeader;


@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
public class CallGqRequest {
	@XmlElement(name = "Header", required = true)
    protected ExperianRequestHeader head;
	@XmlElement(name = "Reqinfo", required = true)
    protected RequestInfo body;
	public ExperianRequestHeader getHead() {
		return head;
	}
	public void setHead(ExperianRequestHeader head) {
		this.head = head;
	}
	public RequestInfo getBody() {
		return body;
	}
	public void setBody(RequestInfo body) {
		this.body = body;
	}
}
