package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Student {

    @Id
    private String studentId;

    private String studentName;

    private String studentAvatarUrl;

    private String studentPhone;

    private String openid;

    private Date createTime;

    private Date updateTime;

}
