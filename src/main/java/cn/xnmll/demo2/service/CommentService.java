package cn.xnmll.demo2.service;

import cn.xnmll.demo2.dao.CommentMapper;
import cn.xnmll.demo2.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xnmll
 * @create 2021-08-2021/8/28  14:41
 */

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> findCommentsByEntity(int entityType,int entityId,int offset,int limit){
        return commentMapper.selectCommentByEntity(entityType,entityId,offset,limit);
    }

    public int findCommentCount(int entityType,int entityId) {
        return commentMapper.selectCountByEntity(entityType,entityId);
    }

}
