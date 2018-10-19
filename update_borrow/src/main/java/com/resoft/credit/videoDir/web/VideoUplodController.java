package com.resoft.credit.videoDir.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.common.utils.DateUtils;
import com.resoft.common.utils.ZipUtils;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.CreVideoPath;
import com.resoft.credit.applyRegister.service.ApplyRegisterService;
import com.resoft.credit.applyRegister.service.CreVieoPathService;
import com.resoft.credit.applyRegister.service.VideoParamService;
import com.resoft.credit.fdfs.util.FdfsUtils;
import com.resoft.credit.videoDir.entity.VideoDir;
import com.resoft.credit.videoDir.service.VideoUploadService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping({ "${adminPath}/credit/videoUpload" })
public class VideoUplodController extends BaseController {
	@Autowired
	private VideoUploadService videoUploadService;
	@Autowired
	private VideoParamService videoParamService;
	@Autowired
	private ApplyRegisterService applyRegisterService;
	@Autowired
	private CreVieoPathService creVieoPathService;

	@RequestMapping({ "" })
	public String index(ActTaskParam actTaskParam, VideoDir videoDir, Model model, HttpServletRequest request) {
		videoDir.setType("1");
		model.addAttribute("applyNo", actTaskParam.getApplyNo());
		model.addAttribute("taskDefKey", request.getParameter("taskDefKey"));
		model.addAttribute("list", this.videoUploadService.findList(videoDir));
		model.addAttribute("videoDir", videoDir);
		model.addAttribute("id", "11");
		return "app/credit/videoDir/videoUploadIndex";
	}

	@ResponseBody
	@RequestMapping({ "treeData" })
	public List<Map<String, Object>> treeData(String extId, String type, HttpServletResponse response, VideoDir videoDir,String taskDefKey) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<VideoDir> list = this.videoUploadService.findList(videoDir);
		String grade = "";
		if("utask_fgsfksh".equalsIgnoreCase(taskDefKey)||"utask_fgsjlsh".equalsIgnoreCase(taskDefKey)){
			grade="4"; //分公司
		}
		if("utask_qyfksh".equalsIgnoreCase(taskDefKey)||"utask_qyjlsh".equalsIgnoreCase(taskDefKey)){
			grade="3"; 		
		}
		if("utask_dqfkzysh".equalsIgnoreCase(taskDefKey)||"utask_dqfkjlsh".equalsIgnoreCase(taskDefKey)){
			grade="2"; 
		}
		if("utask_zgsfksh".equalsIgnoreCase(taskDefKey)||"utask_zgsjlsh".equalsIgnoreCase(taskDefKey)){
			grade="1"; 
		}
		for (int i = 0; i < list.size(); i++) {
			VideoDir e = (VideoDir) list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getName());
			map.put("type", type);
			map.put("fileDir", e.getFileDir());
			if(checkIsAdd(grade,e.getFileDir(),null)){
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	//downType为空表示会显示其他的   downType不为空表示只是对应的岗责 
		private boolean checkIsAdd(String grade, String fileDir,String downType) {
			if(fileDir.contains(Constants.GWZZQRS_NAME)){
				if(StringUtils.isEmpty(grade)){//不属于这八个流程节点的，隐藏岗责下载
					return false;
				}
				if(Constants.ZGS.equals(grade)){
					return true;
				}
				if(Constants.DQ.equals(grade)){
					if(fileDir.contains(Constants.ZGS_GWZZQRS_NAME))
						return false;
					else 
						return true;
				}
				if(Constants.QY.equals(grade)){
					if(fileDir.contains(Constants.ZGS_GWZZQRS_NAME)||fileDir.contains(Constants.DQ_GWZZQRS_NAME))
						return false;
					else 
						return true;
				}
				if(Constants.FZG.equals(grade)){
					if(fileDir.contains(Constants.ZGS_GWZZQRS_NAME)||fileDir.contains(Constants.DQ_GWZZQRS_NAME)||fileDir.contains(Constants.QY_GWZZQRS_NAME))
						return false;
					else 
						return true;
				}
			}
			if(StringUtils.isEmpty(downType)){
				return true;
			}else{
				return false;
			}
			
		}
		

	@ResponseBody
	@RequestMapping({ "userDefined" })
	public List<Map<String, Object>> userDefined(String extId, VideoDir videoDir) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<VideoDir> list = this.videoUploadService.findCustList(videoDir);
		for (int i = 0; i < list.size(); i++) {
			VideoDir e = (VideoDir) list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getName());
			map.put("type", e.getType());
			mapList.add(map);
		}
		return mapList;
	}

	@RequestMapping({ "videoShow" })
	public String videoShow(VideoDir videoDir, Model model, HttpServletRequest request) {
		model.addAttribute("applyNo", request.getParameter("applyNo"));
		videoDir = this.videoUploadService.get(videoDir.getId());
		model.addAttribute("videoDir", videoDir);
		model.addAttribute("taskDefKey", request.getParameter("taskDefKey"));
		logger.info("-----------------------使用者："+UserUtils.getUser().getName()+"----------------------");   
		return "app/credit/videoDir/videoShow";
	}

	@RequestMapping({ "webuploader" })
	public String webuploader(VideoDir videoDir, Model model, HttpServletRequest request) {
		model.addAttribute("applyNo", request.getParameter("applyNo"));
		model.addAttribute("videoDir", videoDir);
		model.addAttribute("taskDefKey", request.getParameter("taskDefKey"));
		return "app/credit/videoDir/webuploader";
	}

	@ResponseBody
	@RequestMapping({ "addColumn" })
	public String addColumn(VideoDir videoDir, Model model) {
		String _id = this.videoUploadService.getIdByName(videoDir);
		videoDir.setId(_id);
		String applyNo = videoDir.getApplyNo();
		List<VideoDir> list = this.videoUploadService.findAllList(videoDir);
		int fileNum = this.videoUploadService.getNumByName(videoDir);
		for (int i = 0; i < list.size(); i++) {
			String name = ((VideoDir) list.get(i)).getName();
			String id = ((VideoDir) list.get(i)).getId();
			String fileDir = ((VideoDir) list.get(i)).getFileDir();
			if (i == 0) {
				((VideoDir) list.get(i)).setName(name.substring(0, name.length() - 1) + (fileNum + 2));
				((VideoDir) list.get(i)).setId(id + (fileNum + 1) + applyNo);
				((VideoDir) list.get(i)).setParent(new VideoDir("0"));
				((VideoDir) list.get(i)).setApplyNo(videoDir.getApplyNo());
				((VideoDir) list.get(i)).setSort(Integer.valueOf(((VideoDir) list.get(i)).getSort().intValue() + (fileNum + 1)));
				((VideoDir) list.get(i)).setFileDir(fileDir.substring(0, fileDir.length() - 1) + (fileNum + 2));
			} else {
				((VideoDir) list.get(i)).setId(id + (fileNum + 1) + applyNo);
				((VideoDir) list.get(i)).setParent(new VideoDir(((VideoDir) list.get(0)).getId()));
				((VideoDir) list.get(i)).setApplyNo(videoDir.getApplyNo());
				String[] fileName = fileDir.split("/");
				((VideoDir) list.get(i)).setFileDir(fileName[0].substring(0, fileName[0].length() - 1) + (fileNum + 2) + "/" + fileName[1]);
			}
		}
		this.videoUploadService.insert(list);
		return "success";
	}

	@RequestMapping({ "rootForm" })
	public String rootForm(VideoDir videoDir, Model model) {
		return "app/credit/videoDir/rootForm";
	}

	@ResponseBody
	@RequestMapping({ "save" })
	public AjaxView save(VideoDir videoDir, Model model) {
		AjaxView ajaxView = new AjaxView();
		try {
			if ((videoDir.getParent().getId() == null) || (videoDir.getParent().getId().equals(""))) {
				videoDir.setParent(new VideoDir("0"));
				videoDir.setParentIds("-1");
				videoDir.setFileDir(videoDir.getName());
				videoDir.setSort(Integer.valueOf(240));
				if (!StringUtils.isNull(this.videoUploadService.isExist(videoDir))) {
					ajaxView.setTimeout().setMessage("目录重复，请重新填写目录！");
					return ajaxView;
				}
			} else {
				String p_name = this.videoUploadService.getFileNameById(videoDir);
				videoDir.setFileDir(p_name + "/" + videoDir.getName());
				videoDir.setSort(Integer.valueOf(240));
			}
			this.videoUploadService.save(videoDir);
			ajaxView.setSuccess().setMessage("保存自定义目录成功");
		} catch (Exception e) {
			this.logger.error("保存自定义目录失败", e);
			ajaxView.setFailed().setMessage("保存自定义目录失败");
		}
		return ajaxView;
	}

	@RequestMapping({ "upload" })
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				if (file != null) {
					String myFileName = file.getOriginalFilename();
					if (StringUtils.isNotEmpty(myFileName.trim())) {
						String result = FdfsUtils.upload1(file) + "," + myFileName;
						//String result = FdfsUtils.myUpload(file) + "," + myFileName;
						return result;
					}
				}
			}
		}
		return null;
	}

	@RequestMapping({ "savePath" })
	public void savePath(HttpServletRequest request, HttpServletResponse response) {
		CreVideoPath creVideo = new CreVideoPath();
		String src = request.getParameter("src");
		String applyNo = request.getParameter("applyNo");
		String fileDir = "/" + request.getParameter("fileDir") + "/";
		String taskDefKey = request.getParameter("taskDefKey");
		creVideo.setTaskDefKey(taskDefKey);
		if (Constants.DBGS_YYZZ.equals(taskDefKey) || Constants.DBGS_JYXKZ.equals(taskDefKey) ||Constants.DBGS_DBHMB.equals(taskDefKey) || Constants.DBGS_QT.equals(taskDefKey)) {
		}else{
			taskDefKey = this.videoParamService.getAuthorityLevel(taskDefKey);
		}
		String storagePath = request.getParameter("storagePath");
		String[] pathAndName = storagePath.split(",");
		String registerDate = this.applyRegisterService.getRegisterDateByApplyNo(applyNo);
		creVideo.setApplyNo(applyNo);
		creVideo.setFileDir(fileDir);
		creVideo.setFileName(pathAndName[1]);
		creVideo.setFileStoragePath(pathAndName[0]);
		creVideo.setThumbnailStoragePath(src);
		creVideo.setCreateBy1(UserUtils.getUser().getName());
		creVideo.setRegisterDate(registerDate);
		creVideo.setCreateDate1(DateUtils.getDateTime());
		this.creVieoPathService.saveCreVideoPath(creVideo, taskDefKey);
	}

	@RequestMapping({ "/delPicture" })
	public String delPicture(String ids, HttpServletRequest request, HttpServletResponse response, VideoDir videoDir, Model model) {
		List<String> pathVal = Arrays.asList(ids.split(","));
		Iterator<String> it = pathVal.iterator();
		while (it.hasNext()) {
			String videoPath = (String) it.next();
			String[] storgePath = videoPath.split("\\|");
			try {
				this.creVieoPathService.deleteByVideoPath(storgePath[0]);
				for (String s : storgePath) {
					if (StringUtils.isNotEmpty(s)) {
						FdfsUtils.delete(s);
					}
				}
			} catch (IOException e) {
				this.logger.error("删除图片失败:", e);
			}
		}
		model.addAttribute("currentUser", UserUtils.getUser());
		model.addAttribute("applyNo", request.getParameter("applyNo"));
		model.addAttribute("taskDefKey", request.getParameter("taskDefKey"));
		model.addAttribute("id", request.getParameter("id"));
		model.addAttribute("videoDir", videoDir);
		return "app/credit/videoDir/videoUploadIndex";
	}

	@RequestMapping({ "download" })
	public void download(String applyNo, String taskDefKey, String fileDir, HttpServletResponse response,String downType) {
		List<CreVideoPath> videoPathList;
		if("1".equals(downType)){
			 videoPathList = this.creVieoPathService.findCreVideoPathList(applyNo, taskDefKey,null);
		}else{
			 videoPathList = this.creVieoPathService.findCreVideoPathList(applyNo, taskDefKey, fileDir.substring(fileDir.indexOf("/"), fileDir.length()));
			
		}
		String tempPath = System.getProperty("java.io.tmpdir");
		String filePath = new StringBuffer(tempPath).append(File.separator).append(applyNo).toString();
		String grade = "";
		if("utask_fgsfksh".equals(taskDefKey)||"utask_fgsjlsh".equals(taskDefKey)){
			grade="4"; //分公司
		}
		if("utask_qyfksh".equals(taskDefKey)||"utask_qyjlsh".equals(taskDefKey)){
			grade="3"; 		
		}
		if("utask_dqfkzysh".equals(taskDefKey)||"utask_dqfkjlsh".equals(taskDefKey)){
			grade="2"; 
		}
		if("utask_zgsfksh".equals(taskDefKey)||"utask_zgsjlsh".equals(taskDefKey)){
			grade="1"; 
		}
		for (CreVideoPath cre : videoPathList) {
			if(checkIsAdd(grade,cre.getFileDir(),downType)){
				StringBuffer fileTempPath = new StringBuffer(tempPath).append(File.separator).append(applyNo).append(File.separator).append(cre.getFileDir());
				String fileDirPath = null;
				OutputStream os=null;
				try {
					fileDirPath = new String(fileTempPath.toString().replace("/", File.separator).getBytes("GBK"), "GBK");
					File photofileDir = new File(fileDirPath);
					if (!photofileDir.exists()) {
						photofileDir.mkdirs();
					}
					File photofile = new File(fileDirPath + cre.getFileName());
					if (!photofile.exists()) {
						photofile.createNewFile();
						os = new FileOutputStream(photofile);
						byte[] bs = FdfsUtils.download(cre.getFileStoragePath());
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
	
	@ResponseBody
	@RequestMapping({ "checkHasFile" })
	public AjaxView checkHasFile(String applyNo, String taskDefKey, String fileDir, HttpServletResponse response,String downType) {
		AjaxView ajaxView = new AjaxView();
		List<CreVideoPath> videoPathList = this.creVieoPathService.findCreVideoPathList(applyNo, taskDefKey,null);
		String grade = "";
		if("utask_fgsfksh".equals(taskDefKey)||"utask_fgsjlsh".equals(taskDefKey)){
			grade="4"; //分公司
		}
		if("utask_qyfksh".equals(taskDefKey)||"utask_qyjlsh".equals(taskDefKey)){
			grade="3"; 		
		}
		if("utask_dqfkzysh".equals(taskDefKey)||"utask_dqfkjlsh".equals(taskDefKey)){
			grade="2"; 
		}
		if("utask_zgsfksh".equals(taskDefKey)||"utask_zgsjlsh".equals(taskDefKey)){
			grade="1"; 
		}
		int amountFile=0;
		for (CreVideoPath cre : videoPathList) {
			if(checkIsAdd(grade,cre.getFileDir(),downType)){
				amountFile++;
			}
		}
		if(amountFile==0){
			ajaxView.setFailed();
		}else{
			ajaxView.setSuccess();
		}
		return ajaxView;
	}
	
	/*@RequestMapping({ "download" })
	public void download(String applyNo, String taskDefKey, String fileDir, HttpServletResponse response,String downType) {
		List<CreVideoPath> videoPathList;
		if("1".equals(downType)){
			 videoPathList = this.creVieoPathService.findCreVideoPathList(applyNo, taskDefKey,null);
		}else{
			 videoPathList = this.creVieoPathService.findCreVideoPathList(applyNo, taskDefKey, fileDir.substring(fileDir.indexOf("/"), fileDir.length()));
			
		}
		String tempPath = System.getProperty("java.io.tmpdir");
		String filePath = new StringBuffer(tempPath).append(File.separator).append(applyNo).toString();
		Office company = UserUtils.getUser().getCompany();//1代表总部，以此类推
		String grade = officeService.get(company.getId()).getGrade();
		for (CreVideoPath cre : videoPathList) {
			if(checkIsAdd(grade,cre.getFileDir(),downType)){
				StringBuffer fileTempPath = new StringBuffer(tempPath).append(File.separator).append(applyNo).append(File.separator).append(cre.getFileDir());
				String fileDirPath = null;
				try {
					fileDirPath = new String(fileTempPath.toString().replace("/", File.separator).getBytes("GBK"), "GBK");
					File photofileDir = new File(fileDirPath);
					if (!photofileDir.exists()) {
						photofileDir.mkdirs();
					}
					File photofile = new File(fileDirPath + cre.getFileName());
					if (!photofile.exists()) {
						photofile.createNewFile();
						OutputStream os = new FileOutputStream(photofile);
						//byte[] bs = FdfsUtils.download(cre.getFileStoragePath());
						//-------------------------------------------------------------------------
						String savePath = Global.getUserfilesBaseDir()+cre.getFileName();
						byte[] bs=new byte[1024*10000];
						FileInputStream fis=new FileInputStream(new File(savePath));
						fis.read(bs, 0, bs.length);
						os.write(bs);
						//-------------------------------------------------------------------------
						if (bs != null) {
							os.write(bs);
						} else {
							photofile.delete();
						}
						os.close();
					}
				} catch (Exception e) {
					logger.error("", e);
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
	}*/
}