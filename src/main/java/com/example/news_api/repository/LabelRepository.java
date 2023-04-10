package com.example.news_api.repository;

import com.example.news_api.entity.Label;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends MongoRepository<Label,String> {
}
