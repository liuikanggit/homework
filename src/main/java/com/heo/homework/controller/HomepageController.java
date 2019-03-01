package com.heo.homework.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 刘康
 * @create 2019-02-28 20:55
 * @desc 首页
 **/
@RestController
public class HomepageController {

    @GetMapping("/")
    public String homepage(){
        return "<h1 align='center'>小程序服务端</h1><span>" + new Date().toString()+"</span>";

    }
}
