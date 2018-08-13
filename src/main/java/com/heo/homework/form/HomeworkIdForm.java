package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class HomeworkIdForm extends LoginIdForm{

    @NotEmpty(message = "请求错误")
    private String homeworkId;
}
