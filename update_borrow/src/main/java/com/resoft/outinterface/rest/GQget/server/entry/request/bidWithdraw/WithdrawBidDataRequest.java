package com.resoft.outinterface.rest.GQget.server.entry.request.bidWithdraw;

public class WithdrawBidDataRequest {
	private String contract_id;
	private String contract_no;
	private String deposit_status;
	private String deposit_amt;
	private String acc_no;
	private String deposit_time;
	
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getDeposit_status() {
		return deposit_status;
	}
	public void setDeposit_status(String deposit_status) {
		this.deposit_status = deposit_status;
	}
	public String getDeposit_amt() {
		return deposit_amt;
	}
	public void setDeposit_amt(String deposit_amt) {
		this.deposit_amt = deposit_amt;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getDeposit_time() {
		return deposit_time;
	}
	public void setDeposit_time(String deposit_time) {
		this.deposit_time = deposit_time;
	}
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	@Override
	public String toString() {
		return "WithdrawBidDataRequest [contract_id=" + contract_id
				+ ", contract_no=" + contract_no + ", deposit_status="
				+ deposit_status + ", deposit_amt=" + deposit_amt + ", acc_no="
				+ acc_no + ", deposit_time=" + deposit_time + "]";
	}
}
