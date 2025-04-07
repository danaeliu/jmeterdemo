package com.demos.jmeterdemo.zentao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayUtil {
    public boolean isinDay(String dateString,String startTimeString,String endTimeString) {
        boolean isInRange = false;
        dateString = dateString.replace("T"," ").replace("Z","");
        // 将时间和范围的起止时间转换为Date对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date time = sdf.parse(dateString);
            Date startTime = sdf.parse(startTimeString);
            Date endTime = sdf.parse(endTimeString);

            // 判断时间是否在范围内
            isInRange = time.after(startTime) && time.before(endTime);
            if (isInRange) {
                isInRange = true;
//                System.out.println("在内");
            } else {
                System.out.println("不在内");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInRange;
    }

}
