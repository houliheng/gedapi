package com.gq.ged.order.service.impl;

import com.gq.ged.order.dao.mapper.OrderLogMapper;
import com.gq.ged.order.dao.model.OrderLog;
import com.gq.ged.order.dao.model.OrderLogExample;
import com.gq.ged.order.service.OrderLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/29.
 */
@Service
public class OrderLogServiceImpl implements OrderLogService {

  @Resource
  OrderLogMapper orderLogMapper;

  @Override
  public OrderLog getOrderLogByOrderId(Long orderId, Integer targetStatus) {
    OrderLogExample example = new OrderLogExample();
    example.createCriteria().andOrderIdEqualTo(orderId).andTargetStatusEqualTo(targetStatus);
    List<OrderLog> list = orderLogMapper.selectByExample(example);
    if (list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  @Override
  public int createOrderlog(OrderLog orderLog) {
    return  orderLogMapper.insertSelective(orderLog);
  }
}
