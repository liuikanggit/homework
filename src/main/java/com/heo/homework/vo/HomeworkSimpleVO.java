package com.heo.homework.vo;

import com.heo.homework.utils.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author 刘康
 * @create 2019-03-27 19:14
 * @desc 作业简单数据
 **/
@Data
public class HomeworkSimpleVO {

    /**
     * 作业id
     */
    private String id;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 科目
     */
    private String subject;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 截至日期
     */
    private String endDate;

    /**
     * 是否截至
     */
    private boolean isEnd;

    public HomeworkSimpleVO(String id, String className, String subject, Date startDate, Date endDate, boolean isEnd) {
        this.id = id;
        this.className = className;
        this.subject = subject;
        this.startDate = DateUtil.formatter(startDate, "yyyy-MM-dd hh:mm");
        this.endDate = DateUtil.formatter(endDate, "yyyy-MM-dd hh:mm");
        this.isEnd = isEnd;
    }
}
