package com.zlt.weibo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtils {


    /**
     * 通过name 获取对应的set方法 并且执行
     * @param clazz
     * @param obj
     * @param name
     * @param value
     * @return
     */
    public static void invokeMethod (Class clazz,Object obj ,String name,Object value){
        String first = name.charAt(0) + "";
        //获取到set方法名
        String setName = "set" + first.toUpperCase() + name.substring(1);
        //获取set方法的参数类型
        try {
            Field field = clazz.getDeclaredField(name);
            Method method = clazz.getDeclaredMethod(setName,field.getType());
            method.invoke(obj,value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过name 获取对应的set方法 并且执行
     * @param clazz
     * @param obj
     * @param name
     * @param value
     * @return
     */
    public static void invokeMethod (Class clazz,Object obj ,String name,String value){
        String first = name.charAt(0) + "";
        //获取到set方法名
        String setName = "set" + first.toUpperCase() + name.substring(1);
        //获取set方法的参数类型
        try {
            Field field = clazz.getDeclaredField(name);
            Method method = clazz.getDeclaredMethod(setName,field.getType());
            if(field.getType() == int.class || field.getType() == Integer.class){
                method.invoke(obj,Integer.parseInt(value));
            } else if(field.getType() == double.class || field.getType() == Double.class){
                method.invoke(obj,Double.parseDouble(value));
            } else{
                method.invoke(obj,value);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查类中是否存在某个属性
     * @param name
     * @param clazz
     * @return
     */
    public static Field getDeclaredField(String name,Class clazz){
        try {
            Field field = clazz.getDeclaredField(name);
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

}
