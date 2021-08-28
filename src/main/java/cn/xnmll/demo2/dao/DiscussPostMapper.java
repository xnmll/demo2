package cn.xnmll.demo2.dao;

import cn.xnmll.demo2.entity.DisCussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xnmll
 * @create 2021-08-2021/8/20  22:55
 */

@Mapper
public interface DiscussPostMapper {

    List<DisCussPost> selectDiscussPosts(int userId, int offset, int limit);

    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DisCussPost disCussPost);

    DisCussPost selectDiscussPostById(int id);

    int updateCommentCount(int id,int commentCount);

}
