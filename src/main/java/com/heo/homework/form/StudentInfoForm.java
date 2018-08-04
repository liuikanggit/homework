package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class StudentInfoForm extends LoginIdForm {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotEmpty(message = "学号不能为空")
    private String nid;

    @NotEmpty(message = "手机号不能空，方便老师联系你")
    private String phone;

    @NotEmpty(message = "头像不能为空")
    private String avatarUrl;


}
