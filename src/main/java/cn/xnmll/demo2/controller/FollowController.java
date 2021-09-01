package cn.xnmll.demo2.controller;

import cn.xnmll.demo2.entity.User;
import cn.xnmll.demo2.service.FollowService;
import cn.xnmll.demo2.util.HostHolder;
import cn.xnmll.demo2.util.demo2Util;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xnmll
 * @create 2021-08-2021/8/31  21:46
 */

@Controller
public class FollowController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private FollowService followService;

    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        followService.follow(user.getId(),entityType,entityId);
        return demo2Util.getJSONString(0,"已关注");
    }

    @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    public String unfollow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        followService.unfollow(user.getId(),entityType,entityId);
        return demo2Util.getJSONString(0,"已取消关注");
    }

}
