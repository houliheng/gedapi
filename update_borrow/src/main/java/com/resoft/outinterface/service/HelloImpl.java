package com.resoft.outinterface.service;

import javax.jws.WebService;


@WebService(endpointInterface="com.resoft.outinterface.service.HelloHi")
public class HelloImpl implements HelloHi {

  /**
   * 根据传递的字符串生成对应的xml。
   * 
   * @param 传递过来的参数name
   * @return 返回xml的字符串
   */
  public String createXml(String name) {

    StringBuffer sb = new StringBuffer();
    sb.append("<?xml version='1.0' encoding='UTF-8'?>");
    sb.append("<Result>");
    sb.append("<cinamaName>机械战警</cinamaName>");
    sb.append("<director>" + name + "</director>");
    sb.append("<introduce>一部好莱坞大片，3D观影，不错！！！</introduce>");
    sb.append("<price>25</price>");
    sb.append("</Result>");
    return sb.toString();
  }
@Override
public String sayHello1(String str) {
    System.out.println("web services1调用成功");
    String result = createXml(str);
    return result;
}
@Override
public String sayHello2(String str) {
    System.out.println("web services2调用成功");
    String result = createXml(str);
    return result;
}
@Override
public String sayHello3(String str) {
    System.out.println("web services3调用成功");
    String result = createXml(str);
    return result;
}
@Override
public String sayHello4(String str) {
    System.out.println("web services4调用成功");
    String result = createXml(str);
    return result;
}
@Override
public String sayHello5(String str) {
    System.out.println("web services5调用成功");
    String result = createXml(str);
    return result;
}
}