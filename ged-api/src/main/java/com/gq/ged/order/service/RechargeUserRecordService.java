package com.gq.ged.order.service;

import com.gq.ged.order.dao.model.UserRechargeRecord;

/**
 * Created by wyq_tomorrow on 2018/7/27.
 */
public interface RechargeUserRecordService {

  /**
   * 根据资金流水号获得用户充值记录
   * 
   * @param ssn
   * @return
   */
  UserRechargeRecord getGedUserRechargeRecordOrderNo(String ssn);

  /**
   * 更新用户充值记录
   * 
   * @param rechargeRecord
   */
  void updateUserRechargeRecordById(UserRechargeRecord rechargeRecord);

  /**
   * 插入用户还款计划表
   * 
   * @param rechargeRecord
   */
  void insertUserRechargeRecorde(UserRechargeRecord rechargeRecord);
}
