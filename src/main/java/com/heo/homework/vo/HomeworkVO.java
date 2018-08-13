package com.heo.homework.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HomeworkVO {

    /** 作业id */
    private String homeworkId;

    /** 班级id  */
    private String classId;

    /** 班级名称 */
    private String className;

    /** 班级科目 */
    private String subject;

    /** 作业说明 */
    private String homeworkDesc;

    /** 教师姓名 */
    private String teacherName;

    /** 教师id 方便查看教师信息 */
    private String teacherId;

    /** 作业状态 0：未提交  1：待审核 2：通过 3：没通过 */
    private Integer status;

    /** 作业提交图片 */
    private List<String> homeworkImageUrls;

    /** 发布时间 */
    private Date beginTime;

    /** 截至时间 */
    private Date endTime;

}
