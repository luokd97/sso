package com.lkd.sso.client1.domain;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long commentCount=0L;

    //    @Version
    private Long version=0L;
}