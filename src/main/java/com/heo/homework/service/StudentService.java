package com.heo.homework.service;

import com.heo.homework.form.StudentInfoForm;
import com.heo.homework.vo.ResultVO;

public interface StudentService {

    /** 学生登录 */
    public ResultVO login(String code);

    /** 查看学生资料 */
    public ResultVO getStudentInfo(String studentId);

    /** 修改学生资料 */
    public ResultVO modifyStudentInfo(StudentInfoForm studentInfoForm);
}
