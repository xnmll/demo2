package cn.xnmll.demo2.controller;

import cn.xnmll.demo2.entity.DisCussPost;
import cn.xnmll.demo2.entity.User;
import cn.xnmll.demo2.service.DiscussPostService;
import cn.xnmll.demo2.service.UserService;
import cn.xnmll.demo2.util.HostHolder;
import cn.xnmll.demo2.util.demo2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author xnmll
 * @create 2021-08-2021/8/27  22:33
 */

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title,String content){
        User user = hostHolder.getUser();
        if (user == null) {
            return demo2Util.getJSONString(403,"你还没有登陆");
        }
        DisCussPost post = new DisCussPost();
        post.setContent(content);
        post.setTitle(title);
        post.setCreateTime(new Date());
        post.setUserId(user.getId());
        discussPostService.addDiscuessPost(post);

        return demo2Util.getJSONString(0,"发布成功");
    }

    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model) {
        DisCussPost discussPostById = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post",discussPostById);
        User userById = userService.findUserById(discussPostById.getUserId());
        model.addAttribute("user",userById);
        return "/site/discuss-detail";
    }

}
