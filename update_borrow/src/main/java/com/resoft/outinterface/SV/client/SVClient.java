package com.resoft.outinterface.SV.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.common.utils.Constants;
import com.resoft.outinterface.SV.client.entry.request.OrderDistribute;
import com.resoft.outinterface.SV.client.entry.request.WorkOrderDto;
import com.resoft.outinterface.SV.client.entry.response.CommonVO;
import com.resoft.outinterface.SV.client.entry.response.OrderDistributeResponse;
import com.resoft.outinterface.SV.server.entry.request.SVRequest;
import com.resoft.outinterface.SV.service.SVRequestService;
import com.resoft.outinterface.SV.service.WorkOrderService;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

@RestController
@RequestMapping(value = "/f/sv/request/")
public class SVClient {

	private static final Logger logger = LoggerFactory.getLogger(SVClient.class);

	private WorkOrderService workOrderService = SpringContextHolder.getBean("workOrderService");


	@RequestMapping(value = "getSVClient/{applyNo}")
	public void SVServiceMethod(@PathVariable("applyNo") String applyNo) {
		try {
			WorkOrderDto workOrderDto = workOrderService.getWorkOrderDto(applyNo);
			String xml = Facade.facade.BeanToXmlString(workOrderDto, WorkOrderDto.class);
			logger.debug(xml);
			CommonVO svRequest = Facade.facade.getDispatchedInterface(Constants.INTERFACE_SV, CommonVO.class, applyNo, xml);
			logger.debug(Facade.facade.BeanToXmlString(svRequest, OrderDistributeResponse.class));
		} catch (Exception e) {
			logger.error("外访接口调用失败", e);
		}
	}
}
