package com.resoft.outinterface.SV.client;

import javax.jws.WebService;

import com.resoft.outinterface.SV.client.entry.request.WorkOrderDto;
import com.resoft.outinterface.SV.client.entry.response.CommonVO;

@WebService(targetNamespace="http://workorder.controller.application.mobile.com/")
public interface WorkOrderControllerService {
	public CommonVO orderDistribute(WorkOrderDto odt);
}
