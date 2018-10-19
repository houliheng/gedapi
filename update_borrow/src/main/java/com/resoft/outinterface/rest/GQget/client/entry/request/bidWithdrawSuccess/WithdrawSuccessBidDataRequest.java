package com.resoft.outinterface.rest.GQget.client.entry.request.bidWithdrawSuccess;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
@JsonAutoDetect(fieldVisibility=Visibility.PUBLIC_ONLY)
public class WithdrawSuccessBidDataRequest {
	private String bid_offline_id;
	private String contract_no;
	protected String getBidOfflineId() {
		return bid_offline_id;
	}
	protected void setBidOfflineId(String bid_offline_id) {
		this.bid_offline_id = bid_offline_id;
	}
	protected String getContractNo() {
		return contract_no;
	}
	protected void setContractNo(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getBid_offline_id() {
		return bid_offline_id;
	}
	public void setBid_offline_id(String bid_offline_id) {
		this.bid_offline_id = bid_offline_id;
	}
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
}
