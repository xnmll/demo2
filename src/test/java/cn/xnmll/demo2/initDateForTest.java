package cn.xnmll.demo2;

import cn.xnmll.demo2.entity.DisCussPost;
import cn.xnmll.demo2.service.DiscussPostService;
import javafx.geometry.Pos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author xnmll
 * @create 2021-09-2021/9/9  15:26
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo2Application.class)
public class initDateForTest {

    @Autowired
    private DiscussPostService discussPostService;

    @Test
    public void initDate() {
        for (int i = 0; i < 300000; i ++ ) {
            DisCussPost disCussPost = new DisCussPost();
            disCussPost.setUserId(111);
            disCussPost.setTitle("test_9_9");
            disCussPost.setContent("this is for test");
            disCussPost.setCreateTime(new Date());
            disCussPost.setScore(Math.random() * 2000);
            discussPostService.addDiscuessPost(disCussPost);
        }
    }

    @Test
    public void testCache() {

        System.out.println(discussPostService.findDiscussPosts(0,0,10,1));
        System.out.println(discussPostService.findDiscussPosts(0,0,10,1));
        System.out.println(discussPostService.findDiscussPosts(0,0,10,1));
        System.out.println(discussPostService.findDiscussPosts(0,0,10,0));
    }


}
