package com.resoft.outinterface.rest.Get;


import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.GedImportGedOrder.entity.CreGedImportGetOrder;
import com.thinkgem.jeesite.common.utils.JsonUtil;
import net.sf.json.JSONArray;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/f/rest/get/service")
public class Test  {

	@RequestMapping(method=RequestMethod.GET,value="test/{json}")
	public String form(@PathVariable String json,Model model) {
		return json;
	}



}