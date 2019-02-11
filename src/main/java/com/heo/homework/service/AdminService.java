package com.heo.homework.service;

import com.heo.homework.vo.ResultVO;

public interface AdminService {

    /**
     * 管理员登录
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    ResultVO login(String userName, String password);

    ResultVO logout(String token);

    ResultVO verify(String token);



}
