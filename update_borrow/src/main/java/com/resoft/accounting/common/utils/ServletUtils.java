package com.resoft.accounting.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.config.Global;

public class ServletUtils {
	
	private static Logger logger = Logger.getLogger(ServletUtils.class);

	/**
	 * @reqno:H1510130134
	 * @date-designer:2015年10月16日-songmin
	 * @date-author:2015年10月16日-songmin:获取请求参数，同时对请求参数进行解密
	 */
	public static String getRequstContent(HttpServletRequest request){
		try{
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(request.getInputStream(), "UTF-8"));
			StringBuffer rsBuffer = new StringBuffer();
			String line = null;
			while (null != (line = reader.readLine())) {
				rsBuffer.append(line);
			}
			reader.close();
			
			String reqContent = DESUtil.decryptDES(rsBuffer.toString(), Global.getConfig("CRYPT_KEY"));
			return reqContent;
		}catch(IOException e){
			logger.error("接口解析请求内容时异常："+e.getMessage());
		}
		return "";
	}
	
	/**
	 * @reqno:H1510130134
	 * @date-designer:2015年10月16日-songmin
	 * @date-author:2015年10月16日-songmin:格式化、加密处理接口处理结果
	 */
	public static String formatResContent(String resCode,Object content){
		String rsContent = "";
		try {
			ObjectMapper objMapper = new ObjectMapper();
			Map<String,String> rs = new HashMap<String,String>();
			rs.put("rsCode", resCode);
			if("200".equals(resCode)){
				if(null!=content){
					/**
					 * @reqno:H1509130078
					 * @date-designer:2015年10月10日-songmin
					 * @date-author:2015年10月10日-songmin:将接口调用方法返回的结果以string方式再次放到Map对象中转成json
					 */
					String content_str = objMapper.writeValueAsString(content);
					rs.put("rsContent", content_str);
				}
			}
			//返回内容转JSON
			rsContent = objMapper.writeValueAsString(rs);
		} catch (JsonProcessingException e) {
			logger.error("接口返回内容时转换JSON异常："+e.getMessage());
		}
		return rsContent;
	}
	
	/**
	 * 向接口请求方返回处理结果
	 * @param response
	 * @param rsContent
	 */
	public static void responseWrite(HttpServletResponse response,String rsContent){
		//返回内容加密
		rsContent = DESUtil.encryptDES(rsContent, Global.getConfig("CRYPT_KEY"));
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(rsContent);
		} catch (IOException e) {
			logger.error("接口返回内容时异常："+e.getMessage());
		}finally{
			if(null!=out){
				out.flush();
				out.close();
			}
		}
	}
	/**
	 * @reqno:H1511130067
	 * @date-designer:20151116-chenshaojia
	 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
	 * 从jee_acc合并代码至jee_cre 故删除main方法
	 */
}
