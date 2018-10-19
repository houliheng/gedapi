package com.gq.ged.activemq.service;

import javax.jms.Destination;

/**
 * Created by gickeuy on 2018/1/9.
 */
public interface JmsConsumer {
    public void receive(Destination destination, String message);
}
