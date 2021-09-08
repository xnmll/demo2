package cn.xnmll.demo2;

import cn.xnmll.demo2.dao.DiscussPostMapper;
import cn.xnmll.demo2.dao.LoginTicketMapper;
import cn.xnmll.demo2.dao.MessageMapper;
import cn.xnmll.demo2.dao.UserMapper;
import cn.xnmll.demo2.entity.DisCussPost;
import cn.xnmll.demo2.entity.LoginTicket;
import cn.xnmll.demo2.entity.Message;
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

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MessageMapper messageMapper;

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

//    @Test
//    public void testSelectPosts(){
//        List<DisCussPost> disCussPosts = discussPostMapper.selectDiscussPosts(0, 0, 10);
////        for (DisCussPost a : disCussPosts) {
////            System.out.println(a);
////        }
//        int rows = discussPostMapper.selectDiscussPostRows(0);
//        System.out.println(rows);
//    }

    @Test
    public void testIn() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);

        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testS() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abc",1);

        System.out.println(loginTicketMapper.selectByTicket("abc"));

    }

    @Test
    public void testSelect() {
//        List<Message> messages = messageMapper.selectConversations(111, 0, 20);
//        for (var c : messages) {
//            System.out.println(c);
//        }
        System.out.println(messageMapper.selectConversationCount(111));
        System.out.println("-------------------");
        List<Message> list = messageMapper.selectLetters("111_112", 0, 10);
        for (var i : list)
            System.out.println(i);
        System.out.println("-------------------");
        System.out.println(messageMapper.selectLetterCount("111_112"));
        System.out.println("-------------------");

    }



}
