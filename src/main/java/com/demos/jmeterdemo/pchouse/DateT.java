package com.demos.jmeterdemo.pchouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: zywork-app
 * @description: 时间工具测试类
 * @author: 危锦辉
 * @create: 2021-08-07 21:39
 */
public class DateT {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取本周的所有日期
     * @param date
     * @return
     */
    public static String getTimeInterval(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        return imptimeBegin + "," + imptimeEnd;
    }

    /**
     * 获取上周的所有日期
     * @return
     */
    public static String getLastTimeInterval() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
        String lastBeginDate = sdf.format(calendar1.getTime());
        // System.out.println(sdf.format(calendar2.getTime()));// last Sunday
        String lastEndDate = sdf.format(calendar2.getTime());
        return lastBeginDate + "," + lastEndDate;
    }

    /**
     * 通用方法，查找日期
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> findDates(Date dBegin, Date dEnd)
    {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /*public static void main(String args[]){
        *//** 测试获取上周的所有日期 *//*
        String s1="2023-01-24";
        if(isupweek(s1)){
            System.out.println("是上周时间");
        }else if(isweek(s1)){
            System.out.println("是本周时间");
        }

    }*/
    //是不是上周
    public  boolean isupweek(String d){
        boolean flag=false;
        List<String> dates=new ArrayList<>();
        try {
            String yz_time = DateT.getLastTimeInterval();
            String array[] = yz_time.split(",");
            //上周第一天
            String start_time = array[0];
            //上周最后一天
            String end_time = array[1];
            //格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dBegin = sdf.parse(start_time);
            Date dEnd = sdf.parse(end_time);
            //获取上周所有date
            List<Date> lDate = findDates(dBegin, dEnd);
            for (Date date : lDate) {
                System.out.println(sdf.format(date));
                dates.add(sdf.format(date));
            }
            if(dates.contains(d)){
                flag = true;
                System.out.println("是上周的时间");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    //是不是本周
    public  boolean isweek(String dat){
        boolean flag=false;
        List<String> dates=new ArrayList<>();

        /** 测试获取本周的所有日期 */
        try {
            //获取本周时间
            String yz_time = DateT.getTimeInterval(new Date());
            String array[] = yz_time.split(",");
            //本周第一天
            String start_time = array[0];
            //本周最后一天
            String end_time = array[1];
            //格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dBegin = sdf.parse(start_time);
            Date dEnd = sdf.parse(end_time);
            //获取这周所有date
            List<Date> lDate = findDates(dBegin, dEnd);
            for (Date date : lDate) {
                System.out.println("本周："+sdf.format(date));
                dates.add(sdf.format(date));
            }
            if(dates.contains(dat)){
                flag = true;
                System.out.println("是本周的时间");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


}
