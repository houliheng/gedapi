package com.resoft.outinterface.rest.newged.entity;

import org.apache.poi.ss.formula.functions.T;

/**
 * ClassName:GedApiReturn
 *  推送冠易贷返回的状态码
 *
 * @Date 2018/5/28 15:50
 * @Author liangbin
 */
public class GedApiReturn<T> {

    private T data;
    private String code;
    private int httpStatus;
    private String path;
    private Long timestamp;
    private String exception;
    private String reason;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
