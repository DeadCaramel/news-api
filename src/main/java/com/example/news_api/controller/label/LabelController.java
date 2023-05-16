package com.example.news_api.controller.label;

import com.example.news_api.entity.Label;
import com.example.news_api.entity.User;
import com.example.news_api.entity.Vo.LabelVo;
import com.example.news_api.repository.LabelRepository;
import com.example.news_api.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get_list")
    public String getList(@RequestParam(required = false) String name,@RequestParam(required = true) String user_id,@RequestParam(required = false,defaultValue = "") String type) throws Exception{
        JsonObject resultDate = new JsonObject();
        Label label = new Label();
        List<Label> list=null;
        List<LabelVo> newlist=new ArrayList<>();
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

        if(!StringUtils.isEmpty(user_id)) {
            User user = new User();
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("_id",ExampleMatcher.GenericPropertyMatchers.exact())
                    .withIgnoreCase(false);
            user.set_id(user_id);
            Example<User> example = Example.of(user,matcher);
            user=userRepository.findOne(example).get();
            String[] label_ids= user.getLabel_ids();

            if(!ArrayUtils.isEmpty(label_ids)){
                for (Label temp : list) {
                    LabelVo labelVo=new LabelVo();
                    BeanUtils.copyProperties(temp, labelVo);
                    if (ArrayUtils.contains(label_ids , temp.get_id())) {
                        labelVo.setCurrent(true);
                    }else{
                        labelVo.setCurrent(false);
                    }
                    newlist.add(labelVo);
                }
            }
        }
        if(!type.equals("all")){
            resultDate.add("data",new Gson().toJsonTree(newlist));
        }else{
            resultDate.add("data",new Gson().toJsonTree(list));
        }


//        resultDate.add("code",new Gson().toJsonTree(200));
//        resultDate.add("msg",new Gson().toJsonTree("success"));
        return resultDate.toString();
    }
}
