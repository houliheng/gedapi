package com.resoft.accounting.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.utils.StringUtils;

/***
 * 字符串转义工具类
 * 
 * @author songmin
 *
 */

public class CharTransUtils {
	
	private static final String CHARSET_NAME = "UTF-8";
	private static final Logger log = LoggerFactory.getLogger(CharTransUtils.class);
	
	/**
	 * @reqno:H1601140059
	 * @date-designer:2016年1月14日-songmin
	 * @date-author:2016年1月14日-songmin:字符串解码吗，该方法对应客户端中文js转码的解码操作
	 */
	public static String decode(String content){
		if(StringUtils.isEmpty(content)){
			return content;
		}
		String resultContent = null;
		try {
			resultContent = URLDecoder.decode(content, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			log.error("字符串解码失败："+content, e);
			return content;
		}
		return resultContent;
	}
}
