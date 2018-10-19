package com.gq.ged.config;

import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.Message;
import javax.jms.MessageListener;

public class Listener implements MessageListener {
  public void onMessage(Message message) {
    int i = 8 / 0;// 会导致redelivery
    try {
      if (message instanceof ActiveMQTextMessage) {
        ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
        message.acknowledge();
        System.out.println("收到的消息:" + textMessage.getText());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

