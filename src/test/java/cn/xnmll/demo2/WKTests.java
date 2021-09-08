package cn.xnmll.demo2;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author xnmll
 * @create 2021-09-2021/9/8  17:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo2Application.class)
public class WKTests {
    public static void main(String[] args) {
        String cmd = "D:/wkhtmltopdf/bin/wkhtmltoimage/ --quality 75 http://www.nowcoder.com d:/work/data/wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
