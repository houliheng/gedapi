
package com.resoft.outinterface.SV.client2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>surveyInfoDto complex type�� Java �ࡣ
 *
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 *
 * <pre>
 * &lt;complexType name="surveyInfoDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DISTRICT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IS_MASTER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MASTER_RELATION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PHONE_NO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PROCESS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PROVINCES" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="REMARK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SURVEY_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "surveyInfoDto", propOrder = {
	"workid",
    "address",
    "city",
    "district",
    "ismaster",
    "masterrelation",
    "phoneno",
    "process",
    "provinces",
    "remark",
    "surveyname",
    "flag"
})
public class SurveyInfoDto {

	@XmlElement(required = true, name = "WORK_ID")
	private String workid;
    @XmlElement(name = "ADDRESS")
    protected String address;
    @XmlElement(name = "CITY")
    protected String city;
    @XmlElement(name = "DISTRICT")
    protected String district;
    @XmlElement(name = "IS_MASTER")
    protected String ismaster;
    @XmlElement(name = "MASTER_RELATION")
    protected String masterrelation;
    @XmlElement(name = "PHONE_NO")
    protected String phoneno;
    @XmlElement(name = "PROCESS")
    protected String process;
    @XmlElement(name = "PROVINCES")
    protected String provinces;
    @XmlElement(name = "REMARK")
    protected String remark;
    @XmlElement(name = "SURVEY_NAME")
    protected String surveyname;
    @XmlElement(required = true, name = "FLAG")
	private String flag;


    /**
     * ��ȡaddress���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getADDRESS() {
        return address;
    }

    /**
     * ����address���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setADDRESS(String value) {
        this.address = value;
    }

    /**
     * ��ȡcity���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCITY() {
        return city;
    }

    /**
     * ����city���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCITY(String value) {
        this.city = value;
    }

    /**
     * ��ȡdistrict���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDISTRICT() {
        return district;
    }

    /**
     * ����district���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDISTRICT(String value) {
        this.district = value;
    }

    /**
     * ��ȡismaster���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getISMASTER() {
        return ismaster;
    }

    /**
     * ����ismaster���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setISMASTER(String value) {
        this.ismaster = value;
    }

    /**
     * ��ȡmasterrelation���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMASTERRELATION() {
        return masterrelation;
    }

    /**
     * ����masterrelation���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMASTERRELATION(String value) {
        this.masterrelation = value;
    }

    /**
     * ��ȡphoneno���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPHONENO() {
        return phoneno;
    }

    /**
     * ����phoneno���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPHONENO(String value) {
        this.phoneno = value;
    }

    /**
     * ��ȡprocess���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPROCESS() {
        return process;
    }

    /**
     * ����process���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPROCESS(String value) {
        this.process = value;
    }

    /**
     * ��ȡprovinces���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPROVINCES() {
        return provinces;
    }

    /**
     * ����provinces���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPROVINCES(String value) {
        this.provinces = value;
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
     * ��ȡsurveyname���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSURVEYNAME() {
        return surveyname;
    }

    /**
     * ����surveyname���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSURVEYNAME(String value) {
        this.surveyname = value;
    }


	public void setWORKID(String workId) {
		this.workid = workId;
	}

	public void setFLAG(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "SurveyInfoDto [workid=" + workid + ", address=" + address + ", city=" + city + ", district=" + district
				+ ", ismaster=" + ismaster + ", masterrelation=" + masterrelation + ", phoneno=" + phoneno
				+ ", process=" + process + ", provinces=" + provinces + ", remark=" + remark + ", surveyname="
				+ surveyname + ", flag=" + flag + "]";
	}



}
