package cn.xnmll.demo2.util;

import org.springframework.http.HttpRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xnmll
 * @create 2021-08-2021/8/26  12:59
 */

public class CookieUtil {
    public static String getValue(HttpServletRequest httpRequest, String name) {
        if (httpRequest == null || name == null) {
            throw new IllegalArgumentException("参数为空");
        }
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
