package com.resoft.outinterface.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.resoft.outinterface.SV.server.SVServeInterface;


public class Client {
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(SVServeInterface.class);
	    factory.setAddress("http://localhost:8080/jee_cre_inprogress/f/webservice/SVServer?wsdl");
//	    SVServeInterface hello = (SVServeInterface) factory.create();
	    StringBuffer sb =new StringBuffer();
	    BufferedReader br =null;
	    try {
			FileInputStream file = new FileInputStream(new File("src/resources/SVRequest.xml"));
			InputStreamReader isr =new InputStreamReader(file,"UTF-8");
			br =new BufferedReader(isr);
			String str ="";
			while((str=br.readLine())!=null){
				sb.append(str);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String svr =sb.toString();
	    System.out.println(svr);
		try {
//			String xml = hello.SVInvestInfoResponse(svr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}