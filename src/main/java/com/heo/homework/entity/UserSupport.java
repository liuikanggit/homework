package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-03-11 14:21
 * @desc 用户被点赞的表
 **/
@Entity
@Data
public class UserSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userId;

    private String likedUserId;

    private Integer num;

    private Date createTime;


    public UserSupport(String userId,String likedUserId){
        this.userId = userId;
        this.likedUserId = likedUserId;
        this.num = 0;
        this.createTime = new Date();
    }

    public void setNumAdd(){
        this.num += 1;
    }

}
