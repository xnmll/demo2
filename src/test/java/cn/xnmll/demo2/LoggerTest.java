package cn.xnmll.demo2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xnmll
 * @create 2021-08-2021/8/23  19:16
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo2Application.class)
public class LoggerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void testLogger() {
        System.out.println(LOGGER.getName());

        LOGGER.debug("debug");
        LOGGER.info("info");
        LOGGER.warn("warn");
        LOGGER.error("error");


    }


}
