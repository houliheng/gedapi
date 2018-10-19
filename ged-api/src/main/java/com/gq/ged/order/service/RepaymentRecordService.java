package com.gq.ged.order.service;

import com.gq.ged.order.dao.model.GedRepaymentRecord;

import java.util.List;

public interface RepaymentRecordService {

    /***
     * 根据订单编号查询
     * @param orderNo
     * @return
     */
    public GedRepaymentRecord getRechargeRecordByOrderNo(String orderNo);

    /**
     * 根据订单编号查询还款充值记录
     * @param orderNo
     * @return
     */
    public List<GedRepaymentRecord> getRepaymentRecordsByOrderNo(String orderNo);

    /**
     * 根据流水号查询还款充值记录
     * @param seqNo
     * @return
     */
    public GedRepaymentRecord getRapaymentRecordBySeqNo(String seqNo);

    /**
     * 根据id更新还款充值记录表
     * @param data
     * @return
     */
    public Integer updateRepaymentRecordById(GedRepaymentRecord data);

    /**
     * 新增还款充值记录表
     * @param data
     * @return
     */
    public Integer insertRepaymentRecordById(GedRepaymentRecord data);


}
