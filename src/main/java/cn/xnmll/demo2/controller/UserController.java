package cn.xnmll.demo2.controller;

import cn.xnmll.demo2.annotation.LoginRequired;
import cn.xnmll.demo2.entity.User;
import cn.xnmll.demo2.service.FollowService;
import cn.xnmll.demo2.service.LikeService;
import cn.xnmll.demo2.service.UserService;
import cn.xnmll.demo2.util.HostHolder;
import cn.xnmll.demo2.util.demo2Constant;
import cn.xnmll.demo2.util.demo2Util;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.catalina.Host;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author xnmll
 * @create 2021-08-2021/8/26  15:25
 */

@Controller
@RequestMapping("/user")
public class UserController implements demo2Constant {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${demo2.path.upload}")
    private String uploadPath;

    @Value("${demo2.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @Value("${qiniu.key.access}")
    private String accessKey;

    @Value("${qiniu.key.secret}")
    private String secretKey;

    @Value("${qiniu.bucket.header.name}")
    private String headerBucketName;

    @Value("${qiniu.bucket.header.url}")
    private String headerBucketUrl;


    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage(Model model) {
        // ??????????????????
        String fileName = demo2Util.generateUUID();
        // ??????????????????
        StringMap policy = new StringMap();
        policy.put("returnBody", demo2Util.getJSONString(0));
        // ??????????????????
        Auth auth = Auth.create(accessKey, secretKey);
        String uploadToken = auth.uploadToken(headerBucketName, fileName, 3600, policy);

        model.addAttribute("uploadToken", uploadToken);
        model.addAttribute("fileName", fileName);


        return "/site/setting";
    }


    //??????????????????
    @RequestMapping(path = "/header/url", method = RequestMethod.POST)
    @ResponseBody
    public String updateHeaderUrl(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return demo2Util.getJSONString(1, "?????????????????????!");
        }

        String url = headerBucketUrl + "/" + fileName;
        userService.updateHeader(hostHolder.getUser().getId(), url);

        return demo2Util.getJSONString(0);
    }


    // ??????????????????????????????pc?????????????????????????????????????????????
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "????????????????????????");
            return "/site/setting";
        }
        String originalFilename = headerImage.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "????????????????????????");
            return "/site/setting";
        }

        String fileName = demo2Util.generateUUID() + suffix;

        File dest = new File(uploadPath + "/" + fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            LOGGER.error("????????????" + e.getMessage());
            throw new RuntimeException("??????" + e);
        }

        //??????????????????????????????
        User user = hostHolder.getUser();
        String headUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headUrl);

        return "redirect:/index";

    }

    // ????????????????????????????????????
    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        //?????????????????????
        fileName = uploadPath + "/" + fileName;

        String suffix = fileName.substring(fileName.lastIndexOf("."));

        response.setContentType("image/" + suffix);

        try (
                ServletOutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(fileName);
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (Exception e) {
            LOGGER.error("??????????????????" + e.getMessage());
        }
    }

    //????????????
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User userById = userService.findUserById(userId);
        if (userById == null) {
            throw new RuntimeException("??????????????????");
        }
        //??????
        model.addAttribute("user", userById);
        //????????????
        int userLikeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount",userLikeCount);
        //????????????
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount",followeeCount);
        //????????????
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount",followerCount);
        //????????????
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(),ENTITY_TYPE_USER,userId);
        }
        model.addAttribute("hasFollowed",hasFollowed);

        return "/site/profile";
    }

}
