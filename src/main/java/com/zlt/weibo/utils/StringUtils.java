package com.zlt.weibo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUtils {


    private static final String str = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ0123456789";

    /**
     * 随机生成字符串
     * @param len
     * @return
     */
    public static String randomStr(int len){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < len;i++){
            sb.append(str.charAt((int)(Math.random()*str.length())));
        }
        return sb.toString();
    }

    /**
     * 判断字符串不为空
     * @param str
     * @return
     */
    public static boolean isNotNull(String str){
        return str != null && !"".equals(str);
    }

    /**
     * 判断对象不为空
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj){
        return obj != null && !"".equals(obj);
    }

    /**
     * 获取一个uuid的值
     * @return
     */
    public static String randomUUID(){
        return UUID.randomUUID().toString();
    }


    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
