package cn.xnmll.demo2.util;

import cn.xnmll.demo2.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author xnmll
 * @create 2021-08-2021/8/26  13:09
 * 持有用户的信息，用于代替session对象
 */

@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
