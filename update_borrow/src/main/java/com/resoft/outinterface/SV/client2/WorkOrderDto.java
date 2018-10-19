
package com.resoft.outinterface.SV.client2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>workOrderDto complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="workOrderDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="APP_NO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AUTO_ISSUE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BS_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CLOSING_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="COMMISSION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="COMMISSION_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CONTRACT_NO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CUSTOMER_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INCLUDE_GUAR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INCLUDE_WEEKEND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INVESTIGATOR_COUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INVESTIGATOR_LEVEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LOAN_AMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LOAN_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ORDER_DISCOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ORDER_NO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ORDER_PRICE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PRODUCT_NO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="REMARK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="surveyInfoDto" type="{http://workorder.controller.application.mobile.com/}surveyInfoDto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="USER_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workOrderDto", propOrder = {
    "appno",
    "autoissue",
    "bsflag",
    "closingdate",
    "commission",
    "commissiontype",
    "contractno",
    "customername",
    "includeguar",
    "includeweekend",
    "investigatorcount",
    "investigatorlevel",
    "loanamount",
    "loantype",
    "orderdiscount",
    "orderno",
    "orderprice",
    "orgno",
    "productno",
    "remark",
    "surveyInfoDto",
    "usercode"
})
public class WorkOrderDto {

    @XmlElement(name = "APP_NO")
    protected String appno;
    @XmlElement(name = "AUTO_ISSUE")
    protected String autoissue;
    @XmlElement(name = "BS_FLAG")
    protected String bsflag;
    @XmlElement(name = "CLOSING_DATE")
    protected String closingdate;
    @XmlElement(name = "COMMISSION")
    protected String commission;
    @XmlElement(name = "COMMISSION_TYPE")
    protected String commissiontype;
    @XmlElement(name = "CONTRACT_NO")
    protected String contractno;
    @XmlElement(name = "CUSTOMER_NAME")
    protected String customername;
    @XmlElement(name = "INCLUDE_GUAR")
    protected String includeguar;
    @XmlElement(name = "INCLUDE_WEEKEND")
    protected String includeweekend;
    @XmlElement(name = "INVESTIGATOR_COUNT")
    protected String investigatorcount;
    @XmlElement(name = "INVESTIGATOR_LEVEL")
    protected String investigatorlevel;
    @XmlElement(name = "LOAN_AMOUNT")
    protected String loanamount;
    @XmlElement(name = "LOAN_TYPE")
    protected String loantype;
    @XmlElement(name = "ORDER_DISCOUNT")
    protected String orderdiscount;
    @XmlElement(name = "ORDER_NO")
    protected String orderno;
    @XmlElement(name = "ORDER_PRICE")
    protected String orderprice;
    @XmlElement(name = "ORG_NO")
    protected String orgno;
    @XmlElement(name = "PRODUCT_NO")
    protected String productno;
    @XmlElement(name = "REMARK")
    protected String remark;
    @XmlElement(nillable = true)
    protected List<SurveyInfoDto> surveyInfoDto;
    @XmlElement(name = "USER_CODE")
    protected String usercode;

    /**
     * ��ȡappno���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPPNO() {
        return appno;
    }

    /**
     * ����appno���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPPNO(String value) {
        this.appno = value;
    }
	public String getORGNO(){
		return orgno;
	}
	public void setORGNO(String value){
		this.orgno=value;
	}
    /**
     * ��ȡautoissue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUTOISSUE() {
        return autoissue;
    }

    /**
     * ����autoissue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUTOISSUE(String value) {
        this.autoissue = value;
    }

    /**
     * ��ȡbsflag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBSFLAG() {
        return bsflag;
    }

    /**
     * ����bsflag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBSFLAG(String value) {
        this.bsflag = value;
    }

    /**
     * ��ȡclosingdate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLOSINGDATE() {
        return closingdate;
    }

    /**
     * ����closingdate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLOSINGDATE(String value) {
        this.closingdate = value;
    }

    /**
     * ��ȡcommission���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMMISSION() {
        return commission;
    }

    /**
     * ����commission���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMMISSION(String value) {
        this.commission = value;
    }

    /**
     * ��ȡcommissiontype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMMISSIONTYPE() {
        return commissiontype;
    }

    /**
     * ����commissiontype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMMISSIONTYPE(String value) {
        this.commissiontype = value;
    }

    /**
     * ��ȡcontractno���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTRACTNO() {
        return contractno;
    }

    /**
     * ����contractno���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTRACTNO(String value) {
        this.contractno = value;
    }

    /**
     * ��ȡcustomerno���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUSTOMERNAME() {
        return customername;
    }

    /**
     * ����customerno���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUSTOMERNAME(String value) {
        this.customername = value;
    }

    /**
     * ��ȡincludeguar���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINCLUDEGUAR() {
        return includeguar;
    }

    /**
     * ����includeguar���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINCLUDEGUAR(String value) {
        this.includeguar = value;
    }

    /**
     * ��ȡincludeweekend���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINCLUDEWEEKEND() {
        return includeweekend;
    }

    /**
     * ����includeweekend���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINCLUDEWEEKEND(String value) {
        this.includeweekend = value;
    }

    /**
     * ��ȡinvestigatorcount���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINVESTIGATORCOUNT() {
        return investigatorcount;
    }

    /**
     * ����investigatorcount���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINVESTIGATORCOUNT(String value) {
        this.investigatorcount = value;
    }

    /**
     * ��ȡinvestigatorlevel���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINVESTIGATORLEVEL() {
        return investigatorlevel;
    }

    /**
     * ����investigatorlevel���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINVESTIGATORLEVEL(String value) {
        this.investigatorlevel = value;
    }

    /**
     * ��ȡloanamount���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOANAMOUNT() {
        return loanamount;
    }

    /**
     * ����loanamount���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOANAMOUNT(String value) {
        this.loanamount = value;
    }

    /**
     * ��ȡloantype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOANTYPE() {
        return loantype;
    }

    /**
     * ����loantype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOANTYPE(String value) {
        this.loantype = value;
    }

    /**
     * ��ȡorderdiscount���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORDERDISCOUNT() {
        return orderdiscount;
    }

    /**
     * ����orderdiscount���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORDERDISCOUNT(String value) {
        this.orderdiscount = value;
    }

    /**
     * ��ȡorderno���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORDERNO() {
        return orderno;
    }

    /**
     * ����orderno���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORDERNO(String value) {
        this.orderno = value;
    }

    /**
     * ��ȡorderprice���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORDERPRICE() {
        return orderprice;
    }

    /**
     * ����orderprice���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORDERPRICE(String value) {
        this.orderprice = value;
    }

    /**
     * ��ȡproductno���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRODUCTNO() {
        return productno;
    }

    /**
     * ����productno���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRODUCTNO(String value) {
        this.productno = value;
    }

    /**
     * ��ȡremark���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREMARK() {
        return remark;
    }

    /**
     * ����remark���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREMARK(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the surveyInfoDto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the surveyInfoDto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSurveyInfoDto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SurveyInfoDto }
     * 
     * 
     */
    public List<SurveyInfoDto> getSurveyInfoDto() {
        if (surveyInfoDto == null) {
            surveyInfoDto = new ArrayList<SurveyInfoDto>();
        }
        return this.surveyInfoDto;
    }
    

    public void setSurveyInfoDto(List<SurveyInfoDto> surveyInfoDto) {
		this.surveyInfoDto = surveyInfoDto;
	}

	/**
     * ��ȡusercode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSERCODE() {
        return usercode;
    }

    /**
     * ����usercode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSERCODE(String value) {
        this.usercode = value;
    }

	@Override
	public String toString() {
		return "WorkOrderDto [appno=" + appno + ", autoissue=" + autoissue
				+ ", bsflag=" + bsflag + ", closingdate=" + closingdate
				+ ", commission=" + commission + ", commissiontype="
				+ commissiontype + ", contractno=" + contractno
				+ ", customername=" + customername + ", includeguar=" + includeguar
				+ ", includeweekend=" + includeweekend + ", investigatorcount="
				+ investigatorcount + ", investigatorlevel="
				+ investigatorlevel + ", loanamount=" + loanamount
				+ ", loantype=" + loantype + ", orderdiscount=" + orderdiscount
				+ ", orderno=" + orderno + ", orderprice=" + orderprice
				+ ", orgno=" + orgno + ", productno=" + productno + ", remark=" + remark
				+ ", surveyInfoDto=" + surveyInfoDto.toString() + ", usercode=" + usercode
				+ "]";
	}
    
}
