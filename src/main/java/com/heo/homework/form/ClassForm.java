package com.heo.homework.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Data
public class ClassForm extends LoginIdForm{

    private String classId;

    /** 加入密码 */
    @Max(value = 13,message = "密码太长了")
    private String password = "";

    /** 年级 */
    @NotEmpty(message = "年级不能为空")
    private String grade;

    /** 科目 */
    @NotEmpty(message = "科目不能为空")
    private String classSubject;

    /**  班级头像 */
    private String avatarUrl;

    /** 班级描述 */
    private String desc;
}
