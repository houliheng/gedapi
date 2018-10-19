package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * 其他
 */
@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class CreditOther {
	private int ple_type;
	private String ple_desc;

	public int getPle_type() {
		return ple_type;
	}

	public void setPle_type(int ple_type) {
		this.ple_type = ple_type;
	}

	public String getPle_desc() {
		return ple_desc;
	}

	public void setPle_desc(String ple_desc) {
		this.ple_desc = ple_desc;
	}

	protected int getPleType() {
		return ple_type;
	}

	protected void setPleType(int pleType) {
		this.ple_type = pleType;
	}

	protected String getPleDesc() {
		return ple_desc;
	}

	protected void setPleDesc(String pleDesc) {
		this.ple_desc = pleDesc;
	}

}
