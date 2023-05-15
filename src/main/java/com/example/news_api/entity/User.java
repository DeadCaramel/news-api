package com.example.news_api.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
    @Id
    private String _id;
    private String[] article_ids;
    private String[] article_likes_ids;
    private String[] author_likes_ids;

    private String Author_name;

    private String avatar;

    private String explain;
    private Long fans_count;
    private Long follow_count;
    private String gender;
    private String id;
    private Long integral_count;
    private String professioncal;
    private String status;
    private String[] thembs_up_article_ids;

    private String[] label_ids;
}
