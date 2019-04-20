package com.heo.homework.service;

/**
 * 微信注册成功模板
 */
public interface WechatMessageService {

    /**
     * 发送注册成功模板
     * @param userId 用户id，用来取formId
     * @param openid openid,用来推送信息
     * @param name 用户名称
     * @param userType 用户类型
     * @return
     */
    boolean sendRegisterNotice(String userId, String openid, String name, String userType);

    boolean sendHomeworkNotice(String userId,String homeworkId,String subjectName,String teacherName,String content,String beginTime,String endTime);
}
