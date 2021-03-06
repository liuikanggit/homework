package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "student2class")
@Data
public class Student2Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String studentId;

    private String classId;

    private Date createTime;

    private Date updateTime;

    public Student2Class(){}

    public Student2Class(String studentId,String classId){
        this.studentId =studentId;
        this.classId = classId;
    }
}
