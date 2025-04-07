package com.demos.jmeterdemo.pchouse;

import com.alibaba.fastjson.JSONObject;
import com.demos.jmeterdemo.twoutil.SendRuquest;

import java.util.HashMap;
import java.util.Map;

public class TapdApiHouse {
    static int pageSize = 200;
    public static Map getheadermap() {
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Basic azJZYjY4al86NjJGMkM1NzYtODc4Ni04MjdGLUQ4RDQtRUU5NkNERTk5MDFG");
        return headerMap;
    }
    //得出BUG总数不带迭代
    public  int getbugtotalCount_house(String workspaceid,String iteration_id) {
        int totalCount = getbugscount_house(workspaceid,iteration_id);
        return totalCount;
    }

    /*public static void main(String[] args) {
        TapdApiHouse apiHouse=new TapdApiHouse();
        System.out.println(apiHouse.getbugscount_house("51027841"));
    }*/
    /**
     * 统计缺陷数量
     */
    public  int getbugscount_house(String workspaceid,String iteration_id) {
        SendRuquest sendRuquest = new SendRuquest();
        String url = "https://api.tapd.cn/bugs/count";
        Map<String, String> params = new HashMap<String, String>();
        params.put("workspace_id", workspaceid);
        params.put("iteration_id", iteration_id);
        int bugcount = 0;
        try {
            JSONObject jsonObject = JSONObject.parseObject(sendRuquest.sendGetList(url, params, getheadermap()));
            System.out.println(jsonObject);
            bugcount = jsonObject.getJSONObject("data").getInteger("count");
           // System.out.println(bugcount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bugcount;

    }
    //得出bug总页数-house
    public  int getbugtotlePages_house(String workspaceid,String iteration_id) {
        int totalPage = 0;
        int totalCount = getbugtotalCount_house(workspaceid,iteration_id);
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        return totalPage;
    }

    /**
     * 汽车tapd获取线上报障问题分页
     * https://api.tapd.cn/bugs?workspace_id=68331031&iteration_id=1168331031001028936
     * https://api.tapd.cn/bugs?workspace_id=61792590&iteration_id=1161792590001000446
     * https://api.tapd.cn/bugs?workspace_id=30892271&iteration_id=1130892271001000738
     * 获取bug数量
     * @return
     */
    public  JSONObject getautobugslist_house(String workspaceid, String iteration_id,String page) {
        SendRuquest sendRuquest = new SendRuquest();
        String url = "https://api.tapd.cn/bugs";
        Map<String, String> params = new HashMap<String, String>();
        params.put("workspace_id", workspaceid);
        params.put("iteration_id", iteration_id);
        params.put("limit", Integer.toString(pageSize));
        params.put("page", page);
        JSONObject result = null;
        try {
            result = JSONObject.parseObject(sendRuquest.sendGetList(url, params, getheadermap()));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

}
