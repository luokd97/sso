package com.lkd.sso.client1.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/")
public class LoginFilter implements Filter {
    @Value("${sso.sso-url}")
    String serverURL;

    @Value("${sso.app-url}")
    String appURL;

    @Value("${sso.tokenCheck-url}")
    String tokenURL;

    @Value("${sso.keyCheck-url}")
    String keyURL;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //判断当前session是不是登录状态
        Object isLogin = req.getSession().getAttribute("isLogin");
        if (isLogin != null && isLogin.toString().equals("true")) {
            //过滤器放行
            chain.doFilter(request, response);
            return;
        }

        //检查cookie中是否保存了身份信息key
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            String key = null;
            for (Cookie c : cookies) {
                if (c.getName().equals("key")) {
                    key = c.getValue();
                }
            }
            if (key != null) {
                //若存在，向sso确认
                if (checkKey(key,keyURL)) {
                    //完成登录
                    login(req);
                    //过滤器放行
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        //判断当前的请求是否是从sso回来的，即判断是否带有token参数，校验token成功则完成登录
        String token = req.getParameter("token");
        if (token != null) {
            //校验token
            if (checkToken(token,tokenURL)) {
                //完成登录
                login(req);
                //过滤器放行
                chain.doFilter(request, response);
                return;
            }
        }

        //非上面三种情况，则需要去sso登录
        res.sendRedirect(serverURL + "?url=" + appURL + "&sessionId=" + req.getSession().getId());

    }

    @Override
    public void destroy() {

    }

    private static boolean checkToken(String token,String tokenURL) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("clientToken", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenURL, request, String.class);
        System.out.println(response.getBody());

        if (response.toString().contains("true")) {
            return true;
        }
        return false;
    }

    private static boolean checkKey(String key,String keyURL) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("clientKey", key);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(keyURL, request, String.class);
        System.out.println(response.getBody());

        if (response.toString().contains("true")) {
            return true;
        }
        return false;
    }

    private static void login(HttpServletRequest req) {
        //校验成功，保存用户信息，当前APP登录完成
        // TODO: 2019/6/30
        req.getSession().setAttribute("isLogin", "true");
    }
}
