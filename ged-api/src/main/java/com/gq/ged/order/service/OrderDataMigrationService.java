package com.gq.ged.order.service;

import com.gq.ged.order.controller.req.OrderDataMigrationReqForm;
import com.gq.ged.order.controller.res.ContractSignResult;

import java.util.List;

/**
 * Created by wrh on 2018/5/22.
 */
public interface OrderDataMigrationService {

  /**
   * 数据迁移
   *
   * @param reqForm
   * @return
   */
  void pushDataMigration(OrderDataMigrationReqForm reqForm) throws Exception;

  /**
   * 数据迁移(冠E通)
   *
   * @return
   */
  void dataMigration() throws Exception;

  /**
   * 合同签署
   * @param orderNo
   * @return
   */
  List<ContractSignResult> contractSign(String orderNo) throws  Exception;
}
