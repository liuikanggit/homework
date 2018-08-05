package com.heo.homework.service;

import com.heo.homework.form.ClassForm;
import com.heo.homework.form.ClassIdForm;
import com.heo.homework.vo.ResultVO;

public interface ClassService {

    /** 创建班级 */
    public ResultVO createClass(ClassForm classForm);

    /** 修改班级信息 */
    public ResultVO modifyClass(ClassForm classForm);

    /** 通过班级id 得到班级信息 */
    public ResultVO getClassInfo(ClassIdForm classIdForm);
}
