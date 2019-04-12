package com.heo.homework.entity;

import com.heo.homework.constant.DelStatus;
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

    private String homeworkId;

    private Integer number = 1;

    private String imageUrl;

    private Integer del = DelStatus.NOT_DEL;

    private Integer correction;

    private Date createTime;

    private Date updateTime;
}
