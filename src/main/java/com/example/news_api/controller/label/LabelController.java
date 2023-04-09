package com.example.news_api.controller.label;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LabelController {

    @GetMapping("/get_label")
    public String getLabel() {
        return "Hello World";
    }
}
