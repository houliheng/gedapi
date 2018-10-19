package com.resoft.outinterface.utils;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.resoft.outinterface.SV.client.WorkOrderControllerService;
import com.resoft.outinterface.SV.client.entry.request.WorkOrderDto;
import com.resoft.outinterface.SV.client.entry.response.CommonVO;
import com.resoft.outinterface.cn.com.experian.sos.webservice.gq.GqServiceType;
import com.resoft.outinterface.testEntry.HelloHi123;
import com.resoft.outinterface.themis.ThemisServerInterface;
import com.thinkgem.jeesite.common.config.Global;

public class Dispatcher implements DispatchInterface {
	private JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

	@Override
	public <T> T getDispatchedInterface(int interfaceName, Class<T> objClass, String applyNo, String xml) throws Exception {
		switch (interfaceName) {
		case 1:
			return this.themisInterface("themis", objClass, applyNo, xml);
		case 2:
			return this.testInterface("test", objClass, applyNo);
		case 3:
			return this.experianInterface("experian", objClass, applyNo, xml);
		case 4:
			return this.SVInterface("SV", objClass, applyNo, xml);
		default:
			return null;
		}
	}

	private <T> T themisInterface(String interfaceName, Class<T> objClass, String applyNo, String xml) throws Exception {
		factory.setServiceClass(ThemisServerInterface.class);
		factory.setAddress(Global.getConfig(interfaceName));
		ThemisServerInterface hello = (ThemisServerInterface) factory.create();
		String responseXml = hello.getAnalysisResult(xml);
		logger.debug(responseXml);
		T obj = Facade.facade.XMLStringToBean(responseXml, objClass);
		return obj;
	}

	private <T> T testInterface(String interfaceName, Class<T> objClass, String applyNo) throws Exception {
		factory.setServiceClass(HelloHi123.class);
		factory.setAddress(Global.getConfig(interfaceName));
		HelloHi123 hello = (HelloHi123) factory.create();
		String xml = hello.sayHello1();
		logger.debug(xml);
		T obj = Facade.facade.XMLStringToBean(xml, objClass);
		return obj;
	}

	private <T> T experianInterface(String interfaceName, Class<T> objClass, String applyNo, String xml) throws Exception {
		factory.setServiceClass(GqServiceType.class);
		factory.setAddress(Global.getConfig("experian"));
		GqServiceType hello = (GqServiceType) factory.create();
		String responseXml = hello.callGqFromStr(xml);
		logger.debug("experian返回信息："+responseXml);
		T obj = Facade.facade.XMLStringToBean(responseXml, objClass);
		return obj;
	}

	private <T> T SVInterface(String interfaceName, Class<T> objClass, String applyNo, String xml) throws Exception {
		factory.setServiceClass(WorkOrderControllerService.class);
	    factory.setAddress(Global.getConfig(interfaceName));
	    WorkOrderControllerService hello = (WorkOrderControllerService) factory.create();
	    WorkOrderDto orb =  Facade.facade.XMLStringToBean(xml, WorkOrderDto.class);
	    CommonVO responseXml =hello.orderDistribute(orb);
	    CommonVO obj = responseXml;
		return (T) obj;
	}
}
