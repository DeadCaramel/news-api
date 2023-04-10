package com.example.news_api.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Label {

    @Id
    private String _id;

    private String name;

    private String[] user;
}
