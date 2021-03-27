package com.zlt.weibo.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class BeanUtils {

    /**
     * 根据请求自动解析成对象
     * @param clazz
     * @param request
     * @param <T>
     * @return
     */
    public static <T> T resolveBean(Class<T> clazz, HttpServletRequest request){
        //先获取所有的参数名
        Enumeration<String> names = request.getParameterNames();
        T obj = null;
        try {
             obj = clazz.newInstance();
             while(names.hasMoreElements()){
                 //单个的参数名
                 String name = names.nextElement();
                 //单个的参数值
                 String value = request.getParameter(name);
                 //通过set方法 将值注入对象
                 ClassUtils.invokeMethod(clazz,obj,name,value);
             }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
