package com.gq.ged.common.resp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gq.ged.common.Errors;
import com.github.pagehelper.PageInfo;

public class ResponseEntityUtil {

  public static <T> ResponseEntity<T> success(T data) {
    ResponseEntity<T> entity = new ResponseEntity<T>();
    entity.setData(data);
    entity.setCode(String.valueOf(Errors.SUCCESS.code));
    entity.setHttpStatus(HttpStatusCode.OK.value());
    return entity;
  }

  public static <T> ResponseEntity<T> successDefine(T data, String code) {
    ResponseEntity<T> entity = new ResponseEntity<T>();
    entity.setData(data);
    entity.setCode(code);
    entity.setHttpStatus(HttpStatusCode.OK.value());
    return entity;
  }

  public static <T> ResponsePagesEntityUtil<List<?>> successPages(List<?> data) {
    ResponsePagesEntityUtil<List<?>> entity = new ResponsePagesEntityUtil<List<?>>();
    entity.setData(data);
    entity.setCode(String.valueOf(Errors.SUCCESS.code));
    entity.setHttpStatus(HttpStatusCode.OK.value());
    entity.setPage(new PageInfo(data));
    return entity;
  }

  public static <T> ResponsePagesEntityUtil<List<?>> successPageInfo(PageInfo pageInfo) {
    ResponsePagesEntityUtil<List<?>> entity = new ResponsePagesEntityUtil<List<?>>();
    entity.setData(pageInfo.getList());
    entity.setCode(String.valueOf(Errors.SUCCESS.code));
    entity.setHttpStatus(HttpStatusCode.OK.value());
    entity.setPage(pageInfo);
    return entity;
  }

  public static ResponseEntity<Void> success() {
    ResponseEntity<Void> entity = build();
    entity.setCode(String.valueOf(Errors.SUCCESS.code));
    entity.setHttpStatus(HttpStatusCode.OK.value());
    return entity;
  }

  public static ResponseEntity<Void> fail(Integer httpStatus, String code, String message,
      HttpServletRequest request) {
    ResponseEntity<Void> entity = build();
    entity.setCode(code);
    entity.setHttpStatus(httpStatus);
    entity.setException(message);
    if (null != request) {
      entity.setPath(request.getRequestURI());
    }
    return entity;
  }

  public static <T> ResponseEntity<T> fail(Errors error,T data) {
    ResponseEntity<T> entity = new ResponseEntity<T>();
    entity.setCode(String.valueOf(error.code));
    entity.setData(data);
    entity.setException(error.label);
    return entity;
  }

  public static  ResponseEntity fail(Errors error,String message) {
    ResponseEntity entity = new ResponseEntity();
    entity.setCode(String.valueOf(error.code));
    entity.setException(message);
    return entity;
  }

  private static ResponseEntity<Void> build() {
    ResponseEntity<Void> entity = new ResponseEntity<Void>();
    entity.setTimestamp(Long.valueOf(System.currentTimeMillis()));
    return entity;
  }

}
