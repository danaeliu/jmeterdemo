package com.demos.jmeterdemo.twoutil;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TapdApiTool {
    static int pageSize = 200;

    //得出BUG总数
    public  int getbugtotalCount(String workspaceid, String iteration_id) {
        int totalCount = getbugscount(workspaceid, iteration_id);
        return totalCount;
    }


    //得出bug总页数
    public  int getbugtotlePages(String workspaceid, String iteration_id) {
        int totalPage = 0;
        int totalCount = getbugtotalCount(workspaceid, iteration_id);
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        return totalPage;
    }

    public static Map getheadermap() {
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Authorization", "Basic azJZYjY4al86NjJGMkM1NzYtODc4Ni04MjdGLUQ4RDQtRUU5NkNERTk5MDFG");
        return headerMap;
    }

    /**
     * 获取bug数量
     *
     * @return
     */
    public  JSONObject gettapdbugs(String workspaceid) {
        SendRuquest sendRuquest = new SendRuquest();
        String url = "https://api.tapd.cn/bugs";
        Map<String, String> params = new HashMap<String, String>();
        params.put("workspace_id", workspaceid);
        params.put("limit", "100");
        JSONObject result = null;
        try {
            result = JSONObject.parseObject(sendRuquest.sendGetList(url, params, getheadermap()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 汽车tapd获取线上报障问题分页
     * https://api.tapd.cn/bugs?workspace_id=68331031&iteration_id=1168331031001028936
     * https://api.tapd.cn/bugs?workspace_id=61792590&iteration_id=1161792590001000446
     * https://api.tapd.cn/bugs?workspace_id=30892271&iteration_id=1130892271001000738
     * 获取bug数量
     * @return
     */
    public   JSONObject getautobugslist(String workspaceid, String iteration_id, String page) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 统计缺陷数量
     */
    public static int getbugscount(String workspaceid, String iteration_id) {
        SendRuquest sendRuquest = new SendRuquest();
        String url = "https://api.tapd.cn/bugs/count";
        Map<String, String> params = new HashMap<String, String>();
        params.put("workspace_id", workspaceid);
        params.put("iteration_id", iteration_id);
        params.put("title", "【线上报障");
        int bugcount = 0;
        try {
            JSONObject jsonObject = JSONObject.parseObject(sendRuquest.sendGetList(url, params, getheadermap()));
            bugcount = jsonObject.getJSONObject("data").getInteger("count");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bugcount;

    }

}
