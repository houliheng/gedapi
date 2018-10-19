package com.gq.ged.activemq.controller;

import com.gq.ged.account.controller.req.AccountBaseReqForm;
import com.gq.ged.activemq.service.JmsProvider;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.user.dao.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wyq_tomorrow on 2018/3/16.
 */
@RestController
@RequestMapping(value = "/api/mq", produces = MediaType.APPLICATION_JSON_VALUE)
public class MqController {

  @Resource
  JmsProvider jmsProvider;

  @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
  public ResponseEntity<Void> sendMessage(HttpServletRequest request) throws Exception {
    jmsProvider.sendMessage("dd","ccc");
    return ResponseEntityUtil.success();
  }
}
