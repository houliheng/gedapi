package com.gq.ged.account.controller.req.v2;

import java.io.Serializable;

public class EnterpriseAccountReqForm  implements Serializable {
  private String mchn;// 系统代码（子商户）
  private String seq_no;// 交易流水号
  private String trade_type;// 交易类型
  private EnterpriseAccountReq data;// 数据域
  private String page_url;// 页面通知地址
  private String back_url;// 异步通知地址
  private String signature;// 签名

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

  public String getTrade_type() {
    return trade_type;
  }

  public void setTrade_type(String trade_type) {
    this.trade_type = trade_type;
  }

  public EnterpriseAccountReq getData() {
    return data;
  }

  public void setData(EnterpriseAccountReq data) {
    this.data = data;
  }

  public String getPage_url() {
    return page_url;
  }

  public void setPage_url(String page_url) {
    this.page_url = page_url;
  }

  public String getBack_url() {
    return back_url;
  }

  public void setBack_url(String back_url) {
    this.back_url = back_url;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }
}
