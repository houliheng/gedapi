package com.resoft.activemq.controller;

/**
 * Created by Levi on 2017/8/9.
 */
import com.resoft.activemq.service.ProducerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;



@RestController
@RequestMapping("/activity")
public class ActivityMQController {

  // 队列消息生产者
  @Resource
  private ProducerService producer;


  @RequestMapping(value = "/send", method = RequestMethod.GET)
  public void producer() {
    producer.sendMessage();
  }

}
