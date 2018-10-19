package com.resoft.accounting.fdfs.util;

import com.thinkgem.jeesite.common.utils.PropertiesLoader;

/**
 * 资源服务常量类
 * @author chunlai
 */	
public class FdsfConstant {
	private static PropertiesLoader pLoader = new PropertiesLoader("fdfs_client.conf");
	//资源服务器地址
		public static String fdfsServerUrl;
	static{
		fdfsServerUrl = pLoader.getProperty("fdfs_server");
	}
	
}
