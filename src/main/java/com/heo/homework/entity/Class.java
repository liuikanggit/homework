package com.heo.homework.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Class {

    /** 班级id 6位唯一  */
    @Id
    private String classId;

    /** 创建教师id */
    private String teacherId;

    /** 加入密码 */
    private String classPassword = "";

    /** 年级 */
    private String grade;

    /** 科目 */
    private String classSubject;

    /**  班级头像 */
    private String classAvatarUrl;

    /**  班级描述 */
    private String classDesc = "";

    /** 发布时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}
