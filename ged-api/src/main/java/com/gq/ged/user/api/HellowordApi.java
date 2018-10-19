package com.gq.ged.user.api;

import com.gq.ged.annotation.THttpService;
import com.gq.ged.user.tmodel.FileData;
import com.gq.ged.user.tmodel.HelloWorldService;
import org.apache.thrift.TException;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by wyq_tomorrow on 2018/1/18.
 */
@THttpService("/api/helloTService")
public class HellowordApi implements HelloWorldService.Iface {
  @Override
  public String sayHello(String username) throws TException {
    return "gq_thrift_test";
  }

  @Override
  public String upload(FileData f) throws FileNotFoundException {
    System.out.println(f.filename);
    String filePath = "d:\\test\\" + f.filename;
    java.io.File file = new java.io.File(filePath);
    FileOutputStream fos = new FileOutputStream(file);
    FileChannel channel = fos.getChannel();
    try {
      channel.write(f.buf);
      channel.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";

  }


}
