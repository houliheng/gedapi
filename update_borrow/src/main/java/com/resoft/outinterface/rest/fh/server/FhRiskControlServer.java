package com.resoft.outinterface.rest.fh.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.fdfs.util.FdfsUtils;
import com.resoft.credit.fhRiskControl.service.FhRiskControlService;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.rest.fh.server.entity.request.FhRiskControlServerRequest;
import com.resoft.outinterface.rest.fh.server.entity.response.FhRiskControlServerResponse;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;

@RestController
@RequestMapping(value = "/f/rest/fh/")
public class FhRiskControlServer {
	private static final Logger logger = LoggerFactory.getLogger(FhRiskControlServer.class);
	private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");
	private FhRiskControlService fhRiskControlService = SpringContextHolder.getBean("fhRiskControlService");

	@RequestMapping(method = RequestMethod.POST, value = "fhRiskControl")
	public String fhRiskControlServer(@RequestBody String json) {

		String localPath = this.getClass().getResource("/").getPath();
		logger.info("法海风控服务接口开始-----------------------start");
		logger.info("法海风控发送信息：" + json);
		FhRiskControlServerRequest fhRiskControlServerRequest = null;
		FhRiskControlServerResponse fhRiskControlServerResponse = new FhRiskControlServerResponse();
		String response = null;
		try {
			fhRiskControlServerRequest = JsonTransferUtils.json2Bean(json, FhRiskControlServerRequest.class);
			String fileName = URLDecoder.decode(fhRiskControlServerRequest.getFileName(), "utf-8");
			String applyNo = fhRiskControlServerRequest.getApplyNo();
			String custId = fhRiskControlServerRequest.getCustId();
			try {
				fhRiskControlService.updateReportStatusAndFilePathById(null, fileName, fhRiskControlServerRequest.getFilePath(), null, applyNo, custId);
				Map<String, Object> map = generateReport(Global.getConfig("fhRiskControlFTPIP"), 21, Global.getConfig("fhRiskControlFTPName"), Global.getConfig("fhRiskControlFTPPassWord"), fhRiskControlServerRequest.getFilePath(), fileName, localPath);
				logger.info("=======生成报告返回结果： "+map.toString());
				// Map<String, Object> map = generateReport("192.168.0.214", 21,
				// "wgd", "wgd123456", fhRiskControlServerRequest.getFilePath(),
				// fhRiskControlServerRequest.getFileName(), localPath);
				if ("1".equals(map.get("flag"))) {
					if (StringUtils.isNull(map.get("msg"))) {
						try {
							interfaceInfoService.save(new InterfaceInfo(applyNo, "法海风控生成报告接口", "上传影像服务器失败", new Date(), json));
						} catch (Exception e) {
							logger.error("接口信息记录失败！", e);
						}
						fhRiskControlServerResponse.setApplyNo(applyNo);
						fhRiskControlServerResponse.setCustId(custId);
						fhRiskControlServerResponse.setCode("9999");
						fhRiskControlServerResponse.setMsg("上传影像服务器失败");
						fhRiskControlService.updateReportStatusAndFilePathById(null, fileName, fhRiskControlServerRequest.getFilePath(), Constants.FH_REPORT_STATUS_SCSB, applyNo, custId);
					}else if ("noFile".equalsIgnoreCase(map.get("msg").toString())){
						try {
							interfaceInfoService.save(new InterfaceInfo(applyNo, "法海风控生成报告接口", "未找到文件:"+fileName, new Date(), json));
						} catch (Exception e) {
							logger.error("接口信息记录失败！", e);
						}
						fhRiskControlServerResponse.setApplyNo(applyNo);
						fhRiskControlServerResponse.setCustId(custId);
						fhRiskControlServerResponse.setCode("9999");
						fhRiskControlServerResponse.setMsg("未找到文件:"+fileName);
						fhRiskControlService.updateReportStatusAndFilePathById(null, fileName, fhRiskControlServerRequest.getFilePath(), Constants.FH_REPORT_STATUS_SCSB, applyNo, custId);
					}else {
						fhRiskControlService.updateReportStatusAndFilePathById(map.get("msg").toString(), fileName, fhRiskControlServerRequest.getFilePath(), Constants.FH_REPORT_STATUS_YSC, applyNo, custId);
						fhRiskControlServerResponse.setApplyNo(applyNo);
						fhRiskControlServerResponse.setCustId(custId);
						fhRiskControlServerResponse.setCode("0000");
						fhRiskControlServerResponse.setMsg("请求成功");
						try {
							interfaceInfoService.save(new InterfaceInfo(applyNo, "法海风控生成报告接口", "成功！", new Date(), json));
						} catch (Exception e) {
							logger.error("接口信息记录失败！", e);
						}
					}
				} else {
					fhRiskControlServerResponse.setApplyNo(applyNo);
					fhRiskControlServerResponse.setCustId(custId);
					fhRiskControlServerResponse.setCode("9999");
					fhRiskControlServerResponse.setMsg(map.get("msg").toString());
					fhRiskControlService.updateReportStatusAndFilePathById(null, fileName, fhRiskControlServerRequest.getFilePath(), Constants.FH_REPORT_STATUS_SCSB, applyNo, custId);
					try {
						interfaceInfoService.save(new InterfaceInfo(applyNo, "法海风控生成报告接口", map.get("msg").toString(), new Date(), json));
					} catch (Exception e) {
						logger.error("接口信息记录失败！", e);
					}
				}
			} catch (Exception e) {
				logger.error("信息保存失败", e);
				fhRiskControlServerResponse.setApplyNo(applyNo);
				fhRiskControlServerResponse.setCustId(custId);
				fhRiskControlServerResponse.setCode("9999");
				fhRiskControlServerResponse.setMsg("信息保存失败" + e.toString());
				fhRiskControlService.updateReportStatusAndFilePathById(null, fileName, fhRiskControlServerRequest.getFilePath(), Constants.FH_REPORT_STATUS_SCSB, applyNo, custId);
				try {
					interfaceInfoService.save(new InterfaceInfo(fhRiskControlServerRequest.getApplyNo(), "法海风控生成报告接口", "失败！" + e.toString(), new Date(), json));
				} catch (Exception e1) {
					logger.error("接口信息记录失败！", e1);
				}
			}
		} catch (Exception e) {
			logger.error("法海风控接口回盘失败！", e);
			fhRiskControlServerResponse.setCode("9999");
			fhRiskControlServerResponse.setMsg("请求失败" + e.toString());
			try {
				interfaceInfoService.save(new InterfaceInfo(fhRiskControlServerRequest.getApplyNo(), "法海风控生成报告接口", "失败！" + e.toString(), new Date(), json));
			} catch (Exception e1) {
				logger.error("接口信息记录失败！", e1);
			}
		}
		try {
			response = JsonTransferUtils.bean2Json(fhRiskControlServerResponse);
		} catch (IOException e) {
			logger.error("法海风控接口返回值转化JSON失败", e);
		}
		logger.info("返回信息：" + response);
		return response;
	}

	FTPClient client = new FTPClient();

	Map<String, Object> generateReport(String url, int port, String username, String password, String remotePath, String fileName, String localPath) {
		Map<String, Object> result = Maps.newHashMap();
		String real = null;
		try {
			client.setControlEncoding("GBK");
			FTPClientConfig clientConfig = new FTPClientConfig(FTPClientConfig.SYST_NT);
			clientConfig.setServerLanguageCode("zh");
			client.connect(url, port);
			client.login(username, password);
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			int reply = client.getReplyCode();
			if (!(FTPReply.isPositiveCompletion(reply))) {
				client.disconnect();
				result.put("flag", "0");
				result.put("msg", "拒绝连接");
				return result;
			}
			client.changeWorkingDirectory(new String(remotePath.getBytes("GBK"), "iso-8859-1"));
			FTPFile[] fs = client.listFiles();
			if(fs.length != 0){
				for (FTPFile f : fs) {
					logger.info("ftp名称:"+f.getName()+"需要上传文件名称："+fileName);
					if (f.getName().equalsIgnoreCase(fileName)) {
						File file = new File(localPath + f.getName());
						OutputStream is = new FileOutputStream(file);
						client.retrieveFile(f.getName(), is);
						is.close();
						logger.info("开始上传pdf"+f.getName());
						String path = FdfsUtils.upload(file);
						logger.info("上传结束"+path);
						real = path;
						file.delete();
						break;
					}
				}
			}else{
				real = "noFile";
			}
			client.logout();
			result.put("flag", "1");
			result.put("msg", real);
		} catch (IOException e) {
			logger.error("连接ftp下载文件失败", e);
			result.put("flag", "0");
			result.put("msg", "连接ftp下载文件失败" + e.toString());
		} finally {
			if (client.isConnected()) {
				try {
					client.disconnect();
				} catch (IOException e) {
					logger.error("关闭连接失败", e);
				}
			}
		}
		return result;
	}

}
