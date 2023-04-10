package com.example.news_api.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Article {

    @Id
    private String _id;

    private String[] author;

    private Long browse_count;
    private Long collection_count;

    private String content;
    private String[] cover;
    private String create_time;
    private String mode;
    private String title;
    private Long thumbs_up_count;
    private String classify;
}
