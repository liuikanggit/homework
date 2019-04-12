package com.heo.homework.service;

import com.heo.homework.form.HomeworkForm;
import com.heo.homework.vo.ResultVO;

import java.util.Date;

public interface HomeworkService {

    /** 修改作业 */
    ResultVO modifyHomework(HomeworkForm homeworkForm);

    /** 布置作业 */
    ResultVO assignmentHomework(String teacherId, String[] classId, String[] image, Date date, String desc);

    ResultVO getCreateHomeworkEnd(String classId, int page, int size);

    ResultVO getCreateHomeworkNotEnd(String classId, int page, int size);

    ResultVO getHomeworkDetail(String homeworkId);

    ResultVO getHomeworkImage(String homeworkId, String userId);

    /** 批改作业 */
    ResultVO correctionHomeworkImage(String homeworkId,String userId,Integer score,String comment,String[] images);
}
