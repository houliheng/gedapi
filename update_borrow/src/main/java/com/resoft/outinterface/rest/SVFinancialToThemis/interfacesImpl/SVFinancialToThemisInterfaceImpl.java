package com.resoft.outinterface.rest.SVFinancialToThemis.interfacesImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.resoft.common.utils.HttpRequestUtil;
import com.resoft.credit.financialStateImport.entity.req.*;
import com.resoft.outinterface.themis.entry.request.*;
import com.resoft.outinterface.themis.server.ThemisRequestServer;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.financialStateImport.entity.ReportInfo4Excel;
import com.resoft.credit.financialStateImport.entity.ThemisReportHead;
import com.resoft.credit.financialStateImport.entity.ThemisReportInfo;
import com.resoft.credit.financialStateImport.service.FinancialStateImportService;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.rest.SVFinancialToThemis.entity.SVFinancialToThemisRequest;
import com.resoft.outinterface.rest.SVFinancialToThemis.entity.SVFinancialToThemisResponse;
import com.resoft.outinterface.rest.SVFinancialToThemis.interfaces.SVFinancialToThemisInterface;
import com.resoft.outinterface.themis.entry.response.ThemisResponse;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;

@RestController
@RequestMapping(value = "/f/rest/SVReceive/server")
public class SVFinancialToThemisInterfaceImpl implements SVFinancialToThemisInterface{

	private static final Logger logger = LoggerFactory.getLogger(SVFinancialToThemisInterfaceImpl.class);

	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");
	
	private FinancialStateImportService financialStateImportService = SpringContextHolder.getBean("financialStateImportService");


	private ThemisRequestServer themisRequestServer = SpringContextHolder.getBean("themisRequestServer");

	@RequestMapping(method = RequestMethod.POST, value = "sendFinanceReportToThemis")
	public String sendFinanceReportToThemis(@RequestBody String json) {// 先存储本地再推送themie
		logger.info("开始接收外访系统传递的数据------------------------");
		logger.info("开始接收外访系统传递的数据" + json);
		int saveFlag=0;//插入数据库的标志位
		String str = "";//返回值
		String exceptionMsg="";//异常信息
		String interfaceName="外访系统导入财报";//接口名字
		json=json.trim();
		String applyNo = "";
		ThemisReportHead themisReportHead = null;//sheet1
		List<ThemisReportInfo> themisReportInfoList1 = null;//sheet2
		List<ThemisReportInfo> themisReportInfoList2 = null;//sheet3
		SVFinancialToThemisRequest trh = new SVFinancialToThemisRequest();
		try {
			if(StringUtils.isEmpty(json)){
				exceptionMsg="外访系统传递数据为空";
				SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(false, "0000", "外访系统传递数据为空。");
				str = JsonTransferUtils.bean2Json(sr);
				interfaceInfoService.save(new InterfaceInfo(null, interfaceName, "接口被调用失败:sv系统传递数据为空", new Date(),json));
				logger.info("返回信息：" + str);
				return str;
			}
			try {
				logger.info("-----开始解析数据-------");
				trh = (SVFinancialToThemisRequest) JsonTransferUtils.json2Bean(json, SVFinancialToThemisRequest.class);
				logger.info("-----解析数据成功-------");
				themisReportHead = trh.getTrh();// 公司信息
				//外访系统推送数据存放的id为9999
				User user=new User();
				user.setId("9999");
				themisReportHead.setUser(user);
				themisReportInfoList1 = trh.getTriList1();
				themisReportInfoList2 = trh.getTriList2();
				applyNo = themisReportHead.getApplyNo();
			} catch (Exception e1) {
				e1.printStackTrace();
				exceptionMsg=e1.toString();
				SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(false, "0000", "解析数据发生错误"+exceptionMsg);
				str = JsonTransferUtils.bean2Json(sr);
				interfaceInfoService.save(new InterfaceInfo(applyNo, interfaceName, "接口被调用失败:解析数据发生错误"+exceptionMsg, new Date(),json));
				logger.info("返回信息：" + str);
				return str;
			}
			if (StringUtils.isNotEmpty(trh.getTrh().getApplyNo())) {//插入数据库
				try {
					if (themisReportInfoList1.get(0).getReportInfo().size() == 39) {
						for (ThemisReportInfo themisReportInfo : themisReportInfoList2) {
							List<ReportInfo4Excel> reportInfo = themisReportInfo.getReportInfo();
							reportInfo.remove(73);
							reportInfo.remove(63);
							reportInfo.remove(40);
							reportInfo.remove(19);
						}
						financialStateImportService.saveSVExcel(themisReportHead, themisReportInfoList2,
								themisReportInfoList1, themisReportHead.getApplyNo());
						saveFlag=1;
					} else {
						for (ThemisReportInfo themisReportInfo : themisReportInfoList1) {
							List<ReportInfo4Excel> reportInfo = themisReportInfo.getReportInfo();
							reportInfo.remove(73);
							reportInfo.remove(63);
							reportInfo.remove(40);
							reportInfo.remove(19);
						}
						financialStateImportService.saveSVExcel(themisReportHead, themisReportInfoList1,
								themisReportInfoList2, themisReportHead.getApplyNo());
						saveFlag=1;
					}
				} catch (Exception e) {
					e.printStackTrace();
					exceptionMsg=e.toString();
					SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(false, "0000", "借款系统保存失败"+exceptionMsg);
					if(saveFlag==1){
						financialStateImportService.deleteSVFinancial(applyNo);
					}
					str = JsonTransferUtils.bean2Json(sr);
					interfaceInfoService.save(new InterfaceInfo(applyNo, interfaceName, "借款系统发生异常："+exceptionMsg, new Date(),json));
					logger.info("返回信息：" + str);
					return str;
				}


				ReportData reportData = new ReportData();
				RequestParamData requestParamData = new RequestParamData();

				//财报信息
				List<NewFinanceBS> lnfbs = new ArrayList<NewFinanceBS>();
				List<NewFinancePL> lnfpl = new ArrayList<NewFinancePL>();
				List<String> bs = themisRequestServer.findYearMonthByApply("NEW_FINANCE_BS", applyNo);
				System.out.println(bs);
				Iterator<String> it = bs.iterator();
				while(it.hasNext()){
					String str2 = it.next();
					System.out.println(str2);
					NewFinanceBS nfbs = new NewFinanceBS();
					try {
						List<String> ls=themisRequestServer.findFinancialReport("NEW_FINANCE_BS", str2, applyNo);
						ReportInfoBS rbs = OutInterfaceUtils.listToBean(ls, ReportInfoBS.class);
						nfbs.setReportinfo(rbs);
						nfbs.setReportyear(str2.substring(0, 4));
						nfbs.setReportmonth(str2.substring(4));
						lnfbs.add(nfbs);
					} catch (Exception e) {
						logger.error("BS财报查询失败，请检查日志",e);
						interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "BS财报查询失败", new Date(),e.toString()));
						return null;
					}
				}
				List<String> pls = themisRequestServer.findYearMonthByApply("NEW_FINANCE_PL", applyNo);
				Iterator<String> its = pls.iterator();
				while(its.hasNext()){
					String str2 = its.next();
					NewFinancePL nfpl = new NewFinancePL();
					try {
						List<String> ls=themisRequestServer.findFinancialReport("NEW_FINANCE_PL", str2, applyNo);
						ReportInfoPL rpl = OutInterfaceUtils.listToBean(ls, ReportInfoPL.class);
						nfpl.setReportinfo(rpl);
						nfpl.setReportyear(str2.substring(0, 4));
						nfpl.setReportmonth(str2.substring(4));
						lnfpl.add(nfpl);
					} catch (Exception e) {
						logger.error("PL财报查询失败，请检查日志",e);
						interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "PL财报查询失败", new Date(),e.toString()));
						return null;
					}
				}
				List<FinanceBs>  financeBs =new ArrayList<FinanceBs>();
				List<FinancePl> financePl = new ArrayList<FinancePl>();
				FinanceBs b = null;
				for (NewFinanceBS ebs:lnfbs ){
					ReportInfoBS reportinfo = ebs.getReportinfo();
					b=new FinanceBs();
					b.setReportYear(ebs.getReportyear());
					b.setReportMonth(ebs.getReportmonth());
					b.setB1101(reportinfo.getValue1());
					b.setB1102(reportinfo.getValue2());
					b.setB1103(reportinfo.getValue3());
					b.setB1104(reportinfo.getValue4());
					b.setB1105(reportinfo.getValue5());
					b.setB1106(reportinfo.getValue6());
					b.setB1107(reportinfo.getValue7());
					b.setB1108(reportinfo.getValue8());
					b.setB1109(reportinfo.getValue9());
					b.setB1110(reportinfo.getValue10());
					b.setB1111(reportinfo.getValue11());
					b.setB1112(reportinfo.getValue12());
					b.setB1113(reportinfo.getValue13());
					b.setB1114(reportinfo.getValue14());
					b.setB1115(reportinfo.getValue15());
					b.setB111501(reportinfo.getValue16());
					b.setB1116(reportinfo.getValue17());
					b.setB1117(reportinfo.getValue18());
					b.setB11(reportinfo.getValue19());
					b.setB1201(reportinfo.getValue20());
					b.setB1202(reportinfo.getValue21());
					b.setB1203(reportinfo.getValue22());
					b.setB1204(reportinfo.getValue23());
					b.setB1205(reportinfo.getValue24());
					b.setB1206(reportinfo.getValue25());
					b.setB1207(reportinfo.getValue26());
					b.setB1208(reportinfo.getValue27());
					b.setB1209(reportinfo.getValue28());
					b.setB1210(reportinfo.getValue29());
					b.setB1211(reportinfo.getValue30());
					b.setB1212(reportinfo.getValue31());
					b.setB1213(reportinfo.getValue32());
					b.setB1214(reportinfo.getValue33());
					b.setB1215(reportinfo.getValue34());
					b.setB1216(reportinfo.getValue35());
					b.setB1217(reportinfo.getValue36());
					b.setB1218(reportinfo.getValue37());
					b.setB12(reportinfo.getValue38());
					b.setB1(reportinfo.getValue39());
					b.setB2101(reportinfo.getValue40());
					b.setB2102(reportinfo.getValue41());
					b.setB2103(reportinfo.getValue42());
					b.setB2104(reportinfo.getValue43());
					b.setB2105(reportinfo.getValue44());
					b.setB2106(reportinfo.getValue45());
					b.setB2107(reportinfo.getValue46());
					b.setB2108(reportinfo.getValue47());
					b.setB2109(reportinfo.getValue48());
					b.setB2110(reportinfo.getValue49());
					b.setB2111(reportinfo.getValue50());
					b.setB2112(reportinfo.getValue51());
					b.setB2113(reportinfo.getValue52());
					b.setB2114(reportinfo.getValue53());
					b.setB2115(reportinfo.getValue54());
					b.setB2116(reportinfo.getValue55());
					b.setB2117(reportinfo.getValue56());
					b.setB2118(reportinfo.getValue57());
					b.setB2119(reportinfo.getValue58());
					b.setB2120(reportinfo.getValue59());
					b.setB2121(reportinfo.getValue60());
					b.setB21(reportinfo.getValue61());
					b.setB2201(reportinfo.getValue62());
					b.setB2202(reportinfo.getValue63());
					b.setB2203(reportinfo.getValue64());
					b.setB2204(reportinfo.getValue65());
					b.setB2205(reportinfo.getValue66());
					b.setB2206(reportinfo.getValue67());
					b.setB2207(reportinfo.getValue68());
					b.setB22(reportinfo.getValue69());
					b.setB2(reportinfo.getValue70());
					b.setB4001(reportinfo.getValue71());
					b.setB4002(reportinfo.getValue72());
					b.setB4003(reportinfo.getValue73());
					b.setB4004(reportinfo.getValue74());
					b.setB4005(reportinfo.getValue75());
					b.setB4006(reportinfo.getValue76());
					b.setB4007(reportinfo.getValue77());
					b.setB40(reportinfo.getValue78());
					b.setB41(reportinfo.getValue79());
					b.setB4(reportinfo.getValue80());
					b.setB5(reportinfo.getValue81());
					financeBs.add(b);
				}
				FinancePl p2=null;
				for (NewFinancePL pl:lnfpl){
					p2=new FinancePl();
					ReportInfoPL reportinfo = pl.getReportinfo();
					p2.setReportYear(pl.getReportyear());
					p2.setReportMonth(pl.getReportmonth());
					p2.setB60(reportinfo.getValue1());
					p2.setB6001(reportinfo.getValue2());
					p2.setB6002(reportinfo.getValue3());
					p2.setB6003(reportinfo.getValue4());
					p2.setB6004(reportinfo.getValue5());
					p2.setB61(reportinfo.getValue6());
					p2.setB6101(reportinfo.getValue7());
					p2.setB6102(reportinfo.getValue8());
					p2.setB6103(reportinfo.getValue9());
					p2.setB6104(reportinfo.getValue10());
					p2.setB6105(reportinfo.getValue11());
					p2.setB6106(reportinfo.getValue12());
					p2.setB6107(reportinfo.getValue13());
					p2.setB6108(reportinfo.getValue14());
					p2.setB6109(reportinfo.getValue15());
					p2.setB6110(reportinfo.getValue1501());
					p2.setB6111(reportinfo.getValue16());
					p2.setB6112(reportinfo.getValue17());
					p2.setB6113(reportinfo.getValue18());
					p2.setB6114(reportinfo.getValue19());
					p2.setB6115(reportinfo.getValue20());
					p2.setB6116(reportinfo.getValue21());
					p2.setB6117(reportinfo.getValue22());
					p2.setB62(reportinfo.getValue23());
					p2.setB6201(reportinfo.getValue24());
					p2.setB6202(reportinfo.getValue25());
					p2.setB6203(reportinfo.getValue26());
					p2.setB63(reportinfo.getValue27());
					p2.setB6301(reportinfo.getValue28());
					p2.setB64(reportinfo.getValue29());
					p2.setB6401(reportinfo.getValue30());
					p2.setB6402(reportinfo.getValue31());
					p2.setB65(reportinfo.getValue32());
					p2.setB6501(reportinfo.getValue33());
					p2.setB6502(reportinfo.getValue34());
					p2.setT66(reportinfo.getValue35());
					p2.setT67(reportinfo.getValue36());
					p2.setT6701(reportinfo.getValue37());
					p2.setT6702(reportinfo.getValue38());
					financePl.add(p2);
				}
				reportData.setFinanceBs(financeBs);
				reportData.setFinancePl(financePl);


				//组装CompanyInfo
				CompanyInfo companyInfo = getCompanyInfo(applyNo);
				if (companyInfo == null) exceptionMsg="Themis接口调用失败，公司信息不能为空";
				requestParamData.setCompanyInfo(companyInfo);
				requestParamData.setReportData(reportData);
				if (requestParamData == null) exceptionMsg="Themis接口调用失败，财报信息不能为空";;
				String reqjson = JsonTransferUtils.bean2Json(requestParamData);
				//调Themis接口
				String addr = Global.getConfig("ThemisRequestUrlAddress");
				String reqParam = reqjson;
				logger.info("财报导入请求参数===》"+ reqParam);
				JSONObject jsonObj = HttpRequestUtil.doHttpClient(addr, reqParam);
				logger.info("返回json====》"+jsonObj.toJSONString());

				//调用themis接口
				if(jsonObj == null || !"200".equals(jsonObj.getString("resCode"))){
					if(saveFlag==1){
						financialStateImportService.deleteSVAndThemis(applyNo);
					}
					SVFinancialToThemisResponse sr=null;
					String themisMsg="";
					if( jsonObj != null && jsonObj.getJSONObject("data") != null && jsonObj.getJSONObject("data").getJSONArray("error") != null){
						JSONArray errorArray= jsonObj.getJSONObject("data").getJSONArray("error");
						StringBuilder sbmsg  = new StringBuilder("导入失败，错误信息如下：");
						for (Iterator iterator = errorArray.iterator(); iterator.hasNext();) {
							JSONObject jsonObject = (JSONObject) iterator.next();
							String msg = jsonObject.getString("msg");
							String code = jsonObject.getString("code");
							sbmsg.append(code+"："+msg+";");
						}
						 exceptionMsg = sbmsg.toString();
						 themisMsg="Themis接口调用失败："+exceptionMsg;
						 sr = new SVFinancialToThemisResponse(false, "0000", themisMsg);
					}else{
						 themisMsg="Themis接口调用失败,详情查看themis接口日志";
						sr = new SVFinancialToThemisResponse(false, "0000", themisMsg);
					}
					str = JsonTransferUtils.bean2Json(sr);
					interfaceInfoService.save(new InterfaceInfo(applyNo,interfaceName, themisMsg, new Date(),json));
				}else{
					exceptionMsg="数据传递成功";
					SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(true, "1111", exceptionMsg);
					str = JsonTransferUtils.bean2Json(sr);
					interfaceInfoService.save(new InterfaceInfo(applyNo, interfaceName, "成功", new Date(),json));
				}
				logger.info("返回信息：" + str);
				return str;
			} else {
				SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(false, "0000", "外访系统数据传递缺失，没有申请编号。");
				str = JsonTransferUtils.bean2Json(sr);
				interfaceInfoService.save(new InterfaceInfo(applyNo,interfaceName,"接口被调用失败:外访系统数据传递缺失，没有申请编号。", new Date(),json));
				logger.info("返回信息：" + str);
				return str;
			}
		} catch (IOException e) {
			logger.error("返回SV值转化JSON失败", e);
			logger.error(e.toString());
			str=exceptionMsg+e.toString();
			if(saveFlag==1){
				financialStateImportService.deleteSVAndThemis(applyNo);
			}
		}
		logger.info("返回信息：" + str);
		return str;
	}


	private CompanyInfo getCompanyInfo(String applyNo) {
		ThemisRequestHead trh1 = new ThemisRequestHead();
		try {
			trh1= themisRequestServer.findThemisReportHead(applyNo);
			String str = Global.getConfig("ThemisConfig");
			String[] st = str.split("\\|");
			trh1.setUserid(st[0]);
			trh1.setPassword(st[1]);
			trh1.setDepcode(st[2]);
			trh1.setType("1");
		} catch (Exception e) {
			logger.error("财报中公司信息查询失败，请检查日志",e);
			interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "财报中公司信息查询失败", new Date(),e.toString()));
			return null;
		}


		CompanyInfo companyInfo = new CompanyInfo(trh1.getDepcode(),trh1.getCompanycode(),
				trh1.getCompanychnname(),trh1.getCompanyengname(),trh1.getCreatedate(),
				trh1.getCompanynature(),trh1.getSwindu1(),trh1.getSwindu2(),
				trh1.getMarketclass(),trh1.getPostcode(),trh1.getIsmerger(),
				trh1.getReportunit(),trh1.getReporttype(),trh1.getType());

		companyInfo.setUserId(trh1.getUserid());
		companyInfo.setPassword(trh1.getPassword());
		companyInfo.setType(trh1.getType());
		logger.info("companyInfo==>"+companyInfo.toString());
		return companyInfo;
	}
	/*@RequestMapping(method = RequestMethod.POST, value = "sendFinanceReportToThemis")
	public String sendFinanceReportToThemis(@RequestBody String json) {// 先存储本地再推送themie
		logger.info("开始接收外访系统传递的数据------------------------");
		logger.info("开始接收外访系统传递的数据" + json);
		int saveFlag=0;//插入数据库的标志位
		String str = "";//返回值
		String exceptionMsg="";//异常信息
		String interfaceName="外访系统导入财报";//接口名字
		json=json.trim();
		String applyNo = "";
		ThemisReportHead themisReportHead = null;//sheet1
		List<ThemisReportInfo> themisReportInfoList1 = null;//sheet2
		List<ThemisReportInfo> themisReportInfoList2 = null;//sheet3
		SVFinancialToThemisRequest trh = new SVFinancialToThemisRequest();
		try {
			if(StringUtils.isEmpty(json)){
				exceptionMsg="外访系统传递数据为空";
				SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(false, "0000", "外访系统传递数据为空。");
				str = JsonTransferUtils.bean2Json(sr);
				interfaceInfoService.save(new InterfaceInfo(null, interfaceName, "接口被调用失败:sv系统传递数据为空", new Date(),json));
				logger.info("返回信息：" + str);
				return str;
			}
			try {
				logger.info("-----开始解析数据-------");
				trh = (SVFinancialToThemisRequest) JsonTransferUtils.json2Bean(json, SVFinancialToThemisRequest.class);
				logger.info("-----解析数据成功-------");
				themisReportHead = trh.getTrh();// 公司信息
				//外访系统推送数据存放的id为9999
				User user=new User();
				user.setId("9999");
				themisReportHead.setUser(user);
				themisReportInfoList1 = trh.getTriList1();
				themisReportInfoList2 = trh.getTriList2();
				applyNo = themisReportHead.getApplyNo();
			} catch (Exception e1) {
				e1.printStackTrace();
				exceptionMsg=e1.toString();
				SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(false, "0000", "解析数据发生错误"+exceptionMsg);
				str = JsonTransferUtils.bean2Json(sr);
				interfaceInfoService.save(new InterfaceInfo(applyNo, interfaceName, "接口被调用失败:解析数据发生错误"+exceptionMsg, new Date(),json));
				logger.info("返回信息：" + str);
				return str;
			}
			if (StringUtils.isNotEmpty(trh.getTrh().getApplyNo())) {//插入数据库
				try {
					if (themisReportInfoList1.get(0).getReportInfo().size() == 39) {
						for (ThemisReportInfo themisReportInfo : themisReportInfoList2) {
							List<ReportInfo4Excel> reportInfo = themisReportInfo.getReportInfo();
							reportInfo.remove(73);
							reportInfo.remove(63);
							reportInfo.remove(40);
							reportInfo.remove(19);
						}
						financialStateImportService.saveSVExcel(themisReportHead, themisReportInfoList2,
								themisReportInfoList1, themisReportHead.getApplyNo());
						saveFlag=1;
					} else {
						for (ThemisReportInfo themisReportInfo : themisReportInfoList1) {
							List<ReportInfo4Excel> reportInfo = themisReportInfo.getReportInfo();
							reportInfo.remove(73);
							reportInfo.remove(63);
							reportInfo.remove(40);
							reportInfo.remove(19);
						}
						financialStateImportService.saveSVExcel(themisReportHead, themisReportInfoList1,
								themisReportInfoList2, themisReportHead.getApplyNo());
						saveFlag=1;
					}
				} catch (Exception e) {
					e.printStackTrace();
					exceptionMsg=e.toString();
					SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(false, "0000", "借款系统保存失败"+exceptionMsg);
					if(saveFlag==1){
						financialStateImportService.deleteSVFinancial(applyNo);
					}
					str = JsonTransferUtils.bean2Json(sr);
					interfaceInfoService.save(new InterfaceInfo(applyNo, interfaceName, "借款系统发生异常："+exceptionMsg, new Date(),json));
					logger.info("返回信息：" + str);
					return str;
				}
				//-------
				//调用themis接口
				ThemisResponse xml = Facade.facade.getAnalysisResult(applyNo);
				if(xml == null || !"0000".equals(xml.getHead().getReturncode())){
					if(saveFlag==1){
						financialStateImportService.deleteSVAndThemis(applyNo);
					}
					SVFinancialToThemisResponse sr=null;
					String themisMsg="";
					if( xml != null && xml.getHead() != null && xml.getHead().getReturnmessage() != null){
						exceptionMsg=xml.getHead().getReturnmessage();
						themisMsg="Themis接口调用失败："+exceptionMsg;
						sr = new SVFinancialToThemisResponse(false, "0000", themisMsg);
					}else{
						themisMsg="Themis接口调用失败,详情查看themis接口日志";
						sr = new SVFinancialToThemisResponse(false, "0000", themisMsg);
					}
					str = JsonTransferUtils.bean2Json(sr);
					interfaceInfoService.save(new InterfaceInfo(applyNo,interfaceName, themisMsg, new Date(),json));
				}else{
					exceptionMsg="数据传递成功";
					SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(true, "1111", exceptionMsg);
					str = JsonTransferUtils.bean2Json(sr);
					interfaceInfoService.save(new InterfaceInfo(applyNo, interfaceName, "成功", new Date(),json));
				}
				logger.info("返回信息：" + str);
				return str;
			} else {
				SVFinancialToThemisResponse sr = new SVFinancialToThemisResponse(false, "0000", "外访系统数据传递缺失，没有申请编号。");
				str = JsonTransferUtils.bean2Json(sr);
				interfaceInfoService.save(new InterfaceInfo(applyNo,interfaceName,"接口被调用失败:外访系统数据传递缺失，没有申请编号。", new Date(),json));
				logger.info("返回信息：" + str);
				return str;
			}
		} catch (IOException e) {
			logger.error("返回SV值转化JSON失败", e);
			logger.error(e.toString());
			str=exceptionMsg+e.toString();
			if(saveFlag==1){
				financialStateImportService.deleteSVAndThemis(applyNo);
			}
		}
		logger.info("返回信息：" + str);
		return str;
	}*/
}
