package com.heo.homework.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-01 16:02
 * @desc 班级用户信息
 **/
@Data
public class ClassUserInfoVO {

    private String id;

    private String name;

    private Integer studentNum;

    private Integer endHomeworkNum;

    private Integer goingHomeworkNum;

    private UserSimpleVO teacher;

    private List<UserSimpleVO> students;



}
