package com.zlt.weibo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtils {

    /**
     * 将字符串解析成json对象
     * @param str
     * @return
     */
    public static JSONObject parseJSONObject(String str){
        return JSON.parseObject(str);
    }

    /**
     * 将字符串解析成json数组
     * @param str
     * @return
     */
    public static JSONArray parseJSONArray(String str){
        return JSON.parseArray(str);
    }
}
