package com.zlt.weibo.dao;

import com.zlt.weibo.entity.Follow;
import com.zlt.weibo.entity.Message;
import com.zlt.weibo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表持久层接口
 */
public interface UserDao {

    /**
     * 添加用户，注册用户
     * @param user
     * @return
     */
    void saveUser(User user);

    /**
     * 用户登录时的方法--根据姓名和密码查找用户
     * @param user
     * @return
     */
    User selectUser(User user);

    /**
     * 更新用户登录时间
     * @param user
     */
    void updateLoginUser(User user);

    /**
     * 查询粉丝数量
     * @param uid
     * @return
     */
    int selectFollowCount(String uid);

    /**
     * 查询关注数量
     * @param uid
     * @return
     */
    int selectAboutCount(String uid);

    /**
     * 跟新用户表的方法
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据id查找用户
     * @param uid
     * @return
     */
    User selectUserByUid(String uid);

    /**
     * 搜索用户的方法
     * @param keyWords
     * @return
     */
    List<User> searchUser(String keyWords);

    /**
     * 判断关注表中是否该条关注信息
     * @param follow
     * @param about
     * @return
     */
     Follow isFollow(@Param("follow") String follow, @Param("about") String about);

    /**
     * 在数据库表中已有关注信息记录时，在此执行关注--更新关注表
     * @param follow
     * @param about
     * @param visible
     */
    void updateFollow(@Param("follow") String follow, @Param("about") String about, @Param("visible") int visible);

    /**
     * 添加关注----第一次执行关注
     * @param fid
     * @param follow
     * @param about
     * @param visible
     */
    void insertFollow(@Param("fid")String fid,@Param("follow") String follow,@Param("about") String about,@Param("visible") int visible);

    /**
     * 发送私信
     * @param message
     */
    void insertMessage(Message message);

    /**
     *查询好友列表（私信人）
     * @param uid
     * @return
     */
    List<User> getFriends(String uid);

    /**
     * 查询私信
     * @param sender
     * @param receiver
     * @return
     */
    List<Message> getMessage(@Param("sender") String sender,@Param("receiver") String receiver);
}
