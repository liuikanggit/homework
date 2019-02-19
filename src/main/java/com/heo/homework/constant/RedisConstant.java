package com.heo.homework.constant;

/**
 * redis常量
 * Created by 廖师兄
 * 2017-07-30 16:22
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    Integer EXPIRE = 1800; //30分钟

    // header中保存token的字段
    String AUTHORIZATION = "Authorization";
}
