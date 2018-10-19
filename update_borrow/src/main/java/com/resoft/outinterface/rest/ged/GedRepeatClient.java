package com.resoft.outinterface.rest.ged;

import com.alibaba.fastjson.JSONObject;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.JsonUtil;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/f/rest/ged/service")
public class GedRepeatClient {

	private static final Logger logger = LoggerFactory.getLogger(GedRepeatClient.class);

	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");

	/**
	 * 企业开户退回推给冠E贷
	 * @param reason
	 * @param status
	 */
	public void repeatAccount(String socialCreditCode,String  reason,String status,String userId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("socialCreditCode", socialCreditCode);
		params.put("returnReason",reason);
		params.put("status", status);
		params.put("userId", userId);
		String json=null;
		String responseInfo=null;
		logger.info("回退接口返回的参数值="+ JSONObject.toJSONString(params));
		try {
			json = JsonUtil.getJSONString(params);
			responseInfo = OutInterfaceUtils.load(Global.getConfig("repeatAccount"), json);
			interfaceInfoService.save(new InterfaceInfo(socialCreditCode, "企业开户退回推给冠E贷状态接口","返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceInfoService.save(new InterfaceInfo(socialCreditCode, "企业开户退回推给冠E贷状态接口","异常信息"+e.toString()+"返回信息:"+responseInfo, new Date(),"发送信息:"+json));
		}
	}

}
