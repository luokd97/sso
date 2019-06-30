package com.lkd.sso.client1.controller;

import com.lkd.sso.client1.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("article")
    @ResponseBody
    public String article(String title, String content) {
        try {
//            log.info("##################"+articleId+"###################"+content);
            articleService.postArticle(title, content);
        } catch (Exception e) {
            log.error("{}", e);
            return "error: " + e.getMessage();
        }
        return "success";
    }

    @RequestMapping("createArticle")
    public String createArticle(){
        return "create_article";
    }
}
