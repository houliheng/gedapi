package com.resoft.credit.InterfaceDispatch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/credit/InterfaceDisptch")
public class InterfaceDispatch extends BaseController {
	@RequestMapping(value = "index")
	public String list() {
		return "app/credit/interfaceDispatch/index";
	}
}
