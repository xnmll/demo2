package cn.xnmll.demo2.service;

import cn.xnmll.demo2.dao.DiscussPostMapper;
import cn.xnmll.demo2.entity.DisCussPost;
import cn.xnmll.demo2.util.SenstiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author xnmll
 * @create 2021-08-2021/8/20  23:09
 */

@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SenstiveFilter senstiveFilter;

    public List<DisCussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    public int addDiscuessPost(DisCussPost post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        //转义
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        post.setTitle(senstiveFilter.filter(post.getTitle()));
        post.setContent(senstiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);

    }

    public DisCussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    public int updateCommentCount(int id,int commentCount) {
        return discussPostMapper.updateCommentCount(id,commentCount);
    }


}
