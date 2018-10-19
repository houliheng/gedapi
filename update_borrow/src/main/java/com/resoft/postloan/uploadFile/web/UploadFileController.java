package com.resoft.postloan.uploadFile.web;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.resoft.postloan.plVideoPath.service.PLVideoPathService;
import com.resoft.postloan.uploadFile.uploadFile.Manager;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 账务清收Controller
 * @author yanwanmei
 * @version 2016-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/uploadFile")
public class UploadFileController extends BaseController {
	
	@Autowired
	private PLVideoPathService creVieoPathService;
	
	@RequestMapping("toUploadPage")
	public String toUploadPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyNo = request.getParameter("applyNo");
		String contractNo = request.getParameter("contractNo");
		model.addAttribute("contractNo", contractNo);
		model.addAttribute("applyNo", applyNo);
		return "app/postloan/uploadFile/uploadFile";
	}
	

	@RequestMapping("upload")
	public void upload(MultipartHttpServletRequest request, HttpServletResponse response, Model model,String applyNo) throws IOException {
		//借后中不分权限，所以把权限分级设置为1
		String taskDefKey="1";
		MultipartFile f = request.getFile("uploadify");
		String registerDate = null;
		//String registerDate = applyRegisterService.getRegisterDateByApplyNo(applyNo);
		File tmp = File.createTempFile("1222", "2222");
		OutputStream out = new FileOutputStream(tmp);
		out.write(f.getBytes());
		out.flush();
		out.close();
		Manager.setDbSource(creVieoPathService);
		Manager.uploadZip(tmp, applyNo, taskDefKey,UserUtils.getUser().getName(), true,registerDate);
	}

}