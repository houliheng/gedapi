package com.resoft.outinterface.rest.financialPlatform.entry;

import com.thinkgem.jeesite.common.persistence.DataEntity;
 /**
 * @author caoyinglong
 *银行卡变更
 */
public class BankCardChangeRequest extends DataEntity<BankCardChangeRequest> {
	private static final long serialVersionUID = 1L;
	private String mchn;
	private String seq_no;
	private String trade_type;
	private String signature;
	private String bank_id;
	private String city_id;
	private String bank_card;
	private String acc_no;
	private String file_path;
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getBank_card() {
		return bank_card;
	}
	public void setBank_card(String bank_card) {
		this.bank_card = bank_card;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getMchn() {
		return mchn;
	}
	public void setMchn(String mchn) {
		this.mchn = mchn;
	}
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}
	public String getTradeType() {
		return trade_type;
	}
	public void setTradeType(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getBankId() {
		return bank_id;
	}
	public void setBankId(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getCityId() {
		return city_id;
	}
	public void setCityId(String city_id) {
		this.city_id = city_id;
	}
	public String getBankCard() {
		return bank_card;
	}
	public void setBankCard(String bank_card) {
		this.bank_card = bank_card;
	}
	public String getAccNo() {
		return acc_no;
	}
	public void setAccNo(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getFilePath() {
		return file_path;
	}
	public void setFilePath(String file_path) {
		this.file_path = file_path;
	}
	@Override
	public String toString() {
		return "BankCardChangeRequest [mchn=" + mchn + ", seq_no=" + seq_no
				+ ", trade_type=" + trade_type + ", signature=" + signature
				+ ", bank_id=" + bank_id + ", city_id=" + city_id
				+ ", bank_card=" + bank_card + ", acc_no=" + acc_no
				+ ", file_path=" + file_path + "]";
	}
	
}
