package cn.xnmll.demo2;

import cn.xnmll.demo2.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xnmll
 * @create 2021-08-2021/8/28  14:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo2Application.class)
public class TransactionTest {
    @Autowired
    private AlphaService alphaService;

    @Test
    public void testSave() {
        Object o = alphaService.save1();
        System.out.println(o);
    }

    @Test
    public void testSave2() {
        Object o = alphaService.save2();
        System.out.println(o);
    }

}
