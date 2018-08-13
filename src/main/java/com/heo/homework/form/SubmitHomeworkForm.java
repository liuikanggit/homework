package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SubmitHomeworkForm extends HomeworkIdForm{

    @NotEmpty(message = "请求错误")
    private String homeworkImageUrls;
}
