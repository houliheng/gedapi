/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.theme.service.SysConfigService;
/**
 * @reqno:H1511260120
 * @date-designer:2015年11月30日-hanmeng
 * @date-author:2015年11月30日-hanmeng:集成统门户
 */
/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}
		
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}
		return StringUtils.isBlank(Global.getConfig("sec.loginView"))?"modules/sys/sysLogin":Global.getConfig("sec.loginView");
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
//		if (logger.isDebugEnabled()){
//			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
//					sessionDAO.getActiveSessions(false).size(), message, exception);
//		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		// 如果是手机登录，则返回JSON字符串
		if (mobile){
	        return renderString(response, model);
		}
		
		return StringUtils.isBlank(Global.getConfig("sec.loginView"))?"modules/sys/sysLogin":Global.getConfig("sec.loginView");
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}
		
//		// 登录成功后，获取上次登录的当前站点ID
//		UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//		System.out.println("==========================a");
//		try {
//			byte[] bytes = com.thinkgem.jeesite.common.utils.FileUtils.readFileToByteArray(
//					com.thinkgem.jeesite.common.utils.FileUtils.getFile("c:\\sxt.dmp"));
//			UserUtils.getSession().setAttribute("kkk", bytes);
//			UserUtils.getSession().setAttribute("kkk2", bytes);
//		} catch (Exception e) {
//			e.log.error("",e);
//		}
////		for (int i=0; i<1000000; i++){
////			//UserUtils.getSession().setAttribute("a", "a");
////			request.getSession().setAttribute("aaa", "aa");
////		}
//		System.out.println("==========================b");
	//	model.put("quickMenues",quickMenueService.findQucikMenue());
	//	model.put("quickMenueAll",quickMenueService.findAllList());
		//model.put("userQucikMenueIds",quickMenueService.findQucikMenueIds());
		{//切换主题处理
			Map<String, String> userTheme = sysConfigService.getUserTheme(principal.getLoginName());//获取当前用户主题
			
			String theme = request.getParameter("theme");//首次登陆theme为空
			if(StringUtils.isNotEmpty(theme)){//首次登陆theme为空,不走此IF
				Map<String, String> paramMap = Maps.newHashMap();
				paramMap.put("configValue", theme);//主题代码
				if(userTheme==null || userTheme.size()==0){//如果不存在主题则进行插入主题操作
					paramMap.put("userId", principal.getLoginName());//用户ID
					paramMap.put("configType", "theme");//主题的类型
					paramMap.put("configName", "系统主题");//类型的名称
					paramMap.put("remarks", "用户：" + principal.getLoginName() + "的自定义主题");
					sysConfigService.insertSysConfig(paramMap);
				}else{//如果存在则进行更新操作
					String configValue = userTheme.get("CONFIG_VALUE");
					if(!theme.equals(configValue)){//如果与之前的主题不一样，则不需要进行更新操作
						paramMap.put("configId", userTheme.get("CONFIG_ID"));
						sysConfigService.updateSysConfig(paramMap);
					}
				}
				if(!"default".equals(theme)){//如果切换的不是默认主题
					// yuanshisong主题信息放到session里
					request.getSession().setAttribute("userTheme", theme);
					request.setAttribute("userType", "normal");
					if("ace_admin".equals(theme)){
						return "/theme/" +theme +"/indexNew";
					}else{
						return "/theme/" + theme + "/index";
					}
				}else{
					return "modules/sys/sysIndex";
				}
			}
			
			if(userTheme!=null&&userTheme.size()!=0&&!"default".equals(userTheme.get("CONFIG_VALUE"))){//如果是首次登陆，且数据库里存在保存过的主题，则打开保存过的主题页
				CookieUtils.setCookie(response, "theme", userTheme.get("CONFIG_VALUE"));
				// yuanshisong 
				request.getSession().setAttribute("userTheme", userTheme.get("CONFIG_VALUE"));
				request.setAttribute("userType", "normal");
				if("ace_admin".equals(userTheme.get("CONFIG_VALUE"))){
					if(!"yes".equals(Global.getConfig("openOlatform"))){
						return "modules/sys/sysIndex";
					}
					return "/theme/" +userTheme.get("CONFIG_VALUE") +"/indexNew";
				}else{
					return "modules/sys/sysIndex";
				}
			}
		}
		CookieUtils.setCookie(response, "theme", "cerulean");
		return "modules/sys/sysIndex";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/changetheme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		String url = request.getParameter("url");
		if (StringUtils.isNotBlank(theme) && !"default".equals(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			CookieUtils.setCookie(response, "theme", "cerulean");
		}
		System.out.println("redirect:" + adminPath + "?theme=" + (StringUtils.isNotBlank(theme)?theme:"cerulean"));
		return "redirect:" + adminPath + "?theme=" + (StringUtils.isNotBlank(theme)?theme:"cerulean");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
       	
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
}
