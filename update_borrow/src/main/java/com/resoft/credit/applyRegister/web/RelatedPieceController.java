package com.resoft.credit.applyRegister.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resoft.credit.applyRegister.entity.RelatedPiece;
import com.resoft.credit.applyRegister.service.RelatedPieceService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 关联进件Controller
 * @author wangguodong
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/RelatedPiece")
public class RelatedPieceController extends BaseController{
	@Autowired
	private RelatedPieceService pieceService; 
	
	@RequiresPermissions("credit:custinfo:edit")
	@RequestMapping(value = {"list", ""})
	public String list(RelatedPiece relatedPiece, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RelatedPiece> page = pieceService.findPage(new Page<RelatedPiece>(request, response), relatedPiece); 
		model.addAttribute("page", page);
		model.addAttribute("custId", relatedPiece.getCustId());
		return "app/credit/custinfo/relatedPieceList";
		
	}
}
