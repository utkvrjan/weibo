package com.zlt.weibo.test;

public class Solution2 {
    private String name;
    private int age;

    public Solution2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Solution2() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public static int testStatic(int a,int b) {
        return a+b;
    }
}
