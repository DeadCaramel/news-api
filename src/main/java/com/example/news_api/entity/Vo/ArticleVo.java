package com.example.news_api.entity.Vo;

import com.example.news_api.entity.Article;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ArticleVo extends Article {


    private boolean is_like;
}
