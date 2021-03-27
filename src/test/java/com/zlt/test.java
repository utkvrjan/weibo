package com.zlt;

import com.zlt.weibo.dao.UserDao;
import com.zlt.weibo.entity.User;
import com.zlt.weibo.utils.MD5Utils;
import com.zlt.weibo.utils.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class test {
    private InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;
    @Before
    public void start() throws IOException {
        //读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //使用工厂生产SqlSessionFactory对象
        sqlSession = factory.openSession();
        //使用SqlSessionFactory创建Dao接口的代理对象
        userDao = (UserDao) sqlSession.getMapper(UserDao.class);
    }

    @After
    public void end(){
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void m01() throws IOException {

        User user = new User();
        user.setUsername("gg");
        user.setPassword(MD5Utils.getMd5("cc"));
        user.setSex("男");
        user.setEmail("cc@123");
        user.setNickname("小宝贝");
        //设置主键的值为uuid
        user.setUid(StringUtils.randomUUID());
        //密码使用md5加密
        user.setPassword(MD5Utils.getMd5(user.getPassword()));
        //添加注册时间
        user.setRegTime(StringUtils.getCurrentDateTime());
        userDao.saveUser(user);

    }

    @Test
    public void m02(){
        String name = "美美";
        List<User> lists = userDao.searchUser(name);
        for (User user :  lists ) {
            System.out.println(user);
        }
    }

    @Test
    public void m03(){
        String content = "今天你快乐吗？";
        String uid = MD5Utils.getMd5("123456");
        System.out.println(uid);
    }
}
