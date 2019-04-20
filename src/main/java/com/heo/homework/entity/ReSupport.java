package com.heo.homework.entity;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-17 19:30
 * @desc
 **/
@Entity
@Data
public class ReSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer reId;

    private String userId;

    private Integer userType;

    private Date createTime;

    private Date updateTime;

    public ReSupport(){
    }

    public ReSupport(Integer reId,String userId,Integer userType){
        this.reId = reId;
        this.userId = userId;
        this.userType = userType;
    }

}
