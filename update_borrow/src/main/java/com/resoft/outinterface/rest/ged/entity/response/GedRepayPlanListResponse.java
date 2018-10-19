package com.resoft.outinterface.rest.ged.entity.response;

import java.util.List;

public class GedRepayPlanListResponse {
	private List<GedRepayPlanResponse> list;
	private String code;//0000 成功
	private String msg;
	public GedRepayPlanListResponse() {
		super();
	}
	public GedRepayPlanListResponse(List<GedRepayPlanResponse> list, String code, String msg) {
		super();
		this.list = list;
		this.code = code;
		this.msg = msg;
	}
	public List<GedRepayPlanResponse> getList() {
		return list;
	}
	public void setList(List<GedRepayPlanResponse> list) {
		this.list = list;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
