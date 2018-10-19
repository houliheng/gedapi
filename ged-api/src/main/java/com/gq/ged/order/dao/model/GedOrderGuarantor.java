package com.gq.ged.order.dao.model;

import java.util.Date;

public class GedOrderGuarantor {
    private Long id;

    private Long orderId;

    private Integer guarantorType;

    private Long guarantorId;

    private String borrowGuarantorId;

    private String refuseReason;

    private Integer status;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getGuarantorType() {
        return guarantorType;
    }

    public void setGuarantorType(Integer guarantorType) {
        this.guarantorType = guarantorType;
    }

    public Long getGuarantorId() {
        return guarantorId;
    }

    public void setGuarantorId(Long guarantorId) {
        this.guarantorId = guarantorId;
    }

    public String getBorrowGuarantorId() {
        return borrowGuarantorId;
    }

    public void setBorrowGuarantorId(String borrowGuarantorId) {
        this.borrowGuarantorId = borrowGuarantorId == null ? null : borrowGuarantorId.trim();
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason == null ? null : refuseReason.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}