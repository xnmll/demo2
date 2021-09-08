package cn.xnmll.demo2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xnmll
 * @create 2021-09-2021/9/8  14:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo2Application.class)
public class QuartzTests {

    @Autowired
    private Scheduler scheduler;

    @Test
    public void d1() {
        try {
            boolean b = scheduler.deleteJob(new JobKey("alphaJob", "alphaJobGroup"));
            System.out.println(b);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
