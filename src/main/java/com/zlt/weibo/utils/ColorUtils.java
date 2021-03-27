package com.zlt.weibo.utils;

import java.awt.*;

public class ColorUtils {


    /**
     * 随机一种颜色
     * @param type  1 浅色系    0 深色系
     * @return
     */
    public static Color getColor(int type){
        if(type == 0 || type == 1){
            int r = (int)(Math.random()*128) + type*128;
            int g = (int)(Math.random()*128) + type*128;
            int b = (int)(Math.random()*128) + type*128;
            return new Color(r,g,b);
        }
        return null;
    }
}
