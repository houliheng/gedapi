package com.resoft.outinterface.themis;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.resoft.common.utils.Constants;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.themis.entry.request.NewFinanceBS;
import com.resoft.outinterface.themis.entry.request.NewFinancePL;
import com.resoft.outinterface.themis.entry.request.ReportInfoBS;
import com.resoft.outinterface.themis.entry.request.ReportInfoPL;
import com.resoft.outinterface.themis.entry.request.ThemisRequest;
import com.resoft.outinterface.themis.entry.request.ThemisRequestBody;
import com.resoft.outinterface.themis.entry.request.ThemisRequestHead;
import com.resoft.outinterface.themis.entry.response.ThemisResponse;
import com.resoft.outinterface.themis.server.ThemisRequestServer;
import com.resoft.outinterface.utils.Facade;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

@RestController
@RequestMapping(value = "/f/webService/themis")
public class ThemisServerInterfaceImpl {

	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");

	private static final Logger logger = LoggerFactory.getLogger(ThemisServerInterfaceImpl.class);
	private ThemisRequestServer themisRequestServer = SpringContextHolder.getBean("themisRequestServer");
	@RequestMapping(method=RequestMethod.GET,value="return/{applyNo}")
	public String getAnalysisResult(@PathVariable String applyNo) {
		System.out.println(applyNo);
		ThemisRequest tr = new ThemisRequest();
		ThemisRequestBody trb = new ThemisRequestBody();
		ThemisRequestHead trh = new ThemisRequestHead();
		List<NewFinanceBS> lnfbs = new ArrayList<NewFinanceBS>();
		List<NewFinancePL> lnfpl = new ArrayList<NewFinancePL>();
		List<String> bs = themisRequestServer.findYearMonthByApply("NEW_FINANCE_BS", applyNo);
		System.out.println(bs);
		Iterator<String> it = bs.iterator();
		while(it.hasNext()){
			String str = it.next();
			System.out.println(str);
			NewFinanceBS nfbs = new NewFinanceBS();
			try {
				List<String> ls=themisRequestServer.findFinancialReport("NEW_FINANCE_BS", str, applyNo);
				ReportInfoBS rbs = OutInterfaceUtils.listToBean(ls, ReportInfoBS.class);
				nfbs.setReportinfo(rbs);
				nfbs.setReportyear(str.substring(0, 4));
				nfbs.setReportmonth(str.substring(4));
				lnfbs.add(nfbs);
			} catch (Exception e) {
				logger.error("BS财报查询失败，请检查日志",e);
				interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "BS财报查询失败", new Date(),e.toString()));
				return null;
			}
		}
		List<String> pl = themisRequestServer.findYearMonthByApply("NEW_FINANCE_PL", applyNo);
		Iterator<String> its = pl.iterator();
		while(its.hasNext()){
			String str = its.next();
			NewFinancePL nfpl = new NewFinancePL();
			try {
				List<String> ls=themisRequestServer.findFinancialReport("NEW_FINANCE_PL", str, applyNo);
				ReportInfoPL rpl = OutInterfaceUtils.listToBean(ls, ReportInfoPL.class);
				nfpl.setReportinfo(rpl);
				nfpl.setReportyear(str.substring(0, 4));
				nfpl.setReportmonth(str.substring(4));
				lnfpl.add(nfpl);
			} catch (Exception e) {
				logger.error("PL财报查询失败，请检查日志",e);
				interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "PL财报查询失败", new Date(),e.toString()));
				return null;
			}
		}
		trb.setNewFinanceBS(lnfbs);
		trb.setNewFinancePL(lnfpl);
		try {
			trh= themisRequestServer.findThemisReportHead(applyNo);
			String str = Global.getConfig("ThemisConfig");
			String[] st = str.split("\\|");
			trh.setUserid(st[0]);
			trh.setPassword(st[1]);
			trh.setDepcode(st[2]);
			trh.setType("1");
		} catch (Exception e) {
			logger.error("财报中公司信息查询失败，请检查日志",e);
			interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "财报中公司信息查询失败", new Date(),e.toString()));
			return null;
		}
		tr.setBody(trb);
		tr.setHead(trh);
		String xml="";
		ThemisResponse returnxml = null;
		try {
			xml=Facade.facade.BeanToXmlString(tr, ThemisRequest.class);
		} catch (Exception e) {
			logger.error("PL财报查询失败，请检查日志",e);
			interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "PL财报查询失败", new Date(),e.toString()));
			return null;
		}
		logger.debug(xml);
		String returnx="";
		Date sendDate = new Date();
		try {
			returnxml=Facade.facade.getDispatchedInterface(Constants.INTERFACE_THEMIS, ThemisResponse.class, applyNo, xml);
			returnx = Facade.facade.BeanToXmlString(returnxml, ThemisResponse.class);
			themisRequestServer.insertIntoThemisReturn(returnxml,applyNo);
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , returnxml.getHead().getReturnmessage(), sendDate,xml));
			} catch (Exception e) {
				logger.error("接口信息记录失败！",e);
				interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "接口信息记录失败", new Date(),e.toString()));
				return null;
			}
		} catch (Exception e) {
			logger.error("返回信息转化失败",e);
			try {
				interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "接口访问失败:"+e.toString(), sendDate,xml));
				return null;
			} catch (Exception e2) {
				logger.error("接口信息记录失败！",e2);
				interfaceInfoService.save(new InterfaceInfo(applyNo, "Themis接口" , "接口信息记录失败:"+e.toString(), new Date(),xml));
				return null;
			}
		}
		return returnx;
	}

}
