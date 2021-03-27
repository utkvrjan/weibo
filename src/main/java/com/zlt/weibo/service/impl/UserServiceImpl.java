package com.zlt.weibo.service.impl;

import com.zlt.weibo.dao.UserDao;
import com.zlt.weibo.entity.Follow;
import com.zlt.weibo.entity.Message;
import com.zlt.weibo.entity.User;
import com.zlt.weibo.service.UserService;
import com.zlt.weibo.utils.MD5Utils;
import com.zlt.weibo.utils.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class UserServiceImpl implements UserService {

    private InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;



    /**
     * 用户注册实现方法---事务提交
     * @param user
     * @return
     */
    @Override
    public boolean reg(User user) throws IOException {

        //读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //使用工厂生产SqlSessionFactory对象
        sqlSession = factory.openSession();
        //使用SqlSessionFactory创建Dao接口的代理对象
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);

        //设置主键的值为uuid
        user.setUid(StringUtils.randomUUID());
        //密码使用md5加密
        user.setPassword(MD5Utils.getMd5(user.getPassword()));
        //添加注册时间
        user.setRegTime(StringUtils.getCurrentDateTime());
        //设置默认头像
        user.setPhoto("default.png");
        userDao.saveUser(user);
        sqlSession.commit();
        sqlSession.close();
        return true;
    }

    /**
     * 执行登录实现方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);
        User userR =  userDao.selectUser(user);
        if(userR != null){
            //更新用户登录时间--事务提交
            userR.setLoginTime(StringUtils.getCurrentDateTime());
            userDao.updateLoginUser(userR);
            sqlSession.commit();
            sqlSession.close();
        }
        return userR;

    }

    /**
     * 查询粉丝数实现方法
     * @param uid
     * @return
     */
    @Override
    public int selectFollowCount(String uid) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);

        return userDao.selectFollowCount(uid);
    }

    /**
     * 查询关注数实现方法
     * @param uid
     * @return
     */
    @Override
    public int selectAboutCount(String uid) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);

        return userDao.selectAboutCount(uid);
    }

    /**
     * 更新用户表实现方法---事务提交
     * @param user
     * @throws IOException
     */
    @Override
    public void update(User user) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);

        //加密用户密码
        user.setPassword(MD5Utils.getMd5(user.getPassword()));
        userDao.updateUser(user);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 根据uid查找用户信息实现方法
     * @param uid
     * @return
     */
    @Override
    public User selectUserByUid(String uid) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);

        return userDao.selectUserByUid(uid);
    }

    /**
     * 根据关键字搜搜用户实现方法--模糊查询
     * @param keyWords
     * @return
     */
    @Override
    public List<User> searchUser(String keyWords) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);

        return userDao.searchUser(keyWords);
    }

    /**
     * 查询判断两人是否相互关注
     * @param cuid 当前用户的uid
     * @param uid 搜索得到的个人搜索uid
     * @return
     */
    @Override
    public boolean isFollow(String cuid, String uid) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);
        Follow follow = userDao.isFollow(cuid,uid);
        if(follow != null && follow.getVisible() == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 执行关注的实现方法
     * @param follow
     * @param about
     */
    @Override
    public void follow(String follow, String about) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);

        //判断表中是否有这条数据
        //根据visible字段来判断，如果开始没有关注，当执行关注的时候，会将visible字段设置为0
        //如果取消关注，不会删除该条数据，而是通过visible的值来管理不同的状态
        Follow follow1 = userDao.isFollow(follow,about);
        if(follow1 != null && follow1.getVisible() == 1){
            //如果有这条数据  执行update  把visible改成0 显示----执行关注（不是第一次）
            userDao.updateFollow(follow,about,0);
        }else{
            //以前没有关注过，第一次执行关注
            String fid = StringUtils.randomUUID();
            userDao.insertFollow(fid,follow,about,0);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 执行取消关注的具体方法
     * @param follow
     * @param about
     */
    @Override
    public void unfollow(String follow, String about) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);

        userDao.updateFollow(follow,about,1);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 查询好友列表
     * @param uid
     * @return
     */
    @Override
    public List<User> getFriends(String uid) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);
        List<User> lists = userDao.getFriends(uid);
        return lists;
    }

    /**
     * 查询私信
     * @param uid
     * @param uid1
     * @return
     */
    @Override
    public List<Message> getMessage(String uid, String uid1) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);
        List<Message> lists = userDao.getMessage(uid,uid1);
        return lists;
    }

    /**
     * 发送私信---事务提交
     * @param message
     * @return
     */
    @Override
    public boolean sendMessage(Message message) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);
        userDao.insertMessage(message);
        return true;
    }
}
