package com.heo.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heo.homework.utils.DateSerializer;
import lombok.Data;

import com.heo.homework.entity.Class;
import org.springframework.beans.BeanUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
public class ClassVO {

    /** 班级id 6位唯一  */
    private String classId;

    /** 加入密码 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    /**班级名称 */
    @JsonProperty("name")
    private String name;

    /** 老师名称 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String teacherName;

    /** 创建教师的id */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String teacherId;

    /** 班级里学生人数 */
    private Integer studentNum;

    /** 科目 */
    private String subject;

    /**  班级头像 */
    private String classAvatarUrl;

    /**  班级描述 */
    private String classDesc;

    /** 创建日期时间 */
    @JsonSerialize(using = DateSerializer.class)
    private Date createTime;

    /** 修改时间 */
    @JsonSerialize(using = DateSerializer.class)
    private Date updateTime;

    public ClassVO(){}

    /**
     * 教师查看自己创建的班级信息
     * @param clazz
     */
    public ClassVO(Class clazz,Integer studentNum){
        this.classId = clazz.getClassId();
        this.password = clazz.getClassPassword();
        this.name = clazz.getClassName();
        this.subject = clazz.getClassSubject();
        this.studentNum = studentNum;
        this.classAvatarUrl = clazz.getClassAvatarUrl();
        this.classDesc = clazz.getClassDesc();
        this.createTime = clazz.getCreateTime();
        this.updateTime = clazz.getUpdateTime();
    }

    public ClassVO(Class mClass,String teacherName,Integer studentNum){
        BeanUtils.copyProperties(mClass,this);
        this.teacherName = teacherName;
        this.studentNum = studentNum;
    }

}
