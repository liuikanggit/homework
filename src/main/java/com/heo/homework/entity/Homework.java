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

    private String classId;

    private String homeworkDesc;

    /** 截至时间 */
    private Date endTime;

    /** 发布时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    public Homework(HomeworkForm homeworkForm) {
        homeworkId = KeyUtil.genUniqueKey();
        classId = homeworkForm.getClassId();
        homeworkDesc = homeworkForm.getDesc();
        endTime = homeworkForm.getEndTime();
        createTime = new Date();
    }
}
