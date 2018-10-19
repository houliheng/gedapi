package com.gq.ged.order.service;

import com.gq.ged.account.controller.req.v2.RechargeFeeForm;
import com.gq.ged.order.dao.model.GedRechargeRecord;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Levi on 2018/3/9.
 */
public interface RechargeRecordService {

  public Integer insert(GedRechargeRecord rechargeRecord);

  /***
   * 根据订单编号查询
   *
   * @param orderNo
   * @return
   */
    GedRechargeRecord getRechargeRecordByOrderNo(String orderNo);
    /**
     * 根据流水号查询充值记录
     * @param seqNo
     * @return
     */
    GedRechargeRecord getRechargeRecordBySeqNo(String seqNo);

  /**
   * 根据资金流水号获取交易记录
   *
   * @param streamNo
   * @return
   */
   GedRechargeRecord getRechargeRecordByStreamNo(String streamNo);

   /**
     * 根据提现记录
     * @param rechargeFeeForm
     * @return
     */
    GedRechargeRecord getRechargeRecordByOrderNoAndAmount(RechargeFeeForm rechargeFeeForm);

    /**
     * 根据订单编号查询充值记录
     * @param orderNo
     * @return
     */
    public List<GedRechargeRecord> getGedRechargeRecordsByOrderNo(String orderNo);

  /**
   * 根据id更新
   *
   * @param data
   * @return
   */
  public Integer updateById(GedRechargeRecord data);

    /**
     * 根据客户id查询充值缴费记录
     * @param custId
     * @return
     */
    public GedRechargeRecord getRechargeRecordByCustId(String custId);

    /**
     * 根据流水号查询提现记录
     * @param orderNo
     * @return
     */
    public GedRechargeRecord getWithDrawRecordByOrderNoAndSeqNO(String orderNo, BigDecimal amount, String resp_code);

    public GedRechargeRecord getWithDrawRecordBySeqNo(String seqNo);

}
