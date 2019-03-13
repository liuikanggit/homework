package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
public class HomeworkForm{

    @NotEmpty(message = "作业描述不能为空")
    private String desc;

    @NotEmpty(message = "截至日期不能为空")
    private String endDate;

    private String[] image;

}
