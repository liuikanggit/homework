package com.heo.homework.service;

public interface WechatLoginService {

    /** 微信认证 */
    public String auth(String code);
}
