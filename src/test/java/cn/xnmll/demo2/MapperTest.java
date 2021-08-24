package cn.xnmll.demo2;

import cn.xnmll.demo2.dao.DiscussPostMapper;
import cn.xnmll.demo2.dao.UserMapper;
import cn.xnmll.demo2.entity.DisCussPost;
import cn.xnmll.demo2.entity.User;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author xnmll
 * @create 2021-08-2021/8/20  16:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo2Application.class)
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectUser() {
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("demo2");
        user.setPassword("123123");
        user.setSalt("a");
        user.setEmail("lcy@qq.com");
        user.setHeaderUrl("http://demo2");
        user.setCreateTime(new Date());
        int row = userMapper.insertUser(user);
        System.out.println(row);
        System.out.println(user.getId());
    }

    @Test
    public void testSelectPosts(){
        List<DisCussPost> disCussPosts = discussPostMapper.selectDiscussPosts(0, 0, 10);
//        for (DisCussPost a : disCussPosts) {
//            System.out.println(a);
//        }
        int rows = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(rows);
    }
}
