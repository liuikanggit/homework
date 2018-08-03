package com.heo.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping("hello")
    public String helloWorld(){
        return "hello world";
    }

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code) {
        log.info("进入auth方法。。。");
        log.info("code={}", code);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx07c2f70bc511f031&secret=66c79c9d56271af7c71886812df0d306&js_code=" + code + "&grant_type=authorization_code";
        //oAS8c5JEgJZUP9g0s5-ZgLPDqUcs
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
    }
}
