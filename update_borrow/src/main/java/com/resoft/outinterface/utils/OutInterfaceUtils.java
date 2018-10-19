package com.resoft.outinterface.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class OutInterfaceUtils {
	private static short a=0;
	private static final Logger logger = LoggerFactory.getLogger(OutInterfaceUtils.class);
	public static synchronized String makeSeqNo(){
		if(a==99)
			a=0;
		a+=1;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(1);
		nf.setMaximumIntegerDigits(1);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = format.format(date)+String.format("%02d", a);
		return str;
	}
	public static String load(String url,String json) throws Exception{
		URL restURL = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type","application/json");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
		out.write(json);
		out.close();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		String line,resultStr="";
		while(null != (line=bReader.readLine())){
			resultStr +=line;
			}
			bReader.close();
		return resultStr;
	}

	public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)conn;
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(1000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

	public static String transfer(String url,String json) throws Exception{
		URL restURL = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type","application/json");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
		out.write(json);
		out.close();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line,resultStr="";
		while(null != (line=bReader.readLine())){
			resultStr +=line;
			}
			bReader.close();
		return resultStr;
	}

	public static <T>T listToBean(List<String> list,Class<T> objClass) throws Exception{
		Field[] beanField = objClass.getDeclaredFields();
		T trh = objClass.newInstance();
		Iterator<String> it =list.iterator();
		int z=0;
		while(it.hasNext()){
			String str = it.next();
			try {
				beanField[z].setAccessible(true);
				beanField[z].set(trh, str);
			} catch (IllegalArgumentException e) {
				logger.error("参数值错误", e);
			} catch (IllegalAccessException e) {
				logger.error("安全权限异常", e);
			}
			z+=1;
		}
		return trh;
	}
	public static String signatureMaker(String mchn,String seqNo,String tradeType,String mchnKey){
		String str=mchn+"|"+seqNo+"|"+tradeType+"|"+mchnKey;
		String signature =OutInterfaceUtils.string2MD5(str);
		return signature;
	}
	public static String string2MD5(String str){
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bs =md5.digest(str.getBytes());
			StringBuilder sb = new StringBuilder(40);
			for(byte x:bs){
				if((x& 0xff)>>4==0){
					sb.append("0").append(Integer.toHexString(x&0xff));
				}else{
					sb.append(Integer.toHexString(x&0xff));
				}
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error("数据加密失败", e);
		}
		return null;
	}
	public static Object getFieldValueByName(String fieldName,Object o) throws Exception{
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getter="get"+firstLetter+fieldName.substring(1);
		Method method = o.getClass().getMethod(getter,new Class[] {});
		Object value =method.invoke(o,new Object[] {});
		return value;
	}
	
	public static String doPostForm(String url, Map<String, String> params)
		      throws  SocketTimeoutException, Exception {
		    return getDataByForm(url, params, 60000, 120000);
	}
	
	@SuppressWarnings("all")
	  public final static String getDataByForm(String url, Map<String, String> params,
	      Integer connectTimeout, Integer socketTimeout) throws  Exception {
	    if (StringUtils.isBlank(url)) {
	      throw new IllegalAccessException("请求地址为空");
	    }
	    HttpPost postMethoed = new HttpPost(url);
	    Header[] headers = setFormHeader();
	    postMethoed.setHeaders(headers);

	    try {
	      UrlEncodedFormEntity urlEntity = setReqParams(params);
	      if (null != urlEntity) {
	        postMethoed.setEntity(urlEntity);
	      }
	    } catch (UnsupportedEncodingException e) {
	      throw new IllegalAccessException(e.getMessage());
	    }
	    RequestConfig reqConfig = setReqConfig(connectTimeout, socketTimeout);
	    if (null != reqConfig) {
	      postMethoed.setConfig(reqConfig);
	    }
	    CloseableHttpResponse response = null;
	    String result = null;
	    HttpEntity entity = null;
	    try {
	      response = getHttpclient().execute(postMethoed);
	      entity = response.getEntity();
	      if (null != entity) {
	        result = EntityUtils.toString(entity, "UTF-8");
	      }
	    }catch (Exception e) {
	    	
	    } finally {
	      try {
	        close(entity, postMethoed, response);
	      } catch (IOException io) {
	        throw new IOException("关闭HttpClient请求异常");
	      }
	    }
	    return result;
	  }
	
	protected static final Header[] setFormHeader() {
		BasicHeader type = new BasicHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		BasicHeader agent = new BasicHeader("User-Agent", "Mozilla/4.0 (compatible;MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322)");
		return new Header[] { type, agent };
	}
	
	protected static final UrlEncodedFormEntity setReqParams(
			Map<String, String> params) throws UnsupportedEncodingException {
		if (null == params || params.isEmpty()) {
			return null;
		}
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>(
				params.size());
		for (Map.Entry<String, String> param : params.entrySet()) {
			paramsList.add(new BasicNameValuePair(param.getKey(), param
					.getValue()));
		}
		try {
			return new UrlEncodedFormEntity(paramsList, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedEncodingException("设置请求参数异常");
		}
	}
	
	protected static final RequestConfig setReqConfig(Integer connectTimeout,
			Integer socketTimeout) {
		if (null == connectTimeout && null == socketTimeout) {
			return null;
		}
		if (null != connectTimeout && null != socketTimeout
				&& connectTimeout <= 0 && socketTimeout <= 0) {
			return null;
		}
		RequestConfig.Builder builder = RequestConfig.custom();
		if (null != connectTimeout && connectTimeout.intValue() > 0) {
			// 设置请求连接超时时间
			builder.setConnectTimeout(connectTimeout);
			builder.setConnectionRequestTimeout(connectTimeout);
		}
		if (null != socketTimeout && socketTimeout.intValue() > 0) {
			// 设置服务器响应超时时间
			builder.setSocketTimeout(socketTimeout);
		}
		return builder.build();
	}
	
	private static CloseableHttpClient httpClient = null;
	private final static byte[] syncLock = new byte[0];
	private static PoolingHttpClientConnectionManager httpClientConnectionManager = null;
	
	protected static CloseableHttpClient getHttpclient(){
		if(httpClient == null){
			synchronized (syncLock) {
				if(httpClient == null){
					httpClient = initHttpClient();
				}				
			}
		}
		return httpClient;
	}
	
	private static CloseableHttpClient initHttpClient() {
		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory> create();
		PlainConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		SSLContext sslContext;
		CloseableHttpClient httpClient = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			registryBuilder = registryBuilder.register("https", sslsf);
			registryBuilder = registryBuilder.register("http", plainsf);
			httpClientConnectionManager = new PoolingHttpClientConnectionManager(registryBuilder.build());
			httpClientConnectionManager.setMaxTotal(100); 			// 最大连接数设置为100
			httpClientConnectionManager.setDefaultMaxPerRoute(5); 	// 每个域名最连接数为5
			CookieStore cookieStore = new BasicCookieStore();
			HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(httpClientConnectionManager).setDefaultCookieStore(cookieStore);
			RequestConfig globalconfig = RequestConfig.custom().setRedirectsEnabled(true).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
			httpClient = httpClientBuilder.setDefaultRequestConfig(globalconfig).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return httpClient;
	}
	
	protected static void close(HttpEntity entity, HttpRequestBase request,
            CloseableHttpResponse response) throws IOException {
	if (null != request) {
	request.releaseConnection();
	}
	if (null != entity) {
	entity.getContent().close();
	}
	if (null != response) {
	response.close();
	}
}
}
