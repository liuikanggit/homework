package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Admin {

    @Id
    @GeneratedValue
    private Integer id;

    private String userName;

    private String password;

}
