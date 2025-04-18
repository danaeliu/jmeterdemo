package com.demos.jmeterdemo.twoutil;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author:liujuan
 * @date: 2020/9/8 11:52
 * @Description:
 */
public class SendRuquest {
    /**
     * 向指定URL发送GET方法的请求
     */

    public  String sendGet(String url, String param, Map<String, String> header) {
        String result = "";
        BufferedReader in = null;
        String urlNameString = url + "?" + param;
        try {
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            //设置超时时间
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(15000);
            // 设置通用的请求属性
            if (header!=null) {
                Iterator<Map.Entry<String, String>> it =header.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<String, String> entry = it.next();
                    //System.out.println(entry.getKey()+":"+entry.getValue());
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
//
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应，设置utf8防止中文乱码
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            if (in != null) {
                in.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

//        System.out.println("result=="+result);
        return result;
    }
    /**
     * 用来存取cookies信息的变量.
     */



    public  boolean gethttpcode(String url) throws UnsupportedEncodingException, IOException {
        URL realUrl = new URL(url);
        HttpURLConnection uConnection = (HttpURLConnection) realUrl.openConnection();

        boolean isconnect = false;
        try {
            uConnection.connect();
            int httpcode = uConnection.getResponseCode();
            if(httpcode==200){
                isconnect=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isconnect;
    }
    /**
     * 向指定URL发送GET方法的请求,可以灵活制定参数有多少个
     */
    public  String sendGetList(String url, Map<String ,String> param, Map<String, String> header) throws UnsupportedEncodingException, IOException {
        String result = "";
        BufferedReader in = null;
        StringBuffer sbparams=new StringBuffer();
        String urlNameString="";
        //处理参数值
        if(param!=null){
            Iterator<Map.Entry<String, String>> it =param.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, String> entry = it.next();
                sbparams.append(entry.getKey()+"="+entry.getValue()+"&");
            }
            //System.out.println("sbparams==="+sbparams);
            urlNameString=url+"?"+sbparams.toString().substring(0,sbparams.toString().length()-1);
            //System.out.println("全URL=="+urlNameString);
        }else {
            urlNameString=url;
        }
        URL realUrl = new URL(urlNameString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        //设置超时时间
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(15000);
        // 设置通用的请求属性
        if (header!=null) {
            Iterator<Map.Entry<String, String>> it =header.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, String> entry = it.next();
                //System.out.println(entry.getKey()+":"+entry.getValue());
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应，设置utf8防止中文乱码
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (in != null) {
            in.close();
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     */
    public  String sendPost(String url, String param, Map<String, String> header) throws UnsupportedEncodingException, IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        //设置超时时间
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        // 设置通用的请求属性
        if (header!=null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if(out!=null){
            out.close();
        }
        if(in!=null){
            in.close();
        }
        return result;
    }




//    public static void main(String[] args) {
//        System.out.println(gitSendGet("http://sonar.pc.com.cn/api/qualitygates/project_status?projectKey=pcauto:changan-api-web"));
//    }
}
