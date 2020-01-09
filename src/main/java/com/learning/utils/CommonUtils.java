package com.learning.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class CommonUtils {

    /**
     * 生成I编号
     * uuid去除"-"
     */
    public static String getRandomStr() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    /**
     * 生成ISV端的缴费账单编号
     * uuid去除"-"
     */
    public static String getIsvTradeNo(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-","");
    }

    /**
     * 日期格式化
     *
     * @param time
     * @param model
     * @return
     */
    public static <T> String timeFormat(T time, String model) {
        String res = "";
        if (model.equals("") || model == null) {
            model = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(model);
        try {
            Date date = new Date();
            if (time instanceof String) {
                date = dateFormat.parse(time.toString());
            }
            res = dateFormat.format(date);
        } catch (ParseException e) {
            log.error("时间解析错误");
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 两个数组比较返回第一个数组的唯一值
     */
    public static List<Integer> removeIntegerDupsInList(List<Integer> list1, List<Integer> list2) {
        List<Integer> list3 = new ArrayList<>();
        OUTERMOST:
        for (Integer e1 : list1) {
            for (Integer e2 : list2) {
                if (e1.equals(e2))
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
                if (e1.equals(e2)) {
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

    /**
     * 两个字符串List比较返回第一个字符串List的唯一值
     */
    public static List<String> removeStringDupsInList(List<String> l1, List<String> l2) {
        List<String> l3 = new ArrayList<>();
        OUTERMOST:
        for (String e1 : l1) {
            for (String e2 : l2) {
                if (e1.equals(e2))
                    continue OUTERMOST;
            }
            l3.add(e1);
        }
        return l3;
    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    public static String StringListToString(List<String> list) {
        if (list != null) {
            String str = String.join(",", list);
            return str;
        } else {
            return null;
        }

    }

    public static Boolean findInStrList(String str, List<String> strList) {
        for (String s : strList) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前时间24小时后的时间返回字符串
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getFaceBillHoursLater(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        return dayAfter;
    }


    /**
     * 去除学校类型的逗号分隔符
     * @param type
     * @return
     */
    public static String schoolTypeFormat(String type){
        return type.replaceAll(",","");
    }

    /**
     * 补全省市区code的位数，6位字符串
     * @param code
     * @return
     */
    public static String pcdCodeFormat(String code){
        if(code.length()<6){
            code = code + "000000";
        }
        return code.substring(0,6);//不包含最后一位的索引
    }

    /**
     * 转化账单状态
     */
    public static String transformBillStatus(String status){
        String res;
        if(status.equals("NOT_PAY")){
            res = PayConfig.NOT_PAY;
        }else if(status.equals("PAYING")){
            res = PayConfig.PAYING;
        }else if(status.equals("PAY_SUCCESS")){
            res = PayConfig.PAY_SUCCESS;
        }else if(status.equals("BILLING_SUCCESS")){
            res = PayConfig.BILLING_SUCCESS;
        }else if(status.equals("TIMEOUT_CLOSED")){
            res = PayConfig.TIMEOUT_CLOSED;
        }else if(status.equals("ISV_CLOSED")){
            res = PayConfig.ISV_CLOSED;
        }else if(status.equals("FAILURE")){
            res = PayConfig.FAILURE;
        }//微信
        else if(status.equals("SUCCESS")){
            res = PayConfig.SUCCESS;
        }else if(status.equals("REFUND")){
            res = PayConfig.REFUND;
        }else if(status.equals("NOTPAY")){
            res = PayConfig.NOTPAY;
        }else if(status.equals("CLOSED")){
            res = PayConfig.CLOSED;
        }else if(status.equals("REVOKED")){
            res = PayConfig.REVOKED;
        }else if(status.equals("USERPAYING")){
            res = PayConfig.USERPAYING;
        }else if(status.equals("PAYERROR")){
            res = PayConfig.PAYERROR;
        }else {
            res = "未知";
        }
        return res;
    }
}
