package com.gq.ged.order.controller.req;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Created by wrh on 2018/4/19.
 */
@ApiModel
public class PageReqForm implements Serializable {
  private static final long serialVersionUID = 1L;
  private  int pageNum;

  private int pageSize;

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
