package com.heo.homework.constant;

/**
 * redis常量
 * Created by 廖师兄
 * 2017-07-30 16:22
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    Integer EXPIRE = 604800; //7天  7 * 24 * 60 * 60

    // header中保存token的字段
    String AUTHORIZATION = "Authorization";

    String FORM_ID_PREFIX = "formId_%s";


    String ACCESS_TOKEN = "access_token";
}
