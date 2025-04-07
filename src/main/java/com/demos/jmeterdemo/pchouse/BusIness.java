package com.demos.jmeterdemo.pchouse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.activation.MailcapCommandMap;
import java.util.ArrayList;
import java.util.List;

/*
统计周期：每周一次
bug数据：https://www.tapd.cn/tapd_fe/51027841/bug/list?confId=1151027841001069162&page=1&queryToken=f47c8f70e6d4c61a3a5a14cd3289b1a4
bug总数、已修复、待修复、本周新增
 */
public class BusIness {
    public  List<String> getAllBugs(String workspaceid,String iteration_id){
        DateT dateT=new DateT();
        TapdApiHouse tapdApiHouse=new TapdApiHouse();
        List<String> info=new ArrayList<>();
        //本周新增BUG数量
        int weeknum=0;
        //已修复数量
        int close=0;
        //待修复
        int open=0;
        //bug总数、已修复、待修复、本周新增
        int totalCount = tapdApiHouse.getbugtotalCount_house(workspaceid,iteration_id);
        info.add("Bug总数："+totalCount);
//得出总页数
        int totalPage = tapdApiHouse.getbugtotlePages_house(workspaceid,iteration_id);
        //循环限每一页数据
        for (int i = 1; i <= totalPage; i++) {
            JSONObject jsonObject =tapdApiHouse.getautobugslist_house(workspaceid,iteration_id, Integer.toString(i));
            JSONArray array = jsonObject.getJSONArray("data");
            for (int j = 0; j < array.size(); j++) {//解析每页的bug数据
                JSONObject bugs = array.getJSONObject(j);
                String status = bugs.getJSONObject("Bug").getString("status");
                String created = bugs.getJSONObject("Bug").getString("created").split(" ")[0];
                if(dateT.isweek(created)){
                    weeknum++;
                }
                if(status.equals("new")||status.equals("reopened")||status.equals("in_progress")||status.equals("rejected")){
                    //待修复
                    open++;
                }
                if(status.equals("closed")||status.equals("resolved")){
                    close++;
                }
                //new 待修复
                // reopened 重新打开
                // in_progress 解决中
                // closed  已关闭
                // rejected 暂不修复
                // resolved 已修复，待验证



            }

        }

        info.add("已修复："+close);
        info.add("待修复:"+open);
        info.add("本周新增:"+weeknum);
        return info;
    }

    /*public static void main(String[] args) {
        List<String> list = getAllBugs("51027841");
        System.out.println(list);
    }*/
}
