package com.heo.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class ClassVO {

    /** 班级id 6位唯一  */
    private String classId;

    /** 加入密码 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String classPassword ;

    /** 年级 */
    private String grade;

    /** 科目 */
    private String classSubject;

    /**  班级头像 */
    private String classAvatarUrl;

    /**  班级描述 */
    private String classDesc;

    /** 创建日期时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

}
