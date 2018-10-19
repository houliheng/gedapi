package com.gq.ged.common.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import com.gq.ged.common.Constant;
import com.gq.ged.common.exception.business.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author wyq
 * @ClassName: CookieUtils
 * @Description: 操作cookies(这里用一句话描述这个类的作用)
 * @date 2016/12/7 10:09
 */
public class CookieUtils {
  static Logger logger = LoggerFactory.getLogger(BaseController.class);

  public static String getCookieTokenId(HttpServletRequest request)
      throws BusinessException, UnsupportedEncodingException {
    // 1、从cookie中获取token
    String ticketCookie = request.getHeader(Constant.CURR_USER_INFO_TOKEN);
    if (StringUtils.isNotBlank(ticketCookie) && !"null".equals(ticketCookie)) {
      String cookieValue = URLDecoder.decode(ticketCookie, "UTF-8");
      return cookieValue;
    }
    return "";
  }
}
