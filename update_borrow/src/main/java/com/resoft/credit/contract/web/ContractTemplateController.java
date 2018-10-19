package com.resoft.credit.contract.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.NumberToCN;
import com.resoft.common.utils.WordUtils;
import com.resoft.credit.applyRegister.entity.ApplyRegister;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.companyInfo.entity.CompanyInfo;
import com.resoft.credit.companyInfo.service.CompanyInfoService;
import com.resoft.credit.contract.entity.ContractTemplate;
import com.resoft.credit.contract.entity.ContractUploadFiles;
import com.resoft.credit.contract.service.ContractPrintService;
import com.resoft.credit.contract.service.ContractTemplateService;
import com.resoft.credit.contract.service.ContractUploadFilesService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 合同模板Controller
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "${adminPath}/credit/contractTemplate")
public class ContractTemplateController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ContractTemplateController.class);

	@Autowired
	private ApplyRegisterService applyRegisterService;
	
	@Autowired
	private CompanyInfoService companyInfoService;
	
	@Autowired
	private ContractTemplateService contractTemplateService;

	@Autowired
	private ContractUploadFilesService contractUploadFilesService;

	@Autowired
	private ContractPrintService contractPrintService;

	@RequestMapping(value = { "/list", "" })
	public String list(ContractTemplate contractTemplate, HttpServletRequest request, HttpServletResponse response, Model model,String templateNameSearch) {
		try {
			//查询条件
			if(templateNameSearch != null && !"".equals(templateNameSearch)){
				templateNameSearch = URLDecoder.decode(templateNameSearch, "UTF-8");
				contractTemplate.setTemplateName(templateNameSearch);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("模板名称编码失败", e);
		}
		Page<ContractTemplate> page = contractTemplateService.findPage(new Page<ContractTemplate>(request, response), contractTemplate);
		model.addAttribute("page", page);
		return "app/credit/contract/template/contractTemplateList";
	}

	@RequestMapping(value = "/Form")
	public String Form(HttpServletRequest request, HttpServletResponse response, ContractTemplate contractTemplate, Model model) {

		return "app/credit/contract/template/contractTemplateFrom";
	}

	/**
	 * 加载修改页面
	 */
	@RequestMapping(value = "/updateForm")
	public String updateForm(HttpServletRequest request, HttpServletResponse response, ContractTemplate contractTemplate, Model model) {
		List<ContractTemplate> ctl = contractTemplateService.findList(contractTemplate);
		contractTemplate = ctl.get(0);
		model.addAttribute("contractTemplate", contractTemplate);
		return "app/credit/contract/template/contractTemplateUpdateFrom";
	}

	@RequestMapping(value = "/FormImport")
	public String FormImport(HttpServletRequest request, HttpServletResponse response, ContractTemplate contractTemplate, Model model,String templateTypeSearch,String productTypeSearch,String orgplatformSearch,String templateNameSearch,String templateNameSearchImport) {
		try {
			List<ContractTemplate> ctl = contractTemplateService.findList(contractTemplate);
			contractTemplate = ctl.get(0);
			try {
				if(templateNameSearch != null && !"".equals(templateNameSearch)){
					templateNameSearch = URLDecoder.decode(templateNameSearch, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("合同模板名称编码失败", e);
			}
			model.addAttribute("contractTemplate", contractTemplate);
			model.addAttribute("templateTypeSearch", templateTypeSearch);
			model.addAttribute("productTypeSearch", productTypeSearch);
			model.addAttribute("orgplatformSearch", orgplatformSearch);
			model.addAttribute("templateNameSearchImport", templateNameSearchImport);
			model.addAttribute("templateNameSearch", templateNameSearch);
		} catch (Exception e) {
			logger.error("导入失败", e);
		}
		return "app/credit/contract/template/contractTemplateFromImport";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	public String update(ContractTemplate contractTemplate, RedirectAttributes redirectAttributes) {
		ContractTemplate con = new ContractTemplate();
		con = contractTemplateService.get(contractTemplate.getId());
		String url;
		if (con != null) {
			con.setTemplateName(contractTemplate.getTemplateName());
			contractTemplateService.update(con);
			addMessage(redirectAttributes, "修改成功");
			url = "redirect:" + adminPath + "/credit/contractTemplate/";
		} else {
			addMessage(redirectAttributes, "修改失败");
			url = "redirect:" + adminPath + "/credit/contractTemplate/updateForm";
		}
		return url;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/addnewForm")
	public String addnewForm(ContractTemplate contractTemplate, RedirectAttributes redirectAttributes) {
		String url;
			contractTemplate.setIsuploadfile("0");
			contractTemplate.setProductType("0");
			contractTemplateService.insert(contractTemplate);
			addMessage(redirectAttributes, "保存成功");
			url = "redirect:" + adminPath + "/credit/contractTemplate/";
		return url;
	}

	@RequestMapping("/delete")
	public String delete(ContractTemplate contractTemplate, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
		String[] strid = request.getParameter("strid").split("-");
		for (int k = 0; k < strid.length; k++) {
			contractTemplate.setId(strid[k]);
			// 文件删除
//			ContractTemplate ctl = contractTemplateService.get(contractTemplate.getId());
//			String fileName = ctl.getTemplateType() + ctl.getProductType() + ".pdf";
			ContractUploadFiles cuf = new ContractUploadFiles();
			cuf.setModuleId(contractTemplate.getId());

			List<ContractUploadFiles> listcuf = contractUploadFilesService.findList(cuf);
//			String version = "1";// 版本号
			if (listcuf != null && listcuf.size() > 0) {
				for (int i = 0; i < listcuf.size(); i++) {

//					Integer in = new Integer(listcuf.get(i).getVersionNo());
//					version = in.intValue() + "";
//					String path = Global.getUserfilesBaseDir() + "upload/contract/";
					File file = new File(listcuf.get(i).getFilePath()+"\\" + listcuf.get(i).getFileName());
					if (file.exists()) {
						if (file.isFile()) {
							deleteFile(listcuf.get(i).getFilePath()+"\\", listcuf.get(i).getFileName());
						}
					}
				}
				contractUploadFilesService.delete(cuf);
			}
			contractTemplateService.delete(contractTemplate); // 删除模板配置
		}

		addMessage(redirectAttributes, "删除成功");

		return "redirect:" + adminPath + "/credit/contractTemplate/";
	}

	public static boolean deleteFile(String path, String fileName) {
		//+ File.separator 
		File file = new File(path + fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				// "删除文件失败!"
				return false;
			}
		} else {
			// "文件不存在!"
			return false;
		}
	}

	@RequestMapping("/import")
	public String upload(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes,Model model) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String id = request.getParameter("id");
		String isUnion = request.getParameter("isUnion");
		String version = "1";// 版本号
		ContractUploadFiles cuf = new ContractUploadFiles();
		cuf.setModuleId(id);
		List<ContractUploadFiles> listcuf = contractUploadFilesService.findList(cuf); // 获取上传记录
		if (listcuf != null && listcuf.size() > 0) {
			Integer in = new Integer(listcuf.get(0).getVersionNo());
			version = in.intValue() + 1 + "";
		}
		String savePath = Global.getUserfilesBaseDir() + "upload/contract/" + id;
		String message = "";
		File file = new File(savePath);
		// 如果不存在上传文件夹,就创建文件夹
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				// 上传文件
				MultipartFile mf = entity.getValue();
				String fileName = mf.getOriginalFilename();
				if (fileName == null || ("").equals(fileName.trim())) {
					message = "请选择上传文件";
					continue;
				}
				fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
				// 文件扩展名
				String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
				if ("docx".equals(fileExtName)) {
					ContractTemplate ctl = contractTemplateService.get(id);
					if("1".equals(isUnion)){
						fileName = ctl.getTemplateType() + ctl.getProductType() + "-union.docx";
					}else{
						fileName = ctl.getTemplateType() + ctl.getProductType() + ".docx";
					}
					
					ctl.setTemplateName(fileName);
					ctl.setIsuploadfile("1");
					InputStream in = mf.getInputStream();
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + File.separator + fileName);
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					contractTemplateService.updateName(ctl);// 将上传的文件按固定规则取名，然后保存起来
					// 添加一条上传记录
					cuf.preInsert();
					cuf.setFileName(fileName);
					cuf.setFilePath(savePath);
					cuf.setVersionNo(version);
					cuf.setModuleId(id);
					contractUploadFilesService.insert(cuf);
					
					message = "文件上传成功";
				} else {
					message = "上传文件类型有误，请重新上传docx类型文件!";
				}
			}
			addMessage(redirectAttributes, message);
			model.addAttribute("close", "close");
			model.addAttribute("message", message);
		} catch (Exception e) {
			addMessage(redirectAttributes, "模板上传发生未知错误");
			model.addAttribute("message", "模板上传发生未知错误");
			logger.error("模板上传发生未知错误", e);
		}
		String templateTypeSearch = request.getParameter("templateTypeSearch");
		String productTypeSearch = request.getParameter("productTypeSearch");
		String orgplatformSearch = request.getParameter("orgplatformSearch");
		String templateNameSearchImport = request.getParameter("templateNameSearch");
		
		ContractTemplate contractTemplate = new ContractTemplate();
		contractTemplate.setId(id);
		return FormImport(request, response, contractTemplate, model,templateTypeSearch,productTypeSearch,orgplatformSearch,null,templateNameSearchImport);
	}

	@RequestMapping("/download")
	public String download(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		String id = request.getParameter("id");
		String isUnion = request.getParameter("isUnion");
//		String version = "1";// 版本号
		ContractUploadFiles cuf = new ContractUploadFiles();
		cuf.setModuleId(id);
//		List<ContractUploadFiles> listcuf = contractUploadFilesService.findList(cuf); // 获取上传记录
//		if (listcuf != null && listcuf.size() > 0) {
//			Integer in = new Integer(listcuf.get(0).getVersionNo());
//			version = String.valueOf(in.intValue());
//		}
		String path = Global.getUserfilesBaseDir() + "upload/contract/" + id;
		ContractTemplate ctl = contractTemplateService.get(id);
		String fileName = null;
		if("1".equals(isUnion)){
			fileName = ctl.getTemplateType() + ctl.getProductType() + "-union.docx";
		}else{
			fileName = ctl.getTemplateType() + ctl.getProductType() + ".docx";
		}
		
		if (!"1".equals(ctl.getIsuploadfile())) {
			addMessage(redirectAttributes, "没有上传模板！");
			return "redirect:" + adminPath + "/credit/contractTemplate";
		}
		File file = new File(path + File.separator + fileName);
		if (!file.exists()) {
			if("1".equals(isUnion)){
				file = new File(path + File.separator + ctl.getTemplateType() + ctl.getProductType() + "-union.docx");
				if(!file.exists()){
					addMessage(redirectAttributes, "您要下载的资源已被删除！");
					return "redirect:" + adminPath + "/credit/contractTemplate/";
				}
			}else{
				addMessage(redirectAttributes, "您要下载的资源已被删除！");
				return "redirect:" + adminPath + "/credit/contractTemplate/";
			}
		}
		String realName = fileName.substring(fileName.indexOf("_") + 1);
		// 设置响应头,控制浏览下载该文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
		// 读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(path + File.separator + fileName);
		OutputStream out = response.getOutputStream();
		// 创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		// 循环将输入流中的内容读取到缓冲区中
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
		return "redirect:" + adminPath + "/credit/contractTemplate/";
	}

	@ResponseBody
	@RequestMapping("/printContract")
	public String printContract(HttpServletRequest request, HttpServletResponse response, ContractTemplate contractTemplate, RedirectAttributes redirectAttributes, Model model) {
		FileInputStream inputStream = null;
		OutputStream outputStream = null;
		String contractNo = request.getParameter("contractNo");
		try {
			contractTemplate = contractTemplateService.get(contractTemplate.getId());
			String path = Global.getUserfilesBaseDir() + "upload/contract";
			String fileName = contractTemplate.getTemplateType() + contractTemplate.getProductType() + ".docx";
			ContractUploadFiles cuf = new ContractUploadFiles();
			cuf.setModuleId(contractTemplate.getId());
			List<ContractUploadFiles> contractList = contractUploadFilesService.findList(cuf); // 获取上传记录
			String version = "1";
			if (!contractList.isEmpty()) {
				version = contractList.get(0).getVersionNo();
			}
			String templateFile = path + File.separator + version + File.separator + fileName;
			String destFile = path + File.separator + "down" + File.separator + fileName;
			if (!"1".equals(contractTemplate.getIsuploadfile())) {
				addMessage(redirectAttributes, "没有上传模板！");
				return "redirect:" + adminPath + "/credit/contractTemplate/print?id=" + contractTemplate.getId();
			}
			String realName = fileName.substring(fileName.indexOf("_") + 1);
			// 将数据加载到合同模板中
			contractTemplateService.generateWordFile(templateFile, destFile, contractNo);
			// 设置响应头,控制浏览下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
			inputStream = new FileInputStream(destFile);
			outputStream = response.getOutputStream();
			// 创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			// 循环将输入流中的内容读取到缓冲区中
			while ((len = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}
		} catch (Exception e) {
				logger.error("打印错误",e);
		} finally {
			try {
				inputStream.close();
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {

			}
		}
		return "redirect:" + adminPath + "/credit/contractTemplate/print?id=" + contractTemplate.getId();
	}

	/**
	 * 加载合同合同打印页面
	 * */
	@RequestMapping("/print")
	public String print(ContractTemplate contractTemplate, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
		try {
			String productType = contractTemplate.getProductType();
			if (!"".equals(productType) && productType != null) {
				List<ContractTemplate> templateList = contractTemplateService.findList(contractTemplate);
				model.addAttribute("listctl", templateList);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return "app/credit/contract/template/contractTemplatePrint";
	}

	// 打印选择
	@RequestMapping(value = "printSelect")
	public String printSelect(String contractNo, ContractTemplate contractTemplate, HttpServletRequest request, HttpServletResponse response, Model model,String applyNo) {
		Page<ContractTemplate> page = contractTemplateService.findPage(new Page<ContractTemplate>(request, response), contractTemplate);
		model.addAttribute("page", page);
		model.addAttribute("contractNo", contractNo);
		model.addAttribute("applyNo", applyNo);
		return "app/credit/contract/template/printSelect";
	}

	// 打印
	@ResponseBody
	@RequestMapping(value = "printWord")
	/**
	 * 
	 * @param contractNo	合同编号
	 * @param applyNo		申请编号
	 * @param id			id 
	 * @param contractTemplate
	 * @param request
	 * @param response
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	public String  printWord(String contractNo,String applyNo,String id,ContractTemplate contractTemplate, HttpServletRequest request, HttpServletResponse response,
			Model model,HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		id = id.substring(0, id.length()-1);
//		String version = "1";// 版本号
		ContractUploadFiles cuf = new ContractUploadFiles();
		cuf.setModuleId(id);
//		List<ContractUploadFiles> listcuf = contractUploadFilesService.findList(cuf); // 获取上传记录
//		if (listcuf != null && listcuf.size() > 0) {
//			Integer in = new Integer(listcuf.get(0).getVersionNo());
//			version = String.valueOf(in.intValue());
//		}
		String path = Global.getUserfilesBaseDir() + "upload/contract/" + id;
		ContractTemplate ctl = contractTemplateService.get(id);
		String fileName = ctl.getTemplateType() + ctl.getProductType() ;
		
		if (!"1".equals(ctl.getIsuploadfile())) {
			return "没有上传模板！";
		}
		String filePath = path + File.separator + fileName;
		String printNum=IdGen.uuid();
		if (StringUtils.isEmpty(contractNo)) {
			return "请先保存并生成还款计划！";
		}
		File file = contractReplace(contractNo,applyNo,printNum,filePath);
		//将生成的文件下载到本地
		cuf.setModuleId(id);
//		if (listcuf != null && listcuf.size() > 0) {
//			Integer in = new Integer(listcuf.get(0).getVersionNo());
//			version = String.valueOf(in.intValue());
//		}
		if (!"1".equals(ctl.getIsuploadfile())) {
			addMessage(redirectAttributes, "没有上传模板！");
			return "没有上传模板！";
		}
		if (!file.exists()) {
			addMessage(redirectAttributes, "您要下载的资源已被删除！");
			return "您要下载的资源已被删除！";
		}
		fileName += ".docx";
		String realName = fileName.substring(fileName.indexOf("_") + 1);
		// 设置响应头,控制浏览下载该文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
		// 读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(Global.getUserfilesBaseDir()+printNum+".docx");
		OutputStream out = response.getOutputStream();
		// 创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		// 循环将输入流中的内容读取到缓冲区中
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
		//下载后删除
		deleteFile(Global.getUserfilesBaseDir(), printNum+".docx");
		return "redirect:" + adminPath + "/credit/contractTemplate/printSelect";
	}

	//打印方法入口
	private File contractReplace(String contractNo,String applyNo, String printNum,String filePath)throws Exception {
		File file = null;
		Map<String, Object> paramss = new HashMap<String, Object>();
		paramss.put("contract_no", contractNo);
		//合同打印获得客户信息
		boolean isUnion = contractPrintGetCustInfo(applyNo,contractNo,paramss);
		if(isUnion){
			file = new File(filePath + "-union.docx");
			if(!file.exists()){
				file = new File(filePath + ".docx");
			}
		}else{
			file = new File(filePath + ".docx");
			//查询主借企业信息
			paramss = getMajorLoanCompany(applyNo, paramss);
		}
		//-------------联合授信和非联合授信通用数据------------------------------
		//担保人mapGurrantorInfo
		List<Map<String, Object>> gurrantorInfoList = contractPrintService.findGurrantorInfo(applyNo);
		if (gurrantorInfoList!=null){
			paramss.put("gurrantorInfoList",gurrantorInfoList);
		}
		//查询担保公司数据
		paramss = getGurrantorCompanyInfo(paramss,applyNo,isUnion);
		//查询借款合同信息
		getContractInfoList(contractNo, paramss, applyNo, isUnion);
		//收款确认使用 查询借款合同信息
		getContractInfoListByContractNo(contractNo, paramss, applyNo, isUnion);
		//打印时查询还款计划
		paramss = getRepayPlanSum(paramss,contractNo,isUnion);
		//查询还款计划
		paramss = getRepayPlanInfo(paramss,contractNo,isUnion);
		String pathTrue = file.getPath();
		FileInputStream is = new FileInputStream(pathTrue);
		XWPFDocument doc = new XWPFDocument(is);
		if(isUnion){
			clearUnionCustList(contractNo, paramss);
		}
		
		WordUtils.replaceInParaUnion(doc, paramss);
		WordUtils.removeAllForKeys(doc);
		WordUtils.replaceInTables(doc, paramss);
		OutputStream os = new FileOutputStream(Global.getUserfilesBaseDir()+printNum+".docx");
		doc.write(os);
		WordUtils.close(os);
		WordUtils.close(is);
		
		return file;
	}
	
	//对联合授信数据的收尾处理
	private void clearUnionCustList(String contractNo,Map<String, Object> paramss){
		List<Map<String, Object>> unionCustList = (List<Map<String, Object>>)paramss.get("unionCustList");
		//存在主从关系的联合授信
		List<Map<String, Object>> msUnionList = new ArrayList<Map<String, Object>>();
		Map<String,Object> single = null;
		for(Map<String, Object> map : unionCustList){
			String conNo = map.get("contract_no").toString();
			if(contractNo.equals(conNo)){
				single = map;
			}else{
				msUnionList.add(map);
			}
		}
		if(single != null){
			unionCustList = new ArrayList<Map<String, Object>>();
			unionCustList.add(single);
			paramss.putAll(single);
		}
		paramss.put("msUnionList", msUnionList);
	}
	
	// 打印方法入口
//	private File testReplace(String contractNo,String applyNo, String printNum,String filePath) throws Exception {
//		File file = null;
//		Map<String, Object> paramss = new HashMap<String, Object>();
//		Map<String, Object> mapMarginInfo = new HashMap<String,Object>();
//		paramss.put("contract_no", contractNo);
//		
//		//担保人mapGurrantorInfo
//		List<Map<String, Object>> gurrantorInfoList = contractPrintService.findGurrantorInfo(applyNo);
//		if (gurrantorInfoList!=null){
//			paramss.put("gurrantorInfoList",gurrantorInfoList);
//		}
//		
//		//担保-合同基本信息
//		Map<String, Object> mapContractBaseInfo = contractPrintService.findContractBaseInfo(contractNo);
//		if (mapContractBaseInfo!=null){
//			paramss.putAll(mapContractBaseInfo);
//		}
//		
//		//合同打印获得客户信息
//		boolean isUnion = contractPrintGetCustInfo(applyNo,contractNo,paramss);
//		if(isUnion){
//			file = new File(filePath + "-union.docx");
//			if(!file.exists()){
//				file = new File(filePath + ".docx");
//			}
//		}else{
//			file = new File(filePath + ".docx");
//			// 保证金
//			mapMarginInfo = contractPrintService.findMarginInfo(contractNo);
//			if(mapMarginInfo!=null){
//				Object margin_amount = mapMarginInfo.get("margin_amount");
//				if (margin_amount!=null){
//					BigDecimal numberOfMoney2 = new BigDecimal(margin_amount.toString());
//					String s2 = NumberToCN.number2CNMontrayUnit(numberOfMoney2);
//					mapMarginInfo.put("1margin_amount", s2);
//				}
//				paramss.putAll(mapMarginInfo);
//			}
//		}
//		
//		//担保
//		paramss = getGurrantorCompanyInfo(paramss,applyNo,isUnion);
//		
//		String pathTrue = file.getPath();
//		//车辆
//		List<Map<String, Object>> mapMortgageCarInfo = contractPrintService.findMortgageCarInfo(applyNo);
//		if (mapMortgageCarInfo != null && mapMortgageCarInfo.size() > 0) {
//			for (int i = 0; i < mapMortgageCarInfo.size(); i++) {
//				paramss.put(i + "vehicle_brand", mapMortgageCarInfo.get(i).get("vehicle_brand"));
//				paramss.put(i + "vehicle_no", mapMortgageCarInfo.get(i).get("vehicle_no"));
//				paramss.put(i + "engine_num", mapMortgageCarInfo.get(i).get("engine_num"));
//				paramss.put(i + "vehicle_type", mapMortgageCarInfo.get(i).get("vehicle_type"));
//				paramss.put(i + "vehicle_shelf_no", mapMortgageCarInfo.get(i).get("vehicle_shelf_no"));
//				paramss.put(i + "car_evaluate_price", mapMortgageCarInfo.get(i).get("car_evaluate_price"));
//			}
//		}
//		
//		//个人房屋  
//		List<Map<String, Object>> mapCustMortgageHouseInfo = contractPrintService.findCustMortgageHouseInfo(applyNo);
//		if (mapCustMortgageHouseInfo != null && mapCustMortgageHouseInfo.size() > 0) {
//			for (int i = 0; i < mapCustMortgageHouseInfo.size(); i++) {
//				String province = contractPrintService.findAddres((String) mapCustMortgageHouseInfo.get(i).get("cont_province"));
//				String city = contractPrintService.findAddres((String) mapCustMortgageHouseInfo.get(i).get("cont_city"));
//				String distinct = contractPrintService.findAddres((String) mapCustMortgageHouseInfo.get(i).get("cont_distinct"));
//				paramss.put(i + "house_addr", province+city+distinct+mapCustMortgageHouseInfo.get(i).get("house_addr"));
//				paramss.put(i + "building_area", mapCustMortgageHouseInfo.get(i).get("building_area"));
//				paramss.put(i + "evaluate_price", mapCustMortgageHouseInfo.get(i).get("evaluate_price"));
//				
//			}
//		}
//		
//		//企业房屋
//		List<Map<String, Object>> mapComMortgageHouseInfo = contractPrintService.findComMortgageHouseInfo(applyNo);
//		if (mapComMortgageHouseInfo != null && mapComMortgageHouseInfo.size() > 0) {
//			for (int i = 0; i < mapComMortgageHouseInfo.size(); i++) {
//				String province = contractPrintService.findAddres((String) mapComMortgageHouseInfo.get(i).get("cont_province"));
//				String city = contractPrintService.findAddres((String) mapComMortgageHouseInfo.get(i).get("cont_city"));
//				String distinct = contractPrintService.findAddres((String) mapComMortgageHouseInfo.get(i).get("cont_distinct"));
//				paramss.put(i + "house_addr_com", province+city+distinct+mapComMortgageHouseInfo.get(i).get("house_addr_com"));
//				paramss.put(i + "building_area_com", mapComMortgageHouseInfo.get(i).get("building_area_com"));
//				paramss.put(i + "evaluate_price_com", mapComMortgageHouseInfo.get(i).get("evaluate_price_com"));
//			}
//		}
//		
//		//查询合同信息
//		paramss = getContractInfoList(contractNo, paramss,applyNo,isUnion);
//		
//		//查询还款计划
//		paramss = getRepayPlanInfo(paramss,contractNo,isUnion);
//	
//		//
//		paramss = getRepayPlanSum(paramss,contractNo,isUnion);
//			
//		Map<String,Object> mapLoanInfo = contractPrintService.findLoanInfo(applyNo);
//		if(mapLoanInfo!=null){
//			Object fact_loan_amount = mapLoanInfo.get("fact_loan_amount");
//			if (fact_loan_amount!=null) {
//				BigDecimal numberOfMoney1 = new BigDecimal(fact_loan_amount.toString());
//				String s1 = NumberToCN.number2CNMontrayUnit(numberOfMoney1);
//				mapLoanInfo.put("1fact_loan_amount", s1);
//			}
//			
//			Object margin_amount = mapLoanInfo.get("margin_amount");
//			if (margin_amount!=null){
//				BigDecimal numberOfMoney2 = new BigDecimal(margin_amount.toString());
//				String s2 = NumberToCN.number2CNMontrayUnit(numberOfMoney2);
//				mapLoanInfo.put("1margin_amount", s2);
//				paramss.putAll(mapLoanInfo);
//			}
//		}
//			
//		Map<String,Object>  mapEarlyRepay = contractPrintService.findEarlyRepay(contractNo);
//		if(mapEarlyRepay!=null){
//			paramss.putAll(mapEarlyRepay);
//		}
//				
//		Map<String,Object> mapMainCustPhone = contractPrintService.findMainCustPhone(applyNo);
//		if(mapMainCustPhone!=null){
//			paramss.putAll(mapMainCustPhone);
//		}
//		
//		// 根据证件code取得证件类型
//		String idType =DictUtils.getDictLabel((String) paramss.get("id_type"), "CUSTOMER_P_ID_TYPE", "");
//		paramss.put("id_type", idType);
//		//根据银行code 取得银行名称
//		String bankName = DictUtils.getDictLabel((String) paramss.get("rep_bank"), "BANKS", "");
//		paramss.put("rep_bank", bankName);
//		
//		FileInputStream is = new FileInputStream(pathTrue);
//		XWPFDocument doc = new XWPFDocument(is);
//		if(isUnion){
//			Object unionCustListObject = paramss.get("unionCustList");
//			if(unionCustListObject != null){
//				List<Map<String, Object>> unionCustList = (List<Map<String, Object>>) unionCustListObject;
//				for(Map<String, Object> map : unionCustList){
//					//根据地址dode
//					String regProvince = (String) map.get("reg_province");
//					String regCity = (String) map.get("reg_city");
//					String regDistinct = (String) map.get("reg_distinct");
//					String regDetails = (String) map.get("reg_details");
//					//取得具体地质
//					String province = contractPrintService.findAddres(regProvince);
//					String city = contractPrintService.findAddres(regCity);
//					String distinct = contractPrintService.findAddres(regDistinct);
//					String cont_addr = province+city+distinct+regDetails;
//					map.put("cont_addr", cont_addr);
//				}
//			}
//			WordUtils.replaceInParaUnion(doc, paramss);
//		}else{
//			//根据地址dode
//			String cont_province = (String) paramss.get("cont_province");
//			String cont_city = (String) paramss.get("cont_city");
//			String cont_distinct = (String) paramss.get("cont_distinct");
//			String cont_details = (String) paramss.get("cont_details");
//			//取得具体地质
//			String province = contractPrintService.findAddres(cont_province);
//			String city = contractPrintService.findAddres(cont_city);
//			String distinct = contractPrintService.findAddres(cont_distinct);
//			String cont_addr = province+city+distinct+cont_details;
//			paramss.put("cont_addr", cont_addr);
//			WordUtils.replaceInParaUnion(doc, paramss);
//		}
//		WordUtils.removeAllForKeys(doc);
////		WordUtils.replaceInTable(doc, paramss);
//		OutputStream os = new FileOutputStream(Global.getUserfilesBaseDir()+printNum+".docx");
//		doc.write(os);
//		WordUtils.close(os);
//		WordUtils.close(is);
//		return file;
//	}
	
	
	//合同打印获得客户信息
	private boolean contractPrintGetCustInfo(String applyNo,String contractNo, Map<String, Object> paramss){
		boolean isUnion = false;
		ApplyRegister ar = applyRegisterService.getApplyRegisterByApplyNo(applyNo);
		//判断是否联合授信	1是  0否
		if(ar.getProcDefKey()!=null && ar.getProcDefKey().contains("union")){
			paramss.put("isUnion", "1");
			
			List<Map<String, Object>> unionCustList = contractPrintService.findUnionCustInfo(applyNo);
			boolean contProvinceOk = true;
			for(Map<String, Object> map : unionCustList){
				String conNo = map.get("contract_no").toString();
				if(contProvinceOk){
					String cont_province = (String) map.get("middleman_cont_province");
					//根据地址dode
					String cont_city = (String) map.get("middleman_cont_city");
					String cont_distinct = (String) map.get("middleman_cont_distinct");
					String cont_details = (String) map.get("middleman_cont_details");
					//取得具体地质
					String cont_addr = getContAddr(cont_province, cont_city, cont_distinct, cont_details);
					paramss.put("middleman_cont_addr", cont_addr);
					paramss.put("middleman_name", map.get("middleman_name"));
					paramss.put("middleman_id_num", map.get("middleman_id_num"));
					paramss.put("middleman_mobile_num", map.get("middleman_mobile_num"));
					paramss.put("middleman_bank_detail", map.get("middleman_bank_detail"));
					paramss.put("middleman_bankcard_no", map.get("middleman_bankcard_no"));
					contProvinceOk = false;
				}
				
				//根据地址dode
				String regProvince = (String) map.get("reg_province");
				String regCity = (String) map.get("reg_city");
				String regDistinct = (String) map.get("reg_distinct");
				String regDetails = (String) map.get("reg_details");
				//取得具体地质
				String province = contractPrintService.findAddres(regProvince);
				String city = contractPrintService.findAddres(regCity);
				String distinct = contractPrintService.findAddres(regDistinct);
				String cont_addr = province+city+distinct+regDetails;
				map.put("cont_addr", cont_addr);
				
				//保证金率
				String marginRate = new BigDecimal(map.get("margin_rate").toString()).toString();
				paramss.put("margin_rate", marginRate);
				// 保证金
				Map<String, Object> mapMarginInfo = contractPrintService.findMarginInfo(conNo);
				if(mapMarginInfo!=null){
					Object margin_amount = mapMarginInfo.get("margin_amount");
					if (margin_amount!=null){
						BigDecimal numberOfMoney2 = new BigDecimal(margin_amount.toString());
						String s2 = NumberToCN.number2CNMontrayUnit(numberOfMoney2);
						map.put("1margin_amount", s2);
					}
					map.putAll(mapMarginInfo);
				}
			}
			
			paramss.put("unionCustList", unionCustList);
			isUnion = true;
		}else{
			setIsNotUnion(paramss, applyNo, contractNo);
		}
		return isUnion;
	}
	
	private Map<String,Object> setIsNotUnion(Map<String,Object> paramss,String applyNo,String contractNo){
		paramss.put("isUnion", "0");
		//担保-借款人信息 
		Map<String, Object> mapCustInfo = contractPrintService.findCustInfo(applyNo);
		if(mapCustInfo!=null){
			paramss.putAll(mapCustInfo);
		}
		// 保证金
		Map<String, Object> mapMarginInfo = contractPrintService.findMarginInfo(contractNo);
		if(mapMarginInfo!=null){
			Object margin_amount = mapMarginInfo.get("margin_amount");
			if (margin_amount!=null){
				BigDecimal numberOfMoney2 = new BigDecimal(margin_amount.toString());
				String s2 = NumberToCN.number2CNMontrayUnit(numberOfMoney2);
				mapMarginInfo.put("1margin_amount", s2);
			}
			paramss.putAll(mapMarginInfo);
		}
		return paramss;
	}
	
	//查询主借企业
	private Map<String,Object> getMajorLoanCompany(String applyNo,Map<String, Object> paramss){
		Map<String,Object> companyMap =  contractPrintService.findLoanCustInfo(applyNo);
		if(companyMap != null){
			//根据地址dode
			String regProvince = (String) companyMap.get("reg_province");
			String regCity = (String) companyMap.get("reg_city");
			String regDistinct = (String) companyMap.get("reg_distinct");
			String regDetails = (String) companyMap.get("reg_details");
			//取得具体地质
			String province = contractPrintService.findAddres(regProvince);
			String city = contractPrintService.findAddres(regCity);
			String distinct = contractPrintService.findAddres(regDistinct);
			province = (province==null?"":province);
			city = (city==null?"":city);
			distinct = (distinct==null?"":distinct);
			regDetails = (regDetails==null?"":regDetails);
			String cont_addr = province+city+distinct+regDetails;
			companyMap.put("cont_addr", cont_addr);
			
			//根据地址dode
			String cont_province = (String) companyMap.get("middleman_cont_province");
			String cont_city = (String) companyMap.get("middleman_cont_city");
			String cont_distinct = (String) companyMap.get("middleman_cont_distinct");
			String cont_details = (String) companyMap.get("middleman_cont_details");
			//取得具体地质
			province = contractPrintService.findAddres(cont_province);
			city = contractPrintService.findAddres(cont_city);
			distinct = contractPrintService.findAddres(cont_distinct);
			province = (province==null?"":province);
			city = (city==null?"":city);
			distinct = (distinct==null?"":distinct);
			cont_details = (cont_details==null?"":cont_details);
			cont_addr = province+city+distinct+cont_details;
			companyMap.put("middleman_cont_addr", cont_addr);
			
			paramss.putAll(companyMap);
		}
		
		return paramss;
	}
	
	/**
	 * 
	 * @param name
	 * @param cont_province
	 * @param cont_city
	 * @param cont_distinct
	 * @param cont_details
	 * @return
	 */
	private String getContAddr(String province,String city,String distinct,String details){
		//取得具体地质
		province = contractPrintService.findAddres(province);
		city = contractPrintService.findAddres(city);
		distinct = contractPrintService.findAddres(distinct);
		province = (province==null?"":province);
		city = (city==null?"":city);
		distinct = (distinct==null?"":distinct);
		details = (details==null?"":details);
		return province+city+distinct+details;
	}
	
	//查询据体的合同信息
	private Map<String, Object> getContractInfoList(String contractNo,Map<String, Object> paramss,String applyNo,boolean isUnion){
		//借款信息 
		Map<String, Object> mapApplyInfo = contractPrintService.findApplyInfo(applyNo);
		BigDecimal sumContractAmount = new BigDecimal(0);
		if(isUnion){
			List<Map<String, Object>> unionCustList =(List<Map<String, Object>>) paramss.get("unionCustList");
			for(Map<String, Object> unionCust : unionCustList){
				Map<String, Object> mapContractInfo = contractPrintService.findContractInfo(unionCust.get("contract_no").toString()).get(0);
				//收款人地址
				mapContractInfo.put("rec_addr", getContAddr(mapContractInfo.get("rec_bank_province")+"", mapContractInfo.get("rec_bank_city")+"", mapContractInfo.get("rec_bank_distinct")+"", mapContractInfo.get("rec_bank_detail")+""));
				Object contract_amount = mapContractInfo.get("contract_amount");
				if (contract_amount!=null){
					BigDecimal numberOfMoney = new BigDecimal(contract_amount.toString());
					sumContractAmount = sumContractAmount.add(numberOfMoney);
					String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
					mapContractInfo.put("1contract_amount", s);
				}
				mapContractInfo.put("busi_reg_name", unionCust.get("busi_reg_name"));
				mapContractInfo.put("un_soc_credit_no", unionCust.get("un_soc_credit_no"));
				//根据银行code 取得银行名称
				String bankName = DictUtils.getDictLabel((String) mapContractInfo.get("rep_bank"), "BANKS", "");
				mapContractInfo.put("rep_bank", bankName);
				
				Object all_service_fee = mapContractInfo.get("all_service_fee");
				if (all_service_fee!=null){
					BigDecimal numberOfMoney = new BigDecimal(all_service_fee.toString());
					String s3 = NumberToCN.number2CNMontrayUnit(numberOfMoney);
					mapContractInfo.put("1all_service_fee", s3);
				}
				
				Object loan_amount = mapContractInfo.get("loan_amount");
				if (loan_amount!=null){
					BigDecimal numberOfMoney = new BigDecimal(loan_amount.toString());
					String s4 = NumberToCN.number2CNMontrayUnit(numberOfMoney);
					mapContractInfo.put("1loan_amount", s4);
				}
				
				mapContractInfo.put("loan_amount_CN", NumberToCN.number2CNMontrayUnit(new BigDecimal(loan_amount.toString()))); 
				
				Object money_pay = mapContractInfo.get("money_pay");
				if (money_pay!=null){
					BigDecimal numberOfMoney = new BigDecimal(money_pay.toString());
					String s4 = NumberToCN.number2CNMontrayUnit(numberOfMoney);
					mapContractInfo.put("1money_pay", s4);
				}
				mapContractInfo.putAll(mapApplyInfo);
				unionCust.putAll(mapContractInfo);
//				loanPurpostList.add(mapContractInfo);
			}
//			paramss.put("loanPurpostList", loanPurpostList);
		}else{
			Map<String, Object> mapContractInfo = contractPrintService.findContractInfo(contractNo).get(0);
			Object contract_amount = mapContractInfo.get("contract_amount");
			mapContractInfo.put("rec_addr", getContAddr(mapContractInfo.get("rec_bank_province")+"", mapContractInfo.get("rec_bank_city")+"", mapContractInfo.get("rec_bank_distinct")+"", mapContractInfo.get("rec_bank_detail")+""));
			if (contract_amount!=null){
				BigDecimal numberOfMoney = new BigDecimal(contract_amount.toString());
				String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
				mapContractInfo.put("1contract_amount", s);
				sumContractAmount = sumContractAmount.add(numberOfMoney);
			}
			
			Object all_service_fee = mapContractInfo.get("all_service_fee");
			if (all_service_fee!=null){
				BigDecimal numberOfMoney = new BigDecimal(all_service_fee.toString());
				String s3 = NumberToCN.number2CNMontrayUnit(numberOfMoney);
				mapContractInfo.put("1all_service_fee", s3);
			}
			
			Object loan_amount = mapContractInfo.get("loan_amount");
			if (loan_amount!=null){
				BigDecimal numberOfMoney = new BigDecimal(loan_amount.toString());
				String s4 = NumberToCN.number2CNMontrayUnit(numberOfMoney);
				mapContractInfo.put("1loan_amount", s4);
			}
			
			Object money_pay = mapContractInfo.get("money_pay");
			if (money_pay!=null){
				BigDecimal numberOfMoney = new BigDecimal(money_pay.toString());
				String s4 = NumberToCN.number2CNMontrayUnit(numberOfMoney);
				mapContractInfo.put("1money_pay", s4);
			}
			//根据银行code 取得银行名称
			String bankName = DictUtils.getDictLabel((String) mapContractInfo.get("rep_bank"), "BANKS", "");
			mapContractInfo.put("rep_bank", bankName);
			paramss.putAll(mapApplyInfo);
			paramss.putAll(mapContractInfo);
		}
		contractAmountFourRatioSix(sumContractAmount, paramss);
		return paramss;
	}
	
	
	//修改查询据体的合同信息(收款确认书相关信息查询)
		private Map<String, Object> getContractInfoListByContractNo(String contractNo,Map<String, Object> paramss,String applyNo,boolean isUnion){
			//借款信息 
			BigDecimal sumContractAmount = new BigDecimal(0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("contract_no", contractNo);
			List<Map<String, Object>>  contractMapList =  contractPrintService.findContractInfo(contractNo);
			if(isUnion){
				if(contractMapList.size()>1){
					//公正版收款确认可以使用
					for(Map<String, Object> unionCust : contractMapList){
						Object contract_amount = unionCust.get("contract_amount");
						if (contract_amount!=null){
							BigDecimal numberOfMoney = new BigDecimal(contract_amount.toString());
							sumContractAmount = sumContractAmount.add(numberOfMoney);
						}
					}
				}else{
						//单笔 冠e通联合授信 收款确认
						BigDecimal numberOfMoney = new BigDecimal(contractMapList.get(0).get("contract_amount").toString());
						sumContractAmount = sumContractAmount.add(numberOfMoney);
				}
			}else{
				//非联合授信
				BigDecimal numberOfMoney = new BigDecimal(contractMapList.get(0).get("contract_amount").toString());
				sumContractAmount = sumContractAmount.add(numberOfMoney);
			}
			contractAmountFourRatioSixForNew(sumContractAmount, paramss);
			return paramss;
		}
	
	
	/**
	 * 合同金额拆分成46比列的金额
	 * @param sumContractAmount
	 * @param paramss
	 */
	private void contractAmountFourRatioSix(BigDecimal sumContractAmount,Map<String, Object> paramss){
		BigDecimal interestRate = new BigDecimal("0.4"); //利率   
		BigDecimal fourRatio = sumContractAmount.multiply(interestRate);
		interestRate = new BigDecimal("0.6"); //利率   
		BigDecimal sixRatio = sumContractAmount.multiply(interestRate);
		String s = NumberToCN.number2CNMontrayUnit(fourRatio);
		paramss.put("contract_amount_four_ratio", fourRatio.toString());
		paramss.put("1contract_amount_four_ratio", s);
		s = NumberToCN.number2CNMontrayUnit(sixRatio);
		paramss.put("contract_amount_six_ratio", sixRatio.toString());
		paramss.put("1contract_amount_six_ratio", s);
		
		paramss.put("sum_contract_amount",sumContractAmount.toString());
		paramss.put("1sum_contract_amount", NumberToCN.number2CNMontrayUnit(sumContractAmount));
	}
	
	/**
	 * 合同金额拆分成46比列的金额(新)
	 * @param sumContractAmount
	 * @param paramss
	 */
	private void contractAmountFourRatioSixForNew(BigDecimal sumContractAmount,Map<String, Object> paramss){
		BigDecimal interestRate = new BigDecimal("0.4"); //利率   
		BigDecimal fourRatio = sumContractAmount.multiply(interestRate);
		interestRate = new BigDecimal("0.6"); //利率   
		BigDecimal sixRatio = sumContractAmount.multiply(interestRate);
		String s = NumberToCN.number2CNMontrayUnit(fourRatio);
		paramss.put("contract_amount_four_ratio_new", fourRatio.toString());
		paramss.put("1contract_amount_four_ratio_new", s);
		s = NumberToCN.number2CNMontrayUnit(sixRatio);
		paramss.put("contract_amount_six_ratio_new", sixRatio.toString());
		paramss.put("1contract_amount_six_ratio_new", s);
		
		paramss.put("sum_contract_amount_new",sumContractAmount.toString());
		paramss.put("1sum_contract_amount_new", NumberToCN.number2CNMontrayUnit(sumContractAmount));
	}
	
	/**
	 * 合同打印处理还款计划
	 * @param paramss
	 * @param contractNo
	 * @param isUnion
	 * @return
	 */
	private Map<String, Object> getRepayPlanInfo(Map<String, Object> paramss,String contractNo,boolean isUnion){
		if(isUnion){
			List<Map<String, Object>> unionCustList = (List<Map<String, Object>>)paramss.get("unionCustList");
			List<Map<String, Object>> sumlistRepayPlanInfo = new ArrayList<Map<String, Object>>();
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			for(int u=0;u<unionCustList.size();u++){
				//没有排序的还款计划
				List<Map<String, Object>> listRepayPlanInfo = contractPrintService.findRepayPlanInfoUnion(unionCustList.get(u).get("contract_no").toString());
				if (listRepayPlanInfo != null && listRepayPlanInfo.size() > 0) {
					unionCustList.get(u).put("pay_long", listRepayPlanInfo.size());
					unionCustList.get(u).put("pay_date", listRepayPlanInfo.get(0).get("deduct_date").toString().substring(8,10));
					if(u==0){
						paramss.put("pay_long",unionCustList.get(u).get("pay_long"));
						paramss.put("pay_date",unionCustList.get(u).get("pay_date"));
					}
					List<Map<String, Object>> planUnionList = new ArrayList<Map<String, Object>>();
//					Map<String, Object> sumMap = new HashMap<String, Object>();
					for (int i = 0; i < listRepayPlanInfo.size(); i++) {
//						if( i == 0){
//							sumMap.put("period_num", listRepayPlanInfo.get(i).get("period_num"));
//							sumMap.put("deduct_date", listRepayPlanInfo.get(i).get("deduct_date"));
//							sumMap.put("repay_amount", "0");
//							sumlistRepayPlanInfo.add(sumMap);
//						}
						Map<String, Object> b = new HashMap<String, Object>();
						b.put("index", i+1);
						b.put("period_num", listRepayPlanInfo.get(i).get("period_num"));
						b.put("deduct_date", listRepayPlanInfo.get(i).get("deduct_date"));
						b.put("management_fee", listRepayPlanInfo.get(i).get("management_fee"));
						b.put("repay_amount", listRepayPlanInfo.get(i).get("repay_amount"));
						b.put("capital_interest", listRepayPlanInfo.get(i).get("capital_interest"));
						b.put("service_fee", listRepayPlanInfo.get(i).get("service_fee"));
						planUnionList.add(b);
					}
					unionCustList.get(u).put("planUnionList", planUnionList);
				}
			}
			
//			//联合授信时，把还款计划的每个月月还款相加
//			for(int i=0; i<sumlistRepayPlanInfo.size();i++){
//				BigDecimal sumRepay = new BigDecimal(sumlistRepayPlanInfo.get(i).get("repay_amount").toString());
//				String period_num="";
//				String deduct_date="";
//				for(int j=0;j<unionCustList.size();j++){
//					List<Map<String, Object>> planUnionList = (List<Map<String, Object>>)unionCustList.get(j).get("planUnionList");
//					sumRepay = sumRepay.add(new BigDecimal(planUnionList.get(i).get("repay_amount").toString()));
//					period_num = String.valueOf(planUnionList.get(i).get("period_num"));
//					deduct_date = sdf.format(planUnionList.get(i).get("deduct_date"));
//				}
//				sumlistRepayPlanInfo.get(i).put("repay_amount", sumRepay.toString());
//			}
			
			//by wpy 
			//调整公正抵质押借款合同还款计划
			sumlistRepayPlanInfo = contractPrintService.findRepayPlanInfoUnionNew(contractNo);
			paramss.put("sumPlanUnionList",sumlistRepayPlanInfo);
		}else{
			//没有排序的还款计划
			List<Map<String, Object>> listRepayPlanInfo = contractPrintService.findRepayPlanInfo(contractNo);
			if (listRepayPlanInfo != null && listRepayPlanInfo.size() > 0) {
				paramss.put("pay_long", listRepayPlanInfo.size());
				paramss.put("pay_date", listRepayPlanInfo.get(0).get("deduct_date").toString().substring(8,10));
				List<Map<String, Object>> planUnionList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < listRepayPlanInfo.size(); i++) {
					Map<String, Object> b = new HashMap<String, Object>();
					b.put("index", i+1);
					b.put("period_num", listRepayPlanInfo.get(i).get("period_num"));
					b.put("deduct_date", listRepayPlanInfo.get(i).get("deduct_date"));
					b.put("management_fee", listRepayPlanInfo.get(i).get("management_fee"));
					b.put("repay_amount", listRepayPlanInfo.get(i).get("repay_amount"));
					b.put("capital_interest", listRepayPlanInfo.get(i).get("capital_interest"));
					b.put("service_fee", listRepayPlanInfo.get(i).get("service_fee"));
					planUnionList.add(b);
				}
				paramss.put("planUnionList", planUnionList);
			}
		}
		return paramss;
	}
	
	/**
	 * 查谒担保公司数据
	 * @param paramss
	 * @param applyNo
	 * @return
	 */
	private Map<String, Object> getGurrantorCompanyInfo(Map<String, Object> paramss, String applyNo,boolean isUnion){
		List<Map<String, Object>> unionCustList = (List<Map<String, Object>>)paramss.get("unionCustList");
		Map<String, Object> par = Maps.newConcurrentMap();
		par.put("applyNo", applyNo);
		par.put("roleType", Constants.ROLE_TYPE_DBQY);
		List<CompanyInfo> companyInfoList = companyInfoService.findListByParams(par);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(CompanyInfo com : companyInfoList){
			boolean isOk = true;
			if(isUnion){
				for(Map<String, Object> map : unionCustList){
					if(com.getId().equals(map.get("companyInfoId"))){
						isOk = false;
						break;
					}
				}
			}
			if(isOk){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("busi_reg_name", com.getBusiRegName());
				map.put("un_soc_credit_no",com.getUnSocCreditNo());
				map.put("corporation_name",com.getCorporationName());
				map.put("corporation_mobile",com.getCorporationMobile());
				map.put("reg_province", com.getRegProvince());
				map.put("reg_city", com.getRegCity());
				map.put("reg_distinct",com.getRegDistinct());
				map.put("reg_details", com.getRegDetails());
				map.put("cont_addr",com.getRegProvince() + com.getRegCity()+com.getRegDistinct()+com.getRegDetails());
				list.add(map);
			}
		}
		paramss.put("companyInfoList", list);
		
		return paramss;
	}
	
	/**
	 * 打印时查询还款计划
	 * @param paramss
	 * @param contractNo
	 * @param ContractInfoUnionList
	 * @param isUnion
	 * @return
	 */
	private Map<String, Object> getRepayPlanSum(Map<String, Object> paramss,String contractNo,boolean isUnion){
		if(isUnion){
//			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> unionCustList = (List<Map<String, Object>>)paramss.get("unionCustList");
			for(Map<String,Object> map : unionCustList){
//				Map<String,Object> beanMap = new HashMap<String,Object>();
				/*	还款计划 帐务管理费*/
				Map<String,Object> mapRepayPlanSum = contractPrintService.findRepayPlanSumUnion(map.get("contract_no").toString().toString());
				if (mapRepayPlanSum!=null){
					Object sum_management_fee = mapRepayPlanSum.get("sum_management_fee");
					if (sum_management_fee!=null){
						BigDecimal numberOfMoney = new BigDecimal(sum_management_fee.toString());
						String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
						mapRepayPlanSum.put("1sum_management_fee", s);
					}
					map.putAll(mapRepayPlanSum);
				}
				
//				list.add(beanMap);
			}
//			paramss.put("repayPlanSumUnionList", list);
		}else{
			/*	还款计划 帐务管理费*/
			Map<String,Object> mapRepayPlanSum = contractPrintService.findRepayPlanSum(contractNo);
			if (mapRepayPlanSum!=null){
				Object sum_management_fee = mapRepayPlanSum.get("sum_management_fee");
				if (sum_management_fee!=null){
					BigDecimal numberOfMoney = new BigDecimal(sum_management_fee.toString());
					String s = NumberToCN.number2CNMontrayUnit(numberOfMoney);
					mapRepayPlanSum.put("1sum_management_fee", s);
				}
				paramss.putAll(mapRepayPlanSum);
			}
		}
		return paramss;
	}
}
