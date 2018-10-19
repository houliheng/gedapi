package com.gq.ged.thrift;

import com.gq.ged.message.tmodel.MessageTService;
import com.gq.ged.message.tmodel.MsgTypeEnum;
import com.gq.ged.user.tmodel.UserLoginThriftForm;
import com.gq.ged.user.tmodel.UserRegisterThriftForm;
import com.gq.ged.user.tmodel.UserTService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by wyq_tomorrow on 2018/1/26.
 */
public class ThriftRegisterTest {
    public  static void main(String[] args) throws TTransportException, TException {
        String servletUrl = "http://localhost:9090/api/userTService";

        THttpClient thc = new THttpClient(servletUrl, HttpClientBuilder.create().build());
        TProtocol loPFactory = new TBinaryProtocol(thc);
        //TMultiplexedProtocol loPFactory1 = new TMultiplexedProtocol(loPFactory,"test");
        UserTService.Client client = new UserTService.Client(loPFactory);
        UserLoginThriftForm form=new UserLoginThriftForm();
        form.setMobile("18500521347");
        form.setPassword("123456");
        String result = client.loginUser(form);
        System.out.println(result);
    }
}
