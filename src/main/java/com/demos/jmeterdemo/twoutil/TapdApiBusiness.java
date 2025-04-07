package com.demos.jmeterdemo.twoutil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 核心业务类
 */
public class TapdApiBusiness {
    public static TapdApiTool tapdApiTool=new TapdApiTool();
    /**
     * 得到总的线上故障的所有应用名称
     */
    public static List<String> getprojectname(String workspaceid, String iteration_id) {
        //System.out.println("开始getprojectname===============");
        List<String> allname = new ArrayList<>();
        //得出总页数
        int totalPage = tapdApiTool.getbugtotlePages(workspaceid, iteration_id);
        //循环限每一页数据
        for (int i = 1; i <= totalPage; i++) {
            JSONObject jsonObject =tapdApiTool.getautobugslist(workspaceid, iteration_id, Integer.toString(i));
            JSONArray array = jsonObject.getJSONArray("data");
            for (int j = 0; j < array.size(); j++) {//解析每页的bug数据
                JSONObject bugs = array.getJSONObject(j);
                String title = bugs.getJSONObject("Bug").getString("title");
                //标题获取包括线上报障字眼的才算
                if (title.contains("【线上报障_")) {
                   // System.out.println("原title==" + title);
                    String rightname = title.split("_")[1].split("】")[0];
                    allname.add(rightname);
                }
                if (title.contains("【线上报障-")) {
                    //System.out.println("原title==" + title);
                    String rightname = title.split("-")[1].split("】")[0];
                    allname.add(rightname);
                }

            }
        }
        //System.out.println("去重前的数据：====" + allname);
        //名称的list去重归类
        List<String> vector = new Vector<String>();
        Set<String> set = new HashSet<String>(allname);
        vector = new Vector<String>(set);
        //System.out.println("去重之后的数据：" + vector);
        return vector;
    }

    /**
     * 【统计时间】：当天
     * 【tapd】迭代： 线上问题
     * 【统计状态】新、解决中、待测试、重新打开
     * 【线上报障】今天共有*个待处理：
     * 【线上报障_汽车论坛】2个；【线上报障_汽车APP】3个
     * 【tapd链接】列表
     */
    public List<ResultEntity> getbugsByday(String workspaceid, String iteration_id,int beforeDays ) {
        List<ResultEntity> resultEntityList=new ArrayList<>();
        int totalPage = tapdApiTool.getbugtotlePages(workspaceid, iteration_id);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        IsIntenDay isIntenDay=new IsIntenDay();
        //循环每一页
        for (int i = 1; i <= totalPage; i++) {
            JSONObject jsonObject = tapdApiTool.getautobugslist(workspaceid, iteration_id, Integer.toString(i));
            JSONArray array = jsonObject.getJSONArray("data");
            //循环一页里每一个BUG
            for (int j = 0; j < array.size(); j++) {
                //处理每一个BUG
                JSONObject bugs = array.getJSONObject(j);
                String title = bugs.getJSONObject("Bug").getString("title");
                //标题获取包括线上报障字眼的才算
                if (title.contains("线上报障_")||title.contains("线上报障-")) {
                    String created = bugs.getJSONObject("Bug").getString("created");
                    //如果大于今天的开始日期，小于今天的结束日期
                    if (isIntenDay.getDayDiffFromToday1(created,beforeDays)) {
                        //如果是当天的BUG
                        String status = bugs.getJSONObject("Bug").getString("status");
                        if ((status.equals("new") || status.equals("reopened"))) {
                            String priority = bugs.getJSONObject("Bug").getString("priority");
                            //优先级高或紧急
                            if(priority.equals("high")||priority.equals("urgent")){
                                ResultEntity resultEntity=new ResultEntity();;
                                String id = bugs.getJSONObject("Bug").getString("id");
                                //得到应用名
                                String projectname = title.split("_")[1].split("】")[0];
                                String  url = "https://www.tapd.cn/"+workspaceid+"/bugtrace/bugs/view?bug_id="+id;

                                String custom_field_7 = bugs.getJSONObject("Bug").getString("custom_field_7");//预计修复时间
                                String current_owner = bugs.getJSONObject("Bug").getString("current_owner");//处理人
                                //预计修复时间不为空
                                if(custom_field_7 ==null || custom_field_7.equals("") || custom_field_7.length()==0){
                                    resultEntity.setProjectName(projectname);
                                    resultEntityList.add(resultEntity);

                                    resultEntity.setUrl("["+title.split("_")[1].split("】")[1]+"]"+"("+url+")"+" "+"（预计修复时间：暂无), "+"<font color=\"warning\">"+current_owner+"</font>");
                                }else {//预计修复时间不为空
                                    //预计修复时间近5天
                                    if (isIntenDay.getDayDiffFromToday2(custom_field_7,5)) {
                                        resultEntity.setProjectName(projectname);
                                        resultEntityList.add(resultEntity);
                                        resultEntity.setUrl("["+title.split("_")[1].split("】")[1]+"]"+"("+url+")"+" "+"（预计修复时间："+custom_field_7+"), "+"<font color=\"warning\">"+current_owner+"</font>");
                                    }
                                }

                            }

                        }

                    }
                }
            }
        }
        return resultEntityList;
    }

    /*本周
    线上报障关键字段
    【tapd】标题：线上报障
    【tapd】迭代： 线上问题
    【tapd】状态：not in （新、重新打开、解决中）
    【tapd】是否已回复： 否 、 空
    【tapd】创建时间：本周 （本周一 到 当前时间）
     */
    public List<ResultEntity> getbugsByweek(String workspaceid, String iteration_id,int beforeDays){
        List<ResultEntity> resultEntityList=new ArrayList<>();
        IsIntenDay isIntenDay=new IsIntenDay();
        System.out.println("开始统计每周bug分布情况===============");
        int totalPage = tapdApiTool.getbugtotlePages(workspaceid, iteration_id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer urls=new StringBuffer();
        //循环所有名称来找bug数量
        try {
            for (int i = 1; i <= totalPage; i++) {
                JSONObject jsonObject = tapdApiTool.getautobugslist(workspaceid, iteration_id, Integer.toString(i));
                JSONArray array = jsonObject.getJSONArray("data");
                for (int j = 0; j < array.size(); j++) {
                    JSONObject bugs = array.getJSONObject(j);
                    String title = bugs.getJSONObject("Bug").getString("title");
                    //标题获取包括线上报障字眼的才算
                    if (title.contains("线上报障_") || title.contains("线上报障-")) {
                        String created = bugs.getJSONObject("Bug").getString("created");
                        //判断bug创建时间是否为本周一至当前执行任务时间，即本周,改为10天之内
                        System.out.println(created+"是否为近10天内："+isIntenDay.isten(created,beforeDays));
                        if (isIntenDay.getDayDiffFromToday1(created,beforeDays)) {
                            String status = bugs.getJSONObject("Bug").getString("status");
                            if (!(status.equals("new") || !(status.equals("in_progress")) || !(status.equals("reopened")))) {
                                //是否已回复
                                String reply = bugs.getJSONObject("Bug").getString("custom_field_10");
                                //统计 “否”和空
                                if (!(reply.equals("是"))) {
                                    ResultEntity resultEntity=new ResultEntity();
                                    String projectname = title.split("_")[1].split("】")[0];
                                    String id=bugs.getJSONObject("Bug").getString("id");
                                    String custom_field_7 = bugs.getJSONObject("Bug").getString("custom_field_7");
                                    String url="https://www.tapd.cn/"+workspaceid+"/bugtrace/bugs/view?bug_id="+id;
                                    resultEntity.setUrl("["+title.split("_")[1].split("】")[1]+"]"+"("+url+")");
                                    resultEntity.setProjectName(projectname);
                                    resultEntityList.add(resultEntity);
                                    System.out.println("近10天内："+id+title);
                                }
                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultEntityList;
    }

  /* public static void main(String[] args) {
        TapdApiBusiness tapdApi=new TapdApiBusiness();
       List<ResultEntity> cmap = tapdApi.getbugsByday("68331031", "1168331031001000392",31);
       for (ResultEntity r:cmap){
           System.out.println(r.getProjectName()+"=="+r.getUrl());
       }
   }*/

}