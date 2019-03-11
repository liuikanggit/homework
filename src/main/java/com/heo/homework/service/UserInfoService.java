package com.heo.homework.service;

import com.heo.homework.vo.ResultVO;

/**
 * @author 刘康
 * @create 2019-03-11 15:01
 * @desc
 **/
public interface UserInfoService {

    ResultVO like(String userId,String likedUserId);

}
