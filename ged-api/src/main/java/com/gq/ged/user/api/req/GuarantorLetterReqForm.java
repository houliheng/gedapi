package com.gq.ged.user.api.req;

import java.io.Serializable;

/**
 * 担保函请求.
 *
 * @author SeppSong
 */
public class GuarantorLetterReqForm implements Serializable {

    private static final long serialVersionUID = 4593725006105748745L;

    /**
     * 订单编号
     */
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
