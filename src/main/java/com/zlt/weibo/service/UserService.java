package com.zlt.weibo.service;

import com.zlt.weibo.entity.Message;
import com.zlt.weibo.entity.User;

import java.io.IOException;
import java.util.List;

/**
 * 用户信息业务层接口
 */
public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    public boolean reg(User user) throws IOException;

    /**
     * 执行登录
     * @param user
     * @return
     */
    User login(User user) throws IOException;

    /**
     * 查询粉丝数
     * @param uid
     * @return
     */
    int selectFollowCount(String uid) throws IOException;

    /**
     * 查询关注数
     * @param uid
     * @return
     */
    int selectAboutCount(String uid) throws IOException;

    /**
     * 更新用户数据
     * @param user
     */
    void update(User user) throws IOException;

    /**
     * 根据用户uid查找用户信息
     * @param uid
     * @return
     */
    User selectUserByUid(String uid) throws IOException;

    /**
     * 根据关键字搜索用户
     * @param keyWords
     * @return
     */
    List<User> searchUser(String keyWords) throws IOException;

    /**
     * 判断两个人是否存在关注关系
     * @param cuid 当前用户的uid
     * @param uid 搜索得到的个人搜索uid
     * @return
     */
    boolean isFollow(String cuid, String uid) throws IOException;

    /**
     * 执行关注的方法
     * @param follow
     * @param about
     */
    void follow(String follow, String about) throws IOException;

    /**
     * 执行取消关注的方法
     * @param uid
     * @param uid1
     */
    void unfollow(String uid, String uid1) throws IOException;

    /**
     * 获取好友列表对象
     * @param uid
     * @return
     */
    List<User> getFriends(String uid) throws IOException;

    /**
     * 查询私信
     * @param uid
     * @param uid1
     * @return
     */
    List<Message> getMessage(String uid, String uid1) throws IOException;

    /**
     * 发送私信
     * @param message
     * @return
     */
    boolean sendMessage(Message message) throws IOException;
}
