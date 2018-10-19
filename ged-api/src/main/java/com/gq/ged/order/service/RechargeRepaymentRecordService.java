package com.gq.ged.order.service;

import com.gq.ged.order.dao.model.GedRepaymentRecord;

/**
 * Created by wyq_tomorrow on 2018/7/27.
 */
public interface RechargeRepaymentRecordService {

  /**
   * 根据资金流水号获得还款流水记录
   * 
   * @param streamNo
   * @return
   */
  GedRepaymentRecord getGedRepaymentRecordByZjStreamNo(String streamNo);

  /**
   * 插入或更新还款记录表
   * 
   * @param recorde
   */
  void insertOrUpdate(GedRepaymentRecord recorde);
}
