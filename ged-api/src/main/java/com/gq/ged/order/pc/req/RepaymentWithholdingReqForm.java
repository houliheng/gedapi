package com.gq.ged.order.pc.req;

import java.io.Serializable;
import java.util.List;

public class RepaymentWithholdingReqForm implements Serializable{
    private String mchn;//商户号
    private String seq_no;//流水号
    private String trade_type;//交易类型
    private String signature;//签名
    private List<RepaymentWithholding> repay_list;//代扣流水集合
    private String resp_code;//直接中划扣返回码
    private String resp_msg;//直接中划扣返回信息

    private String transfer_resp_code;//中间人划扣返回码(代扣)
    private String transfer_resp_msg;//中间人划扣返回信息(代扣)

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<RepaymentWithholding> getRepay_list() {
        return repay_list;
    }

    public void setRepay_list(List<RepaymentWithholding> repay_list) {
        this.repay_list = repay_list;
    }

    public String getResp_code() {
        return resp_code;
    }

    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }

    public String getResp_msg() {
        return resp_msg;
    }

    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }

    public String getTransfer_resp_code() {
        return transfer_resp_code;
    }

    public void setTransfer_resp_code(String transfer_resp_code) {
        this.transfer_resp_code = transfer_resp_code;
    }

    public String getTransfer_resp_msg() {
        return transfer_resp_msg;
    }

    public void setTransfer_resp_msg(String transfer_resp_msg) {
        this.transfer_resp_msg = transfer_resp_msg;
    }
}
