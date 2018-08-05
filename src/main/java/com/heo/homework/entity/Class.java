package com.heo.homework.entity;

import com.heo.homework.form.ClassForm;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Class {

    /** 班级id 6位唯一  */
    @Id
    private String classId;

    /** 创建教师id */
    private String teacherId;

    /** 班级名称 */
    private String className;

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


    public void setClassInfo(ClassForm classForm){
        this.className = classForm.getName();
        this.grade = classForm.getGrade();
        this.classSubject = classForm.getSubject();
        this.classPassword = classForm.getPassword();
        this.teacherId = classForm.getId();
        this.classDesc = classForm.getDesc();
        this.classAvatarUrl = classForm.getAvatarUrl();
    }

}
