package cn.xnmll.demo2.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author xnmll
 * @create 2021-08-2021/8/19  22:30
 */

@Service
public class AlphaService {
    public AlphaService() {
        System.out.println("实例化 service");
    }

    @PostConstruct
    public void init() {
        System.out.println("初始化 service");
    }

    @PreDestroy
    public void destory(){
        System.out.println("销毁 service");
    }
}
