package com.gq.ged.message.service.impl;

import com.gq.ged.message.api.req.MessageHttpReqForm;
import com.gq.ged.message.api.req.SysCodeReqForm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by wyq_tomorrow on 2017/8/23.
 */
public class MsgUtils {
  public static String getMessageJsonStr(String sysCode, String mobile, String tempCode,
      String... params) {
    MessageHttpReqForm reqForm = new MessageHttpReqForm();
    reqForm.setSendType("1");
    reqForm.setPhoneNo(mobile);
    reqForm.setTempCode(tempCode);
    SysCodeReqForm sysCodeReqForm = new SysCodeReqForm();
    sysCodeReqForm.setSysCode(sysCode);
    JSONObject jsonOne = JSONObject.fromObject(sysCodeReqForm);
    JSONObject jsonTwo = JSONObject.fromObject(reqForm);
    int i = 0;
    for (String elem : params) {
        jsonTwo.put(i, elem);
      i++;
    }
    JSONArray jsonArray = new JSONArray();
    jsonArray.add(jsonOne);
    jsonArray.add(jsonTwo);
    return jsonArray.toString();
  }
}
