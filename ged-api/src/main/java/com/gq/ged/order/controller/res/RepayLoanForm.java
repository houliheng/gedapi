package com.gq.ged.order.controller.res;

import java.util.List;

/**
 * Created by wrh on 2018/3/19.
 */
public class RepayLoanForm {

    private String applyId;
    private String code;
    private List<DeductResult> msg;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DeductResult> getMsg() {
        return msg;
    }

    public void setMsg(List<DeductResult> msg) {
        this.msg = msg;
    }
}
