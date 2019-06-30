package com.lkd.server.service;

import com.lkd.server.domain.User;
import com.lkd.server.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private List<String> tokens = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void login(User user, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        String token = UUID.randomUUID().toString();
        tokens.add(token);
        session.setAttribute("token", token);
        session.setAttribute("isLogin","true");

        Cookie cookie = new Cookie("key","1");
        response.addCookie(cookie);
    }

    public boolean tokenCheck(String token) {
        if(tokens.contains(token)){
            return true;
        }
        return false;
    }
}
