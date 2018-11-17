package com.heo.homework.entity;

import com.heo.homework.constant.DelStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class QuestionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String questionId;

    private String imageUrl;

    private Integer del = DelStatus.NOT_DEL;

    private Date createTime;

    private Date updateTime;

}
