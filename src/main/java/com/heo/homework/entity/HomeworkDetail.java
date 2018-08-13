package com.heo.homework.entity;

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

    /** 作业状态 0：未提交  1：待审核 2：通过 3：没通过 */
    private Integer homeworkStatus = 0;

    /**  作业提交时间 */
    private Date submitTime;

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
