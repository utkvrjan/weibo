package com.zlt.weibo.entity;

/**
 * ajax响应的数据对象
 * @param <T>  数据
 */
public class Result<T> {

    private int state;//状态码  0 成功   1失败  2未登录

    private String msg;//消息内容

    private T data;//数据

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
