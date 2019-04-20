package com.heo.homework.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-12 23:09
 * @desc 作业详情（学生）
 **/
@Data
public class HomeworkDetailSVO {

    /** 基础值 */
    private String desc;
    private List<String> images;
    private Integer state;//(0,1,2)
    private String createTime;
    private String endTime;
    private boolean end;
    private String subject;

    /** 提交后有的值 */
    private String submitTime;
    private List<String> submitImages;

    /** 批改后的值 */
    private String checkTime;
    private Integer score;
    private String comment;
    private List<String> correctionImages;

}
