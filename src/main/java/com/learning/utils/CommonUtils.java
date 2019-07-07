package com.learning.utils;

import java.util.List;

public class CommonUtils {
    /**
     * 两个数组比较返回第一个数组的唯一值
     */
    public  static Integer[] removeDups(List<Integer> list1, List<Integer> list2) {
        Integer[] a1 = new Integer[list1.size()];
        a1 =list1.toArray(a1);
        Integer[] a2 = new Integer[list2.size()];
        a2 =list2.toArray(a2);
        Integer[] result = null;
        int size = 0;
        OUTERMOST: for (Integer e1: a1){
            for (Integer e2: a2){
                if(e1.equals(e2))
                    continue OUTERMOST;
            }
            Integer[] temp = new Integer[++size];
            if(result!=null){
                for (int i=0;i<result.length;i++){
                    temp[i] = result[i];
                }
            }
            temp[temp.length-1] = e1;
            result = temp;
        }
        return result;
    }
}
