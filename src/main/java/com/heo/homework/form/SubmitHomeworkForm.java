package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SubmitHomeworkForm extends HomeworkIdForm{

    @NotEmpty(message = "请求错误")
    private List<String> image;
}
