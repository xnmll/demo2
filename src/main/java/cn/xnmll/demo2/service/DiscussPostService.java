package cn.xnmll.demo2.service;

import cn.xnmll.demo2.dao.DiscussPostMapper;
import cn.xnmll.demo2.entity.DisCussPost;
import cn.xnmll.demo2.util.SenstiveFilter;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xnmll
 * @create 2021-08-2021/8/20  23:09
 */

@Service
public class DiscussPostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisCussPost.class);

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SenstiveFilter senstiveFilter;

    @Value("${caffeine.posts.max-size}")
    private int maxSize;

    @Value("${caffeine.posts.expire-seconds}")
    private int expireSeconds;

    // caffeine 核心接口 Cache AsyncLoadingCache LoadingCache

    // 帖子列表缓存
    private LoadingCache<String, List<DisCussPost>> postListCache;

    // 帖子总数缓存
    private LoadingCache<Integer, Integer> postRowCache;

    @PostConstruct
    public void init() {

        //初始化帖子列表缓存
        postListCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<DisCussPost>>() {
                    @Override
                    public List<DisCussPost> load(String key) throws Exception {

                        if (key == null || key.length() == 0) {
                            throw new IllegalArgumentException("参数错误");
                        }

                        String[]  params = key.split(":");
                        if (params == null || params.length != 2) {
                            throw new IllegalArgumentException("参数错误");
                        }

                        int offset = Integer.valueOf(params[0]);
                        int limit = Integer.valueOf(params[1]);

                        //加二级缓存

                        LOGGER.debug("load post rows from DB ..");
                        return discussPostMapper.selectDiscussPosts(0,offset,limit,1);
                    }
                });

        //初始化帖子总数缓存
        postRowCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, Integer>() {
                    @Override
                    public Integer load(Integer key) throws Exception {
                        LOGGER.debug("load post rows from DB ..");
                        return discussPostMapper.selectDiscussPostRows(key);
                    }
                });

    }

    public List<DisCussPost> findDiscussPosts(int userId, int offset, int limit, int orderMode) {
//        if (userId == 0 && orderMode == 1) {
//            return postListCache.get(offset + ":" + limit);
//        }
        LOGGER.debug("load post list from DB ..");
        return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
    }

    public int findDiscussPostRows(int userId) {
//        if (userId == 0) {
//            return postRowCache.get(userId);
//        }
        LOGGER.debug("load post rows from DB ..");
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

    public int updateCommentCount(int id, int commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }

    public int updateType(int id, int type) {
        return discussPostMapper.updateType(id, type);
    }

    public int updateStatus(int id, int status) {
        return discussPostMapper.updateStatus(id, status);
    }

    public int updateScore(int id, double score) {
        return discussPostMapper.updateScore(id, score);
    }


}
