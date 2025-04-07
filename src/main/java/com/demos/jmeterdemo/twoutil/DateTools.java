package com.demos.jmeterdemo.twoutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTools {

    /**
     * 当前时间是否在开始和结束时间之内
     * @return 返回长度为2的字符串集合，如：[2022-05-02 00:00:00, 2022-05-08 24:00:00]
     * 当前时间  开始时间  结束时间
     */
    public  boolean istimeboolean(String date,String begintime,Date endtime) {
        boolean flag=false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date now=df.parse(date);
            Date begin=df.parse(begintime);
            if(now.before(endtime)&&now.after(begin)){
                //System.out.println("在之间");
                flag=true;
            }else {
                //System.out.println("不在");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;

    }




    /**
     * 获取本周一的日期，starttime
     * @param args
     * @throws ParseException
     */
    /**
     * 获取当前时间所在周的周一和周日的日期时间
     * @return
     */
    public  String getWeekDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayWeek==1){
            dayWeek = 8;
        }

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);

        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String weekEnd = sdf.format(sundayDate);

        System.out.println(weekBegin+" 00:00:00");
        return weekBegin+" 00:00:00";
    }

    public static boolean between(Date date, Date dateStart, Date dateEnd) {
        boolean flag=false;
        if (date != null && dateStart != null && dateEnd != null) {
            if (date.after(dateStart) && date.before(dateEnd)) {
                flag=true;
            }

        }
        return flag;
    }

    public static Date strToDate(String s) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        } catch (ParseException e) {
            //LOGGER.error("时间转换错误, string = {}", s, e);
        }
        return date;
    }

   /* public static Date strToDate(String s) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        } catch (ParseException e) {
            //LOGGER.error("时间转换错误, string = {}", s, e);
        }
        return date;
    }
*/
   /* public static void main(String[] args) throws ParseException {
        DateTools dt=new DateTools();
        String day="2022-10-22 00:00:00";
        String begin = dt.getWeekDate();
        System.out.println("begin=="+begin);
        //Date now = sdf.parse(day);
        dt.istimeboolean(day,begin,new Date());




    }*/
}
