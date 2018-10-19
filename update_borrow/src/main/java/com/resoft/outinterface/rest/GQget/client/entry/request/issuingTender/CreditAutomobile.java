package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class CreditAutomobile {
	private String model_number;
	private String use_year;
	private String predict_amount;
	private String bazaar_amount;
	protected String getModelNumber() {
		return model_number;
	}
	protected void setModelNumber(String model_number) {
		this.model_number = model_number;
	}
	protected String getUseYear() {
		return use_year;
	}
	protected void setUseYear(String use_year) {
		this.use_year = use_year;
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
	public String getModel_number() {
		return model_number;
	}
	public void setModel_number(String model_number) {
		this.model_number = model_number;
	}
	public String getUse_year() {
		return use_year;
	}
	public void setUse_year(String use_year) {
		this.use_year = use_year;
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
}
