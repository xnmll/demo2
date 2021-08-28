package cn.xnmll.demo2.dao;

import cn.xnmll.demo2.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xnmll
 * @create 2021-08-2021/8/28  14:31
 */


@Mapper
public interface CommentMapper {
    List<Comment> selectCommentByEntity(int entityType,int entityId,int offset, int limit);

    int selectCountByEntity(int entityType,int entityId);
}
