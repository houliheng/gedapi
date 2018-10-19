package com.resoft.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestUtil {
    /**
     * HttpURLConnection 示例
     */
    public static String doHttpURLConnection (String addr,String reqparam){
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(addr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            byte[] writebytes = reqparam.getBytes();
            //设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
            OutputStream outwritestream = conn.getOutputStream();
            outwritestream.write(writebytes);
            outwritestream.flush();
            outwritestream.close();
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(result);
        return result;
    }

    /**
     * HttpClient 示例
     */
    public static JSONObject doHttpClient(String addr, String reqparam){
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(addr);
        JSONObject response = null;
        try {
            StringEntity stringEntity = new StringEntity(reqparam,"UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(stringEntity);
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());//返回json格式
                response = JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.toJSONString());
        return response;
    }


}
