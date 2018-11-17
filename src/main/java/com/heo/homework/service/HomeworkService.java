package com.heo.homework.service;

import com.heo.homework.form.HomeworkForm;
import com.heo.homework.vo.ResultVO;

public interface HomeworkService {

    /** 布置作业 */
    ResultVO assignmentHomework(HomeworkForm homeworkForm);

    /** 修改作业 */
    ResultVO modifyHomework(HomeworkForm homeworkForm);
}
