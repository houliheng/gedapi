package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for workOrderTransfer complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="workOrderTransfer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://workorder.controller.application.mobile.com/}workOrderTransferVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workOrderTransfer", propOrder = { "arg0" })
public class WorkOrderTransfer {

	protected WorkOrderTransferVO arg0;

	/**
	 * Gets the value of the arg0 property.
	 * 
	 * @return possible object is {@link WorkOrderTransferVO }
	 * 
	 */
	public WorkOrderTransferVO getArg0() {
		return arg0;
	}

	/**
	 * Sets the value of the arg0 property.
	 * 
	 * @param value
	 *            allowed object is {@link WorkOrderTransferVO }
	 * 
	 */
	public void setArg0(WorkOrderTransferVO value) {
		this.arg0 = value;
	}

}
