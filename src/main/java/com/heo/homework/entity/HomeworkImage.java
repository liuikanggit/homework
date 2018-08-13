package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class HomeworkImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String homeworkDetailId;

    private String imageUrl;

    private Integer del = 0;

    private Date createTime;

    private Date updateTime;
}
