package cn.xnmll.demo2.controller;

import cn.xnmll.demo2.entity.DisCussPost;
import cn.xnmll.demo2.entity.Page;
import cn.xnmll.demo2.entity.User;
import cn.xnmll.demo2.service.DiscussPostService;
import cn.xnmll.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xnmll
 * @create 2021-08-2021/8/20  23:20
 */

@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DisCussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String,Object>> discussPost = new ArrayList<>();
        if(list!=null){
            for (DisCussPost post : list) {
                Map<String,Object> map = new HashMap<>();
                map.put("post",post);
                User userById = userService.findUserById(post.getUserId());
                map.put("user",userById);
                discussPost.add(map);

            }
        }
        model.addAttribute("discussPosts",discussPost);
        return "/index";
    }

}