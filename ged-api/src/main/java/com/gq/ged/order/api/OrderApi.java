package com.gq.ged.order.api;

import com.alibaba.fastjson.JSONObject;
import com.gq.ged.annotation.THttpService;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.order.tmodel.OrderTService;
import com.gq.ged.order.tmodel.OrderThriftForm;
import org.apache.thrift.TException;

import javax.annotation.Resource;

/**
 * Created by wrh on 2018/1/18.
 */
@THttpService("/orderTApi")
public class OrderApi  implements OrderTService.Iface{
    @Resource
    OrderService orderService;

    @Override
    public String createOrder(OrderThriftForm orderThriftForm) throws TException {
      //  orderService.CreateOrder(orderThriftForm);
      return  JSONObject.toJSONString(ResponseEntityUtil.success());
    }

    @Override
    public String selectStatus(int userId) throws TException {
        return null;
    }
}
