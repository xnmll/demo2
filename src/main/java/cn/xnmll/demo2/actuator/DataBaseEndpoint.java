package cn.xnmll.demo2.actuator;

import cn.xnmll.demo2.util.demo2Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author xnmll
 * @create 2021-09-2021/9/9  17:06
 */

@Component
@Endpoint(id = "database")
public class DataBaseEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseEndpoint.class);

    @Autowired
    private DataSource dataSource;

    @ReadOperation
    public String checkConnection() {
        try (
                Connection connection = dataSource.getConnection();
        ) {
            return demo2Util.getJSONString(0,"获取连接成功");
        } catch (Exception e) {
            LOGGER.error("连接失败");
            e.printStackTrace();
            return demo2Util.getJSONString(1,"失败");
        }
    }

}
