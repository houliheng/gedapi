package com.resoft.postloan.showImage.web;

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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import resoft.crms.util.json.JsonUtil;

import com.resoft.postloan.plProFromColumn.entity.PLProFromColumn;
import com.resoft.postloan.plVideoPath.entity.PLVideoPath;
import com.resoft.postloan.plVideoPath.service.PLVideoPathService;
import com.resoft.postloan.uploadFile.uploadFile.Manager;
import com.resoft.postloan.uploadFile.util.FdfsUtils;
import com.resoft.postloan.uploadFile.util.FileUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 综合查询Controller
 * @author admin
 * @version 2016-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/showImage")
public class ShowImageController extends BaseController {

	@Autowired
	private PLVideoPathService creVieoPathService;
	private List videoPathList;
    
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response,Model model,String applyNo) {
		model.addAttribute("applyNo", applyNo);
		return "app/postloan/showImage/treeJsp";
	}

	@RequestMapping(value="/showTree")
	public @ResponseBody void showTree(PLProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String applyNo){
		String idsStr = request.getParameter("ids");
		String taskDefKey= request.getParameter("taskDefKey");
		taskDefKey="1";
		videoPathList=creVieoPathService.findCreVideoPathList(idsStr,taskDefKey); 	
 		JSONArray jsonArray=new JSONArray();
 		Map<String,String> idMap=new TreeMap<String,String>();
 		Map<String,String> nameMap=new TreeMap<String,String>();
		for(int i=0;i<videoPathList.size();i++)
    	{
			PLVideoPath cvp=new PLVideoPath();
			cvp=(PLVideoPath) videoPathList.get(i);
			String id=cvp.getApplyNo()+cvp.getFileDir();
			String pId=null;
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
			if(idMap.get(s).equals(idsStr+"/")){
				jsonObject.put("open","true");
			}else{
				jsonObject.put("open","false");
			}
			
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
	
		
	@RequestMapping(value="/showPicture")
	public @ResponseBody void showPicture(PLProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String filePath,String pageNo){
		filePath=request.getParameter("filePath"); 
		pageNo=request.getParameter("pageNo"); 
		
		  List list1=null;
		  List list2=null;
		  List list3=new ArrayList();
		  List list5=null;
		  Map checkMap = null;
			
		for(int i=0;i<videoPathList.size();i++){
			PLVideoPath cvp=new PLVideoPath();
			
			cvp=(PLVideoPath) videoPathList.get(i);
			if((cvp.getApplyNo()+cvp.getFileDir()).equals(filePath)&&cvp.getFileName().contains(".jpg")){
				checkMap = new HashMap();
				list1=new ArrayList();
				list2=new ArrayList();
				list5=new ArrayList();
				if(cvp.getFileStoragePath()!=null){
				list1.add(cvp.getFileStoragePath());
				list2.add(cvp.getThumbnailStoragePath());
				list5.add(cvp.getCreateBy1());
				//list2.add(cvp.getThumbnailStoragePath());
				
				checkMap.put("fileStoragePath", list1);
				checkMap.put("thumbnailStoragePath", list2);
				checkMap.put("createBy1",list5);
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
	public @ResponseBody void showPictureDiv(PLProFromColumn creProFromColumn,HttpServletRequest request, HttpServletResponse response, Model model,String filePath){
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
	public String  delPicture(String ids,HttpServletRequest request, HttpServletResponse response,Model model){
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
		return "app/postloan/showImage/treeJsp";
	}
}