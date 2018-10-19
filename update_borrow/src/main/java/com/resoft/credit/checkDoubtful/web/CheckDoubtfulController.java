package com.resoft.credit.checkDoubtful.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.resoft.common.utils.AjaxView;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.checkDoubtful.entity.CheckDoubtful;
import com.resoft.credit.checkDoubtful.service.CheckDoubtfulService;
import com.resoft.credit.processSuggestionInfo.entity.ProcessSuggestionInfo;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 借前外访Controller
 *
 * @author yanwanmei
 * @version 2016-03-01
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/checkDoubtful")
public class CheckDoubtfulController extends BaseController {

	@Autowired
	private CheckDoubtfulService checkDoubtfulService;

	@ModelAttribute
	public CheckDoubtful get(@RequestParam(required = false) String id) {
		CheckDoubtful entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = checkDoubtfulService.get(id);
		}
		if (entity == null) {
			entity = new CheckDoubtful();
		}
		return entity;
	}

	@RequiresPermissions("credit:checkDoubtful:view")
	@RequestMapping(value = { "list", "" })
	// 注入actTaskParam，在流程处理中接收参数
	public String list(ActTaskParam actTaskParam, String readOnly, CheckDoubtful checkDoubtful, HttpServletRequest request, HttpServletResponse response, Model model) {

		CheckDoubtful newCheckDoubtful = new CheckDoubtful();
		String name = UserUtils.getUser().getName();
		if (checkDoubtful != null && StringUtils.isBlank(newCheckDoubtful.getId())) {
			newCheckDoubtful.setCheckUserName(name);
		}
		model.addAttribute("checkDoubtful", newCheckDoubtful);

		String applyNo = actTaskParam.getApplyNo();
		List<CheckDoubtful> checkDoubtfullList = checkDoubtfulService.getPageByApplyNo(applyNo);
		// 信息列表中的数据

		String visitCountFlag = "true";
		if (checkDoubtfullList.size() == 0) {
			visitCountFlag = "false";
		}
		model.addAttribute("checkDoubtfullList", checkDoubtfullList);
		model.addAttribute("actTaskParam", actTaskParam);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("visitCountFlag", visitCountFlag);

		return "app/credit/checkDoubtful/checkDoubtfulList";
	}

	@RequiresPermissions("credit:checkDoubtful:view")
	@RequestMapping(value = "form")
	public String form(CheckDoubtful checkDoubtful, Model model) {
		String loginName = UserUtils.getUser().getLoginName();
		if (checkDoubtful != null && StringUtils.isBlank(checkDoubtful.getId())) {
			checkDoubtful.setCheckUserName(loginName);
		}
		model.addAttribute("checkDoubtful", checkDoubtful);
		return "app/credit/checkDoubtful/checkDoubtfulForm";
	}

	@RequiresPermissions("credit:checkDoubtful:edit")
	@RequestMapping(value = "save")
	public String save(ActTaskParam actTaskParam, String readOnly, CheckDoubtful checkDoubtful, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkDoubtful)) {
			return form(checkDoubtful, model);
		}
		try {
			User loginUser = UserUtils.getUser();
			checkDoubtful.setCheckUserId(loginUser.getId());
			checkDoubtful.setCheckUserName(loginUser.getName());
			checkDoubtfulService.save(checkDoubtful);
			model.addAttribute("saveMessage", "保存借前外访成功");
		} catch (Exception e) {
			logger.error("保存数据错误" + e.getMessage(), e);
			model.addAttribute("saveMessage", "保存借前外访失败");
		}
		// 保存完后在页面提示保存成功！
		model.addAttribute("saveTip", true);
		return list(actTaskParam, readOnly, checkDoubtful, request, response, model);
	}

	@RequiresPermissions("credit:checkDoubtful:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckDoubtful checkDoubtful, RedirectAttributes redirectAttributes) {
		checkDoubtfulService.delete(checkDoubtful);
		addMessage(redirectAttributes, "删除借前外访成功");
		return "redirect:" + Global.getAdminPath() + "/checkDoubtful/checkDoubtful/?repage";
	}

	@ResponseBody
	@RequiresPermissions("credit:checkDoubtful:edit")
	@RequestMapping(value = "saveResult")
	// 当借前外访点“提交”时，将外访意见保存到cre_process_suggestion_info表中
	public AjaxView saveResult(ActTaskParam actTaskParam, ProcessSuggestionInfo processSuggestionInfo) {
		AjaxView ajaxView = new AjaxView();
		ajaxView = checkDoubtfulService.saveResult(processSuggestionInfo, actTaskParam);
		return ajaxView;
	}

	@RequestMapping(value = "showFullMsg")
	public String showFullMsg(Model model, String hitaskid) {
		Object msgBlob = checkDoubtfulService.getFullMsg(hitaskid);
		byte[] msgFull = (byte[]) msgBlob;
		String str = "";
			try {
				str = new String(msgFull,"UTF-8");
			} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("str", str);
		return "app/credit/taskCenter/processMsg";
	}
	
//	/**
//     * 判断字符串是否是乱码
//     *
//     * @param strName 字符串
//     * @return 是否是乱码
//     */
//    public static boolean isMessyCode(String strName) {
//        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
//        Matcher m = p.matcher(strName);
//        String after = m.replaceAll("");
//        String temp = after.replaceAll("\\p{P}", "");
//        char[] ch = temp.trim().toCharArray();
//        float chLength = ch.length;
//        float count = 0;
//        for (int i = 0; i < ch.length; i++) {
//            char c = ch[i];
//            if (!Character.isLetterOrDigit(c)) {
//                if (!isChinese(c)) {
//                    count = count + 1;
//                }
//            }
//        }
//        float result = count / chLength;
//        if (result > 0.4) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//    
//    public static boolean isChinese(char c) {
//        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
//        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
//                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
//                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
//            return true;
//        }
//        return false;
//    }
}