package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
public class HomeworkForm extends ClassIdForm{

    private String homeworkId;

    @NotEmpty(message = "作业描述不能为空")
    private String desc;

    @NotEmpty(message = "截至日期不能为空")
    private Date endTime;

    @NotEmpty(message = "表单id不能为空")
    private String formId;

}
