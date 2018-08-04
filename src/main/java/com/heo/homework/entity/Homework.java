package com.heo.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
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
}
