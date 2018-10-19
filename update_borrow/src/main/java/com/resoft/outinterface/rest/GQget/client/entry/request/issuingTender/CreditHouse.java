package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class CreditHouse {
	private String address;
	private String area;
	private String predict_amount;
	private String bazaar_amount;
	private int impawn_type;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	protected String getPredictAmount() {
		return predict_amount;
	}
	protected void setPredictAmount(String predict_amount) {
		this.predict_amount = predict_amount;
	}
	protected String getBazaarAmount() {
		return bazaar_amount;
	}
	protected void setBazaarAmount(String bazaar_amount) {
		this.bazaar_amount = bazaar_amount;
	}
	protected int getImpawnType() {
		return impawn_type;
	}
	protected void setImpawnType(int impawn_type) {
		this.impawn_type = impawn_type;
	}
	public String getPredict_amount() {
		return predict_amount;
	}
	public void setPredict_amount(String predict_amount) {
		this.predict_amount = predict_amount;
	}
	public String getBazaar_amount() {
		return bazaar_amount;
	}
	public void setBazaar_amount(String bazaar_amount) {
		this.bazaar_amount = bazaar_amount;
	}
	public int getImpawn_type() {
		return impawn_type;
	}
	public void setImpawn_type(int impawn_type) {
		this.impawn_type = impawn_type;
	}
}
