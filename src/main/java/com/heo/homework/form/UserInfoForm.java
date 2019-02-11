package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserInfoForm extends LoginIdForm {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    private String nid;

    private String phone;

    private String sex;

    @NotEmpty(message = "头像不能为空")
    private String avatarUrl;

}
