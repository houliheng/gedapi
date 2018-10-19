package com.resoft.Accoutinterface.gqget.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.Accoutinterface.gqget.server.entity.BankInfo;
import com.resoft.Accoutinterface.gqget.server.entity.GqgetBody;
import com.resoft.Accoutinterface.gqget.server.entity.GqgetCustInfo;
import com.resoft.Accoutinterface.gqget.server.entity.GqgetResponse;
import com.resoft.Accoutinterface.gqget.server.service.AccGqgetService;
import com.resoft.common.utils.JsonTransferUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/f/rest/api/")
public class AccGqgetServer {

	private static final Logger logger = LoggerFactory.getLogger(AccGqgetServer.class);
	private AccGqgetService accGqgetService=SpringContextHolder.getBean("accGqgetService");
	/**
	 * 客户信息同步接口
	 *
	 * @param json
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "synchroGQgetInfo")
	public String synchroGQgetInfo(@RequestBody String json) {
		logger.info("客户信息同步接口-----------start");
		logger.info(json);
		JSONObject jsonObj = JSONObject.fromObject(json);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("results", GqgetCustInfo.class);
		classMap.put("bankInfo", BankInfo.class);
		GqgetBody gCustInfo = null;
		GqgetResponse grs = null;
		try {
			gCustInfo = (GqgetBody) JSONObject.toBean(jsonObj, GqgetBody.class, classMap);
		} catch (Exception e) {
			logger.error("传入参数字段不匹配",e);
			grs = new GqgetResponse("9999","传入参数字段不匹配！");
		}
		try {
			for (GqgetCustInfo cif : gCustInfo.getResults()) {
				accGqgetService.saveGqgetInfo(cif);
				for (BankInfo bif : cif.getBankInfo()) {
					bif.setGqgetCustId(cif.getGqgetCustId());
					accGqgetService.saveBankcardInfo(bif);
				}
			}
			grs = new GqgetResponse("0000","信息同步成功！");
		} catch (Exception e) {
			logger.error("信息保存失败！",e);
			grs = new GqgetResponse("9999","数据库保存信息失败！");
		}
		String result = null;
		try {
			result = JsonTransferUtils.bean2Json(grs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("客户信息同步接口-----------end");
		return result;
	}
}
