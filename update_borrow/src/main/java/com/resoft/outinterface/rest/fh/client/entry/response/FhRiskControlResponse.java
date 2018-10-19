package com.resoft.outinterface.rest.fh.client.entry.response;

public class FhRiskControlResponse {
	private String applyNo;// 申请编号
	private String custId;// 客户Id
	private Integer ktggCount;// 开庭公告条数
	private Integer ajlcCount;// 案件流程条数
	private Integer newsCount;// 新闻媒体条数
	private Integer cpwsCount;// 裁判文书条数
	private Integer zxggCount;// 执行公告条数
	private Integer sxggCount;// 失信公告条数
	private Integer fyggCount;// 法院公告条数
	private Integer bgtCount;// 曝光台条数
	private Integer totalCount;// 总条数
	private String code;// 状态编码 s:成功 e:失败
	private String msg;// 返回信息

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Integer getKtggCount() {
		return ktggCount;
	}

	public void setKtggCount(Integer ktggCount) {
		this.ktggCount = ktggCount;
	}

	public Integer getAjlcCount() {
		return ajlcCount;
	}

	public void setAjlcCount(Integer ajlcCount) {
		this.ajlcCount = ajlcCount;
	}

	public Integer getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(Integer newsCount) {
		this.newsCount = newsCount;
	}

	public Integer getCpwsCount() {
		return cpwsCount;
	}

	public void setCpwsCount(Integer cpwsCount) {
		this.cpwsCount = cpwsCount;
	}

	public Integer getZxggCount() {
		return zxggCount;
	}

	public void setZxggCount(Integer zxggCount) {
		this.zxggCount = zxggCount;
	}

	public Integer getSxggCount() {
		return sxggCount;
	}

	public void setSxggCount(Integer sxggCount) {
		this.sxggCount = sxggCount;
	}

	public Integer getFyggCount() {
		return fyggCount;
	}

	public void setFyggCount(Integer fyggCount) {
		this.fyggCount = fyggCount;
	}

	public Integer getBgtCount() {
		return bgtCount;
	}

	public void setBgtCount(Integer bgtCount) {
		this.bgtCount = bgtCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
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
