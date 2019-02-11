package com.heo.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heo.homework.utils.DateSerializer;
import lombok.Data;

import com.heo.homework.entity.Class;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class ClassVO {

    /** 班级id 6位唯一  */
    private String classId;

    /** 加入密码 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String classPassword;

    /**班级名称 */
    private String className;

    /** 老师名称 */
    private String teacherName;

    /** 班级人数 */
    private Integer classNumber;

    /** 年级 */
    private String grade;

    /** 科目 */
    private String classSubject;

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

    public ClassVO(Class mClass,String teacherName,Integer classNumber){
        BeanUtils.copyProperties(mClass,this);
        this.teacherName = teacherName;
        this.classNumber = classNumber;
    }

    public void hidePassword(){
        this.classPassword = null;
    }

}
