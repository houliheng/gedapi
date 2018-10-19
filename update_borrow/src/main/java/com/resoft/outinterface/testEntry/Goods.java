package com.resoft.outinterface.testEntry;

import java.io.Serializable;

public class Goods implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String desk;
	private String chair;
	public String getDesk() {
		return desk;
	}
	public void setDesk(String desk) {
		this.desk = desk;
	}
	public String getChair() {
		return chair;
	}
	public void setChair(String chair) {
		this.chair = chair;
	}
	@Override
	public String toString() {
		return "Goods [desk=" + desk + ", chair=" + chair + "]";
	}
}
