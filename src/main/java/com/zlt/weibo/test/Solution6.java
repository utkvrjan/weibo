package com.zlt.weibo.test;


import com.zlt.weibo.service.UserService;

import javax.servlet.annotation.WebServlet;
import java.beans.Transient;

@WebServlet
public class Solution6 extends Solution5 implements Solution1p1, Solution1p3 {
    @Override
    public int getAge() {

        return 0;

    }
    public static final int  testMethod(int a,int b) throws Exception {

        return a+b;

    }
}
