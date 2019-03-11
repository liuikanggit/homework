package com.heo.homework.service;

import com.heo.homework.vo.ResultVO;

/**
 * @author 刘康
 * @create 2019-03-10 16:41
 * @desc 帖子服务
 **/
public interface PostService {
    ResultVO createPost(String userId, String title, String content, String[] image);

    ResultVO getAllPost(String userId,int page,int size);

    ResultVO likePost(String userId,Integer userType,Integer postId);

    ResultVO unListPost(String userId,Integer postId);
}
