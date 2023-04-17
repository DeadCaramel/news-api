package com.example.news_api.controller.article;

import com.example.news_api.entity.Article;
import com.example.news_api.repository.ArticleRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @GetMapping("/get_list")
    public String getList(@RequestParam(required = false) String classify,@RequestParam(required = false,defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "5") Integer pageSize){
        JsonObject resultDate = new JsonObject();
        Article article = new Article();
        List<Article> list=null;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable=PageRequest.of(page-1,pageSize,sort);
        Page<Article> pageList=null;
        if(Strings.isEmpty(classify)){
            list=articleRepository.findAll(pageable).getContent();
        }else {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.DEFAULT)
                    .withIgnoreCase(true);
            article.setClassify(classify);
            Example<Article> example = Example.of(article,matcher);
            list = articleRepository.findAll(example,pageable).getContent();
        }
        resultDate.add("data",new Gson().toJsonTree(list));
//        resultDate.add("code",new Gson().toJsonTree(200));
//        resultDate.add("msg",new Gson().toJsonTree("success"));
        return resultDate.toString();
    }
}
