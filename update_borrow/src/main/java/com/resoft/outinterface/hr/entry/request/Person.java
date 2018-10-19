package com.resoft.outinterface.hr.entry.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
	@XmlElement(name="name",required=true)
	private String name;
	@XmlElement(name="jobNo",required=true)
	private String jobNumber;
	@XmlElement(name="phoneNo",required=false)
	private String phoneNo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", jobNumber=" + jobNumber
				+ ", phoneNo=" + phoneNo + "]";
	}
}
