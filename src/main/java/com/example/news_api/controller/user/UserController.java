package com.example.news_api.controller.user;

import com.example.news_api.entity.User;
import com.example.news_api.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get_list")
    public String getList(@RequestParam(required = false) String name,@RequestParam(required = true) String user_id) throws Exception{
        JsonObject resultDate = new JsonObject();
        User user = new User();
        List<User> list=null;
        if(StringUtils.isEmpty(name)){
            list=userRepository.findAll();
        }else{
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.DEFAULT)
                    .withIgnoreCase(true);
            user.setAuthor_name(name);
            Example<User> example = Example.of(user,matcher);
//            Example<Label> example = Example.of(label);
            list=userRepository.findAll(example);
        }
        resultDate.add("data",new Gson().toJsonTree(list));
//        resultDate.add("code",new Gson().toJsonTree(200));
//        resultDate.add("msg",new Gson().toJsonTree("success"));
        return resultDate.toString();
    }

    @GetMapping("/get_user")
    public String getUser(@RequestParam String user_id) throws Exception{
        JsonObject resultDate = new JsonObject();
        User user = new User();
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("_id",ExampleMatcher.GenericPropertyMatchers.exact())
                    .withIgnoreCase(false);
            user.set_id(user_id);
            Example<User> example = Example.of(user,matcher);
            user=userRepository.findOne(example).get();
        resultDate.add("data",new Gson().toJsonTree(user));
//        resultDate.add("code",new Gson().toJsonTree(200));
//        resultDate.add("msg",new Gson().toJsonTree("success"));
        return resultDate.toString();
    }

    @GetMapping("/update_like")
    public String getUpdateLike(@RequestParam String user_id,@RequestParam String article_id){
        JsonObject resultDate = new JsonObject();
        User user = new User();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("_id",ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnoreCase(false);
        user.set_id(user_id);
        Example<User> example = Example.of(user,matcher);
        user=userRepository.findOne(example).get();
        if(ArrayUtils.contains(user.getArticle_likes_ids(),article_id)){
            user.setArticle_likes_ids(ArrayUtils.removeElement(user.getArticle_likes_ids(),article_id));
        }else{
            user.setArticle_likes_ids(ArrayUtils.add(user.getArticle_likes_ids(),article_id));
        }
        user=userRepository.save(user);
        resultDate.add("data",new Gson().toJsonTree(user));
        return resultDate.toString();
    }
}
