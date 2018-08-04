package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TeacherInfoForm extends LoginIdForm{

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotEmpty(message = "手机号不能空")
    private String phone;

    @NotEmpty(message = "头像不能为空")
    private String avatarUrl;
}
