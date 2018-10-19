package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class CreditAdjunt {
	private String adjunct_Type;
	private String adjunct_path;
	public String getAdjunct_Type() {
		return adjunct_Type;
	}
	public void setAdjunct_Type(String adjunct_Type) {
		this.adjunct_Type = adjunct_Type;
	}
	public String getAdjunct_path() {
		return adjunct_path;
	}
	public void setAdjunct_path(String adjunct_path) {
		this.adjunct_path = adjunct_path;
	}
	protected String getAdjunctType() {
		return adjunct_Type;
	}
	protected void setAdjunctType(String adjunct_Type) {
		this.adjunct_Type = adjunct_Type;
	}
	protected String getAdjunctPath() {
		return adjunct_path;
	}
	protected void setAdjunctPath(String adjunct_path) {
		this.adjunct_path = adjunct_path;
	}
	
}
