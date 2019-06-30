package com.lkd.sso.client1.domain;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    //    @Query(value = "select * from article where id = ?1 for update",nativeQuery=true)
    @Query(value = "select * from article a where a.id = :id for update", nativeQuery = true)
    Optional<Article> findArticleForUpdate(Long id);

//    // @Query("select a from Article a where a.id = :id")
//    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
//    @Query("select a from article a where a.id= ?1")
//    Optional<Article> findByIdWithPessimisticLock(Long id);


    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Article a where a.id = :id")
    Optional<Article> findArticleWithPessimisticLock(Long id);

    @Lock(value = LockModeType.OPTIMISTIC)
    @Query("select a from Article a where a.id = :id")
    Optional<Article> findArticleWithOptimisticLock(Long id);

    @Modifying
    @Query("update Article a set a.commentCount= :commentCount, a.version=version+1 where a.id= :id and a.version=:version")
    int saveWithOptimistic(@Param("id")Long id, @Param("commentCount")Long commentCount, @Param("version")Long version);
}
