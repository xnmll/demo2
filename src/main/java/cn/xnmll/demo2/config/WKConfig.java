package cn.xnmll.demo2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author xnmll
 * @create 2021-09-2021/9/8  19:08
 */

@Configuration
public class WKConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WKConfig.class);

    @Value("${wk.image.storage}")
    private String wkImageStorage;



    @PostConstruct
    public void init() {
        //创建wk图片目录
        File file = new File(wkImageStorage);
        if (!file.exists()) {
            file.mkdir();
            LOGGER.info("创建wk目录 ：" + wkImageStorage);
        }
    }

}
