package com.lkd.sso.client1.service;

import com.lkd.sso.client1.domain.Article;
import com.lkd.sso.client1.domain.ArticleRepository;
import com.lkd.sso.client1.domain.Comment;
import com.lkd.sso.client1.domain.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;
//@Slf4j
@Service

public class CommentService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void postComment(Long articleId, String content) throws InterruptedException {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
//        Thread.sleep(2000);
        if (!articleOptional.isPresent()) {
            throw new RuntimeException("没有对应的文章");
        }

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setContent(content);
        commentRepository.save(comment);

        Article article = articleOptional.get();
//        article.setCommentCount(article.getCommentCount() + 1);
//        articleRepository.saveWithOptimistic(article.getId());
        int count = articleRepository.saveWithOptimistic(article.getId(),article.getCommentCount()+1,article.getVersion());
        if (count==0){
            throw new RuntimeException("提交失败");
        }
//        articleRepository.save(article);
    }
}
