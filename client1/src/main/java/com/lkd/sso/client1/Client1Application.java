package com.lkd.sso.client1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SpringBootApplication
@ServletComponentScan
public class Client1Application {

//    @ResponseBody
//    @RequestMapping("/")
//    public String hello(){
//        return "hello, there is client1";
//    }

    @RequestMapping("//")
    public String index(HttpServletRequest req){
        System.out.println(req.getSession().getId());
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(Client1Application.class, args);
    }

}
