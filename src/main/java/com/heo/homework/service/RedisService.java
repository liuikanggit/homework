package com.heo.homework.service;

/**
 * @author 刘康
 * @create 2019-01-31 15:51
 * @desc 做登录处理的service
 **/
public interface RedisService {

    /**
     * 保存登录状态
     * @param userId
     * @return
     */
    String login(String userId);

    /**
     * 删除登录状态
     */
    void logout();

    /**
     * 验证是否登录
     * @return
     */
    boolean verify();

    /**
     * 保存formId
     * @param userId
     * @param formId
     */
    void saveFormId(String userId,String formId);

    /**
     * 获取formId
     * @param userId
     * @return
     */
    String getFormId(String userId);

    /**
     * 获取accessToken
     * @return
     */
    String getAccessToken();


}
