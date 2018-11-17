package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class QuestionDetail {

    @Id
    private String id;

    private String questionId;

    private String homeworkDetailId;

    private String studentId;

    private Integer result;

    private String comment;

    private Date createTime;

    private Date updateTime;

}
