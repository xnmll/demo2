package cn.xnmll.demo2.controller.advice;

import cn.xnmll.demo2.util.demo2Util;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xnmll
 * @create 2021-08-2021/8/30  21:08
 */

@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);


    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.error("服务器异常" + e.getMessage());
        for (var c : e.getStackTrace()) {
            LOGGER.error(c.toString());
        }

        String XRequestionedWith = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(XRequestionedWith)) {
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(demo2Util.getJSONString(1, "服务器异常"));

        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }

}
