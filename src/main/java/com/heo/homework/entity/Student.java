package com.heo.homework.entity;

import com.heo.homework.form.UserInfoForm;
import com.heo.homework.utils.KeyUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Student {

    @Id
    private String studentId;

    private String openid;

    private String studentNid = "";

    private String studentName = "";

    private String studentAvatarUrl = "";

    private String studentPhone = "";

    private String sex = "未填写";

    private Date createTime;

    private Date updateTime;

    public Student(){}

    public Student(String openid){
        this.openid = openid;
        this.studentId = KeyUtil.genUniqueKey();
    }

    public void setStudentInfo(UserInfoForm studentInfoForm){
        this.sex = studentInfoForm.getSex();
        this.studentNid = studentInfoForm.getNid();
        this.studentName = studentInfoForm.getName();
        this.studentPhone = studentInfoForm.getPhone();
        this.studentAvatarUrl = studentInfoForm.getAvatarUrl();
    }

}
