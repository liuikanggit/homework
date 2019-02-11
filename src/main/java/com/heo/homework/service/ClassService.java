package com.heo.homework.service;

import com.heo.homework.form.ClassForm;
import com.heo.homework.form.ClassIdForm;
import com.heo.homework.vo.ResultVO;

public interface ClassService {

    /** 创建班级 */
    ResultVO createClass(ClassForm classForm);

    /** 修改班级信息 */
    ResultVO modifyClass(ClassForm classForm);

    /** 通过班级id 得到班级信息 */
    ResultVO getClassInfo(ClassIdForm classIdForm);

    /** 获取班级map */
    ResultVO getClassMap();
}
