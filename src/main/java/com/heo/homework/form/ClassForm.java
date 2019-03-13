package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ClassForm{

    /** 加入密码 */
    @Size(max = 13,message = "密码太长了")
    private String password = "";

    @NotEmpty(message = "班级名称不能为空")
    private String name;

    /** 科目 */
    @NotEmpty(message = "科目不能为空")
    private String subject;

    /**  班级头像 */
    @NotEmpty(message = "班级头像不能空")
    private String avatarUrl;

    /** 班级描述 */
    private String desc;
}
