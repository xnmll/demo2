package cn.xnmll.demo2.quartz;

import cn.xnmll.demo2.entity.DisCussPost;
import cn.xnmll.demo2.service.DiscussPostService;
import cn.xnmll.demo2.service.ElasticsearchService;
import cn.xnmll.demo2.service.LikeService;
import cn.xnmll.demo2.util.RedisKeyUtil;
import cn.xnmll.demo2.util.demo2Constant;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xnmll
 * @create 2021-09-2021/9/8  16:09
 */

public class PostScoreRefreshJob implements Job, demo2Constant {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostScoreRefreshJob.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    private static final Date epoch;

    static {
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-09-01 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException("初始化失败", e);
        }
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getPostScoreKey();
        BoundSetOperations operations = redisTemplate.boundSetOps(redisKey);

        if (operations.size() == 0) {
            LOGGER.info("任务取消，没有需要刷新的帖子");
            return;
        }
        LOGGER.info("任务开始，正在刷新帖子分数" + operations.size());

        while (operations.size() > 0) {
            this.refresh((Integer) operations.pop());
        }
        LOGGER.info("任务完成，帖子更新完毕");
    }

    private void refresh(int postId) {
        DisCussPost post = discussPostService.findDiscussPostById(postId);

        if (post == null) {
            LOGGER.info("该贴子不存在：id = " + postId);
            return;
        }

        //是否加精
        boolean wonderful = post.getStatus() == 1;

        //评论数量
        int commentCount = post.getCommentCount();

        //点赞数量
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, postId);

        //计算权重
        double w = (wonderful ? 75 : 0) + commentCount * 10 + likeCount * 2;

        //分数 = w + 距离天数
        double score = Math.log10(Math.max(w, 1)) + (post.getCreateTime().getTime() - epoch.getTime()) / (1000 * 3600 * 24);

        //更新帖子的分数
        discussPostService.updateScore(postId, score);

        //同步搜索数据
        post.setScore((score));
        elasticsearchService.saveDiscussPost(post);

    }


}
