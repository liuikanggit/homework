package com.heo.homework.service;

import com.heo.homework.form.ClassForm;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.vo.ResultVO;

public interface TeacherService {

    /** 教师登录 */
    public ResultVO login(String code);

    /** 获取教师信息 */
    public ResultVO getTeacherInfo(String teacherId);

    /** 修改教师信息 */
    public ResultVO modifyTeacherInfo(UserInfoForm teacherInfoForm);

}