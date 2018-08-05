package com.heo.homework.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不正确"),
    NAME_NOT_EMPTY(2, "请填写姓名"),
    NID_NOT_EMPTY(3, "请填写学号"),
    STUDENT_NOT_ENPTY(4,"学生不存在"),
    TEACHER_NOT_ENPTY(5,"教师不存在"),
    CLASS_NOT_ENPTY(6,"班级不存在"),
    HOMEWORK_NOT_ENPTY(7,"作业不存在"),
    REQUSET_EXCEPTION(-2,"请求异常"),
    NO_AUTH(-3,"无权限操作"),
    INVALID_CODE(40029,"code无效")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
