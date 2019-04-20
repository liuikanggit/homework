package com.heo.homework.service;

import com.heo.homework.vo.ResultVO;

public interface ClassService {

    /** 通过班级id 得到班级信息 */
    ResultVO getClassInfo(String classId,boolean isTeacher);

    /** 教师查看自己创建的所有班级 */
    ResultVO getClassInfo(String teacherId);

    /** 获取班级map */
    ResultVO getClassMap();

    ResultVO getClassUserInfo(String classId);
}
