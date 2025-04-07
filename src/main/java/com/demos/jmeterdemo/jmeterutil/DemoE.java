package com.demos.jmeterdemo.jmeterutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//详细签名方法见：https://github.com/yidun/antispam-java-demo/blob/master/src/main/java/com/netease/is/antispam/demo/utils/SignatureUtils.java


public class DemoE{
    public static String genSignature(String secretKey, Map<String, String> params){
        // 1. 参数名按照ASCII码表升序排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 2. 按照排序拼接参数名与参数值
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append(params.get(key));
        }
        // 3. 将secretKey拼接到最后
        sb.append(secretKey);

        try {
            // 4. MD5是128位长度的摘要算法，转换为十六进制之后长度为32字符
            return DigestUtils.md5Hex(sb.toString().getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println("[ERROR] not supposed to happen: " + e.getMessage());
        }
        return "";


    }

    public static void main(String[] args) throws JsonProcessingException, UnsupportedEncodingException {
        String jsonString = "version=v1&category=100&pageNum=1&pageSize=20&signature=5a39c84753645d2889b2fbd995353189&secretId=f8965d97b42bc45567f29eb8cea5ed30&businessId=2dc46c6308a3e0e2fa8ebe07e304c7ac&nonce=420797907&pageNum=1&timestamp=1662011841562";

        Map<String, String> paramMap = new HashMap<>();
        String[] pairs = jsonString.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = URLDecoder.decode(keyValue[1], "UTF-8");
                paramMap.put(key, value); // 后面的值会覆盖前面的相同键
            }
        }
        paramMap.remove("signature");

        System.out.println(paramMap);
        genSignature("1445e2434f086f95cc9a4e8bd2f1bd70", paramMap);
    }
}

