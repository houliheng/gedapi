package com.resoft.postloan.debtColletion.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.resoft.postloan.common.utils.Constants;
import com.resoft.postloan.debtColletion.entity.DoneDebtCollection;
import com.resoft.postloan.debtColletion.service.DoneDebtCollectionService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 已催收Controller
 * 
 * @author wangguodong
 * @version 2016-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/postloan/doneDebtCollection")
public class DoneDebtCollectionController extends BaseController {

	@Autowired
	private DoneDebtCollectionService doneDebtCollectionService;

	@ModelAttribute
	public DoneDebtCollection get(@RequestParam(required = false) String id) {
		DoneDebtCollection entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = doneDebtCollectionService.get(id);
		}
		if (entity == null) {
			entity = new DoneDebtCollection();
		}
		return entity;
	}

	@RequiresPermissions("postloan:debtCollection:view")
	@RequestMapping(value = "/list/{currCollectionType}/{flag}")
	public String list(DoneDebtCollection doneDebtCollection, HttpServletRequest request, @PathVariable("flag") String flag, HttpServletResponse response, Model model) {
		try {
			if (Constants.DEBT_COLLECTION_STATUS_DFP.equals(flag)) {
				doneDebtCollection.setCurrCollectionStatus(Constants.DEBT_COLLECTION_STATUS_DFP);
			} else {
				doneDebtCollection.setCurrCollector(UserUtils.getUser().getId());
				doneDebtCollection.setCurrCollectionStatus(Constants.DEBT_COLLECTION_STATUS_YFPDCS);
			}
			doneDebtCollection.setCollectBy(UserUtils.getUser().getId());
			doneDebtCollection.setOperateOrgId(UserUtils.getUser().getCompany().getId());
			Page<DoneDebtCollection> page = doneDebtCollectionService.findPage(new Page<DoneDebtCollection>(request, response), doneDebtCollection);
			model.addAttribute("page", page);
			model.addAttribute("flag", flag);
			model.addAttribute("debtCollection", doneDebtCollection);
			model.addAttribute("currCollectionType", doneDebtCollection.getCurrCollectionType());
		} catch (Exception e) {
			logger.error("数据出现问题", e);
		}
		if (Constants.DEBT_COLLECTION_STATUS_DFP.equals(flag)) {
			return "app/postloan/debtColletion/doneDebtAllocationList";
		} else {
			return "app/postloan/debtColletion/doneDebtCollectionList";
		}
	}

}