package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author 刘康
 * @create 2019-03-10 18:21
 * @desc 帖子点赞
 **/
@Entity
@Data
public class PostSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer postId;

    private String userId;

    private Integer userType;//0 学生，1 教师

    public PostSupport(Integer postId,String userId,Integer userType){
        this.postId = postId;
        this.userId = userId;
        this.userType = userType;
    }

}
