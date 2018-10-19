
package com.resoft.outinterface.SV.client2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.resoft.outinterface.SV.client2
 * package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _OrderDistributeResponse_QNAME = new QName(
			"http://workorder.controller.application.mobile.com/", "orderDistributeResponse");
	private final static QName _WorkOrderTransfer_QNAME = new QName(
			"http://workorder.controller.application.mobile.com/", "workOrderTransfer");
	private final static QName _WorkOrderTransferResponse_QNAME = new QName(
			"http://workorder.controller.application.mobile.com/", "workOrderTransferResponse");
	private final static QName _OrderDistribute_QNAME = new QName("http://workorder.controller.application.mobile.com/",
			"orderDistribute");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * com.mobile.application.controller.workorder
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link PersonalFamilyData }
	 * 
	 */
	public PersonalFamilyData createPersonalFamilyData() {
		return new PersonalFamilyData();
	}

	/**
	 * Create an instance of {@link ResidenceInformation }
	 * 
	 */
	public ResidenceInformation createResidenceInformation() {
		return new ResidenceInformation();
	}

	/**
	 * Create an instance of {@link OrderDistributeResponse }
	 * 
	 */
	public OrderDistributeResponse createOrderDistributeResponse() {
		return new OrderDistributeResponse();
	}

	/**
	 * Create an instance of {@link WorkOrderDto }
	 * 
	 */
	public WorkOrderDto createWorkOrderDto() {
		return new WorkOrderDto();
	}

	/**
	 * Create an instance of {@link CommonVO }
	 * 
	 */
	public CommonVO createCommonVO() {
		return new CommonVO();
	}

	/**
	 * Create an instance of {@link WorkOrderTransfer }
	 * 
	 */
	public WorkOrderTransfer createWorkOrderTransfer() {
		return new WorkOrderTransfer();
	}

	/**
	 * Create an instance of {@link WorkOrderTransferVO }
	 * 
	 */
	public WorkOrderTransferVO createWorkOrderTransferVO() {
		return new WorkOrderTransferVO();
	}

	/**
	 * Create an instance of {@link AffiliatedEnterpriseData }
	 * 
	 */
	public AffiliatedEnterpriseData createAffiliatedEnterpriseData() {
		return new AffiliatedEnterpriseData();
	}

	/**
	 * Create an instance of {@link SurveyInfoDto }
	 * 
	 */
	public SurveyInfoDto createSurveyInfoDto() {
		return new SurveyInfoDto();
	}

	/**
	 * Create an instance of {@link CompanyBasicInformation }
	 * 
	 */
	public CompanyBasicInformation createCompanyBasicInformation() {
		return new CompanyBasicInformation();
	}

	/**
	 * Create an instance of {@link PledgeVehicleInformation }
	 * 
	 */
	public PledgeVehicleInformation createPledgeVehicleInformation() {
		return new PledgeVehicleInformation();
	}

	/**
	 * Create an instance of {@link PledgeHouseData }
	 * 
	 */
	public PledgeHouseData createPledgeHouseData() {
		return new PledgeHouseData();
	}

	/**
	 * Create an instance of {@link PersonalBasicData }
	 * 
	 */
	public PersonalBasicData createPersonalBasicData() {
		return new PersonalBasicData();
	}

	/**
	 * Create an instance of {@link WorkOrderTransferResponse }
	 * 
	 */
	public WorkOrderTransferResponse createWorkOrderTransferResponse() {
		return new WorkOrderTransferResponse();
	}

	/**
	 * Create an instance of {@link OrderDistribute }
	 * 
	 */
	public OrderDistribute createOrderDistribute() {
		return new OrderDistribute();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link OrderDistributeResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://workorder.controller.application.mobile.com/", name = "orderDistributeResponse")
	public JAXBElement<OrderDistributeResponse> createOrderDistributeResponse(OrderDistributeResponse value) {
		return new JAXBElement<OrderDistributeResponse>(_OrderDistributeResponse_QNAME, OrderDistributeResponse.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link WorkOrderTransfer }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://workorder.controller.application.mobile.com/", name = "workOrderTransfer")
	public JAXBElement<WorkOrderTransfer> createWorkOrderTransfer(WorkOrderTransfer value) {
		return new JAXBElement<WorkOrderTransfer>(_WorkOrderTransfer_QNAME, WorkOrderTransfer.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link WorkOrderTransferResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://workorder.controller.application.mobile.com/", name = "workOrderTransferResponse")
	public JAXBElement<WorkOrderTransferResponse> createWorkOrderTransferResponse(WorkOrderTransferResponse value) {
		return new JAXBElement<WorkOrderTransferResponse>(_WorkOrderTransferResponse_QNAME,
				WorkOrderTransferResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link OrderDistribute
	 * } {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://workorder.controller.application.mobile.com/", name = "orderDistribute")
	public JAXBElement<OrderDistribute> createOrderDistribute(OrderDistribute value) {
		return new JAXBElement<OrderDistribute>(_OrderDistribute_QNAME, OrderDistribute.class, null, value);
	}

}
