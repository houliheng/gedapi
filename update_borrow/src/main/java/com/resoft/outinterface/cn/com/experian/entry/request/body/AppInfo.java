//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.08 at 06:41:27 PM CST 
//


package com.resoft.outinterface.cn.com.experian.entry.request.body;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AppInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="App_SharePropertion" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_ContributYear" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_Loan_Balance" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_MChild_num" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_HouseYear" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_AmountMultiple" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_IsBreak_Pipeline_Persion" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_Average_Pipeline_Persion" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_work_year" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_marital_status" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_live_status" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_Pipeline_Print" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="App_Plpeline_End" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="App_Plpeline_Start" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="App_IsPipelineBreak" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_Monthly_Pipeline" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_IsHaveHouse" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_Age" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_Fchild_num" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_Spouse_IsIBL" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_Spouse_age" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_IsInvolved" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_ID_start" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="App_IsIBL" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_MobilePhone" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="App_industry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="App_Sex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="App_IsFixHouse" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="App_ID_Due_Time" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="App_education" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppInfo", propOrder = {
	"custName",
    "appSharePropertion",
    "appContributYear",
    "appLoanBalance",
    "appMChildNum",
    "appHouseYear",
    "appAmountMultiple",
    "appIsBreakPipelinePersion",
    "appAveragePipelinePersion",
    "appWorkYear",
    "appMaritalStatus",
    "appLiveStatus",
    "appPipelinePrint",
    "appPlpelineEnd",
    "appPlpelineStart",
    "appIsPipelineBreak",
    "appMonthlyPipeline",
    "appIsHaveHouse",
    "appAge",
    "appFchildNum",
    "appSpouseIsIBL",
    "appSpouseAge",
    "appIsInvolved",
    "appIDStart",
    "appIsIBL",
    "appMobilePhone",
    "appCity",
    "appIndustry",
    "appSex",
    "appIsFixHouse",
    "appIDDueTime",
    "appEducation"
})
public class AppInfo {

	@XmlElement(name = "custName", required=false)
	protected String custName;
    @XmlElement(name = "App_SharePropertion")
    protected BigDecimal appSharePropertion;
    @XmlElement(name = "App_ContributYear")
    protected BigDecimal appContributYear;
    @XmlElement(name = "App_Loan_Balance")
    protected BigDecimal appLoanBalance;
    @XmlElement(name = "App_MChild_num")
    protected BigDecimal appMChildNum;
    @XmlElement(name = "App_HouseYear")
    protected BigDecimal appHouseYear;
    @XmlElement(name = "App_AmountMultiple")
    protected BigDecimal appAmountMultiple;
    @XmlElement(name = "App_IsBreak_Pipeline_Persion")
    protected BigDecimal appIsBreakPipelinePersion;
    @XmlElement(name = "App_Average_Pipeline_Persion")
    protected BigDecimal appAveragePipelinePersion;
    @XmlElement(name = "App_work_year")
    protected BigDecimal appWorkYear;
    @XmlElement(name = "App_marital_status")
    protected BigDecimal appMaritalStatus;
    @XmlElement(name = "App_live_status")
    protected BigDecimal appLiveStatus;
    @XmlElement(name = "App_Pipeline_Print")
    @XmlSchemaType(name = "date")
    protected String appPipelinePrint;
    @XmlElement(name = "App_Plpeline_End")
    @XmlSchemaType(name = "date")
    protected String appPlpelineEnd;
    @XmlElement(name = "App_Plpeline_Start")
    @XmlSchemaType(name = "date")
    protected String appPlpelineStart;
    @XmlElement(name = "App_IsPipelineBreak")
    protected BigDecimal appIsPipelineBreak;
    @XmlElement(name = "App_Monthly_Pipeline")
    protected BigDecimal appMonthlyPipeline;
    @XmlElement(name = "App_IsHaveHouse")
    protected BigDecimal appIsHaveHouse;
    @XmlElement(name = "App_Age")
    protected BigDecimal appAge;
    @XmlElement(name = "App_Fchild_num")
    protected BigDecimal appFchildNum;
    @XmlElement(name = "App_Spouse_IsIBL")
    protected BigDecimal appSpouseIsIBL;
    @XmlElement(name = "App_Spouse_age")
    protected BigDecimal appSpouseAge;
    @XmlElement(name = "App_IsInvolved")
    protected BigDecimal appIsInvolved;
    @XmlElement(name = "App_ID_start")
    @XmlSchemaType(name = "date")
    protected String appIDStart;
    @XmlElement(name = "App_IsIBL")
    protected BigDecimal appIsIBL;
    @XmlElement(name = "App_MobilePhone")
    protected BigDecimal appMobilePhone;
    @XmlElement(name = "App_city")
    protected String appCity;
    @XmlElement(name = "App_industry")
    protected String appIndustry;
    @XmlElement(name = "App_Sex")
    protected String appSex;
    @XmlElement(name = "App_IsFixHouse")
    protected BigDecimal appIsFixHouse;
    @XmlElement(name = "App_ID_Due_Time")
    @XmlSchemaType(name = "date")
    protected String appIDDueTime;
    @XmlElement(name = "App_education")
    protected BigDecimal appEducation;

    /**
     * Gets the value of the appSharePropertion property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppSharePropertion() {
        return appSharePropertion;
    }

    /**
     * Sets the value of the appSharePropertion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppSharePropertion(BigDecimal value) {
        this.appSharePropertion = value;
    }

    /**
     * Gets the value of the appContributYear property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppContributYear() {
        return appContributYear;
    }

    /**
     * Sets the value of the appContributYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppContributYear(BigDecimal value) {
        this.appContributYear = value;
    }

    /**
     * Gets the value of the appLoanBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppLoanBalance() {
        return appLoanBalance;
    }

    /**
     * Sets the value of the appLoanBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppLoanBalance(BigDecimal value) {
        this.appLoanBalance = value;
    }
    
	
	/**
     * Gets the value of the appMChildNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppMChildNum() {
        return appMChildNum;
    }

    /**
     * Sets the value of the appMChildNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppMChildNum(BigDecimal value) {
        this.appMChildNum = value;
    }

    /**
     * Gets the value of the appHouseYear property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppHouseYear() {
        return appHouseYear;
    }

    /**
     * Sets the value of the appHouseYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppHouseYear(BigDecimal value) {
        this.appHouseYear = value;
    }

    /**
     * Gets the value of the appAmountMultiple property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppAmountMultiple() {
        return appAmountMultiple;
    }

    /**
     * Sets the value of the appAmountMultiple property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppAmountMultiple(BigDecimal value) {
        this.appAmountMultiple = value;
    }

    /**
     * Gets the value of the appIsBreakPipelinePersion property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppIsBreakPipelinePersion() {
        return appIsBreakPipelinePersion;
    }

    /**
     * Sets the value of the appIsBreakPipelinePersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppIsBreakPipelinePersion(BigDecimal value) {
        this.appIsBreakPipelinePersion = value;
    }

    /**
     * Gets the value of the appAveragePipelinePersion property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppAveragePipelinePersion() {
        return appAveragePipelinePersion;
    }

    /**
     * Sets the value of the appAveragePipelinePersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppAveragePipelinePersion(BigDecimal value) {
        this.appAveragePipelinePersion = value;
    }

    /**
     * Gets the value of the appWorkYear property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppWorkYear() {
        return appWorkYear;
    }

    /**
     * Sets the value of the appWorkYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppWorkYear(BigDecimal value) {
        this.appWorkYear = value;
    }

    /**
     * Gets the value of the appMaritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppMaritalStatus() {
        return appMaritalStatus;
    }

    /**
     * Sets the value of the appMaritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppMaritalStatus(BigDecimal value) {
        this.appMaritalStatus = value;
    }

    /**
     * Gets the value of the appLiveStatus property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppLiveStatus() {
        return appLiveStatus;
    }

    /**
     * Sets the value of the appLiveStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppLiveStatus(BigDecimal value) {
        this.appLiveStatus = value;
    }

    

    /**
     * Gets the value of the appIsPipelineBreak property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppIsPipelineBreak() {
        return appIsPipelineBreak;
    }

    /**
     * Sets the value of the appIsPipelineBreak property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppIsPipelineBreak(BigDecimal value) {
        this.appIsPipelineBreak = value;
    }

    /**
     * Gets the value of the appMonthlyPipeline property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppMonthlyPipeline() {
        return appMonthlyPipeline;
    }

    /**
     * Sets the value of the appMonthlyPipeline property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppMonthlyPipeline(BigDecimal value) {
        this.appMonthlyPipeline = value;
    }

    /**
     * Gets the value of the appIsHaveHouse property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppIsHaveHouse() {
        return appIsHaveHouse;
    }

    /**
     * Sets the value of the appIsHaveHouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppIsHaveHouse(BigDecimal value) {
        this.appIsHaveHouse = value;
    }

    /**
     * Gets the value of the appAge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppAge() {
        return appAge;
    }

    /**
     * Sets the value of the appAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppAge(BigDecimal value) {
        this.appAge = value;
    }

    /**
     * Gets the value of the appFchildNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppFchildNum() {
        return appFchildNum;
    }

    /**
     * Sets the value of the appFchildNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppFchildNum(BigDecimal value) {
        this.appFchildNum = value;
    }

    /**
     * Gets the value of the appSpouseIsIBL property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppSpouseIsIBL() {
        return appSpouseIsIBL;
    }

    /**
     * Sets the value of the appSpouseIsIBL property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppSpouseIsIBL(BigDecimal value) {
        this.appSpouseIsIBL = value;
    }

    /**
     * Gets the value of the appSpouseAge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppSpouseAge() {
        return appSpouseAge;
    }

    /**
     * Sets the value of the appSpouseAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppSpouseAge(BigDecimal value) {
        this.appSpouseAge = value;
    }

    /**
     * Gets the value of the appIsInvolved property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppIsInvolved() {
        return appIsInvolved;
    }

    /**
     * Sets the value of the appIsInvolved property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppIsInvolved(BigDecimal value) {
        this.appIsInvolved = value;
    }

   

    /**
     * Gets the value of the appIsIBL property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppIsIBL() {
        return appIsIBL;
    }

    /**
     * Sets the value of the appIsIBL property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppIsIBL(BigDecimal value) {
        this.appIsIBL = value;
    }

    /**
     * Gets the value of the appMobilePhone property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppMobilePhone() {
        return appMobilePhone;
    }

    /**
     * Sets the value of the appMobilePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppMobilePhone(BigDecimal value) {
        this.appMobilePhone = value;
    }

    /**
     * Gets the value of the appCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppCity() {
        return appCity;
    }

    /**
     * Sets the value of the appCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppCity(String value) {
        this.appCity = value;
    }

    /**
     * Gets the value of the appIndustry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppIndustry() {
        return appIndustry;
    }

    /**
     * Sets the value of the appIndustry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppIndustry(String value) {
        this.appIndustry = value;
    }

    /**
     * Gets the value of the appSex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppSex() {
        return appSex;
    }

    /**
     * Sets the value of the appSex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppSex(String value) {
        this.appSex = value;
    }

    /**
     * Gets the value of the appIsFixHouse property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppIsFixHouse() {
        return appIsFixHouse;
    }

    /**
     * Sets the value of the appIsFixHouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppIsFixHouse(BigDecimal value) {
        this.appIsFixHouse = value;
    }


    /**
     * Gets the value of the appEducation property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAppEducation() {
        return appEducation;
    }

    /**
     * Sets the value of the appEducation property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAppEducation(BigDecimal value) {
        this.appEducation = value;
    }

	public String getAppPipelinePrint() {
		return appPipelinePrint;
	}

	public void setAppPipelinePrint(String appPipelinePrint) {
		this.appPipelinePrint = appPipelinePrint;
	}

	public String getAppPlpelineEnd() {
		return appPlpelineEnd;
	}

	public void setAppPlpelineEnd(String appPlpelineEnd) {
		this.appPlpelineEnd = appPlpelineEnd;
	}

	public String getAppPlpelineStart() {
		return appPlpelineStart;
	}

	public void setAppPlpelineStart(String appPlpelineStart) {
		this.appPlpelineStart = appPlpelineStart;
	}

	public String getAppIDStart() {
		return appIDStart;
	}

	public void setAppIDStart(String appIDStart) {
		this.appIDStart = appIDStart;
	}

	public String getAppIDDueTime() {
		return appIDDueTime;
	}

	public void setAppIDDueTime(String appIDDueTime) {
		this.appIDDueTime = appIDDueTime;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
}
