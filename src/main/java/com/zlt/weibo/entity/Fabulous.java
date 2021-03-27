package com.zlt.weibo.entity;

public class Fabulous {
    private String fid;
    private String uid;
    private String wid;
    private int visible;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public Fabulous(String fid, String uid, String wid, int visible) {
        this.fid = fid;
        this.uid = uid;
        this.wid = wid;
        this.visible = visible;
    }

    public Fabulous() {
    }

    @Override
    public String toString() {
        return "Fabulous{" +
                "fid='" + fid + '\'' +
                ", uid='" + uid + '\'' +
                ", wid='" + wid + '\'' +
                ", visible=" + visible +
                '}';
    }
}
