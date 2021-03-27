package com.zlt.weibo.service.impl;

import com.zlt.weibo.dao.WeiBoDao;
import com.zlt.weibo.entity.Comments;
import com.zlt.weibo.entity.Fabulous;
import com.zlt.weibo.entity.Pager;
import com.zlt.weibo.entity.WeiBo;
import com.zlt.weibo.service.WeiBoService;
import com.zlt.weibo.utils.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 管理微博业务层具体类
 */
public class WeiBoServiceImpl implements WeiBoService {
    private WeiBoDao weiBoDao;
    private InputStream in;
    private SqlSession sqlSession;

    /**
     * 发送weibo方法--事务提交
     * @param weibo
     */
    @Override
    public void saveWeibo(WeiBo weibo) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        weiBoDao = (WeiBoDao) sqlSession.getMapper(WeiBoDao.class);

        //发送时间
        weibo.setSendTime(StringUtils.getCurrentDateTime());
        //微博的id
        weibo.setWid(StringUtils.randomUUID());
        weiBoDao.saveWeibo(weibo);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 搜索当前页weibo数据
     * @param pager
     * @return
     */
    @Override
    public Pager<WeiBo> selectWeiboByNotLogin(Pager<WeiBo> pager) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        weiBoDao = (WeiBoDao) sqlSession.getMapper(WeiBoDao.class);

        //查询微博条数
        pager.setTotalCount(weiBoDao.selectCountByNotLogin());
        //页面数量
        pager.setPageCount((pager.getTotalCount() - 1)/pager.getPageSize() + 1);
        //当前页的数据
        pager.setDatas(weiBoDao.selectWeiboByNotLogin(pager));
        for(WeiBo weibo : pager.getDatas()){
            //查询微博的评论
            //weibo.setCommentsList(weiBoDao.selectCommentsByWid(weibo.getWid()));
        }
        return pager;
    }

    /**
     * 登录情况下搜索微博的实现方法
     * @param pager
     * @param uid
     * @return
     * @throws IOException
     */
    @Override
    public Pager<WeiBo> selectWeiboByLogin(Pager<WeiBo> pager, String uid) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        weiBoDao = (WeiBoDao) sqlSession.getMapper(WeiBoDao.class);

        //查询登录用户微博条数
        pager.setTotalCount(weiBoDao.selectCountByLogin(uid));
        //页面数量
        pager.setPageCount((pager.getTotalCount() - 1)/pager.getPageSize() + 1);
        //当前页的数据--查询用户所有微博
        pager.setDatas(weiBoDao.selectWeiboByLogin(pager,uid));

        //判断一下当前登录的人和这个微博是否有点赞的关系
       for(WeiBo weibo : pager.getDatas()){
           Fabulous fabulous = weiBoDao.isFabulous(weibo.getWid(),uid);
           if(fabulous != null && fabulous.getVisible() == 0){
               weibo.setFabulous(true);
           }else {
               weibo.setFabulous(false);
           }
            //查询微博的评论
           weibo.setCommentsList(weiBoDao.selectCommentsByWid(weibo.getWid()));
        }

        return pager;
    }

    /**
     * 根据wid查找微博
     * @param pager
     * @param uid
     * @param cuid
     * @return
     */
    @Override
    public Pager<WeiBo> selectWeiboByUid(Pager<WeiBo> pager, String uid, String cuid) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        weiBoDao = (WeiBoDao) sqlSession.getMapper(WeiBoDao.class);
        //查询微博条数
        pager.setTotalCount(weiBoDao.selectCountByUid(uid));
        //页面数量
        pager.setPageCount((pager.getTotalCount() - 1)/pager.getPageSize() + 1);
        //当前页的数据
        pager.setDatas(weiBoDao.selectWeiboByLogin(pager,uid));
        //判断一下当前登录的人和这个微博是否有点赞的关系
        for(WeiBo weibo : pager.getDatas()){
            Fabulous fabulous = weiBoDao.isFabulous(weibo.getWid(),cuid);
            if(fabulous != null && fabulous.getVisible() == 0){
                weibo.setFabulous(true);
            }else {
                weibo.setFabulous(false);
            }
            //查询微博的评论
            weibo.setCommentsList(weiBoDao.selectCommentsByWid(weibo.getWid()));
        }
        return pager;
    }

    /**
     * 执行点赞的具体方法
     * @param wid
     * @param uid
     */
    @Override
    public void fabulous(String wid, String uid) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        weiBoDao = (WeiBoDao) sqlSession.getMapper(WeiBoDao.class);
        //判断之前有没有赞
        Fabulous fabulous = weiBoDao.isFabulous(wid,uid);
        if(fabulous != null && fabulous.getVisible() == 1){
            //之前点过赞，直接更新
            weiBoDao.updateFabulous(wid,uid,0);
        }else {
            //之前没有点过赞，需要添加
            String fid = StringUtils.randomUUID();
            weiBoDao.insertFabulous(fid,wid,uid,0);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 执行微博取消赞的具体方法
     * @param wid
     * @param uid
     */
    @Override
    public void unfabulous(String wid, String uid) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        weiBoDao = (WeiBoDao) sqlSession.getMapper(WeiBoDao.class);

        weiBoDao.updateFabulous(wid,uid,1);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 保存评论具体方法--事务提交
     * @param comm
     */
    @Override
    public void saveComments(Comments comm) throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        weiBoDao = (WeiBoDao) sqlSession.getMapper(WeiBoDao.class);

        weiBoDao.saveComments(comm);
        sqlSession.commit();
        sqlSession.close();
    }

}
