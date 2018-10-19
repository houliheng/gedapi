package com.resoft.credit.applyRegister.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resoft.accounting.fdfs.util.FdsfConstant;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ApplyRegisterVO;
import com.resoft.credit.applyRegister.entity.CreVideoPath;
import com.resoft.credit.applyRegister.service.CreVieoPathService;
import com.resoft.credit.applyRegister.service.VideoParamService;
import com.resoft.credit.dynamicSet.entity.CreProFromColumn;
import com.resoft.credit.fdfs.Manager;
import com.resoft.credit.fdfs.util.FdfsUtils;
import com.resoft.credit.fdfs.util.FileUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import resoft.crms.util.json.JsonUtil;

/**
 * 综合查询Controller
 * @author admin
 * @version 2016-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/ApplyRegisterVO")
public class ApplyRegisterVOController extends BaseController {

	@Autowired
	private VideoParamService videoParamService;
	@Autowired
	private CreVieoPathService creVieoPathService;
	@Autowired
	private OfficeService officeService;
	
	/*private List videoPathList;*/
	@RequestMapping(value = {"list", ""})
	public String list(ApplyRegisterVO ApplyRegisterVO, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ApplyRegisterVO> page = comprehensiveQueryService.findPage(new Page<ApplyRegisterVO>(request, response), ApplyRegisterVO);
//		model.addAttribute("page", page);
		return "app/credit/comprehensiveQuery/comprehensiveQueryList";
	}

	@RequestMapping(value = "form")
	public String form(ApplyRegisterVO ApplyRegisterVO, HttpServletRequest request, HttpServletResponse response,Model model) {
		model.addAttribute("applyRegisterVO", ApplyRegisterVO);
		model.addAttribute("applyNo", ApplyRegisterVO.getApplyNo());
		model.addAttribute("taskDefKey",request.getParameter("taskDefKey"));
		model.addAttribute("status",request.getParameter("status"));
		return "app/credit/comprehensiveQuery/treeJsp";
	}
	
	@RequestMapping(value = "post_form")
	public String post_form(ApplyRegisterVO ApplyRegisterVO, HttpServletRequest request, HttpServletResponse response,Model model) {
		model.addAttribute("applyRegisterVO", ApplyRegisterVO);
		model.addAttribute("applyNo", ApplyRegisterVO.getApplyNo());
		model.addAttribute("taskDefKey",request.getParameter("taskDefKey"));
		model.addAttribute("status",request.getParameter("status"));
		return "app/credit/comprehensiveQuery/postTreeJsp";
	}

	@RequestMapping(value="/showTree")
	public @ResponseBody void showTree(CreProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String applyNo){
		String idsStr = request.getParameter("ids");
		String taskDefKey= request.getParameter("taskDefKey");
		String taskDefKeyGrade=taskDefKey;
		List<CreVideoPath> videoPathList;
		if (Constants.DBGS_YYZZ.equals(taskDefKey) || Constants.DBGS_JYXKZ.equals(taskDefKey) ||Constants.DBGS_DBHMB.equals(taskDefKey) || Constants.DBGS_QT.equals(taskDefKey)) {
			videoPathList=creVieoPathService.findCreVideoPathDbgsList(idsStr,taskDefKey);
		}else{
			taskDefKey=videoParamService.getAuthorityLevel(taskDefKey);
			videoPathList=creVieoPathService.findCreVideoPathList(idsStr,taskDefKey);
		}
 		JSONArray jsonArray=new JSONArray();
 		Map<String,String> idMap=new TreeMap<String,String>();
 		Map<String,String> nameMap=new TreeMap<String,String>();
		String grade = "";
		if("utask_fgsfksh".equalsIgnoreCase(taskDefKeyGrade)||"utask_fgsjlsh".equalsIgnoreCase(taskDefKeyGrade)){
			grade="4"; //分公司
		}
		if("utask_qyfksh".equalsIgnoreCase(taskDefKeyGrade)||"utask_qyjlsh".equalsIgnoreCase(taskDefKeyGrade)){
			grade="3"; 		
		}
		if("utask_dqfkzysh".equalsIgnoreCase(taskDefKeyGrade)||"utask_dqfkjlsh".equalsIgnoreCase(taskDefKeyGrade)){
			grade="2"; 
		}
		if("utask_zgsfksh".equalsIgnoreCase(taskDefKeyGrade)||"utask_zgsjlsh".equalsIgnoreCase(taskDefKeyGrade)){
			grade="1"; 
		}
		for(int i=0;i<videoPathList.size();i++)
    	{
			CreVideoPath cvp=new CreVideoPath();
			cvp=(CreVideoPath) videoPathList.get(i);
			if(interceptorPicture(grade,cvp.getFileDir())){	
				String id=cvp.getApplyNo()+cvp.getFileDir();
				String name=null;
				String pathId=id;
				while(pathId.indexOf("/")!=-1){
					name=pathId.substring( pathId.substring(0,pathId.length()-1).lastIndexOf("/")+1,pathId.length()-1);
					pathId=pathId.substring(0, pathId.substring(0,pathId.length()-1).lastIndexOf("/")+1);
					idMap.put(pathId+name+"/", pathId);
					nameMap.put(pathId+name+"/", name);
				}
			}
    	}
		for(String s:idMap.keySet()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id",s);
			jsonObject.put("pId",idMap.get(s));
			jsonObject.put("name",nameMap.get(s));
			jsonObject.put("isParent","false");
			jsonObject.put("click", "onCheck('"+s+"','1')");

			jsonObject.put("open","false");

			jsonArray.add(jsonObject);
		}



		String json=JsonUtil.toJsonString(jsonArray);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(json);
		} catch (IOException e) {
			logger.error("showTree失败", e);
		}finally{
			out.close();
		}

	}
	
	
	private boolean interceptorPicture(String grade, String fileDir) {
		if(fileDir.contains(Constants.GWZZQRS_NAME)){
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
		return true;
	}
	
	/**
	 * 无权限访问影像树
	 * @param creProFromColumn
	 * @param request
	 * @param response
	 * @param model
	 * @param applyNo
	 */
	@RequestMapping(value="/showPostTree")
	public @ResponseBody void showPostTree(CreProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String applyNo){
		String idsStr = request.getParameter("ids");
		List<CreVideoPath> videoPathList;
		videoPathList=creVieoPathService.findPostCreVideoPathList(idsStr);
 		JSONArray jsonArray=new JSONArray();
 		Map<String,String> idMap=new TreeMap<String,String>();
 		Map<String,String> nameMap=new TreeMap<String,String>();
		for(int i=0;i<videoPathList.size();i++)
    	{
			CreVideoPath cvp=new CreVideoPath();
			cvp=(CreVideoPath) videoPathList.get(i);
			String id=cvp.getApplyNo()+cvp.getFileDir();
			String name=null;
			String pathId=id;
			while(pathId.indexOf("/")!=-1){
				name=pathId.substring( pathId.substring(0,pathId.length()-1).lastIndexOf("/")+1,pathId.length()-1);
				pathId=pathId.substring(0, pathId.substring(0,pathId.length()-1).lastIndexOf("/")+1);
				idMap.put(pathId+name+"/", pathId);
				nameMap.put(pathId+name+"/", name);
			}
    	}
		for(String s:idMap.keySet()){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id",s);
			jsonObject.put("pId",idMap.get(s));
			jsonObject.put("name",nameMap.get(s));
			jsonObject.put("isParent","false");
			jsonObject.put("click", "onCheck('"+s+"','1')");

			jsonObject.put("open","false");

			jsonArray.add(jsonObject);
		}



		String json=JsonUtil.toJsonString(jsonArray);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(json);
		} catch (IOException e) {
			logger.error("showTree失败", e);
		}finally{
			out.close();
		}

	}

	@RequestMapping(value="/postShowPicture")
	public @ResponseBody void postShowPicture(CreProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String filePath,String pageNo){
		filePath=request.getParameter("filePath");
		pageNo=request.getParameter("pageNo");

		  List list1=null;
		  List list2=null;
		  List list3=new ArrayList();
		  List list5=null;
		  List list6=null;
		  List lockList = null;
		  Map checkMap = null;

		String idsStr = request.getParameter("ids");
		String taskDefKey= request.getParameter("taskDefKey");
		taskDefKey=videoParamService.getAuthorityLevel(taskDefKey);
		List videoPathList;
		videoPathList=creVieoPathService.findPostCreVideoPathList(idsStr);

		for(int i=0;i<videoPathList.size();i++){
			CreVideoPath cvp=new CreVideoPath();

			cvp=(CreVideoPath) videoPathList.get(i);
			if((cvp.getApplyNo()+cvp.getFileDir()).equals(filePath)&&(!cvp.getFileName().contains(".pdf"))){
				checkMap = new HashMap();
				list1=new ArrayList();
				list2=new ArrayList();
				list5=new ArrayList();
				list6=new ArrayList();
				lockList = new ArrayList();
				if(cvp.getFileStoragePath()!=null){
				list1.add(cvp.getFileStoragePath());
				list2.add(cvp.getThumbnailStoragePath());
				list5.add(cvp.getCreateBy1());
				list6.add(cvp.getFileName().substring(0, cvp.getFileName().length()-4));
				lockList.add(cvp.getLockFlag());

				checkMap.put("fileStoragePath", list1);
				checkMap.put("thumbnailStoragePath", list2);
				checkMap.put("createBy1",list5);
				checkMap.put("fileName", list6);
				checkMap.put("lockFlag", lockList);
				list3.add(checkMap);
				}

			}
		}
		Map pageMap = new HashMap();
		      int listSize=list3.size();
        	  list3=list3.subList((Integer.parseInt(pageNo)-1)*15, Math.min(Integer.parseInt(pageNo)*15, list3.size()));
              pageMap.put("pageNo", pageNo);
              pageMap.put("pageSize", 15);
              pageMap.put("listSize", listSize);
              pageMap.put("filePath", filePath);
              pageMap.put("currentUser", UserUtils.getUser().getName());
              pageMap.put("feilServerUrl", FdsfConstant.fdfsServerUrl);
              list3.add(pageMap);

		String jsonStr = JsonUtil.toJsonString(list3);
		try {
			response.setContentType("application/json;charser=utf-8");
			response.setCharacterEncoding("utf-8");
			Writer out = response.getWriter();
			out.write(jsonStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("显示图片失败", e);
		}

	}

	@RequestMapping(value="/showPicture")
	public @ResponseBody void showPicture(CreProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String filePath,String pageNo){
		filePath=request.getParameter("filePath");
		pageNo=request.getParameter("pageNo");
		
		  List list1=null;
		  List list2=null;
		  List list3=new ArrayList();
		  List list5=null;
		  List list6=null;
		  List lockList = null;
		  Map checkMap = null;

		String idsStr = request.getParameter("ids");
		String taskDefKey= request.getParameter("taskDefKey");
		if (Constants.DBGS_YYZZ.equals(taskDefKey) || Constants.DBGS_JYXKZ.equals(taskDefKey) ||Constants.DBGS_DBHMB.equals(taskDefKey) || Constants.DBGS_QT.equals(taskDefKey)) {
		}else{
			taskDefKey=videoParamService.getAuthorityLevel(taskDefKey);
		}
		List videoPathList;
		videoPathList=creVieoPathService.findCreVideoPathList(idsStr,taskDefKey);

		for(int i=0;i<videoPathList.size();i++){
			CreVideoPath cvp=new CreVideoPath();

			cvp=(CreVideoPath) videoPathList.get(i);
			if((cvp.getApplyNo()+cvp.getFileDir()).equals(filePath)&&(!cvp.getFileName().contains(".pdf"))){
				checkMap = new HashMap();
				list1=new ArrayList();
				list2=new ArrayList();
				list5=new ArrayList();
				list6=new ArrayList();
				lockList = new ArrayList();
				if(cvp.getFileStoragePath()!=null){
				list1.add(cvp.getFileStoragePath());
				list2.add(cvp.getThumbnailStoragePath());
				list5.add(cvp.getCreateBy1());
				list6.add(cvp.getFileName().substring(0, cvp.getFileName().length()-4));
				lockList.add(cvp.getLockFlag());

				checkMap.put("fileStoragePath", list1);
				checkMap.put("thumbnailStoragePath", list2);
				checkMap.put("createBy1",list5);
				checkMap.put("fileName", list6);
				checkMap.put("lockFlag", lockList);
				list3.add(checkMap);
				}

			}
		}
		Map pageMap = new HashMap();
		      int listSize=list3.size();
        	  list3=list3.subList((Integer.parseInt(pageNo)-1)*15, Math.min(Integer.parseInt(pageNo)*15, list3.size()));
              pageMap.put("pageNo", pageNo);
              pageMap.put("pageSize", 15);
              pageMap.put("listSize", listSize);
              pageMap.put("filePath", filePath);
              pageMap.put("currentUser", UserUtils.getUser().getName());
              pageMap.put("feilServerUrl", FdsfConstant.fdfsServerUrl);
              list3.add(pageMap);

		String jsonStr = JsonUtil.toJsonString(list3);
		try {
			response.setContentType("application/json;charser=utf-8");
			response.setCharacterEncoding("utf-8");
			Writer out = response.getWriter();
			out.write(jsonStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("显示图片失败", e);
		}

	}
	@RequestMapping(value="/showPictureDiv")
	public @ResponseBody void showPictureDiv(CreProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String filePath){
		OutputStream out = null;
		try {
		byte[] b=Manager.download(filePath) ;
			out = response.getOutputStream();
			out.write(b);
		} catch (Exception e) {
			logger.error("显示图片Div失败", e);
		}finally{
			try {
				FileUtils.closeIOSource(out);
			} catch (IOException e) {
				logger.error("关闭IO资源失败", e);
			}
		}

	}
	@RequestMapping(value="/delPicture")
	public String  delPicture(String ids,ApplyRegisterVO ApplyRegisterVO, HttpServletRequest request, HttpServletResponse response,Model model){
		List<String> pathVal=Arrays.asList(ids.split(","));
		Iterator<String> it = pathVal.iterator();
		while(it.hasNext()){
			String videoPath=it.next();
			String[]  storgePath = videoPath.split("\\|");
			try {
				creVieoPathService.deleteByVideoPath(storgePath[0]);
				for(String s:storgePath){
					if (StringUtils.isNotEmpty(s)){
							FdfsUtils.delete(s);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("删除图片失败:",e);
			}
		}
		model.addAttribute("currentUser", UserUtils.getUser());
		model.addAttribute("applyNo", request.getParameter("applyNo"));
		model.addAttribute("taskDefKey",request.getParameter("taskDefKey"));
		model.addAttribute("status", request.getParameter("status"));
		return "app/credit/comprehensiveQuery/treeJsp";
	}
}