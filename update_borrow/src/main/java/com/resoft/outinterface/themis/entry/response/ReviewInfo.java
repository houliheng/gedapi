package com.resoft.outinterface.themis.entry.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class ReviewInfo {
	@XmlElement(name="R01")
	private String value1;
	@XmlElement(name="R02")
	private String value2;
	@XmlElement(name="R03")
	private String value3;
	@XmlElement(name="R04")
	private String value4;
	@XmlElement(name="R05")
	private String value5;
	@XmlElement(name="R06")
	private String value6;
	@XmlElement(name="R07")
	private String value7;
	@XmlElement(name="R08")
	private String value8;
	@Override
	public String toString() {
		return "ReviewInfo [value1=" + value1 + ", value2=" + value2
				+ ", value3=" + value3 + ", value4=" + value4 + ", value5="
				+ value5 + ", value6=" + value6 + ", value7=" + value7
				+ ", value8=" + value8 + "]";
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public String getValue6() {
		return value6;
	}
	public void setValue6(String value6) {
		this.value6 = value6;
	}
	public String getValue7() {
		return value7;
	}
	public void setValue7(String value7) {
		this.value7 = value7;
	}
	public String getValue8() {
		return value8;
	}
	public void setValue8(String value8) {
		this.value8 = value8;
	}
	public ReviewInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
}
