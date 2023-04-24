package com.example.news_api.controller.label;

import com.example.news_api.entity.Label;
import com.example.news_api.repository.LabelRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelRepository labelRepository;

    @GetMapping("/get_list")
    public String getList(@RequestParam(required = false) String name,@RequestParam(required = true) String user_id) throws Exception{
        JsonObject resultDate = new JsonObject();
        Label label = new Label();
        List<Label> list=null;
        if(Strings.isEmpty(name)){
            list=labelRepository.findAll();
        }else{
            ExampleMatcher matcher = ExampleMatcher.matching()
                            .withStringMatcher(ExampleMatcher.StringMatcher.DEFAULT)
                                    .withIgnoreCase(true);
            label.setName(name);
            Example<Label> example = Example.of(label,matcher);
//            Example<Label> example = Example.of(label);
            list=labelRepository.findAll(example);
        }

        resultDate.add("data",new Gson().toJsonTree(list));
//        resultDate.add("code",new Gson().toJsonTree(200));
//        resultDate.add("msg",new Gson().toJsonTree("success"));
        return resultDate.toString();
    }
}
