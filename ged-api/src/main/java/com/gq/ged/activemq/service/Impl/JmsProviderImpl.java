package com.gq.ged.activemq.service.Impl;

import com.gq.ged.activemq.service.JmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

;

/**
 * Created by gickeuy on 2018/1/9.
 */
@Service
public class JmsProviderImpl implements JmsProvider {
  @Autowired
  private JmsTemplate jmsTemplate;

  @Override
  public void sendMessage(String destination, String message) {
    this.jmsTemplate.convertAndSend(destination, message);
  }

  @Override
  public void sendMessage(String destination, Object messageObj){
    this.jmsTemplate.convertAndSend(destination, messageObj);
  }

}
