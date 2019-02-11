package com.heo.homework.service;

import com.heo.homework.form.*;
import com.heo.homework.vo.ResultVO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public interface StudentService {

    /** 学生登录 */
    ResultVO login(String code,String formId);

    /** 查看学生资料 */
    ResultVO getStudentInfo(String studentId);

    /** 修改学生资料 */
    ResultVO modifyStudentInfo(UserInfoForm studentInfoForm);

    /** 所有班级 */
    ResultVO searchClass(ClassIdForm classIdForm);

    /** 加入班级 */
    ResultVO joinClass(ClassIdForm classIdForm,String password);

    /** 学生查询所有作业信息 */
    ResultVO getHomework(String studentId,int start,int size);

    /** 学生查询作业详情 */
    ResultVO getHomeworkDetail(HomeworkIdForm homeworkIdForm);

    /** 提交作业 */
    ResultVO submitHomework(SubmitHomeworkForm submitHomeworkForm);

    /** 获取学生加入所有班级信息 */
    ResultVO getAllClassInfo(String studentId,int page,int size);

    /** 获取学生 条件分页 */
    ResultVO getStudent(int page, int size, String name, String sex, String phone,String classId);

    ResultVO addStudent(UserInfoForm userInfoForm);
}
