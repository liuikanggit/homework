package com.heo.homework.entity;

import com.heo.homework.form.HomeworkForm;
import com.heo.homework.utils.KeyUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import java.util.Date;

@Entity
@Data
public class Homework {

    @Id
    private String homeworkId;

    /**
     * 班级id
     */
    private String classId;

    private String teacherId;

    /**
     * 作业描述
     */
    private String homeworkDesc;

    /**
     * 图片
     */
    private String image;

    /**
     * 截至时间
     */
    private Date endTime;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Homework() {
    }

    public Homework(String classId,String teacherId, String image, Date endTime, String desc) {
        this.teacherId = teacherId;
        this.homeworkId = KeyUtil.genUniqueKey();
        this.classId = classId;
        this.homeworkDesc = desc;
        this.endTime = endTime;
        this.createTime = new Date();
        this.image = image;
    }
}
