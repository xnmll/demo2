package cn.xnmll.demo2.service;

import cn.xnmll.demo2.dao.DiscussPostMapper;
import cn.xnmll.demo2.dao.UserMapper;
import cn.xnmll.demo2.entity.DisCussPost;
import cn.xnmll.demo2.entity.User;
import cn.xnmll.demo2.util.demo2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * @author xnmll
 * @create 2021-08-2021/8/19  22:30
 */

@Service
public class AlphaService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public AlphaService() {
        System.out.println("实例化 service");
    }

    @PostConstruct
    public void init() {
        System.out.println("初始化 service");
    }

    @PreDestroy
    public void destory() {
        System.out.println("销毁 service");
    }


    //REQUIRED 支持当前事务（外部事务），如果不存在则创建新事务
    //REQUIRED_NEW 创建一个新事务，并且暂停当前事务（外部事务）
    //NESTED 如果当前存在事务（外部事务），则嵌套在该事务中执行（独立的提交和回滚），否则和REQUIRED一样
    //@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Object save1() {
        //新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(demo2Util.generateUUID().substring(0, 5));
        user.setPassword(demo2Util.md5("123" + user.getSalt()));
        user.setEmail("alpha@qq.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/100t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //新增帖子
        DisCussPost post = new DisCussPost();
        post.setUserId(user.getId());
        post.setTitle("hello");
        post.setContent("new one");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

        Integer.valueOf("abc");

        return "ok";
    }

    public Object save2() {
        transactionTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_READ_UNCOMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        
        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                //新增用户
                User user = new User();
                user.setUsername("bbbbbb");
                user.setSalt(demo2Util.generateUUID().substring(0, 5));
                user.setPassword(demo2Util.md5("123" + user.getSalt()));
                user.setEmail("alpha@qq.com");
                user.setHeaderUrl("http://image.nowcoder.com/head/100t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);

                //新增帖子
                DisCussPost post = new DisCussPost();
                post.setUserId(user.getId());
                post.setTitle("hello");
                post.setContent("new one");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);

                Integer.valueOf("abc");
                return "ok";
            }
        });
        
    }


}
