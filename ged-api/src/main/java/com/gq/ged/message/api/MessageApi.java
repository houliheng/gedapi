package com.gq.ged.message.api;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.annotation.THttpService;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.message.service.MsgService;
import com.gq.ged.message.tmodel.MessageTService;
import com.gq.ged.message.tmodel.MsgTypeEnum;
import org.apache.thrift.TException;

import javax.annotation.Resource;

/**
 * Created by wyq_tomorrow on 2018/1/26.
 */
@THttpService("/api/messageTService")
public class MessageApi implements MessageTService.Iface {

  @Resource
  MsgService msgService;

  @Override
  public String getMessageByType(String mobile, MsgTypeEnum msgType) throws TException {
    msgService.sendCheckCodeMessage(mobile, msgType);
    return JSONObject.toJSONString(ResponseEntityUtil.success());
  }
}
