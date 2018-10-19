package com.gq.ged.underImport.service.impl;

import com.gq.ged.underImport.service.GedImportGetOrderService;
import com.gq.ged.user.service.impl.AsyncTaskServiceImpl;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by wyq_tomorrow on 2018/7/18.
 */
@Component
@JobHandler(value = "excutePushOrder")
public class ExcutePushOrder extends IJobHandler {
  static Logger logger = LoggerFactory.getLogger(AsyncTaskServiceImpl.class);
  @Resource
  GedImportGetOrderService orderService;

  @Override
  public ReturnT<String> execute(String s) throws Exception {
    logger.info("start调用定时任务excutePushOrder....");
    orderService.dealUnderInfos();
    logger.info("end定时任务excutePushOrder....");
    return ReturnT.SUCCESS;
  }
}
