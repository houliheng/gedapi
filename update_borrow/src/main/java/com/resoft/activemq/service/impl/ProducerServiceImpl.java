package com.resoft.activemq.service.impl;

/**
 * Created by Levi on 2017/8/9.
 */
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.resoft.activemq.service.ProducerService;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService {

  @Resource(name = "jmsTemplate")
  private JmsTemplate jmsTemplate;

  @Value("${activityMQ.queue.repayment.procedure}")
  private String repaymentProcedure;

  @Override
  public void sendMessageRepayment(final String msg) {
    jmsTemplate.send(repaymentProcedure, new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
    	  return session.createTextMessage(msg);
      }
    });

  }

  @Override
  public void sendMessage() {
    jmsTemplate.send("ged.borrow.test", new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage("111222");
      }
    });
  }

	@Override
	public void pushMoney(final String msg) {
		jmsTemplate.send("pushMoney", new MessageCreator() {
		      public Message createMessage(Session session) throws JMSException {
		    	  return session.createTextMessage(msg);
		      }
		 });
	}

	@Override
	public void discountEnterAccount(final String msg) {
		jmsTemplate.send("discountEnterAccount", new MessageCreator() {
		      public Message createMessage(Session session) throws JMSException {
		    	  return session.createTextMessage(msg);
		      }
		 });
	}

	@Override
    public void advancePullRepayment(final String msg) {
      jmsTemplate.send("advancePullRepayment", new MessageCreator() {
          @Override
          public Message createMessage(Session session) throws JMSException {
              return session.createTextMessage(msg);
          }
      });
    }
}
