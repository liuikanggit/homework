package com.heo.homework.service;

import com.heo.homework.form.*;
import com.heo.homework.vo.ResultVO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public interface StudentService {

    /** 学生登录 */
    ResultVO login(String code);

    /** 查看学生资料 */
    ResultVO getStudentInfo(String studentId);

    /** 修改学生资料 */
    ResultVO modifyStudentInfo(UserInfoForm studentInfoForm);

    /** 加入班级 */
    ResultVO joinClass(ClassIdForm classIdForm);

    /** 查询学生所有作业信息 */
    ResultVO getHomework(String studentId);

    /** 提交作业 */
    ResultVO submitHomework(SubmitHomeworkForm submitHomeworkForm);
}
