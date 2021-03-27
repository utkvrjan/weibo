package com.zlt.weibo.entity;

public class Message {

    private String mid;

    private User sender;

    private User receiver;

    private String mcontent;

    private String sendTime;

    public Message() {
    }

    public Message(String mid, User sender, User receiver, String mcontent, String sendTime) {
        this.mid = mid;
        this.sender = sender;
        this.receiver = receiver;
        this.mcontent = mcontent;
        this.sendTime = sendTime;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mid='" + mid + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", mcontent='" + mcontent + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
