//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.08 at 06:41:47 PM CST 
//


package com.resoft.outinterface.cn.com.experian.entry.request.head;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the test1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Header_QNAME = new QName("", "Header");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: test1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SOSHeader }
     * 
     */
    public ExperianRequestHeader createSOSHeader() {
        return new ExperianRequestHeader();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SOSHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Header")
    public JAXBElement<ExperianRequestHeader> createHeader(ExperianRequestHeader value) {
        return new JAXBElement<ExperianRequestHeader>(_Header_QNAME, ExperianRequestHeader.class, null, value);
    }

}
