package com.resoft.Accoutinterface.gqget.server.entity;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class GqgetBody implements Serializable {

	private List<GqgetCustInfo> results;

    public GqgetBody() {
		super();
	}
	public List<GqgetCustInfo> getResults() {
		return results;
	}

	public void setResults(List<GqgetCustInfo> results) {
		this.results = results;
	}

}
