package com.resoft.accounting.discountList.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.resoft.accounting.common.utils.Constants;
import com.resoft.accounting.discountList.entity.Discount;
import com.resoft.accounting.discountList.service.DiscountService;
import com.resoft.accounting.staContractStatus.service.StaContractStatusService;
import com.resoft.credit.underCompanyInfo.entity.UnderCompanyInfo;
import com.resoft.credit.underCompanyInfo.service.UnderCompanyInfoService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
* @author guoshaohua
* @version 2018年5月17日 下午2:21:48
* 
*/
@Controller
@RequestMapping(value = "${adminPath}/accounting/discount")
public class DiscountControctller extends BaseController{
	@Autowired
	private DiscountService discountService;
	@Autowired
	private StaContractStatusService staContractStatusService;
	@Autowired 
	private UnderCompanyInfoService underCompanyInfoService;
	@ModelAttribute
	public Discount get(@RequestParam(required = false) String id) {
		Discount entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = discountService.get(id);
		}
		if (entity == null) {
			entity = new Discount();
		}
		return entity;
	}
	
	
	@RequestMapping(value = { "list", "" })
	public String list(Discount discount, HttpServletRequest request, HttpServletResponse response, Model model,String queryFlag) {
		String level = null;
		if (discount != null && discount.getCompany() != null && !StringUtils.isEmpty(discount.getCompany().getId())) {
			Office office = new Office();
			office.setId(discount.getCompany().getId());
			level = staContractStatusService.getOfficeLevel(discount.getCompany().getId());
			if (Constants.OFFICE_LEVEL_DQ.equals(level)) {
				discount.setOrgLevel2(office);
			} else if (Constants.OFFICE_LEVEL_QY.equals(level)) {
				discount.setOrgLevel3(office);
			} else if (Constants.OFFICE_LEVEL_MD.equals(level)) {
				discount.setOrgLevel4(office);
			}
		}
		Page<Discount>  page = null;
		if ("button".equals(queryFlag)) {
			page = discountService.findPage(new Page<Discount>(request, response), discount);
			List<Discount> discounts = page.getList();
			if (discounts.size() >0) {
				for(Discount discoun:discounts){
					Discount discou = discountService.finPalnRepayDetailsByContractNo(discoun.getContractNo());
					Integer isImport = discountService.isImport(discoun.getContractNo());
					if (isImport != 0) {
						discoun.setImportStatus("0");
					}else {
						discoun.setImportStatus("1");
					}
					discoun.setDiscountFee(discou.getDiscountFee());
					discoun.setFactDiscountFee(discou.getFactDiscountFee());
					discoun.setNoDiscountFee(discou.getNoDiscountFee());
					UnderCompanyInfo underCompanyInfo = underCompanyInfoService.getByApplyNo(discoun.getApplyNo());
					if (underCompanyInfo != null) {
						discoun.setUnderFlag("1");
					}
				}
			}
		}
		model.addAttribute("page", page);
		return "app/accounting/discountList/discountList";
	}


}
