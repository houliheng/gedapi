package com.resoft.outinterface.utils;


public interface XmlUtilsInterface {
	 public String formatXml(String str) throws Exception ;
	 public <T>T XMLStringToBean(String xml,Class<T> objClass)throws Exception;
	 public <T>String BeanToXmlString(Object obj,Class<T> objClass)throws Exception;
}
