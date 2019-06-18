package com.learning.utils;

import java.util.HashMap;
import java.util.Map;

public class DateTransUtil {
    public static Map<String, String> monthToDateRange(String month) {
        Map map = new HashMap();
        String[] strs = month.split("-");
        int year = Integer.parseInt(strs[0]);
        String monthStr = strs[1];
        int mon = Integer.parseInt(monthStr);
        StringBuilder startTime = new StringBuilder();
        StringBuilder endTime = new StringBuilder();
        startTime.append(year).append("-");
        startTime.append(mon).append("-01 00:00:00");
        if (mon == 12) {
            endTime.append(year + 1).append("-01-01 00:00:00");
        } else {
            endTime.append(year).append("-");
            endTime.append(mon + 1).append("-01 00:00:00");
        }
        map.put("startTime", startTime.toString());
        map.put("endTime", endTime.toString());
        return map;
    }
}
