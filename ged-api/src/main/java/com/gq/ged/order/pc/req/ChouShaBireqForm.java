package com.gq.ged.order.pc.req;

/**
 * Created by Administrator on 2018/7/24.
 *
 * caonimadeshabimen  duinimenwuyu
 */
public class ChouShaBireqForm {
String orderNo;
String loginName;
String seqNum;
String orderPayType;
String amt;
String issinscd;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getOrderPayType() {
        return orderPayType;
    }

    public void setOrderPayType(String orderPayType) {
        this.orderPayType = orderPayType;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getIssinscd() {
        return issinscd;
    }

    public void setIssinscd(String issinscd) {
        this.issinscd = issinscd;
    }
}
