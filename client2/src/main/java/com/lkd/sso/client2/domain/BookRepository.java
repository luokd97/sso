package com.lkd.sso.client2.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {
    List<Book> findByTitle(String title);
    List<Book> findByIdAndTitle(Long id,String Title);
}
