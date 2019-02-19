package com.heo.homework.service;

/**
 * @author 刘康
 * @create 2019-01-31 15:51
 * @desc 做登录处理的service
 **/
public interface RedisService {

    String login(String userId);

    void logout();

    boolean verify();

}
