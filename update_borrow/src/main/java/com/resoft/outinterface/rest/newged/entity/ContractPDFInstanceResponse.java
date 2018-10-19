package com.resoft.outinterface.rest.newged.entity;

public class ContractPDFInstanceResponse {
	private String title;
	private String url;
	private String type;
	
	
	public ContractPDFInstanceResponse(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}
	
	public ContractPDFInstanceResponse(String title, String url, String type) {
		super();
		this.title = title;
		this.url = url;
		this.type = type;
	}

	public ContractPDFInstanceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
