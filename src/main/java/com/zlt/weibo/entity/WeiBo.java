package com.zlt.weibo.entity;

import java.util.List;

public class WeiBo {
    private String wid;

    private User sender;

    private String sendTime;

    private String content;

    private String pic;

    private String video;

    private boolean fabulous = false;//是否点赞

    private List<Comments> commentsList;

    public WeiBo() {
    }

    public WeiBo(String wid, User sender, String sendTime, String content, String pic, String video) {
        this.wid = wid;
        this.sender = sender;
        this.sendTime = sendTime;
        this.content = content;
        this.pic = pic;
        this.video = video;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public boolean isFabulous() {
        return fabulous;
    }

    public void setFabulous(boolean fabulous) {
        this.fabulous = fabulous;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
