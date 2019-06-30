package com.lkd.sso.client1.service;

import com.lkd.sso.client1.domain.Article;
import com.lkd.sso.client1.domain.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Slf4j
@Service

public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    public void postArticle(String title, String content) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        articleRepository.save(article);
    }
}
