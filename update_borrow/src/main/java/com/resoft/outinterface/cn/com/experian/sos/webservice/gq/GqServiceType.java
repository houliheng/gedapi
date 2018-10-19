package com.resoft.outinterface.cn.com.experian.sos.webservice.gq;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;



@WebService(targetNamespace = "http://sos.webservice.com.cn/GqService/", name = "GqServiceType")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface GqServiceType {
	
	  @WebResult(name = "return", targetNamespace = "http://sos.webservice.com.cn/GqService/")
	  @WebMethod
	  public void addStudent(@WebParam(name = "xmlData",targetNamespace = "http://sos.webservice.com.cn/GqService/") String xmlData);
	  
	  
	  
	  @WebResult(name = "return", targetNamespace = "http://sos.webservice.com.cn/GqService/")
	  @WebMethod
	  public String callGqFromStr(@WebParam(name = "xmlData",targetNamespace = "http://sos.webservice.com.cn/GqService/") String gqXmlData);
	  
	  

}
