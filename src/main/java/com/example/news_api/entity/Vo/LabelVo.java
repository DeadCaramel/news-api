package com.example.news_api.entity.Vo;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class LabelVo {

    @Id
    private String _id;

    private String name;

    private String[] user;

    private boolean current;
}
