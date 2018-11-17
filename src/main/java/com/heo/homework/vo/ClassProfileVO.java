package com.heo.homework.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClassProfileVO {

    private String teacherName;

    @JsonProperty("avatarUrl")
    private String classAvatarUrl;

    /** 科目 */
    private String classSubject;

    private String className;

    private String classId;

    private Integer status;//0 未加入 1已加入

    private Integer joinNumber; //加入人数

    private Boolean isNeedPwd;//0 需要密码 1 不需要密码

}
