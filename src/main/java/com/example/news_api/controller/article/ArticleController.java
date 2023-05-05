package com.example.news_api.controller.article;

import Vo.ArticleVo;
import com.example.news_api.entity.Article;
import com.example.news_api.entity.User;
import com.example.news_api.repository.ArticleRepository;
import com.example.news_api.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/get_list")
    public String getList(@RequestParam(required = false) String classify,@RequestParam(required = true) String user_id,@RequestParam(required = false,defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "5") Integer pageSize){
        JsonObject resultDate = new JsonObject();
        Article article = new Article();
        List<Article> list=null;
        List<Article> newlist=new ArrayList<>();
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
        if(!StringUtils.isEmpty(user_id)) {
            User user = new User();
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("_id",ExampleMatcher.GenericPropertyMatchers.exact())
                    .withIgnoreCase(false);
            user.set_id(user_id);
            Example<User> example = Example.of(user,matcher);
            user=userRepository.findOne(example).get();
            String[] article_likes_ids= user.getArticle_likes_ids();

            if(!ArrayUtils.isEmpty(article_likes_ids)){
                for (Article temp : list) {
                    ArticleVo articleVo = new ArticleVo();
                    BeanUtils.copyProperties(temp, articleVo);
                    if (ArrayUtils.contains(article_likes_ids , temp.getId())) {
                        articleVo.set_like(true);
                    }else{
                        articleVo.set_like(false);
                    }
                    newlist.add(articleVo);
                }
            }
        }
        if(newlist.size()>0){
            resultDate.add("data",new Gson().toJsonTree(newlist));
        }else {
            resultDate.add("data",new Gson().toJsonTree(list));
        }

//        resultDate.add("code",new Gson().toJsonTree(200));
//        resultDate.add("msg",new Gson().toJsonTree("success"));
        return resultDate.toString();
    }

    @GetMapping("/get_search")
    public String getSearch(@RequestParam(required = false) String title,@RequestParam(required = true) String user_id){
        JsonObject resultDate = new JsonObject();
        Article article = new Article();
        List<Article> list=null;
        List<Article> newlist=new ArrayList<>();
        ExampleMatcher matcher = ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase(true);
        article.setTitle(title);
        Example<Article> example = Example.of(article,matcher);
        list = articleRepository.findAll(example);
        if(!StringUtils.isEmpty(user_id)) {
            User user = new User();
             matcher = ExampleMatcher.matching()
                    .withMatcher("_id",ExampleMatcher.GenericPropertyMatchers.exact())
                    .withIgnoreCase(false);
            user.set_id(user_id);
            Example<User> userExample = Example.of(user,matcher);
            user=userRepository.findOne(userExample).get();
            String[] article_likes_ids= user.getArticle_likes_ids();

            if(!ArrayUtils.isEmpty(article_likes_ids)){
                for (Article temp : list) {
                    ArticleVo articleVo = new ArticleVo();
                    BeanUtils.copyProperties(temp, articleVo);
                    if (ArrayUtils.contains(article_likes_ids , temp.getId())) {
                        articleVo.set_like(true);
                    }else{
                        articleVo.set_like(false);
                    }
                    newlist.add(articleVo);
                }
            }
        }
        if(newlist.size()>0){
            resultDate.add("data",new Gson().toJsonTree(newlist));
        }else {
            resultDate.add("data",new Gson().toJsonTree(list));
        }

//        resultDate.add("code",new Gson().toJsonTree(200));
//        resultDate.add("msg",new Gson().toJsonTree("success"));
        return resultDate.toString();
    }


}
