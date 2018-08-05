package com.heo.homework.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.heo.homework.entity.Student;
import com.heo.homework.entity.Teacher;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
public class UserInfoVO {

    /** 学号 */
    @JsonProperty("nid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nid;

    /** 姓名 */
    @JsonProperty("name")
    private String name;

    /** 手机号 */
    @JsonProperty("phone")
    private String phone;

    /** 头像 */
    @JsonProperty("avatarUrl")
    private String avatarUrl;

    public UserInfoVO(){}

    public UserInfoVO(Student student){
        this.nid = student.getStudentNid();
        this.name = student.getStudentName();
        this.phone = student.getStudentPhone();
        this.avatarUrl = student.getStudentAvatarUrl();
    }

    public UserInfoVO(Teacher teacher){
        this.name = teacher.getTeacherName();
        this.phone = teacher.getTeacherPhone();
        this.avatarUrl = teacher.getTeacherAvatarUrl();
    }

    @JsonIgnore
    public boolean isBlank(){
        return Strings.isBlank(name) && Strings.isBlank(avatarUrl);
    }
}
