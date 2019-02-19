package com.heo.homework.service;

import com.heo.homework.form.ClassForm;
import com.heo.homework.vo.ResultVO;

public interface ClassService {

    /** 通过班级id 得到班级信息 */
    ResultVO getClassInfo(String classId);

    /** 获取班级map */
    ResultVO getClassMap();
}
