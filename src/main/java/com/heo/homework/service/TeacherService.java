package com.heo.homework.service;

import com.heo.homework.form.ClassForm;
import com.heo.homework.form.HomeworkForm;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.vo.ResultVO;

import javax.validation.Valid;

public interface TeacherService {

    /** 教师登录 */
    ResultVO login(String code,String formId);
    ResultVO login(String code, String[] formId, String nickName, String avatarUrl, String gender);

    /** 获取教师信息 */
    ResultVO getTeacherInfo(String teacherId);

    /** 修改教师信息 */
    ResultVO modifyTeacherInfo(String teacherId,UserInfoForm teacherInfoForm);

    /** 创建班级 */
    ResultVO createClass(String teacherId,ClassForm classForm);

    /** 修改班级信息 */
    ResultVO modifyClass(String teacherId,String classId,ClassForm classForm);



}