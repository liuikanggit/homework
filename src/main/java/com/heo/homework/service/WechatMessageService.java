package com.heo.homework.service;

/**
 * 微信注册成功模板
 */
public interface WechatMessageService {

    boolean sendRegisterNotice(String userId, String openid, String name, String userType);
}
