package com.resoft.outinterface.rest.fh.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.fhRiskControl.entity.FhRiskControl;
import com.resoft.credit.fhRiskControl.service.FhRiskControlService;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.rest.fh.client.entry.response.FhRiskControlResponse;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;

@RestController
@RequestMapping(value = "/f/rest/fh/client")
public class FhRiskControlClient {
	private static final Logger logger = LoggerFactory.getLogger(FhRiskControlResponse.class);
	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");
	private FhRiskControlService fhRiskControlService = SpringContextHolder.getBean("fhRiskControlService");

	@RequestMapping(method = RequestMethod.POST, value = "fhRiskControlRequest")
	public Map<String, Object> fhRiskControl(String authCode, String applyNo, String custId, String custName, String idCard) throws Exception {
		String json = null;
		String response = null;
		String custNameEncode = null;
		String custNameTemp = null;
		custNameTemp = URLEncoder.encode(custName,"utf-8");
		custNameEncode = URLEncoder.encode(custNameTemp,"utf-8");
		if (StringUtils.isNull(idCard)) {
			json = "?authCode=" + authCode + "&companyName=" + custNameEncode + "&applyNo=" + applyNo + "&custId=" + custId;
		} else {
			json = "?authCode=" + authCode + "&name=" + custNameEncode + "&idCard=" + idCard + "&applyNo=" + applyNo + "&custId=" + custId;
		}
		logger.info("发送信息：" + json);
		String baseUrl = Global.getConfig("fhRiskControl");
		String pageUrl = baseUrl + json;
		logger.info("访问url：" + pageUrl);
		Map<String, Object> params = Maps.newHashMap();
		try {
			response = connToFH(pageUrl);
			// response =
			// OutInterfaceUtils.sendPost(Global.getConfig("fhRiskControl"),
			// json);
			logger.info("返回信息END：" + response);
			if (StringUtils.isNull(response)) {
				params.put("code", "fail");
				params.put("msg", "返回信息为空");
			} else {
				JSONObject jsonObject = JSONObject.fromObject(response);
				FhRiskControlResponse fhRiskControlResponse = (FhRiskControlResponse) JSONObject.toBean(jsonObject, FhRiskControlResponse.class);
				params.put("code", fhRiskControlResponse.getCode());
				params.put("msg", fhRiskControlResponse.getMsg());
				insertData(fhRiskControlResponse);
				try {
					String message = "发送信息：" + pageUrl + "。返回信息：" + response;
					interfaceInfoService.save(new InterfaceInfo(applyNo, "法海风控预查接口", "成功！", new Date(), message));
				} catch (Exception e) {
					logger.error("接口信息记录失败！", e);
				}
			}
		} catch (Exception e) {
			logger.error("法海风控预查接口调用失败！", e);
			params.put("code", "fail");
			params.put("msg", "法海风控预查接口调用失败");
			try {
				String message = "发送信息：" + pageUrl + "。返回信息：" + response;
				interfaceInfoService.save(new InterfaceInfo(applyNo, "法海风控预查接口", "失败！" + e.toString(), new Date(), message));
			} catch (Exception e1) {
				logger.error("接口信息记录失败！", e1);
			}
		}
		logger.info("返回信息END：" + response);
		return params;
	}

	@RequestMapping(method = RequestMethod.POST, value = "fhRiskControlReport")
	public Map<String, Object> fhRiskControlReport(String authCode, String applyNo, String custId, String custName, String idCard) throws Exception {
		String json = null;
		String response = null;
		String custNameEncode = null;
		String custNameTemp = null;
		custNameTemp = URLEncoder.encode(custName,"utf-8");
		custNameEncode = URLEncoder.encode(custNameTemp,"utf-8");
		if (StringUtils.isNull(idCard)) {
			json = "?authCode=" + authCode + "&companyName=" + custNameEncode + "&applyNo=" + applyNo + "&custId=" + custId;
		} else {
			json = "?authCode=" + authCode + "&name=" + custNameEncode + "&idCard=" + idCard + "&applyNo=" + applyNo + "&custId=" + custId;
		}
		logger.info("发送信息：" + json+custName);
		String baseUrl = Global.getConfig("fhRiskControlReport");
		String pageUrl = baseUrl + json;
		logger.info("访问url：" + pageUrl);
		Map<String, Object> params = Maps.newHashMap();
		try {
			response = connToFH(pageUrl);
			// response =
			// OutInterfaceUtils.sendPost(Global.getConfig("fhRiskControlReport"),
			// json);
			logger.info("返回信息END：" + response);
			if (StringUtils.isNull(response)) {
				params.put("code", "fail");
				params.put("msg", "返回信息为空");
			} else {
				JSONObject jsonObject = JSONObject.fromObject(response);
				FhRiskControlResponse fhRiskControlResponse = (FhRiskControlResponse) JSONObject.toBean(jsonObject, FhRiskControlResponse.class);
				params.put("code", fhRiskControlResponse.getCode());
				params.put("msg", fhRiskControlResponse.getMsg());
				try {
					String message = "发送信息：" + pageUrl+"--"+custName + "。返回信息：" + response;
					interfaceInfoService.save(new InterfaceInfo(applyNo, "法海风控生成报告接口", "成功！", new Date(), message));
				} catch (Exception e) {
					logger.error("接口信息记录失败！", e);
				}
			}
		} catch (Exception e) {
			logger.error("法海风控生成报告接口调用失败！", e);
			params.put("code", "fail");
			params.put("msg", "法海风控生成报告接口调用失败");
			try {
				String message = "发送信息：" + pageUrl+"--"+custName + "。返回信息：" + response;
				interfaceInfoService.save(new InterfaceInfo(applyNo, "法海风控生成报告接口", "失败！" + e.toString(), new Date(), message));
			} catch (Exception e1) {
				logger.error("接口信息记录失败！", e1);
			}
		}
		logger.info("返回信息END：" + response);
		return params;
	}

	void insertData(FhRiskControlResponse fhRiskControlResponse) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("applyNo", fhRiskControlResponse.getApplyNo());
		params.put("custId", fhRiskControlResponse.getCustId());
		Long checkNumLong = fhRiskControlService.getCountByApplyNoAndCustId(params);
		Integer checkNum = (checkNumLong.intValue()) + 1;
		FhRiskControl fhRiskControl = new FhRiskControl();
		fhRiskControl.setApplyNo(fhRiskControlResponse.getApplyNo());
		fhRiskControl.setCustId(fhRiskControlResponse.getCustId());
		fhRiskControl.setCheckNum(checkNum.toString());
		fhRiskControl.setKtggCount(String.valueOf(fhRiskControlResponse.getKtggCount()));
		fhRiskControl.setAjlcCount(String.valueOf(fhRiskControlResponse.getAjlcCount()));
		fhRiskControl.setNewsCount(String.valueOf(fhRiskControlResponse.getNewsCount()));
		fhRiskControl.setCpwsCount(String.valueOf(fhRiskControlResponse.getCpwsCount()));
		fhRiskControl.setZxggCount(String.valueOf(fhRiskControlResponse.getZxggCount()));
		fhRiskControl.setSxggCount(String.valueOf(fhRiskControlResponse.getSxggCount()));
		fhRiskControl.setFyggCount(String.valueOf(fhRiskControlResponse.getFyggCount()));
		fhRiskControl.setBgtCount(String.valueOf(fhRiskControlResponse.getBgtCount()));
		fhRiskControl.setTotalCount(String.valueOf(fhRiskControlResponse.getTotalCount()));
		if (fhRiskControlResponse.getKtggCount() != 0 || fhRiskControlResponse.getAjlcCount() != 0 || fhRiskControlResponse.getNewsCount() != 0 || fhRiskControlResponse.getCpwsCount() != 0 || fhRiskControlResponse.getZxggCount() != 0 || fhRiskControlResponse.getSxggCount() != 0 || fhRiskControlResponse.getFyggCount() != 0 || fhRiskControlResponse.getBgtCount() != 0) {
			fhRiskControl.setReportStatus(Constants.FH_REPORT_STATUS_WSC);
		}
		fhRiskControlService.save(fhRiskControl);
	}

	String connToFH(String pageUrl) throws IOException {
		StringBuilder pageHTML = new StringBuilder();
		URL url = new URL(pageUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		if (connection != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				pageHTML.append(line);
				pageHTML.append("\r\n");
			}
			reader.close();
		}
		connection.disconnect();
		return pageHTML.toString();
	}

}
