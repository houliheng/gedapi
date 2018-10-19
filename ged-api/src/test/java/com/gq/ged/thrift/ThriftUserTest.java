package com.gq.ged.thrift;

import com.gq.ged.message.tmodel.MessageTService;
import com.gq.ged.message.tmodel.MsgTypeEnum;
import com.gq.ged.user.tmodel.UserLoginThriftForm;
import com.gq.ged.user.tmodel.UserTService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TTransportException;

import java.util.Calendar;

/**
 * Created by wyq_tomorrow on 2018/1/11.
 */
public class ThriftUserTest {

    public  static void main(String[] args) throws TTransportException, TException {
        String servletUrl = "http://localhost:9090/api/userTService";

        THttpClient thc = new THttpClient(servletUrl, HttpClientBuilder.create().build());
        thc.setCustomHeader("111","2222");
        TProtocol loPFactory = new TBinaryProtocol(thc);
        //TMultiplexedProtocol loPFactory1 = new TMultiplexedProtocol(loPFactory,"test");
        UserTService.Client client = new UserTService.Client(loPFactory);
        UserLoginThriftForm loginThriftForm= new UserLoginThriftForm();
        loginThriftForm.setMobile("15311443542");
        loginThriftForm.setPassword("0000121");
        String result = client.loginUser(loginThriftForm);
        System.out.println(result);
    }
}
