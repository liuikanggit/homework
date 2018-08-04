package com.heo.homework.service;

import com.heo.homework.form.ClassForm;
import com.heo.homework.form.TeacherInfoForm;
import com.heo.homework.vo.ResultVO;

public interface TeacherService {

    /** 教师登录 */
    public ResultVO login(String code);

    /** 获取教师信息 */
    public ResultVO getTeacherInfo(String teacherId);

    /** 修改教师信息 */
    public ResultVO modifyTeacherInfo(TeacherInfoForm teacherInfoForm);

    /** 创建班级 */
    public ResultVO createClass(ClassForm classForm);

}