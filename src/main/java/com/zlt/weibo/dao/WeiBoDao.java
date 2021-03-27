package com.zlt.weibo.dao;

import com.zlt.weibo.entity.Comments;
import com.zlt.weibo.entity.Fabulous;
import com.zlt.weibo.entity.Pager;
import com.zlt.weibo.entity.WeiBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * weibo持久层接口
 */
public interface WeiBoDao {

    /**
     * 发送微博
     * @param weibo
     */
    void saveWeibo(WeiBo weibo);

    /**
     * 未登录情况下查询微博条数
     * @return
     */
    int selectCountByNotLogin();

    /**
     * 搜索当前页数据
     * @param pager
     * @return
     */
    List<WeiBo> selectWeiboByNotLogin(Pager<WeiBo> pager);

    /**
     * 查询微博的评论
     * @param wid
     * @return
     */
    List<Comments> selectCommentsByWid(String wid);

    /**
     * 查询用户的微博数量
     * @param uid
     * @return
     */
    int selectCountByLogin(String uid);

    /**
     * 查询用户所有微博
     * @param pager
     * @param uid
     * @return
     */
    List<WeiBo> selectWeiboByLogin(@Param("pager") Pager<WeiBo> pager,@Param("uid") String uid);


    /**
     * 根据uid查询微博条数
     * @param uid
     * @return
     */
    int selectCountByUid(String uid);

    /**
     * 查找是否有点赞记录
     * @param wid
     * @param uid
     * @return
     */
    Fabulous isFabulous(@Param("wid") String wid,@Param("uid") String uid);

    /**
     * 更新点赞信息
     * @param wid
     * @param uid
     * @param visible
     */
    void updateFabulous(@Param("wid") String wid, @Param("uid") String uid,@Param("visible") int visible);

    /**
     * 插入点赞的方法
     * @param fid
     * @param wid
     * @param uid
     * @param visible
     */
    void insertFabulous(@Param("fid") String fid,@Param("wid") String wid,@Param("uid") String uid,@Param("visible") int visible);

    /**
     * 保存微博评论的方法
     * @param comm
     */
    void saveComments(Comments comm);
}
