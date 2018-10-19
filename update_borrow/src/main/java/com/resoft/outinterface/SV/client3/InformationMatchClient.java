package com.resoft.outinterface.SV.client3;

import java.net.URL;
import java.util.Date;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.SV.client2.CommonVO;
import com.resoft.outinterface.SV.client2.WorkOrderControllerDelegate;
import com.resoft.outinterface.SV.client2.WorkOrderControllerService;
import com.resoft.outinterface.SV.client2.WorkOrderTransferVO;
import com.resoft.outinterface.SV.client3.service.InformationMatchClientService;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

@RestController
@RequestMapping(value = "/f/sv/informationMatch/")
public class InformationMatchClient {

	private static final QName SERVICE_NAME = new QName("http://workorder.controller.application.mobile.com/", "WorkOrderControllerService");

	private static final Logger logger = LoggerFactory.getLogger(InformationMatchClient.class);

	private InformationMatchClientService informationMatchClientService = SpringContextHolder.getBean("informationMatchClientService");

	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");

	@RequestMapping(method = RequestMethod.GET, value = "getSVClient3/{applyNo}")
	public void getDataToSVForInformationMatch(@PathVariable("applyNo") String applyNo) {
		logger.info(applyNo);
		Date sendDate = new Date();
		String xml;
		CommonVO returnClass = new CommonVO();
		try {
			WorkOrderTransferVO client = new WorkOrderTransferVO();
			client = informationMatchClientService.getDataToSVForInformationMatch(applyNo);
			xml = Facade.facade.BeanToXmlString(client, WorkOrderTransferVO.class);
			logger.info("SV字段对接发送信息" + xml);
			try {
				URL wsdlURL = WorkOrderControllerService.WSDL_LOCATION;
				WorkOrderControllerService ss = new WorkOrderControllerService(wsdlURL, SERVICE_NAME);
				WorkOrderControllerDelegate wcd = ss.getWorkOrderControllerPort();
				returnClass = wcd.workOrderTransfer(client);
				logger.info("SV字段对接返回信息：" + returnClass.getMsg());
				try {
					interfaceInfoService.save(new InterfaceInfo(applyNo, "字段对接接口", returnClass.getMsg(), sendDate, xml));
				} catch (Exception e) {
					logger.error("字段对接接口信息记录失败！", e);
				}
			} catch (Exception e) {
				logger.error("字段对接接口返回信息转化失败", e);
				try {
					interfaceInfoService.save(new InterfaceInfo(applyNo, "字段对接接口", "接口访问失败:" + e.toString(), sendDate, xml));
				} catch (Exception e2) {
					logger.error("字段对接接口信息记录失败！", e2);
				}
			}
		} catch (Exception e1) {
			logger.error("字段对接接口所需信息获取失败！", e1);
		}
	}
}
