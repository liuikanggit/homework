package com.heo.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heo.homework.entity.Student;
import com.heo.homework.entity.Teacher;
import com.heo.homework.utils.DateSerializer;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class UserInfoVO {

    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String openid;

    /**
     * 学号
     */
    @JsonProperty("nid")
    private String nid;

    /**
     * 姓名
     */
    @JsonProperty("name")
    private String name;

    /**
     * 手机号
     */
    @JsonProperty("phone")
    private String phone;

    /**
     * 头像
     */
    @JsonProperty("avatarUrl")
    private String avatarUrl;

    /**
     * 性别
     */
    private String sex;

    private Integer type;

    private Integer likedNum;

    /**
     * 与之关联的班级信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Map<String, String>> classMap;

    /**
     * 创建日期时间
     */
    @JsonSerialize(using = DateSerializer.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonSerialize(using = DateSerializer.class)
    private Date updateTime;

    private boolean isLike;

    public UserInfoVO() {
    }

    public UserInfoVO(Student student) {
        this.id = student.getStudentId();
        this.nid = student.getStudentNid();
        this.name = student.getStudentName();
        this.phone = student.getStudentPhone();
        this.sex = student.getSex();
        this.avatarUrl = student.getStudentAvatarUrl();
        this.createTime = student.getCreateTime();
        this.updateTime = student.getUpdateTime();
        this.type = 0;
    }

    public UserInfoVO(Student student, List<Map<String, String>> classMap) {
        this.nid = student.getStudentNid();
        this.name = student.getStudentName();
        this.phone = student.getStudentPhone();
        this.sex = student.getSex();
        this.avatarUrl = student.getStudentAvatarUrl();
        this.createTime = student.getCreateTime();
        this.updateTime = student.getUpdateTime();

        // 管理员管理时用的数据
        this.id = student.getStudentId();
        this.openid = student.getOpenid();
        this.classMap = classMap;
    }

    public UserInfoVO(Teacher teacher) {
        this.id = teacher.getTeacherId();
        this.name = teacher.getTeacherName();
        this.phone = teacher.getTeacherPhone();
        this.sex = teacher.getSex();
        this.avatarUrl = teacher.getTeacherAvatarUrl();
        this.createTime = teacher.getCreateTime();
        this.updateTime = teacher.getUpdateTime();
        this.type = 1;
    }

    public static List<UserInfoVO> forStudentList(List<Student> studentList) {
        List<UserInfoVO> userInfoVOS = new ArrayList<>();
        for (Student student : studentList) {
            userInfoVOS.add(new UserInfoVO(student));
        }
        return userInfoVOS;
    }
}
