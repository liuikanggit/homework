package com.heo.homework.entity;

import com.heo.homework.constant.HomeworkStatus;
import com.heo.homework.utils.KeyUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class HomeworkDetail {

    @Id
    private String id;

    private String homeworkId;

    private String studentId;

    private Integer homeworkStatus = HomeworkStatus.NO_LOOK;

    /**  作业提交时间 */
    private Date submitTime;

    /** 提交次数 */
    private Integer submitNumber = 0;

    /** 老师批改作业时间 */
    private Date checkTime;

    /** 分数 */
    private Integer score = -1;

    /** 评语 */
    private String comment = "";

    private Date createTime;

    /** 表更新时间 */
    private Date updateTime;

}
