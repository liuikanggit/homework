package com.heo.homework.vo;

import lombok.Data;

@Data
public class HomeworkDetailVO {
    /**
     * 作业id
     */
    private String id;

    /**
     * 作业描述
     */
    private String desc;

    /**
     * 作业图片
     */
    private String images;

    /**
     * 作业状态
     */
    private Integer state;

    /**
     * 
     */
}
