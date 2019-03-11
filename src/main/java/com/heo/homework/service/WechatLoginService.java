package com.heo.homework.service;

public interface WechatLoginService {

    /** 微信认证 */
    String auth(String code);
}
