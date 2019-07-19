package com.learning.utils;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
    /**
     * 两个数组比较返回第一个数组的唯一值
     */
    public static List<Integer> removeArrayDups(List<Integer> list1, List<Integer> list2) {
        List<Integer> list3 = new ArrayList<>();
        OUTERMOST:
        for (Integer e1 : list1) {
            for (Integer e2 : list2) {
                if (e1 == e2)
                    continue OUTERMOST;
            }
            list3.add(e1);
        }
        return list3;
    }

    /**
     * 两个字符串数组比较返回第一个字符串数组的唯一值
     */
    public static String[] removeStringDups(String[] a1, String[] a2) {
        String[] result = null;
        int size = 0;
        OUTERMOST:
        for (String e1 : a1) {
            for (String e2 : a2) {
                if (e1.equals(e2))
                    continue OUTERMOST;
            }
            String[] temp = new String[++size];
            if (result != null) {
                for (int i = 0; i < result.length; i++) {
                    temp[i] = result[i];
                }
            }
            temp[temp.length - 1] = e1;
            result = temp;
        }
        return result;
    }

    /**
     * 两个字符串数组比较返回两个字符串数组的相同值
     */
    public static String[] retainStringDups(String[] a1, String[] a2) {
        String[] result = null;
        int size = 0;
        OUTERMOST:
        for (String e1 : a1) {
            for (String e2 : a2) {
                if (e1.equals(e2)){
                    String[] temp = new String[++size];
                    if (result != null) {
                        for (int i = 0; i < result.length; i++) {
                            temp[i] = result[i];
                        }
                    }
                    temp[temp.length - 1] = e1;
                    result = temp;
                    continue OUTERMOST;
                }
            }
        }
        return result;
    }
}
