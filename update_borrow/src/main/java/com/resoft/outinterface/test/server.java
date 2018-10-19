package com.resoft.outinterface.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.outinterface.rest.fh.client.entry.response.FhRiskControlResponse;
import com.resoft.outinterface.rest.ged.GedServer;

@RestController
@RequestMapping(value = "/f/rest/fh/")
public class server {

	private static final Logger logger = LoggerFactory.getLogger(GedServer.class);

	@RequestMapping(method = RequestMethod.POST, value = "fhTest")
	public String gedApplyRegister(String authCode, String applyNo, String custId, String name, String idCard) {
		logger.info("法海服务接口开始-----------------------start");
		logger.info("法海发送信息：" + authCode);
		logger.info("法海发送信息：" + applyNo);
		logger.info("法海发送信息：" + custId);
		logger.info("法海发送信息：" + name);
		logger.info("法海发送信息：" + idCard);
		FhRiskControlResponse riskControlResponse = new FhRiskControlResponse();
		try {
			riskControlResponse.setApplyNo(applyNo);
			riskControlResponse.setCustId(custId);
			riskControlResponse.setKtggCount(11111);
			riskControlResponse.setAjlcCount(22211);
			riskControlResponse.setNewsCount(33311);
			riskControlResponse.setCpwsCount(44411);
			riskControlResponse.setZxggCount(55511);
			riskControlResponse.setSxggCount(66611);
			riskControlResponse.setFyggCount(77711);
			riskControlResponse.setBgtCount(88811);
			riskControlResponse.setTotalCount(99911);
			riskControlResponse.setCode("s");
			riskControlResponse.setMsg("成功返回");
		} catch (Exception e) {
			logger.error("传输数据格式错误:" + e.toString());
		}
		String response = null;
		try {
			response = JsonTransferUtils.bean2Json(riskControlResponse);
		} catch (IOException e) {
			logger.error("法海服务接口返回值转化JSON失败", e);
		}
		logger.info("法海返回信息:" + response);
		logger.info("法海服务结束-----------------------end");
		return response;
	}
}
