package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "student2class")
@Data
public class Student2Class {

    @Id
    @GeneratedValue
    private Integer id;

    private String studentId;

    private String classId;

    private Date createTime;

    private Date updateTime;

}
