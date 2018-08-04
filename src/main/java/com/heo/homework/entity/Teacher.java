package com.heo.homework.entity;

import com.heo.homework.form.TeacherInfoForm;
import com.heo.homework.utils.KeyUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Teacher {

    @Id
    private String teacherId;

    private String openid;

    private String teacherName;

    private String teacherAvatarUrl;

    private String teacherPhone;

    private Date createTime;

    private Date updateTime;

    public Teacher(){}

    public Teacher(String openid){
        this.openid = openid;
        this.teacherId = KeyUtil.genUniqueKey();
    }

    public void setTeacherInfo(TeacherInfoForm teacherInfo){
        this.teacherName = teacherInfo.getName();
        this.teacherPhone = teacherInfo.getPhone();
        this.teacherAvatarUrl = teacherInfo.getAvatarUrl();
    }

}
