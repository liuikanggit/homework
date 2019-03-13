package com.heo.homework.service;

import com.heo.homework.form.*;
import com.heo.homework.vo.ResultVO;

import java.util.List;

public interface StudentService {

    /** 学生登录 */
    ResultVO login(String code, String[] formId, String nickName, String avatarUrl, String gender);

    /** 查看学生资料 */
    ResultVO getStudentInfo(String studentId);

    /** 修改学生资料 */
    ResultVO modifyStudentInfo(String studentId,UserInfoForm studentInfoForm);

    /** 所有班级 */
    ResultVO searchClass(String userId,String classId);

    /** 加入班级 */
    ResultVO joinClass(String userId,String classId,String password);

    /** 学生查询所有作业信息 */
    ResultVO getHomework(String studentId,int start,int size);

    /** 学生查询作业详情 */
    ResultVO getHomeworkDetail(String studentId,String homeworkId);

    /** 提交作业 */
    ResultVO submitHomework(String studentId, String homeworkId, List<String> imageList);

    /** 获取学生加入所有班级信息 */
    ResultVO getAllClassInfo(String studentId,int page,int size);

    /** 获取学生 条件分页 */
    ResultVO getStudent(int page, int size, String name, String sex, String phone,String classId);

    ResultVO addStudent(UserInfoForm userInfoForm);


}
