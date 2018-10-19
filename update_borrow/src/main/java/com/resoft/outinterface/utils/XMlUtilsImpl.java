package com.resoft.outinterface.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class XMlUtilsImpl  implements XmlUtilsInterface{
	 public <T>T XMLStringToBean(String xml,Class<T> objClass) throws JAXBException{  
	 	  JAXBContext context = JAXBContext.newInstance(objClass);  
          Unmarshaller unmarshaller = context.createUnmarshaller();  
          T financialStatement = (T)unmarshaller.unmarshal(new StringReader(xml));  
        return financialStatement;
	 }  
	
	public  String formatXml(String str) throws Exception {
		  org.dom4j.Document document = null;
		  document =  DocumentHelper.parseText(str);
		  OutputFormat format = OutputFormat.createPrettyPrint();
		  StringWriter writer = new StringWriter();
		  XMLWriter xmlWriter = new XMLWriter(writer, format);
		  xmlWriter.write(document);
		  xmlWriter.close();
		  return writer.toString();
		 }
	public <T>String BeanToXmlString(Object obj,Class<T> objClass) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(objClass); 
        Marshaller marshaller = context.createMarshaller();  
        StringWriter sw = new StringWriter();
        marshaller.marshal(obj, sw);
      return sw.toString();
	}
}
