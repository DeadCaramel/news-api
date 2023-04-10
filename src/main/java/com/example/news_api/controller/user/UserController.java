package com.example.news_api.controller.user;

import com.example.news_api.entity.User;
import com.example.news_api.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get_list")
    public String getList(@RequestParam(required = false) String name) throws Exception{
        JsonObject resultDate = new JsonObject();
        User user = new User();
        List<User> list=null;
        if(Strings.isEmpty(name)){
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
}
