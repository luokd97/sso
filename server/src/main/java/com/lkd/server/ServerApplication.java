package com.lkd.server;

import com.lkd.server.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@SpringBootApplication
@ServletComponentScan
public class ServerApplication {
    @ResponseBody
    @RequestMapping("/")
    public String hello(HttpServletRequest request) {
        return "已登录，用户名：" + ((User) request.getSession().getAttribute("user")).getUsername();
    }

    @ResponseBody
    @RequestMapping("/info")
    public String info(HttpServletRequest request) {
        String isLogin = request.getSession().getAttribute("isLogin").toString();
        String token = request.getSession().getAttribute("token").toString();

        //sso服务端直接登录，需处理sessionId属性NullPointerException
        Object appSessionId = null;
        appSessionId = request.getSession().getAttribute("sessionId");
        if (appSessionId == null) {
            appSessionId = "无来源app的sessionId信息";
        }

        String str = "session info:"
                + "&isLogin=" + isLogin
                + "&token=" + token
                + "&appSessionId=" + appSessionId;
        return str;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
