package com.learning.cloud.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: yyt
 * @Date: 2019-06-13 17:12
 * @Desc: 工具类
 */
public class Utils {

    /**
     * 获取当前日期是星期几
     *
     */
    public static Integer getWeekDay(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int week_index = calendar.get(Calendar.DAY_OF_WEEK)-1;
        return week_index;
    }

    /**
     * 获取年份字符串
     * @param gap
     * @return
     */
    public static String getYear(int gap){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year+gap);
    }
}
