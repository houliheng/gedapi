package com.resoft.credit.creditReport.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.creditReport.service.CreditReportService;
import com.resoft.credit.fdfs.Manager;
import com.resoft.credit.fdfs.util.FileUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.act.entity.MyMap;

@Controller
@RequestMapping(value = "${adminPath}/credit/creditReport")
public class CreditReportController {
	private static final Logger logger = LoggerFactory.getLogger(CreditReportController.class);
	@Autowired
	private CreditReportService creditReportService;

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, ActTaskParam actTaskParam, String readOnly, Model model) {
		String applyNo = request.getParameter("applyNo");
		String fileStoragePath = request.getParameter("fileStoragePath");
		String fileName = request.getParameter("fileName");
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("applyNo", applyNo);
		model.addAttribute("fileName", fileName);
		model.addAttribute("fileStoragePath", fileStoragePath);
		return "app/credit/creditReport/creditReport";
	}

	@RequestMapping(value = { "list", "" })
	public String list(HttpServletRequest request, ActTaskParam actTaskParam, String readOnly, Model model) {
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		// 查询videoPath
		Map<String, Object> params = Maps.newConcurrentMap();
		try {
			params.put("applyNo", actTaskParam.getApplyNo());
		} catch (Exception e) {
			logger.error("查询信审报告列表所需申请编号为空！", e);
			model.addAttribute("message", "查询信审报告列表所需申请编号为空！");
			return "app/credit/creditReport/creditReport";
		}
		List<MyMap> pdfList = creditReportService.findPdfList(params);
		if (pdfList == null || pdfList.size() == 0) {
			pdfList = new ArrayList<MyMap>();
		}
		model.addAttribute("yxUrl",Global.getConfig("YX_ADDRESS"));
		model.addAttribute("pdfList", pdfList);
		return "app/credit/creditReport/creditReportList";
	}

	@ResponseBody
	@RequestMapping(value = "show")
	public void show(String applyNo, String fileStoragePath, String fileName, HttpServletResponse response, HttpServletRequest request) {
		OutputStream out = null;
		BufferedInputStream in = null;
		if ("".equals(fileStoragePath) || fileStoragePath == null || "".equals(fileName) || fileName == null) {
			try {
				File f = new File(request.getSession().getServletContext().getRealPath("/") + "WEB-INF/classes/test1.pdf");
				ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
				in = new BufferedInputStream(new FileInputStream(f));
				int buf_size = 1024;
				byte[] buffer = new byte[buf_size];
				int len = 0;
				while (-1 != (len = in.read(buffer, 0, buf_size))) {
					bos.write(buffer, 0, len);
				}
				byte[] b = bos.toByteArray();
				out = response.getOutputStream();
				out.write(b);
			} catch (Exception e) {
				logger.error("显示信审报告失败", e);
			} finally {
				try {
					FileUtils.closeIOSource(out);
					FileUtils.closeIOSource(in);
				} catch (IOException e) {
					logger.error("访问地址未受信任", e);
				}
			}
			return;
		}
		try {
			byte[] b = Manager.download(fileStoragePath);
			out = response.getOutputStream();
			out.write(b);
		} catch (Exception e) {
			logger.error("下载图片失败", e);
		} finally {
			try {
				FileUtils.closeIOSource(out);
			} catch (IOException e) {
				logger.error("关闭IO资源错误", e);
			}
		}
	}
}
