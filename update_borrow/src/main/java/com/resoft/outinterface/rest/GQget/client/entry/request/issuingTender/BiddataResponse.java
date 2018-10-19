package com.resoft.outinterface.rest.GQget.client.entry.request.issuingTender;
/**
 * @description:
 * @Author:jiangmenglun
 * @E-mail:
 * @version:2018年1月16日 下午2:09:38
 */
public class BiddataResponse {
	private String is_success;
	private String resq_code;
	private String resq_msg;
	/*0000：处理成功（is_success为true，其余false）
	2003：参数不能为空  
	3001：提前还款通知失败，合同编号不存在
	3002：提前还款通知失败，标的状态不为还款中 
	3003：提前还款通知失败，当前债权存在还款中的投标记录
	3004：提前还款通知失败，该标的正在还款中
	9999：系统异常*/
	public BiddataResponse() {
		super();
	}
	public BiddataResponse(String is_success, String resq_code, String resq_msg) {
		super();
		this.is_success = is_success;
		this.resq_code = resq_code;
		this.resq_msg = resq_msg;
	}
	public String getIs_success() {
		return is_success;
	}
	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}
	public String getResq_code() {
		return resq_code;
	}
	public void setResq_code(String resq_code) {
		this.resq_code = resq_code;
	}
	public String getResq_msg() {
		return resq_msg;
	}
	public void setResq_msg(String resq_msg) {
		this.resq_msg = resq_msg;
	}
}
