package com.resoft.credit.videoDir.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.common.utils.Constants;
import com.resoft.credit.videoDir.entity.VideoDir;
import com.resoft.credit.videoDir.service.VideoDirService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 影像目录管理Controller
 * @author wanghong
 * @version 2016-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/videoDir")
public class VideoDirController extends BaseController {

	@Autowired
	private VideoDirService videoDirService;
	
	@ModelAttribute
	public VideoDir get(@RequestParam(required=false) String id) {
		VideoDir entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = videoDirService.get(id);
		}
		if (entity == null){
			entity = new VideoDir();
		}
		return entity;
	}
	
	@RequiresPermissions("credit:videoDir:view")
	@RequestMapping(value = {""})
	public String index(VideoDir videoDir, Model model) {
		videoDir.setType(Constants.PRODUCT_TYPE_XY);
		model.addAttribute("videoDir", videoDir);
		return "app/credit/videoDir/videoDirIndex";
	}
	
	@RequiresPermissions("credit:videoDir:view")
	@RequestMapping(value = {"list"})
	public String list(VideoDir videoDir, Model model) {
		model.addAttribute("list", videoDirService.findByParentIdsLike(videoDir));
		return "app/credit/videoDir/videoDirList";
	}
	
	@RequiresPermissions("credit:videoDir:view")
	@RequestMapping(value = {"listOrg"})
	public String listOrg(VideoDir videoDir, Model model) {
		model.addAttribute("list", videoDirService.findList(videoDir));
		return "app/credit/videoDir/videoDirList";
	}

	@RequiresPermissions("credit:videoDir:view")
	@RequestMapping(value = "form")
	public String form(VideoDir videoDir, Model model, String isCheck) {
		videoDir.setParent(videoDirService.get(videoDir.getParent().getId()));
		VideoDir videoTmp = videoDirService.get(videoDir.getParentId());
		if(videoTmp != null){
			videoDir.setParentIds(videoTmp.getParentIds()+','+videoDir.getParentId());
		}else{
			videoDir.setParent(new VideoDir("0"));
			videoDir.setParentIds("0");
		}
		model.addAttribute("videoDir", videoDir);
		model.addAttribute("isCheck", isCheck);
		return "app/credit/videoDir/videoDirForm";
	}

	@RequiresPermissions("credit:videoDir:edit")
	@RequestMapping(value = "save")
	public String save(VideoDir videoDir, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, videoDir)){
			return form(videoDir, model, "0");
		}
		videoDirService.save(videoDir);
		addMessage(redirectAttributes, "保存影像目录管理成功");
		return "redirect:"+Global.getAdminPath()+"/credit/videoDir/list?repage";
	}
	
	@RequiresPermissions("credit:videoDir:edit")
	@RequestMapping(value = "delete")
	public String delete(VideoDir videoDir, RedirectAttributes redirectAttributes) {
		videoDirService.delete(videoDir);
		addMessage(redirectAttributes, "删除影像目录管理成功");
		return "redirect:"+Global.getAdminPath()+"/credit/videoDir/list?repage";
	}
	
	@RequiresPermissions("credit:videoDir:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String extId,	String type, HttpServletResponse response, VideoDir videoDir) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<VideoDir> list = videoDirService.findList(videoDir);
		for (int i = 0; i < list.size(); i++) {
			VideoDir e = list.get(i);
		
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getName());
			map.put("type", type);

			/*if (type != null && "1".equals(type)){
				map.put("isParent", true);
			}*/	
			mapList.add(map);
		}
		return mapList;
	}
	
	
	@RequiresPermissions("credit:videoDir:view")
	@RequestMapping(value = {"listNode"})
	@ResponseBody
	public List<VideoDir> listNode(VideoDir videoDir, Model model) {
		return videoDirService.findByParentIdsLike(videoDir);
	}
	
	@RequestMapping(value = {"checkNode"})
	@ResponseBody
	public List<VideoDir> checkChildNode(VideoDir videoDir, Model model) {
		List<VideoDir> list = videoDirService.findListByVideoId(videoDir.getParentId());
		return list;
	}
}