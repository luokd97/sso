package com.lkd.server.filter;

import com.lkd.server.domain.User;
import com.lkd.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/","/info"})
public class LoginFilter implements Filter {
    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        HttpSession session = req.getSession();

        //判断当前session是否登录
        System.out.println("1");
        Object isLogin = session.getAttribute("isLogin");
        if(isLogin==null||isLogin.toString().equals("false")){
            //未登录，检查是否存在cookie
            System.out.println("2");
            Cookie[] cookies = req.getCookies();
            if (cookies!=null){
                //有cookie，检查cookie中是否存在key
                System.out.println("3");
                for (Cookie c:cookies){
                    if (c.getName().equals("key")){
                        //判断key的有效性
                        System.out.println("4");
                        User user = getUser(c.getValue());
                        if (user!=null){
                            //key有效，获取用户信息完成登录
                            System.out.println("5");
                            userService.login(user,req,res);
                            res.sendRedirect("/info");
                            return;
                        }
                    }
                }
            }
            //无cookie或cookie无效，重定向至登录页面
            System.out.println("6");
            res.sendRedirect("login");
        }else{
            //已登录，
            //  若用户是直接在sso登录的则filter放行
            System.out.println("7");
            if (session.getAttribute("url")==null){
                chain.doFilter(request,response);
                return;
            }
            //  若用户是从app过来登录的，携带token参数回到原app
            System.out.println("8");
            String url = session.getAttribute("url").toString();
            String token = session.getAttribute("token").toString();
            res.sendRedirect(url+"?token="+token);
        }
        System.out.println("qqq");
    }

    @Override
    public void destroy() {

    }

    private User getUser(String key) {
        // TODO: 2019/7/1
        return userService.findUserByUsername("lkd");
    }
}
