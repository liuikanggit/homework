package com.heo.homework.service;

/**
 * 微信注册成功模板
 */
public interface WechatMessageService {

    /**
     * 发送注册成功模板
     * @param userId 用户id，用来取formId
     * @param openid openid,用来推送信息
     * @param name
     * @param userType
     * @return
     */
    boolean sendRegisterNotice(String userId, String openid, String name, String userType);

//    boolean homeworkNotice(String userId);
}
