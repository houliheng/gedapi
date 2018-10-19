package com.resoft.outinterface.cn.com.experian.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.cn.com.experian.entry.request.CallGqRequest;
import com.resoft.outinterface.cn.com.experian.entry.request.body.AppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.CoAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.CoAppsInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.FirmInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.FirmsInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.GuAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.GuAppsInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.HouseInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.HousesInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.LoanInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.PBOCAppInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.PBOCFirmInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.PBOCFirms;
import com.resoft.outinterface.cn.com.experian.entry.request.body.RequestInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.VehicleInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.body.VehiclesInfo;
import com.resoft.outinterface.cn.com.experian.entry.request.head.ExperianRequestHeader;
import com.resoft.outinterface.cn.com.experian.entry.response.ExperianResponse;
import com.resoft.outinterface.cn.com.experian.service.ExperianClientService;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

@RestController
@RequestMapping(value = "/f/webService/experian")
public class ExperianClient {
	private static final Logger logger = LoggerFactory.getLogger(ExperianClient.class);
	@Autowired
	private ExperianClientService experianClientService;
	SpringContextHolder sch = new SpringContextHolder();

	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");

	@RequestMapping(method = RequestMethod.GET, value = "GetExperianClient/{applyNo}")
	public String getExperianClient(@PathVariable("applyNo") String applyNo) {
		// System.out.println(arg0);
		String xml = "";
		RequestInfo requestInfo = new RequestInfo();
		if (experianClientService == null) {
			experianClientService = (ExperianClientService) sch.getBean("ExperianClientService");
		}
		Date sendDate = new Date();
		try {
			// 贷款信息
			LoanInfo loanInfo = new LoanInfo();
			loanInfo = experianClientService.getLoanInfoByApplyNo(applyNo);

			// 抵押房产信息
			List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();
			houseInfoList = experianClientService.getHouseInfoByApplyNo(applyNo);
			HousesInfo housesInfo = new HousesInfo();
			housesInfo.setHouseInfo(houseInfoList);

			// 抵押车辆信息
			List<VehicleInfo> vehiclesInfoList = new ArrayList<VehicleInfo>();
			vehiclesInfoList = experianClientService.getVehiclesInfoByApplyNo(applyNo);
			VehiclesInfo vehiclesInfo = new VehiclesInfo();
			vehiclesInfo.setVehicleInfo(vehiclesInfoList);

			// 共借人信息
			List<CoAppInfo> coAppList = new ArrayList<CoAppInfo>();
			coAppList = experianClientService.getCoAppInfoByApplyNo(applyNo);
			CoAppsInfo coApps = new CoAppsInfo();
			coApps.setCoAppInfo(coAppList);

			// 借款人信息
			AppInfo app = new AppInfo();
			app = experianClientService.getAppInfoByApplyNo(applyNo);

			// 担保人信息
			List<GuAppInfo> guAppList = new ArrayList<GuAppInfo>();
			guAppList = experianClientService.getGuAppInfoByApplyNo(applyNo);
			GuAppsInfo guAppsInfo = new GuAppsInfo();
			guAppsInfo.setGuAppInfo(guAppList);

			// 企业信息
			List<FirmInfo> firmList = new ArrayList<FirmInfo>();
			firmList = experianClientService.getFirmInfoByApplyNo(applyNo);
			FirmsInfo firmsInfo = new FirmsInfo();
			firmsInfo.setFirmInfo(firmList);

			// 征信共借人信息
			/*
			 * List<PBOCCoAppInfo> pbocCoAppList = new
			 * ArrayList<PBOCCoAppInfo>(); pbocCoAppList =
			 * experianClientService.getPBOCCoAppByApplyNo(applyNo);
			 * PBOCCoAppsInfo pbocCoAppsInfo = new PBOCCoAppsInfo();
			 * pbocCoAppsInfo.setPbocCoAppInfo(pbocCoAppList);
			 */
			// 征信借款人信息
			PBOCAppInfo pbocAppInfo = new PBOCAppInfo();
			pbocAppInfo = experianClientService.getPBOCAppInfoByApplyNo(applyNo);

			// 征信中企业信息
			List<PBOCFirmInfo> pbocFirmList = new ArrayList<PBOCFirmInfo>();
			pbocFirmList = experianClientService.getPBOCFirmInfoByApplyNo(applyNo);
			PBOCFirms pbocFirms = new PBOCFirms();
			pbocFirms.setPbocFirmInfo(pbocFirmList);

			// 征信中担保人信息
			/*
			 * List<PBOCGuAppInfo> pbocGuAppInfoList = new
			 * ArrayList<PBOCGuAppInfo>(); pbocGuAppInfoList =
			 * experianClientService.getPBOCGuAppInfoByApplyNo(applyNo);
			 * PBOCGuApps pbocGuApps = new PBOCGuApps();
			 * pbocGuApps.setPbocGuAppInfo(pbocGuAppInfoList);
			 */

			requestInfo.setLoan(loanInfo);
			requestInfo.setHousesInfo(housesInfo);
			requestInfo.setVehiclesInfo(vehiclesInfo);
			requestInfo.setCoApps(coApps);

			requestInfo.setGuApps(guAppsInfo);
			requestInfo.setFirms(firmsInfo);
			// requestInfo.setPbocCoApps(pbocCoAppsInfo);
			requestInfo.setPbocApp(pbocAppInfo);
			requestInfo.setPbocFirms(pbocFirms);
			// requestInfo.setPbocGuApps(pbocGuApps);
			CallGqRequest cgr = new CallGqRequest();

			ExperianRequestHeader erh = experianClientService.getExperianRequestHeader(applyNo, app);
			// app中多加了一个参数用来接收客户名称
			app.setCustName(null);
			requestInfo.setApp(app);
			cgr.setHead(erh);
			cgr.setBody(requestInfo);
			xml = Facade.facade.BeanToXmlString(cgr, CallGqRequest.class);
			logger.debug(xml);
			ExperianResponse experianResponse = Facade.facade.getDispatchedInterface(3, ExperianResponse.class, applyNo, xml);
			logger.debug(Facade.facade.BeanToXmlString(experianResponse, ExperianResponse.class));
			experianClientService.insertExperianResponse(experianResponse);
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "Experian接口", experianResponse.getHead().getResultInfo(), sendDate,xml));
			} catch (Exception e) {
				logger.error("接口信息记录失败！", e);
			}
		} catch (Exception e) {
			logger.error("Experian接口访问失败", e);
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "Experian接口", "接口访问失败:" + e, sendDate,xml));
			} catch (Exception e2) {
				logger.error("接口信息记录失败！", e2);
			}
		}
		return xml;
	}
}