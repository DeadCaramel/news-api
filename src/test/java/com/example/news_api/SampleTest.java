package com.example.news_api;

import com.example.news_api.entity.User;
//import com.example.news_api.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class SampleTest {
    @Autowired
//    private UserMapper userMapper;

    @Test
    public void testSelect() {
//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList=userMapper.selectList(null);
//        userList.forEach(System.out::println);
    }
}
