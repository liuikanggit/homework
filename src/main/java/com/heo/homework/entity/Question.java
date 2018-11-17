package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
public class Question {

    @Id
    private String questionId;

    private String questionDesc;

    private Date createTime;

    private Date updateTime;

}
