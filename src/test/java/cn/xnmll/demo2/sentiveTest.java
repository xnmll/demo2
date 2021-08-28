package cn.xnmll.demo2;

import cn.xnmll.demo2.util.SenstiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xnmll
 * @create 2021-08-2021/8/27  21:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo2Application.class)
public class sentiveTest {
    @Autowired
    private SenstiveFilter senstiveFilter;

    @Test
    public void testSenstiveFilter() {
        String str = "可以赌博，可以嫖娼";
        System.out.println(senstiveFilter.filter(str));
    }

}
