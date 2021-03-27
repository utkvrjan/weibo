package com.zlt.weibo.entity;

/**
 * 用户表信息
 */
public class User {

    private String uid;

    private String username;

    private String password;

    private String nickname;

    private String photo;

    private String regTime;

    private String loginTime;

    private String email;

    private String sex;

    public User() {
    }

    public User(String uid, String username, String password, String nickname, String photo, String regTime, String loginTime, String email, String sex) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.photo = photo;
        this.regTime = regTime;
        this.loginTime = loginTime;
        this.email = email;
        this.sex = sex;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", photo='" + photo + '\'' +
                ", regTime='" + regTime + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
