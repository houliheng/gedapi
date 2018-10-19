package com.gq.ged.activemq.service;

/**
 * Created by gickeuy on 2018/1/9.
 */
public interface JmsProvider {

  public void sendMessage(String destination, String message);

  public void sendMessage(String destination, Object messageObj);
}
