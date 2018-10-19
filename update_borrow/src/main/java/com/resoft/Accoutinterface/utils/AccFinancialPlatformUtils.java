package com.resoft.Accoutinterface.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AccFinancialPlatformUtils {
	private static short a=0;
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
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.write(json);
		out.close();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		//BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			z+=1;
		}
		return trh;
	}
	public static String signatureMaker(String mchn,String seqNo,String tradeType,String mchnKey){
		String str=mchn+"|"+seqNo+"|"+tradeType+"|"+mchnKey;
		String signature =AccFinancialPlatformUtils.string2MD5(str);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, String> objectToMap(Object obj) throws Exception {    
        if(obj == null){    
            return null;    
        }   
  
        Map<String, String> map = new HashMap<String, String>();    
  
        Field[] declaredFields = obj.getClass().getDeclaredFields();    
        for (Field field : declaredFields) {    
            field.setAccessible(true);  
            map.put(field.getName(), field.get(obj).toString());  
        }    
  
        return map;  
    } 
	
	
	
}
