package com.heo.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heo.homework.utils.DateSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class HomeworkVO {

    /** 作业id */
    private String id;

    private String classId;

    /** 班级名称 */
    private String className;

    /** 班级科目 */
    private String subject;

    /** 作业说明 */
    private String desc;

    /** 教师姓名 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String teacherName;

    /** 学生姓名 */
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String studentName;

    /** 作业状态 -1:未查看 0：未提交  1：待审核 2：通过 3：没通过 */
    /** 教师0未截止 1截止 */
    private Integer status;

    private Integer submitNum;

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

    public HomeworkVO(){}

//    public HomeworkVO(String id,String className,String subject,String desc,String status,String submitNum){
//
//    }
}
