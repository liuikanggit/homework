package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginIdForm {

//    @NotEmpty(message = "请求异常")
    private String id;

}
