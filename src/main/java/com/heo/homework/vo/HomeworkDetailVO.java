package com.heo.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heo.homework.utils.DateSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HomeworkDetailVO {

    /** 作业id */
    @JsonProperty("id")
    private String homeworkId;

    /** 班级id  */
    private String classId;

    /** 班级名称 */
    private String className;

    /** 班级科目 */
    private String subject;

    /** 作业说明 */
    @JsonProperty("description")
    private String homeworkDesc;

    /** 学生id */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String studentId;

    /** 学生姓名 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String studentName;

    /** 教师id 方便查看教师信息 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String teacherId;

    /** 教师姓名 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String teacherName;

    /** 作业状态 0：未提交  1：提交待审核 2：通过 3：没通过 */
    private Integer status;

    private Integer submitNum;

    /** 作业提交图片 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> homeworkImageUrls;

    /** 发布时间 */
    @JsonSerialize(using = DateSerializer.class)
    private Date beginTime;

    /** 提交时间 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = DateSerializer.class)
    private Date submitTime;

    /** 截至时间 */
    @JsonSerialize(using = DateSerializer.class)
    private Date endTime;
}
