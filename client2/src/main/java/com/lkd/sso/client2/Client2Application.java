package com.lkd.sso.client2;

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
public class Client2Application {
//    @ResponseBody
//    @RequestMapping("/")
//    public String hello(){
//        return "hello, there is client2";
//    }

    @RequestMapping("/")
    public String book(){
        return "book_management";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest req){
        System.out.println(req.getSession().getId());
        return "index";
    }
    public static void main(String[] args) {
        SpringApplication.run(Client2Application.class, args);
    }

}
