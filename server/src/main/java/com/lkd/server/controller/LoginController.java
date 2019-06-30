package com.lkd.server.controller;

import com.lkd.server.domain.User;
import com.lkd.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    //绑定"/login"路径到login.html
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //客户端访问sso登录接口，保存调用来源APP的信息，转到登陆页面
    @RequestMapping("/appLogin")
    public String appLogin(HttpServletRequest request, @RequestParam("url") String url, @RequestParam("sessionId") String sessionId){
        request.getSession().setAttribute("url",url);
        request.getSession().setAttribute("sessionId",sessionId);
        return "login";
    }

    @ResponseBody
    @PostMapping("/loginApi")
    public String loginApi(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = null;
        user = userService.findUserByUsername(username);
        if (user==null){
            return "用户名不存在";
        }else if(!user.getPassword().equals(password)){
            return "用户名或密码错误";
        }else {
            userService.login(user,request,response);
            Object url = null;
            url = request.getSession().getAttribute("url");
            if (url==null){
                response.sendRedirect("/info");
                return "";
            }else{
                String token = request.getSession().getAttribute("token").toString();
                return "登陆成功<br><a href="+url.toString()+"?token="+token+">点击这里回去</a>";
            }
        }
    }

    @ResponseBody
    @RequestMapping("/tokenCheck")
    public String tokenCheck(@RequestParam("clientToken") String token){
        if (userService.tokenCheck(token)){
            return "true";
        }
        return "false";
    }

    @ResponseBody
    @RequestMapping("/keyCheck")
    public String keyCheck(@RequestParam("clientKey") String token){
        return "true";
    }
}
