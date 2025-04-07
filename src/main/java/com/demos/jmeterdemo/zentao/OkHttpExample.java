package com.demos.jmeterdemo.zentao;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.ReUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class OkHttpExample {

//    public static void main(String[] args) throws Exception {
//        OkHttpExample obj = new OkHttpExample();
//        obj.sendGet();
//
//    }

    public String sendGet(String url)  {
        OkHttpExample obj = new OkHttpExample();
        OkHttpClient httpClient = new OkHttpClient();
        String result=null;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            result= new String(response.body().bytes(),"UTF-8");
            result = UnicodeUtil.toString(result);


            //System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String getCookie(String url){
        OkHttpClient httpClient = new OkHttpClient();
        String result=null;
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                List<Cookie> cookies = Cookie.parseAll(request.url(), response.headers());
                for (Cookie cookie: cookies){
                    if(cookie.name().equals("zentaosid")){
                        result = cookie.value();
                    }
                }
//                System.out.println(result);
            }


            //System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

//    private void sendPost() throws Exception {
//        RequestBody formBody = new FormBody.Builder()
//                .add("username", "lyl")
//                .add("password", "123")
//                .build();
//
//        Request request = new Request.Builder()
//                .url("你请求数据的url地址")
//                .addHeader("User-Agent", "OkHttp Bot")
//                .post(formBody)
//                .build();
//
//        try (Response response = httpClient.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//            System.out.println(response.body().string());
//        }
//    }
}
