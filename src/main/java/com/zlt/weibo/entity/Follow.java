package com.zlt.weibo.entity;

public class Follow {
    private String fid;
    private String follow;
    private String about;
    private int visible;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public Follow() {
    }

    public Follow(String fid, String follow, String about, int visible) {
        this.fid = fid;
        this.follow = follow;
        this.about = about;
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "fid='" + fid + '\'' +
                ", follow='" + follow + '\'' +
                ", about='" + about + '\'' +
                ", visible=" + visible +
                '}';
    }
}

