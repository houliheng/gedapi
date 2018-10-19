package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;

@Controller
@RequestMapping({ "${adminPath}/sys/customOffice" })
public class AccCustomOfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@ResponseBody
	@RequestMapping({ "treeData" })
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type, @RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll, @RequestParam(required = false) String companyId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = this.officeService.findList(isAll);

		for (int i = 0; i < list.size(); i++) {
			Office e = (Office) list.get(i);
			if (((!StringUtils.isBlank(extId)) && ((extId == null) || (extId.equals(e.getId())) || (e.getParentIds().indexOf("," + extId + ",") != -1))) || ((type != null) && ((type == null) || ((type.equals("1")) && (!type.equals(e.getType()))))) || ((grade != null) && ((grade == null) || (Integer.parseInt(e.getGrade()) > grade.intValue()))) || (!"1".equals(e.getUseable())))
				continue;
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getName());
			map.put("levelnum", e.getlevelnum());
			map.put("type", e.getType());
			if ((type != null) && ("3".equals(type))) {
				map.put("isParent", Boolean.valueOf(true));
			}
			mapList.add(map);
		}
		return mapList;
	}
}