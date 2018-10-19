package com.resoft.credit.applyRegister.entity;

import java.io.Serializable;

public class Organization  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String pId;
	private String open="false";
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
