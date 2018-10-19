package com.resoft.outinterface.SV.server;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.credit.mortgageCarEvaluateInfo.entity.MortgageCarEvaluateInfo;
import com.resoft.credit.mortgageCarEvaluateInfo.service.MortgageCarEvaluateInfoService;
import com.resoft.credit.mortgageHouseInfo.entity.MortgageHouseInfo;
import com.resoft.credit.mortgageHouseInfo.service.MortgageHouseInfoService;
import com.resoft.outinterface.SV.server.entry.request.SVRequest;
import com.resoft.outinterface.SV.server.entry.request.infomation.InformationMatchServerRequest;
import com.resoft.outinterface.SV.server.entry.request.infomation.MortgageCarEvaluateInfoServerRequest;
import com.resoft.outinterface.SV.server.entry.request.infomation.MortgageHouseInfoServerRequest;
import com.resoft.outinterface.SV.server.entry.response.SVResponse;
import com.resoft.outinterface.SV.service.SVRequestService;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

@WebService(endpointInterface = "com.resoft.outinterface.SV.server.SVServeInterface")
public class SVServeImpl implements SVServeInterface {
	private static final Logger logger = LoggerFactory.getLogger(SVServeImpl.class);

	@Autowired
	private SVRequestService svRequestService;
	
	private MortgageHouseInfoService mortgageHouseInfoService = SpringContextHolder.getBean("mortgageHouseInfoService");
	private MortgageCarEvaluateInfoService mortgageCarEvaluateInfoService = SpringContextHolder.getBean("mortgageCarEvaluateInfoService");
	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");

	@Override
	public String SVInvestInfoResponse(String svr) {
		Date sendDate = new Date();
		logger.info("SV服务启动-----------start");
		logger.info(svr);
		SVRequest str = null;
		String restr = "";
		SVResponse svrn = new SVResponse();
		try {
			str = Facade.facade.XMLStringToBean(svr, SVRequest.class);
		} catch (Exception e) {
			logger.error("反序列化失败！" + "/t" + e.toString());
			svrn.setMsg("XML格式不正确");
			svrn.setRespCode("1111");
			svrn.setSuccess(false);
			restr = this.transferXml(svrn);
			return restr;
		}
		try {
			svRequestService.saveSVRequest(str);
		} catch (Exception e1) {
			logger.error("数据入库失败！" + "\t" + e1.toString(), e1);
			svrn.setMsg("数据入库失败！请查看日志更正。");
			svrn.setRespCode("1111");
			svrn.setSuccess(false);
			restr = this.transferXml(svrn);
			return restr;
		}
		svrn.setMsg("成功");
		svrn.setRespCode("0000");
		svrn.setSuccess(true);
		restr = this.transferXml(svrn);
		try {
			interfaceInfoService.save(new InterfaceInfo(str.getHeader().getApplyNo(), "SV服务", svrn.getMsg(), sendDate, svr));
		} catch (Exception e) {
			logger.error("接口信息记录失败", e);
		}
		logger.info("SV服务结束-----------end");
		return restr;
	}

	@Override
	public String SVInformationMatchResponse(String message) throws Exception {

		Date sendDate = new Date();
		logger.info("SV字段级对接服务启动-----------start");
		logger.info(message);
		InformationMatchServerRequest str = null;
		String restr = "";
		SVResponse svrn = new SVResponse();
		try {
			str = Facade.facade.XMLStringToBean(message, InformationMatchServerRequest.class);
		} catch (Exception e) {
			logger.error("反序列化失败！" + "/t" + e.toString());
			svrn.setMsg("XML格式不正确");
			svrn.setRespCode("1111");
			svrn.setSuccess(false);
			restr = this.transferXml(svrn);
			return restr;
		}
		try {
			List<MortgageCarEvaluateInfoServerRequest> carRequests = str.getMortgageCarEvaluateInfoServerRequests();
			if (carRequests != null && carRequests.size() != 0) {
				for (MortgageCarEvaluateInfoServerRequest car : carRequests) {
					MortgageCarEvaluateInfo info = null;
					info = mortgageCarEvaluateInfoService.findListByCarId(car.getWorkId());
					if (info != null) {
						info.setCarId(car.getWorkId());
						info.setVehicleNo(car.getVehicleNo());
						info.setEngineNum(car.getEngineNum());
						info.setVehicleShelfNo(car.getVehicleShelfNo());
						info.setNetPurchasePrice(car.getNetPurchasePrice());
						info.setTravelKms(car.getTravelKms());
						info.setCarStatus(car.getCarStatus());
					} else {
						info = new MortgageCarEvaluateInfo();
						info.setCarId(car.getWorkId());
						info.setVehicleNo(car.getVehicleNo());
						info.setEngineNum(car.getEngineNum());
						info.setVehicleShelfNo(car.getVehicleShelfNo());
						info.setNetPurchasePrice(car.getNetPurchasePrice());
						info.setTravelKms(car.getTravelKms());
						info.setCarStatus(car.getCarStatus());
					}
					mortgageCarEvaluateInfoService.save(info);
				}
			}

			List<MortgageHouseInfoServerRequest> houseRequests = str.getMortgageHouseInfoServerRequests();
			if (houseRequests != null && houseRequests.size() != 0) {
				for (MortgageHouseInfoServerRequest house : houseRequests) {
					MortgageHouseInfo info = null;
					info = mortgageHouseInfoService.get(house.getWorkId());
					info.setHouseYears(house.getHouseYears());
					info.setServiceLife(house.getServiceLife());
					info.setHousePurchasePrice(house.getHousePurchasePrice());
					info.setIsKeepPapers(house.getIsKeepPapers());
					mortgageHouseInfoService.save(info);
				}
			}
		} catch (Exception e1) {
			logger.error("数据入库失败！" + "\t" + e1.toString(), e1);
			svrn.setMsg("数据入库失败！请查看日志更正。");
			svrn.setRespCode("1111");
			svrn.setSuccess(false);
			restr = this.transferXml(svrn);
			return restr;
		}
		svrn.setMsg("成功");
		svrn.setRespCode("0000");
		svrn.setSuccess(true);
		restr = this.transferXml(svrn);
		try {
			interfaceInfoService.save(new InterfaceInfo(str.getApplyNo(), "SV字段级对接服务", svrn.getMsg(), sendDate, message));
		} catch (Exception e) {
			logger.error("接口信息记录失败", e);
		}
		logger.info("SV字段级对接服务结束-----------end");
		return restr;
	}

	public String transferXml(SVResponse svrn) {
		String restr = "";
		try {
			restr = Facade.facade.BeanToXmlString(svrn, SVResponse.class);
		} catch (Exception e) {
			logger.error("SV接口数据转换错误", e);
		}
		return restr;
	}
	
	
	
}
