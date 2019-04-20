package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-17 19:28
 * @desc 帖子评论
 **/
@Entity
@Data
public class RePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer postId;

    private Integer reId;

    private String userId;

    private Integer userType;

    private String content;

    private String image;

    private Date createTime;

    private Date updateTime;

    public RePost(){}

    public RePost(Integer postId, Integer reId, String userId, Integer userType, String content, String image) {
        this.postId = postId;
        if (reId>0){
            this.reId = reId;
        }
        this.userId = userId;
        this.userType = userType;
        this.content = content;
        this.image = image;
    }
}
