package com.zlt.weibo.entity;

public class Comments {
    private String cid;

    private String content;

    private User uid;

    private String wid;

    private String sendTime;

    public Comments() {
    }

    public Comments(String cid, String content, User uid, String wid, String sendTime) {
        this.cid = cid;
        this.content = content;
        this.uid = uid;
        this.wid = wid;
        this.sendTime = sendTime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUid() {
        return uid;
    }

    public void setUid(User uid) {
        this.uid = uid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
