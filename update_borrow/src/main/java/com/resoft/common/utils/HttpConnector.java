package com.resoft.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;

/***
 * 接口请求公共类
 * 
 * @author songmin
 *
 */
public class HttpConnector {
	
	public static void main(String[] args) {
		//模拟测试
		HashMap p = new HashMap();
		p.put("k1", "v1");
		p.put("k2", "v2");
		String rs = new HttpConnector().request(null, "replayPlanCaculate","ACC_VISIT_URL");
		System.out.println(rs);
	}

	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * @reqno:H1510130133
	 * @date-designer:2015年10月17日-songmin
	 * @date-author:2015年10月17日-songmin:CRE_信贷审批_跨系统接口访问规范开发_请求发送
	 * @param params 请求参数
	 * @param reqMethod 请求方法，该参数单独列出来且必填，该参数的值需要与接口提供人员进行协商一致
	 * @param reqAddr  参见interface.properties文件的KEY
	 * @return 接口返回的内容  基本格式：{‘rsCode’:‘接口响应状态码’，‘rsContent’:‘接口相应的内容’}
	 * 
	 * 请求发送请进行加密，且添加请求时间参数
	 * 
	 * 请求接口参数格式：{‘reqTime’：‘请求时间（必填）’，‘reqMethod’：‘请求调用的方法（必填）’，‘reqAddr’：‘ACC_VISIT_URL’，‘key1（可选）’：‘val1’，‘key2（可选）’：‘val2’，}
	 * 接口响应参数格式：{‘rsCode’:‘接口响应状态码’，‘rsContent’:‘接口相应的内容’}
	 * 接口响应状态码定义： 200 接口处理成功
	 * 				 408：超过请求处理规定时间
	 * 				 401：密钥错误或过期 
	 * 				 其他错误码：参见html错误码大全，当状态码不是200时，rsContent参数不存在
	 * 				
	 */
	public String request(Map param,String reqMethod,String reqAddr){
		if(StringUtils.isEmpty(reqMethod) || StringUtils.isEmpty(reqAddr)){
			throw new RuntimeException("reqMethod 或   reqAddr 参数缺失");
		}
		//接口配置参数工具获取类
//		InterfaceConfig interfaceConfig = InterfaceConfig.instanse();
		//设置默认参数
		if(null==param){
			param = new HashMap();
		}
		param.put("reqTime", Calendar.getInstance().getTimeInMillis());
		param.put("reqMethod", reqMethod);
		try {
			/**
			 * @reqno:H1510080106
			 * @date-designer:2015年10月26日-songmin
			 * @date-author:2015年10月26日-songmin:CRE_信贷审批_申请录入_客户信息_更新、保存_基本信息、房产信息、车辆信息、工作信息数据
			 * 		调用地址不再固定，而是通过interface.properties文件指定具体的调用地址，以前的写法直接写死了调用地址的KEY，这样就等同于写死了接口地址，这样对于当前系统调用多个接口时会不再灵活，所有，修改为方法调用者在调用时必需传入一个接口调用地址的映射KEY
			 */
			/**
			 * @reqno:H1510290043
			 * @date-designer:2015年10月26日-songmin
			 * @date-author:2015年10月26日-songmin:接口地址写死，站点为动态从资源文件中获取
			 */
			URL url = new URL(Global.getConfig(reqAddr)+"/servlet/invoke");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.addRequestProperty("Content-Type", "application/json;charset=utf-8");//系统默认使用JSON格式进行数据传输
			http.setDoInput(true);
			http.setDoOutput(true);
			
			// 默认2分钟的连接超时
			http.setConnectTimeout(1000 * 60 * 2);
			http.setReadTimeout(1000 * 60 * 2);

			DataOutputStream out = new DataOutputStream(http.getOutputStream());
			
			
			ObjectMapper mapper =new ObjectMapper();
			String params = mapper.writeValueAsString(param);
			//参数进行加密
			params = DESUtil.encryptDES(params, Global.getConfig("CRYPT_KEY"));
			
			out.writeBytes(params);
			out.flush();
			out.close();
			
			//暂时只处理200请求，对于其他状态码的返回结果都按照错误来对待
			int rsCode = http.getResponseCode();
			if (200 == rsCode) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(http.getInputStream(), "UTF-8"));
				StringBuffer rsBuffer = new StringBuffer();
				String line = null;
				while (null != (line = reader.readLine())) {
					rsBuffer.append(line);
				}
				reader.close();
				//解密接口返回的内容
				String rs = DESUtil.decryptDES(rsBuffer.toString(), Global.getConfig("CRYPT_KEY"));
				return rs;
			} else {
				logger.error("接口访问异常:"+rsCode);
				return "{'rsCode':'"+rsCode+"'}";
			}
		} catch (Exception e) {
			logger.error("网络请求错误："+e.getMessage());
			return "{'rsCode':'500'}";
		} 
	}
}
