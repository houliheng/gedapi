package com.resoft.Accoutinterface.utils;


public interface AccXmlUtilsInterface {
	 public String formatXml(String str) throws Exception ;
	 public <T>T XMLStringToBean(String xml,Class<T> objClass)throws Exception;
	 public <T>String BeanToXmlString(Object obj,Class<T> objClass)throws Exception;
}
