package cn.xnmll.demo2;

import cn.xnmll.demo2.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xnmll
 * @create 2021-09-2021/9/8  12:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo2Application.class)
public class ThreadPoolTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolTests.class);

    //JDK 普通 线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    //JDK 可执行定时任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    // Spring普通的线程池
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    // Spring执行定时任务的线程池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private AlphaService alphaService;

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // JDK 普通 线程池
    @Test
    public void test1() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("hello ExecutorService");
            }
        };
        for (int i = 0; i < 10; i ++ )
                executorService.submit(task);

        sleep(10000);
    }

    //JDK 可执行定时任务的线程池
    @Test
    public void test2() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("hello ScheduledExecutorService");
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(task, 10000,1000, TimeUnit.MILLISECONDS);
        sleep(20000);

    }

    // Spring普通线程池
    @Test
    public void test3() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("hello threadPoolTaskExecutor");
            }
        };
        for (int i = 0; i < 10; i ++ )
            threadPoolTaskExecutor.submit(task);
        sleep(10000);
    }

    // Spring定时任务线程池
    @Test
    public void test4() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("hello threadPoolTaskScheduler");
            }

        };
        Date start = new Date(System.currentTimeMillis() + 10000);
        threadPoolTaskScheduler.scheduleAtFixedRate(task, start, 1000);

        sleep(20000);
    }

    //
    @Test
    public void test5() {
        for (int i = 0 ;i < 10; i ++ )
            alphaService.e1();
        sleep(20000);
    }


}
