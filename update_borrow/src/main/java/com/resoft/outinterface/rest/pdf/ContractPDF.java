package com.resoft.outinterface.rest.pdf;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.rest.newged.entity.AddOrderRequestList;
import com.resoft.outinterface.rest.newged.entity.AddOrderResponse;
import com.resoft.outinterface.rest.newged.entity.ContractPDFAllResponse;
import com.resoft.outinterface.rest.newged.entity.ContractPDFRequest;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;


@RestController
@RequestMapping(value = "/f/rest/contractPDF/service")
public class ContractPDF {
	private static final Logger logger = LoggerFactory.getLogger(ContractPDF.class);
	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");
	
	
	public ContractPDFAllResponse getContractPDF(ContractPDFRequest contractPDFRequest,String contract) {
		String json=null;
		String responseInfo=null;
		ContractPDFAllResponse contractPDFAllResponse=null;
		try {
			json = JsonTransferUtils.bean2Json(contractPDFRequest);
			logger.info(json);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("contractPDF"), json);
			if(StringUtils.isEmpty(responseInfo)) {
				interfaceInfoService.save(new InterfaceInfo(contract, "获取合同相关PDF接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
				return null;
			}
			contractPDFAllResponse = JsonTransferUtils.json2Bean(responseInfo, ContractPDFAllResponse.class);
			interfaceInfoService.save(new InterfaceInfo(contract, "获取合同相关PDF接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {//接口异常
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( contract, "获取合同相关PDF接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return contractPDFAllResponse;
	}


	public HashMap<String, Object> getContractFacePDF(ContractPDFRequest contractPDFRequest, String contract) {
		String json=null;
		String responseInfo=null;
		HashMap<String, Object> hashMap=null;
		try {
			json = JsonTransferUtils.bean2Json(contractPDFRequest);
			logger.info(json);
			 Map<String, String> paras = new HashedMap();
			paras.put("contractNo", contract);
			responseInfo = OutInterfaceUtils.doPostForm(Global.getConfig("contractFacePDF"), paras);
			hashMap = (HashMap<String, Object>) JsonTransferUtils.json2Bean(responseInfo, Map.class);
			interfaceInfoService.save(new InterfaceInfo(contract, "获取合同PDF接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {//接口异常
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo( contract, "获取合同PDF接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
		return hashMap;
	}
}
