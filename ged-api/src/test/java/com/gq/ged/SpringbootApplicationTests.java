package com.gq.ged;

import com.gq.ged.activemq.service.JmsProvider;
import com.gq.ged.order.controller.req.GedOrderReqForm;
import com.gq.ged.order.service.OrderService;
import com.gq.ged.user.dao.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {
    @Resource
    JmsProvider jmsProvider;

    @Resource
    OrderService orderService;


    @Test
    public void createOrder() throws  Exception{
        GedOrderReqForm gedOrder=new GedOrderReqForm();
        gedOrder.setLoanAmount(new BigDecimal(7000));
        gedOrder.setCompanyName("大公司");
        gedOrder.setLoanTerm(100);
        User userInfo=new User();
        userInfo.setId(5l);
        orderService.createOrder(gedOrder,userInfo);
    }

    @Test
    public  void gedOrderStatus() throws  Exception{
        Map<String,String> param=new HashMap<String,String>();
//        param.put("orderNo","GJD001014002001160513153445AAE");
//        param.put("status","100");
//         String json= HttpUtils.doPostForm("http://10.100.161.136:8080/gqilms/f/rest/api/gedOrderStatus",param);
//        JSONObject jsonObject = JSON.parseObject(json);
    }
}
