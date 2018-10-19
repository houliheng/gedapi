package com.resoft.credit.financialStateImport.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.resoft.common.utils.HttpRequestUtil;
import com.resoft.credit.financialStateImport.dao.ThemisReportDicDao;
import com.resoft.credit.financialStateImport.dao.ThemisReturnHeadDao;
import com.resoft.credit.financialStateImport.dao.ThemisReturnScoreDao;
import com.resoft.credit.financialStateImport.entity.*;
import com.resoft.credit.financialStateImport.entity.req.*;
import com.resoft.credit.supportDecision.dao.ThemisReturnReviewDao;
import com.resoft.outinterface.themis.entry.request.*;
import com.resoft.outinterface.themis.server.ThemisRequestServer;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.resoft.common.utils.Constants;
import com.resoft.common.utils.ExcelReader;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.financialStateImport.service.FinancialStateImportService;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 财报导入Controller
 * @author caoyinglong
 * @version 2016-03-11
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/financialStateImport")
public class FinancialStateImportController extends BaseController {

	@Autowired
	private FinancialStateImportService  financialStateImportService;
	@Autowired
	private InterfaceInfoService interfaceInfoService;
	
	@ModelAttribute
	public FinancialStateImport get(@RequestParam(required=false) String id) {
		FinancialStateImport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = financialStateImportService.get(id);
		}
		if (entity == null){
			entity = new FinancialStateImport();
		}
		return entity;
	}
	

	@RequiresPermissions("credit:financialStateImport:view")
	@RequestMapping(value = "form")
	public String form(ActTaskParam actTaskParam, String readOnly, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		ThemisReportHead trh = financialStateImportService.findReportHeadByApplyNo(actTaskParam.getApplyNo());
		List<ThemisReportInfo> ltri1 = financialStateImportService.findThemisReportInfoByApplyNo(actTaskParam.getApplyNo(), Constants.THEMIS_NEW_FINANCE_BS);
		List<ThemisReportInfo> ltri2 = financialStateImportService.findThemisReportInfoByApplyNo(actTaskParam.getApplyNo(), Constants.THEMIS_NEW_FINANCE_PL);
		String json1="";
		String json2="";
		String json3="";
		try {
			json1 = JsonTransferUtils.bean2Json(ltri1);
			json2 = JsonTransferUtils.bean2Json(ltri2);
			json3 = JsonTransferUtils.bean2Json(trh);
		} catch (IOException e) {
			logger.error("财报导入数据转换失败", e);
		}
		//查找外访系统是否推送数据成功
		InterfaceInfo interfaceInfo =new InterfaceInfo();
		interfaceInfo.setApplyNo(actTaskParam.getApplyNo());
		interfaceInfo.setInterfaceName("外访系统导入财报");
		InterfaceInfo interfaceResult=interfaceInfoService.findSVtoThemisResult(interfaceInfo);
		if(interfaceResult!=null){
			if(Constants.STATUS_SV_TO_THEMIS.equals(interfaceResult.getMessage())){
				model.addAttribute("SVerrorMessage","自动导入成功！");
			}else{
				model.addAttribute("SVerrorMessage","自动导入失败，请手工导入");
			}
		}
		model.addAttribute("ThemisReportInfo1",json1);
		model.addAttribute("ThemisReportInfo2",json2);
		model.addAttribute("ThemisReportHead",json3);
		return "app/credit/financialStateImport/financialStateImportForm";
	}
	
	
	
	@RequiresPermissions("credit:financialStateImport:view")
	@RequestMapping(value = "showExcel")
	public String showExcel(Model model) {
		return "app/credit/financialStateImport/themis";
	}
	@RequiresPermissions("credit:financialStateImport:view")
	@RequestMapping(value = "showSheet1")
	public String showSheet1(Model model) {
		return "app/credit/financialStateImport/sheet1";
	}
	@RequiresPermissions("credit:financialStateImport:view")
	@RequestMapping(value = "showSheet2")
	public String showSheet2(Model model,String ThemisReportInfo1) {
		model.addAttribute("ThemisReportInfo1",ThemisReportInfo1);
		return "app/credit/financialStateImport/sheet2";
	}
	@RequiresPermissions("credit:financialStateImport:view")
	@RequestMapping(value = "showSheet3")
	public String showSheet3(Model model,String ThemisReportInfo2) {
		model.addAttribute("ThemisReportInfo2",ThemisReportInfo2);
		return "app/credit/financialStateImport/sheet3";
	}
	
	
	
	@RequiresPermissions("credit:financialStateImport:view")
	@RequestMapping(value = "excel")
	public String getExcelTemplate(HttpServletRequest request,HttpServletResponse response,Model model) {
		response.setContentType("application/vnd.ms-excel");
		InputStream ips = null;
		OutputStream ops=null;
		try {
			File file = new File(request.getSession().getServletContext().getRealPath("/")+"WEB-INF/classes/Themis财务报表模板.xls");
			ips= new FileInputStream(file);
			ops= response.getOutputStream();
			int data =-1;
			while((data = ips.read())!=-1){
				ops.write(data);
			}
		} catch (IOException e) {
			logger.error("下载失败", e);
			model.addAttribute("downloadMassage","下载失败请重试!");
			return "app/credit/financialStateImport/financialStateImportForm";
		}finally{
			try {
				if(ops!=null){
					ops.flush();
					ops.close();
				}
				if(ips!=null){
					ips.close();
				}
			} catch (IOException e) {
				logger.error("关闭资源错误", e);
			}
		}
		return null;
	}
	/*@RequiresPermissions("credit:financialStateImport:view")
	@RequestMapping(value = "upload")
	public String uploadExcel(HttpServletRequest request,HttpServletResponse response,Model model) {
		long startTime = System.currentTimeMillis();
		long endTime1;
		long endTime2;
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest)request;
		MultipartFile file =mhsr.getFile("upload");
		String fileName= file.getOriginalFilename();
		String applyNo = request.getParameter("applyNo");//申请编号
		ActTaskParam actTaskParam = new ActTaskParam();
		actTaskParam.setApplyNo(applyNo);
		if(applyNo==null||"".equals(applyNo)){
			model.addAttribute("actTaskParam", actTaskParam);
			model.addAttribute("errorMessage","请重新打开小窗口上传!");
			return "app/credit/financialStateImport/financialStateImportForm";
		}
		if(fileName == null ||"".equals(fileName)){
			model.addAttribute("actTaskParam", actTaskParam);
			model.addAttribute("errorMessage","请传入文件(后缀名为xls或xlsx)!");
			return "app/credit/financialStateImport/financialStateImportForm";
		}else if(fileName.indexOf(".xls") == -1&&fileName.indexOf(".xlsx") == -1){
			model.addAttribute("actTaskParam", actTaskParam);
			model.addAttribute("errorMessage","请选择正确的Excel文件(后缀名为xls或xlsx)!");
			return "app/credit/financialStateImport/financialStateImportForm";
		}
		try {
			InputStream input = file.getInputStream();
			HSSFWorkbook wb = new HSSFWorkbook(input);
			ThemisReportHead trh;
			List<ThemisReportInfo>  triList1;
			List<ThemisReportInfo>  triList2;
			try {
				try {
					trh = this.getThemisReportHead(0, wb);//公司信息
					//String bean2Json = JsonTransferUtils.bean2Json(trh);
					//logger.info(bean2Json);
				} catch (Exception e1) {
					logger.error(e1.toString());
					model.addAttribute("actTaskParam", actTaskParam);
					model.addAttribute("errorMessage","成立年月格式错误，请按照999912输入");
					return  "app/credit/financialStateImport/financialStateImportForm";
				}
				triList1 = this.getThemisReportInfo(1,wb);//新会计准则资产负债表
				//String bean2Json1 = JsonTransferUtils.bean2Json(triList1);
				//logger.info(bean2Json1);
				triList2 = this.getThemisReportInfo(2,wb);//新会计准则利润表
				//String bean2Json2 = JsonTransferUtils.bean2Json(triList2);
				//logger.info(bean2Json2);
				endTime1 = System.currentTimeMillis();
				logger.info("转换对象时间:"+(endTime1-startTime));
				if("[]".equals(triList1.toString())||"[]".equals(triList2.toString())){
					throw new Exception();
				}
				try {
					financialStateImportService.saveExcel(trh, triList1, triList2, applyNo);
					endTime2 = System.currentTimeMillis();
					logger.info("入库时间:"+(endTime2-endTime1));
				} catch (Exception e) {
					logger.error("文件信息保存失败!",e);
					model.addAttribute("actTaskParam", actTaskParam);
					model.addAttribute("errorMessage","文件信息保存失败!");
					return  "app/credit/financialStateImport/financialStateImportForm";
				}
			} catch (Exception e) {
				logger.error(e.toString());
				model.addAttribute("actTaskParam", actTaskParam);
				model.addAttribute("errorMessage","Excel格式错误，请按照模板格式上传!");
				return  "app/credit/financialStateImport/financialStateImportForm";
			}

			String json1="";
			String json2="";
			String json3="";
			try {
				json1 = JsonTransferUtils.bean2Json(triList1);
				json2 = JsonTransferUtils.bean2Json(triList2);
				json3 = JsonTransferUtils.bean2Json(trh);
			} catch (IOException e) {
				logger.error("上传excel数据转换失败", e);
			}
			//调Themis接口
					ThemisResponse xml = Facade.facade.getAnalysisResult(applyNo);
			if(xml == null || !"0000".equals(xml.getHead().getReturncode())){
				if( xml != null && xml.getHead() != null && xml.getHead().getReturnmessage() != null){
					model.addAttribute("errorMessage", xml.getHead().getReturnmessage());
				}else{ 
					model.addAttribute("errorMessage", "导入失败");
				}
				actTaskParam.setApplyNo(applyNo);
				model.addAttribute("applyNo", applyNo);
				model.addAttribute("actTaskParam", actTaskParam);
				long endTime = System.currentTimeMillis();
				logger.info("转换JSON时间:"+(endTime-endTime2));
				//说明导入的数据有问题，删除导入的信息
				model.addAttribute("actTaskParam", actTaskParam);
				financialStateImportService.deleteSVAndThemis(applyNo);
				return "app/credit/financialStateImport/financialStateImportForm";
			}else{
				model.addAttribute("ThemisReportInfo1",json1);
				model.addAttribute("ThemisReportInfo2",json2);
				model.addAttribute("ThemisReportHead",json3);
				model.addAttribute("errorMessage", xml.getHead().getReturnmessage());
				model.addAttribute("applyNo", applyNo);
				long endTime = System.currentTimeMillis();
				logger.info("转换JSON时间:"+(endTime-endTime2));
				model.addAttribute("actTaskParam", actTaskParam);
				return "app/credit/financialStateImport/financialStateImportForm";
			}
		} catch (IOException e) {
			logger.error("上传数据错误", e);
			model.addAttribute("actTaskParam", actTaskParam);
			model.addAttribute("errorMessage","上传数据错误，请修正后重试!");
			return "app/credit/financialStateImport/financialStateImportForm";
		}
		
	}*/

	@Resource
	private ThemisReturnHeadDao themisReturnHeadDao;
	@Resource
	private ThemisReturnScoreDao themisReturnScoreDao;
	@Resource
	private ThemisReturnReviewDao themisReturnReviewDao;
	@Resource
	private ThemisReportDicDao themisReportDicDao;


	private ThemisRequestServer themisRequestServer = SpringContextHolder.getBean("themisRequestServer");
	@RequiresPermissions("credit:financialStateImport:view")
	@RequestMapping(value = "upload")
	public String uploadExcel(HttpServletRequest request,HttpServletResponse response,Model model) {
		long startTime = System.currentTimeMillis();
		long endTime1;
		long endTime2;
		MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest)request;
		MultipartFile file =mhsr.getFile("upload");
		String fileName= file.getOriginalFilename();
		String applyNo = request.getParameter("applyNo");//申请编号
		ActTaskParam actTaskParam = new ActTaskParam();
		actTaskParam.setApplyNo(applyNo);
		if(applyNo==null||"".equals(applyNo)){
			model.addAttribute("actTaskParam", actTaskParam);
			model.addAttribute("errorMessage","请重新打开小窗口上传!");
			return "app/credit/financialStateImport/financialStateImportForm";
		}
		if(fileName == null ||"".equals(fileName)){
			model.addAttribute("actTaskParam", actTaskParam);
			model.addAttribute("errorMessage","请传入文件(后缀名为xls或xlsx)!");
			return "app/credit/financialStateImport/financialStateImportForm";
		}else if(fileName.indexOf(".xls") == -1&&fileName.indexOf(".xlsx") == -1){
			model.addAttribute("actTaskParam", actTaskParam);
			model.addAttribute("errorMessage","请选择正确的Excel文件(后缀名为xls或xlsx)!");
			return "app/credit/financialStateImport/financialStateImportForm";
		}
		try {
			InputStream input = file.getInputStream();
			HSSFWorkbook wb = new HSSFWorkbook(input);
			ThemisReportHead trh;
			List<ThemisReportInfo>  triList1;
			List<ThemisReportInfo>  triList2;

			try {
				try {
					trh = this.getThemisReportHead(0, wb);//公司信息
					//String bean2Json = JsonTransferUtils.bean2Json(trh);
					//logger.info(bean2Json);
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getMessage());
					model.addAttribute("actTaskParam", actTaskParam);
					model.addAttribute("errorMessage","成立年月格式错误，请按照999912输入");
					return  "app/credit/financialStateImport/financialStateImportForm";
				}
				triList1 = this.getThemisReportInfo(1,wb);//新会计准则资产负债表
				//String bean2Json1 = JsonTransferUtils.bean2Json(triList1);
				//logger.info(bean2Json1);
				triList2 = this.getThemisReportInfo(2,wb);//新会计准则利润表
				//String bean2Json2 = JsonTransferUtils.bean2Json(triList2);
				//logger.info(bean2Json2);
				endTime1 = System.currentTimeMillis();
				logger.info("转换对象时间:"+(endTime1-startTime));
				if("[]".equals(triList1.toString())||"[]".equals(triList2.toString())){
					throw new Exception();
				}
				try {
					financialStateImportService.saveExcel(trh, triList1, triList2, applyNo);
					endTime2 = System.currentTimeMillis();
					logger.info("入库时间:"+(endTime2-endTime1));
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("文件信息保存失败!",e);
					model.addAttribute("actTaskParam", actTaskParam);
					model.addAttribute("errorMessage","文件信息保存失败!");
					return  "app/credit/financialStateImport/financialStateImportForm";
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				model.addAttribute("actTaskParam", actTaskParam);
				model.addAttribute("errorMessage","Excel格式错误，请按照模板格式上传!");
				return  "app/credit/financialStateImport/financialStateImportForm";
			}

			String json1="";
			String json2="";
			String json3="";
			try {
				json1 = JsonTransferUtils.bean2Json(triList1);
				json2 = JsonTransferUtils.bean2Json(triList2);
				json3 = JsonTransferUtils.bean2Json(trh);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				logger.error("上传excel数据转换失败", e);
			}
			//组装Themis接口请求参数
			RequestParamData requestParamData = getRequestParamData(applyNo);
			if (requestParamData == null) return null;
			String reqjson = JsonTransferUtils.bean2Json(requestParamData);
			//获取请求地址
			String addr = Global.getConfig("ThemisRequestUrlAddress");
			String reqParam = reqjson;
			logger.info("财报导入请求参数===》"+ reqParam);
			//调Themis接口
			JSONObject json = HttpRequestUtil.doHttpClient(addr, reqParam);
			logger.info("返回json====》"+json.toJSONString());
			if(json == null || !"200".equals(json.getString("resCode"))){
				JSONObject data1 = json.getJSONObject("data");
				com.alibaba.fastjson.JSONArray errorArray = data1.getJSONArray("error");
				StringBuilder sbmsg  = new StringBuilder("导入失败，错误信息如下：");
				for (Iterator iterator = errorArray.iterator(); iterator.hasNext();) {
					JSONObject jsonObject = (JSONObject) iterator.next();
					String msg = jsonObject.getString("msg");
					String code = jsonObject.getString("code");
					sbmsg.append(code+"："+msg+";");
				}
				model.addAttribute("errorMessage", sbmsg.toString());
				actTaskParam.setApplyNo(applyNo);
				model.addAttribute("applyNo", applyNo);
				model.addAttribute("actTaskParam", actTaskParam);
				long endTime = System.currentTimeMillis();
				logger.info("转换JSON时间:"+(endTime-endTime2));
				//说明导入的数据有问题，删除导入的信息
				model.addAttribute("actTaskParam", actTaskParam);
				financialStateImportService.deleteSVAndThemis(applyNo);
				return "app/credit/financialStateImport/financialStateImportForm";
			}else{
				logger.info("applyNo==>"+applyNo);
				//入库之前先删除
				themisReturnHeadDao.deleteByApplyNo(applyNo);
				themisReturnScoreDao.deleteByApplyNo(applyNo);
				themisReturnReviewDao.deleteByApplyNo(applyNo);
				logger.info("===Themis接口返回值插入数据库开始======");
				//将接口返回值保存数据库
				//获取返回值
				/**
				 * 评语信息
				 */
				JSONObject   data = json.getJSONObject("data");
				JSONObject   result = data.getJSONObject("result");
				String comCode = result.getString("comCode");
				JSONArray reviews = result.getJSONArray("review");
				for (Iterator iterator = reviews.iterator(); iterator.hasNext();) {
					JSONObject review = (JSONObject) iterator.next();
					String reportYear = review.getString("reportYear");
					String reportMonth = review.getString("reportMonth");
					JSONObject reviewInfo = review.getJSONObject("reviewInfo");
					String[] indexCodes = new String[]{"R01","R02","R03","R04"
							,"R05","R06","R07","R08"};
					for (int i = 0; i<8;i++){
						ThemisReportDic dic = themisReportDicDao.findByCode(indexCodes[i]);
						ThemisReturnReview returnReview = new ThemisReturnReview();
						returnReview.setCompanycode(comCode);
						returnReview.setReportYearMonth(reportYear+reportMonth);
						returnReview.setReturnOrderCol(dic.getReportOrderCol());
						returnReview.setReturnIndexCode(indexCodes[i]);
						returnReview.setReturnIndexName(dic.getReportIndexName());
						returnReview.setReview(reviewInfo.getString(indexCodes[i]));
						returnReview.setApplyNo(applyNo);
						themisReturnReviewDao.insert(returnReview);
					}
				}
				logger.info("ift_themis_return_review 表插入成功");

				/**
				 * 评级得分信息
				 */
				JSONArray scores = result.getJSONArray("score");
				for (Iterator iterator = scores.iterator(); iterator.hasNext();) {
					JSONObject score = (JSONObject) iterator.next();
					String reportYear = score.getString("reportYear");
					String reportMonth = score.getString("reportMonth");
					JSONObject scoreinfo = score.getJSONObject("scoreInfo");
					ThemisReturnScore returnScore =null;
					String[] indexCodes = new String[]{"S001","S002","S003","S005"
							,"S006","S007","S008"
							,"S009","S010","S011"
							,"S012","S013","S014"
							,"S015","S016","S017"};
					for(int i =0;i<16;i++){
						returnScore = new ThemisReturnScore();
						ThemisReportDic dic = themisReportDicDao.findByCode(indexCodes[i]);
						returnScore.setReportYearMonth(reportYear+reportMonth);
						returnScore.setCompanycode(comCode);
						returnScore.setReturnOrderCol(dic.getReportOrderCol());
						returnScore.setReturnIndexCode(indexCodes[i]);
						returnScore.setReturnIndexName(dic.getReportIndexName());
						returnScore.setFullMarks("");
						returnScore.setScore(scoreinfo.getString(indexCodes[i]));
						returnScore.setWarnning("");
						returnScore.setApplyNo(applyNo);
						themisReturnScoreDao.insert(returnScore);
					}
				}
				logger.info("ift_themis_return_score表插入成功");

				/**
				 * 返回结果
				 */
				String resCode = json.getString("resCode");
				String resMsg = json.getString("resMsg");
				ThemisReturnHead head = new ThemisReturnHead();
				head.setCompanycode(comCode);
				head.setReturncode(resCode);
				head.setReturnmessage(resMsg);
				head.setApplyNo(applyNo);
				themisReturnHeadDao.insert(head);
				logger.info("ift_themis_return_head 表插入成功");
				logger.info("===Themis接口返回值插入数据库结束======");


				model.addAttribute("ThemisReportInfo1",json1);
				model.addAttribute("ThemisReportInfo2",json2);
				model.addAttribute("ThemisReportHead",json3);
				model.addAttribute("errorMessage", "导入成功！");
				model.addAttribute("applyNo", applyNo);
				long endTime = System.currentTimeMillis();
				logger.info("转换JSON时间:"+(endTime-endTime2));
				model.addAttribute("actTaskParam", actTaskParam);
				return "app/credit/financialStateImport/financialStateImportForm";
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("上传数据错误", e);
			model.addAttribute("actTaskParam", actTaskParam);
			model.addAttribute("errorMessage","上传数据错误，请修正后重试!");
			return "app/credit/financialStateImport/financialStateImportForm";
		}

	}

	private RequestParamData getRequestParamData(String applyNo) {
		ReportData reportData = new ReportData();
		RequestParamData requestParamData = new RequestParamData();

		//财报信息
		List<NewFinanceBS> lnfbs = new ArrayList<NewFinanceBS>();
		List<NewFinancePL> lnfpl = new ArrayList<NewFinancePL>();
		List<String> bs = themisRequestServer.findYearMonthByApply("NEW_FINANCE_BS", applyNo);
		logger.info(bs.toString());
		Iterator<String> it = bs.iterator();
		while(it.hasNext()){
            String str = it.next();
            logger.info(str);
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
		List<String> pls = themisRequestServer.findYearMonthByApply("NEW_FINANCE_PL", applyNo);
		Iterator<String> its = pls.iterator();
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

		/**
         * 组装CompanyInfo
         */
		CompanyInfo companyInfo = getCompanyInfo(applyNo);
		if (companyInfo == null) return null;
		requestParamData.setCompanyInfo(companyInfo);
		requestParamData.setReportData(reportData);
		return requestParamData;
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

	private ThemisReportHead getThemisReportHead(int sheetIndex,HSSFWorkbook wb)throws Exception{
		int rowNum = ExcelReader.getRowNum(0, wb);
		HSSFSheet sheet = wb.getSheetAt(0);
		ThemisReportHead trh;
		String[] str = new String[rowNum+1];
		//获取一列的数据封装为String数组
		for(int i =0;i<12;i++){
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell  = row.getCell(1);
			str[i] = ExcelReader.getStringCellValue(cell);
			if("<null>".equals(str[i])){
				throw new Exception();
			}
			if(i==3){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				sdf.parse(str[i]);
			}
		}
		trh = new ThemisReportHead(str[0],str[1],str[2],str[3],str[4],str[5],str[6],str[7],str[8],str[9],str[10],str[11]);
		return trh;
	}
	private List<ThemisReportInfo> getThemisReportInfo(int sheetIndex,HSSFWorkbook wb)throws Exception{
		int rowNum = ExcelReader.getRowNum(sheetIndex, wb);
		int colNum = ExcelReader.getColumnNum(sheetIndex, wb);
		HSSFSheet sheet = wb.getSheetAt(sheetIndex);
		List<ThemisReportInfo> themisList = new ArrayList<ThemisReportInfo>();
		for(int i = 2;i<colNum;i++){
			HSSFRow row2 = sheet.getRow(0);
			HSSFCell cell2 = row2.getCell(i);
			String year=ExcelReader.getStringCellValue(cell2);
			if("".equals(year)){
				continue;
			}
			List<ReportInfo4Excel> ri4eList = new ArrayList<ReportInfo4Excel>();
			ThemisReportInfo tri=new ThemisReportInfo();
			String yearMonth="";
			int j=0;
			for(;j<2;j++){
				HSSFRow row1 = sheet.getRow(j);
				HSSFCell cell1 = row1.getCell(i);
				yearMonth+=ExcelReader.getStringCellValue(cell1);
			}
			yearMonth = Pattern.compile("[\u4e00-\u9fa5]").matcher(yearMonth).replaceAll("");
			logger.info("yearMonth====>"+yearMonth);
			tri.setReportYearMonth(yearMonth);
			for(;j<rowNum;j++){
				ReportInfo4Excel ri4e = new ReportInfo4Excel();
				HSSFRow row = sheet.getRow(j);
				HSSFCell cell= row.getCell(i);
				String index = ExcelReader.getStringCellValue(cell);
				if("-".equals(index)){
					continue;
				}
				if(StringUtils.isEmpty(index)){
					index = "0.00";
				}
				ri4e.setReportIndexValue(index);
				ri4e.setReportOrderCol(""+(j-1));
				ri4eList.add(ri4e);
			}
			tri.setReportInfo(ri4eList);
			themisList.add(tri);
		}
		if(1==sheetIndex){
			for (ThemisReportInfo themisReportInfo : themisList) {
				int reportInfoList = themisReportInfo.getReportInfo().size();
				if(reportInfoList>81){
					throw new Exception();
				}
			}
		}
		logger.info("第"+sheetIndex+"个Sheet=====》获取list信息：");
		for (ThemisReportInfo t: themisList){
			logger.info(t.toString());
		}
		logger.info("======list展示结束====");
		return themisList;
	}

}