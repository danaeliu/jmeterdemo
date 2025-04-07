package com.demos.jmeterdemo.twoutil;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;


public class IsIntenDay
{
    //private int beforeDays = 11; // Use this vaule to judge the start date within 7 days.


    /*public static void main(String[] args)
    {
        String strTime = "2022-10-16";
        if(isNewzt(strTime, beforeSeconds))
        {
            System.out.println("10 days old");
        }
        else
        {
            System.out.println("more than 10 days");
        }
    }*/
    public boolean isten(String strTime,int beforeDays){
        boolean flag=false;
        long beforeSeconds = beforeDays * 24 * 60 * 60 * 1000;
        if(isNewzt(strTime, beforeSeconds)){
            flag=true;
        }
        return flag;
    }

    private  boolean isNewzt(String theStrTime, long beforeDays)
    {
        boolean flag = false;
        if("0".equals(theStrTime))
        {
            return flag;
        }

        try
        {
            Date tDat = StrToDate(theStrTime, "");
            long thm = tDat.getTime();
            long chm=System.currentTimeMillis();
            if(thm + beforeDays >=chm)
            {
                flag = true;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return flag;
    }

    private static Date StrToDate(String str, String formatStr)
    {
        if (null == formatStr || "".equals(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }

        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try
        {
            date = format.parse(str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     *距离当前时间88天之内的日期
     * @param time
     * type:1--88天之内的
     * @return
     */
    public  boolean getDayDiffFromToday1(String time,long beforeDays)  {
        //将字符串转为日期
//time=20171210144833  -->要对应"yyyyMMddHHmmss"不然会报unparase
        boolean flag=false;
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date param = sdf.parse(time);//参数时间
            long  s1=param.getTime();//将时间转为毫秒
            long  s2=System.currentTimeMillis();//得到当前的毫秒
            int  day= Math.toIntExact((s2 - s1) / 1000 / 60 / 60 / 24);
            if (day <= beforeDays){
                flag=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    /*public static void main(String[] args) {
        IsIntenDay isIntenDay=new IsIntenDay();
        System.out.println(isIntenDay.getDayDiffFromToday1("2022-06-13 18:47:18",31));
        System.out.println(isIntenDay.getDayDiffFromToday1("2022-11-24 18:53:18",31));
        System.out.println(isIntenDay.getDayDiffFromToday2("2022-11-24",31));
        System.out.println(isIntenDay.getDayDiffFromToday2("2022-11-17",5));

    }*/
    public  boolean getDayDiffFromToday2(String time,long beforeDays)  {
        boolean flag=false;
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //开始时间
            Date star = dft.parse(time);
            Date date=new Date();
            //System.out.println("当前时间为="+dft.format(date));
            //当前时间判断
            Date endDay=dft.parse(dft.format(date));
            Long starTime=star.getTime();
            Long endTime=endDay.getTime();
            Long num=endTime-starTime;//时间戳相差的毫秒数
            long differ=num/24/60/60/1000;
            //System.out.println(differ);
            if(differ<=beforeDays){
                flag=true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /*public  boolean getDayDiffFromToday3(String time,long beforeDays)  {
        boolean flag=false;
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //开始时间
            Date star = dft.parse(time);
            Date date=new Date();
            System.out.println("当前时间为="+dft.format(date));
            //当前时间判断
            Date endDay=dft.parse(dft.format(date));
            Long starTime=star.getTime();
            Long endTime=endDay.getTime();
            Long num=endTime-starTime;//时间戳相差的毫秒数
            long differ=num/24/60/60/1000;
            System.out.println(differ);
            if(differ>0 &&differ<=beforeDays){
                flag=true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }*/

}