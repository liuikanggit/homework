package com.heo.homework.constant;

/** 作业状态  */
public interface HomeworkStatus {

    /**
     *  -1：未查看
     */
    Integer NO_LOOK = -1;

    /**
     * 0：待提交
     */
    Integer TO_SUBMIT = 0;

    /**
     * 1：待批改
     */
    Integer TO_CHECK = 1;

    /**
     * 2：通过
     */
    Integer PASS = 2;

}
