package com.resoft.credit.contractSearch.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.common.utils.ZipUtils;
import com.resoft.credit.contract.entity.Contract;
import com.resoft.credit.contract.service.ContractService;
import com.resoft.credit.fdfs.util.FdfsUtils;
import com.resoft.outinterface.rest.newged.entity.ContractPDFAllResponse;
import com.resoft.outinterface.rest.newged.entity.ContractPDFInstanceResponse;
import com.resoft.outinterface.rest.newged.entity.ContractPDFRequest;
import com.resoft.outinterface.utils.Facade;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.common.utils.StringUtils;
@Controller
@RequestMapping(value = "${adminPath}/credit/contractSearch")
public class ContractSearchController extends BaseController {

    @Autowired
    private ContractService contractService;

    @RequestMapping(value = { "list", "" })
    public String showAllContract(HttpServletRequest request, HttpServletResponse response, Contract contract,Model model){
        MyMap paramMap = new MyMap();
        if(contract.getContractNo()!=null) {
        	paramMap.put("contractNo", contract.getContractNo());
        }
        if( contract.getLoanCompanyName()!=null) {
        	paramMap.put("loanCompanyName", contract.getLoanCompanyName());
        }
        Page<MyMap> page = null;
        page = contractService.showAllContract(new Page<MyMap>(request, response), paramMap);
        model.addAttribute("page", page);
        model.addAttribute("contract", contract);
        return "app/credit/contract/contractSearch";
    }
    
    
    @ResponseBody
	@RequestMapping({ "getDownloadPath" })
	public AjaxView getDownloadPath(String contractNo,String type, HttpServletResponse response) {
		AjaxView ajaxView = new AjaxView();
		/*ContractPDFAllResponse contractPDFAllResponse =getContractPDFAllResponse();
		List<ContractPDFInstanceResponse> data = contractPDFAllResponse.getData();
		data=chooseFilePDF(type,data);*/
		
		List<ContractPDFInstanceResponse> data = null;
		if("2".equals(type)) {//合同
			ContractPDFRequest contractPDFRequest = new ContractPDFRequest(contractNo);
			HashMap<String , Object> hashMap = Facade.facade.getContractFacePDF(contractPDFRequest,contractNo);
			if(hashMap==null||!("0000".equals(hashMap.get("code")))) {
				return ajaxView.setFailed();
			}
			HashMap<String,String> hashMap2 = (HashMap<String, String>) hashMap.get("data");
			String url = hashMap2.get("loanContractUrl");
			if (StringUtils.isNotEmpty(url)){
				data = new ArrayList<>();
				ContractPDFInstanceResponse contractPDFInstanceResponse = new ContractPDFInstanceResponse();
				contractPDFInstanceResponse.setTitle("借款合同");
				contractPDFInstanceResponse.setType("2");
				contractPDFInstanceResponse.setUrl(url);
				data.add(contractPDFInstanceResponse);
			}else {
				return ajaxView.setFailed();
			}
		}else if("1".equals(type)){//全部
			ContractPDFRequest contractPDFRequest = new ContractPDFRequest(contractNo);
			ContractPDFAllResponse contractPDFAllResponse = Facade.facade.getContractPDF(contractPDFRequest,contractNo);
			if(contractPDFAllResponse==null||!("0000".equals(contractPDFAllResponse.getCode()))) {
				return ajaxView.setFailed();
			}
			data = contractPDFAllResponse.getData();
			HashMap<String , Object> hashMap = Facade.facade.getContractFacePDF(contractPDFRequest,contractNo);
			String url = "";
			if(hashMap==null||!("0000".equals(hashMap.get("code")))) {
				if(data.size()>0){
					
				}else {
					return ajaxView.setFailed();
				}
			}else {
				HashMap<String,String> hashMap2 = (HashMap<String, String>) hashMap.get("data");
				url = hashMap2.get("loanContractUrl");
			}
			if (StringUtils.isNotEmpty(url)){
				ContractPDFInstanceResponse contractPDFInstanceResponse = new ContractPDFInstanceResponse();
				contractPDFInstanceResponse.setTitle("借款合同");
				contractPDFInstanceResponse.setType("2");
				contractPDFInstanceResponse.setUrl(url);
				data.add(contractPDFInstanceResponse);
			}/*else {
				return ajaxView.setFailed();
			}*/
		}else {//前5个
			ContractPDFRequest contractPDFRequest = new ContractPDFRequest(contractNo);
			ContractPDFAllResponse contractPDFAllResponse = Facade.facade.getContractPDF(contractPDFRequest,contractNo);
			if(contractPDFAllResponse==null||!("0000".equals(contractPDFAllResponse.getCode()))) {
				return ajaxView.setFailed();
			}
			data = contractPDFAllResponse.getData();
			data=chooseFilePDF(type,data);
		}
		if(data==null) {
			return ajaxView.setFailed();
		}else {
			String json = null;
			try {
				for (int i = 0; i < data.size(); i++) {
					String url = data.get(i).getUrl();
					if(StringUtils.isEmpty(url)) {
						data.remove(i);
						i--;
					}
				}
				json = JsonTransferUtils.bean2Json(data);
				ajaxView.setSuccess();
				ajaxView.setMessage(json);
				return ajaxView;
			} catch (IOException e) {
				e.printStackTrace();
				return ajaxView.setFailed();
			}
		}
	}
    
    @RequestMapping({ "download" })
	public void download(String contractNo,String msg, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
    	msg = URLDecoder.decode(msg, "UTF-8");
		String tempPath = System.getProperty("java.io.tmpdir");
		List<LinkedHashMap<String,String>> data = JsonTransferUtils.json2Bean(msg, List.class);
		String filePath = new StringBuffer(tempPath).append(File.separator).append(contractNo).toString();
		for (LinkedHashMap<String,String> contractPDFInstanceResponse :data) {
			StringBuffer fileTempPath = new StringBuffer(tempPath).append(File.separator).append(contractNo).append(File.separator);
			String fileDirPath = null;
			OutputStream os=null;
			try {
				fileDirPath = new String(fileTempPath.toString().replace("/", File.separator).getBytes("GBK"), "GBK");
				File photofileDir = new File(fileDirPath);
				if (!photofileDir.exists()) {
					photofileDir.mkdirs();
				}
				File photofile = new File(fileDirPath + contractPDFInstanceResponse.get("title")+".pdf");
				if (!photofile.exists()) {
					photofile.createNewFile();
					os = new FileOutputStream(photofile);
					byte[] bs;
					/*if("2".equals(contractPDFInstanceResponse.get("type"))) {//合同
						bs = FdfsUtils.downloadContractPDF(contractPDFInstanceResponse.get("url"));
					}else {*/
						bs = FdfsUtils.downloadPDF(contractPDFInstanceResponse.get("url"));
					//}
					if (bs != null) {
						os.write(bs);
					} else {
						photofile.delete();
					}
				}
			} catch (Exception e) {
				logger.error("", e);
			}finally {
				if(os!=null){
					try {
						os.flush();
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		try {
			File zipFile = ZipUtils.fileToZip(filePath);
			ZipUtils.downloadZip(zipFile, response);
			ZipUtils.deleteFile(new File(filePath));
			zipFile.delete();
		} catch (Exception e) {
			logger.error("下载错误！", e);
		}
	}


	public List<ContractPDFInstanceResponse> chooseFilePDF(String type, List<ContractPDFInstanceResponse> data) {
		if("201".equals(type)) {
			return data = surePDFFile(data,"201");
		}
		if("202".equals(type)) {
			return data = surePDFFile(data,"202");
		}
		if("203".equals(type)) {
			return data = surePDFFile(data,"203");
		}
		if("205".equals(type)) {
			return data = surePDFFile(data,"205");
		}
		if("206".equals(type)) {
			return data = surePDFFile(data,"206");
		}
		if("207".equals(type)) {
			return data = surePDFFile(data,"207");
		}
		if("1".equals(type)) {
			return data ;
		}
		return null;
	}

	public List<ContractPDFInstanceResponse> surePDFFile(List<ContractPDFInstanceResponse> data, String title) {
		String flag = "0";
		for (int i = 0; i < data.size(); i++) {
			if(title.equals(data.get(i).getType())) {
				ContractPDFInstanceResponse contractPDFInstanceResponse = data.get(i);
				data.clear();
				data.add(contractPDFInstanceResponse);
				flag = "1";
			}
		}
		if("1".equals(flag)) {
			return data;
		}else {
			return null;
		}
		
	}


	public ContractPDFAllResponse getContractPDFAllResponse() {
		ContractPDFAllResponse contractPDFAllResponse =new ContractPDFAllResponse();
		List<ContractPDFInstanceResponse> data = new ArrayList<ContractPDFInstanceResponse>();
		ContractPDFInstanceResponse e = new ContractPDFInstanceResponse("借款服务协议","pubfile/M00/01/27/ooYBAFsU1riAQbrDAAPiuWYYsFg180.pdf","201");
		ContractPDFInstanceResponse e1 = new ContractPDFInstanceResponse("信息采集授权书","pubfile/M00/00/31/pYYBAFsU1ryAAqllAAIaJxQGhxg422.pdf","203");
		ContractPDFInstanceResponse e2 = new ContractPDFInstanceResponse("授权提现函","pubfile/M00/01/27/oYYBAFsU1r2AXK4zAAHoYdeIyU0462.pdf","205");
		ContractPDFInstanceResponse e3 = new ContractPDFInstanceResponse("电子签名数字证书用户申请确认函","pubfile/M00/01/27/ooYBAFsU1r6ADj7lAAIKe_yVi3U667.pdf","206");
		data.add(e);
		data.add(e1);
		data.add(e2);
		data.add(e3);
		contractPDFAllResponse.setData(data);
		return contractPDFAllResponse;
	}
	
}
