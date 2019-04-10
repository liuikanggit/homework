package com.heo.homework.vo;

import com.heo.homework.utils.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * @author 刘康
 * @create 2019-04-06 23:04
 * @desc 教师端作业详情
 **/
@Data
public class HomeworkDetailVO {

    private String id;

    private String startDate;

    private String endDate;

    private String desc;

    private String img;

    /**
     * 提交率
     */
    private String submitBl;

    /**
     * 批改率
     */
    private String correctBl;

    /**
     * 正确率
     */
    private int rightBl;

    private List<UserSimpleVO> unCorrectStudent;

    private List<UserSimpleVO> correctStudent;

    public HomeworkDetailVO(String id,Date startDate, Date endTime, String desc, String img,long unSubmitNum,long submitNum,long correctNum,long rightScore) {
        this.id = id;
        this.startDate = DateUtil.formatter(startDate,"MM月dd日");
        this.endDate = DateUtil.formatter(endTime, "yyyy/MM/dd hh:mm");
        this.desc = desc;
        this.img = img;
        long total = unSubmitNum+submitNum;
        submitBl = submitNum+"/"+total;
        correctBl = correctNum+"/"+total;
        rightBl = (int) (rightScore*100/(total*100));
    }

}
