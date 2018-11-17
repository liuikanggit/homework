package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "homework2question")
@Data
public class Homework2Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String homeworkId;

    private String questionId;

    public Homework2Question(){}

    public Homework2Question(String homeworkId,String questionId){
        this.homeworkId = homeworkId;
        this.questionId = questionId;
    }
}
