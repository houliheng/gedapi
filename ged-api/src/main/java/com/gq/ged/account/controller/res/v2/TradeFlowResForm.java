package com.gq.ged.account.controller.res.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @Auther: zhaozb
 * @Date: 2018/7/4 21:10
 * @Description:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeFlowResForm implements Serializable {
  private String cust_id;// 客户ID
  private String str_trade_time;// 交易开始时间
  private String end_trade_time;// 交易结束时间
  private String tradeFilters;// c:充值 w:提现 b;出借 r:回款 o 其他
  private Integer sortBy;// 0正序 1倒叙，默认倒叙
  private Integer pageSize;// 每页显示条数
  private Integer pageNum;// 页码
  private Integer total;// 总条数
  private TradeList tradeList;// 交易列表
  private String remark;// 备用字段

  public String getCust_id() {
    return cust_id;
  }

  public void setCust_id(String cust_id) {
    this.cust_id = cust_id;
  }

  public String getStr_trade_time() {
    return str_trade_time;
  }

  public void setStr_trade_time(String str_trade_time) {
    this.str_trade_time = str_trade_time;
  }

  public String getEnd_trade_time() {
    return end_trade_time;
  }

  public void setEnd_trade_time(String end_trade_time) {
    this.end_trade_time = end_trade_time;
  }

  public String getTradeFilters() {
    return tradeFilters;
  }

  public void setTradeFilters(String tradeFilters) {
    this.tradeFilters = tradeFilters;
  }

  public Integer getSortBy() {
    return sortBy;
  }

  public void setSortBy(Integer sortBy) {
    this.sortBy = sortBy;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageNum() {
    return pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public TradeList getTradeList() {
    return tradeList;
  }

  public void setTradeList(TradeList tradeList) {
    this.tradeList = tradeList;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
