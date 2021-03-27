package com.zlt.weibo.service;

import com.zlt.weibo.entity.Comments;
import com.zlt.weibo.entity.Pager;
import com.zlt.weibo.entity.WeiBo;

import java.io.IOException;

/**
 * 管理微博业务层接口
 */
public interface WeiBoService {
    /**
     * 发送微博实现方法
     * @param weibo
     */
    void saveWeibo(WeiBo weibo) throws IOException;

    /**
     * 为登录情况下查询微博总数
     * @param pager
     * @return
     * @throws IOException
     */
    Pager<WeiBo> selectWeiboByNotLogin(Pager<WeiBo> pager) throws IOException;

    /**
     * 登录情况下查询微博总数
     * @param pager
     * @param uid
     * @return
     */
    Pager<WeiBo> selectWeiboByLogin(Pager<WeiBo> pager, String uid) throws IOException;

    /**
     * 根据wid查找微博
     * @param pager
     * @param uid
     * @param cuid
     * @return
     */
    Pager<WeiBo> selectWeiboByUid(Pager<WeiBo> pager, String uid, String cuid) throws IOException;

    /**
     * 执行微博点赞的方法
     * @param wid
     * @param uid
     */
    void fabulous(String wid, String uid) throws IOException;

    /**
     * 执行微博取消赞的方法
     * @param wid
     * @param uid
     */
    void unfabulous(String wid, String uid) throws IOException;

    /**
     * 保存评论的方法
     * @param comm
     */
    void saveComments(Comments comm) throws IOException;
}
