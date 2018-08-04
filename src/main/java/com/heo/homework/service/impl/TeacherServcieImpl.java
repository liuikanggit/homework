package com.heo.homework.service.impl;

import com.heo.homework.entity.Teacher;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.form.ClassForm;
import com.heo.homework.form.TeacherInfoForm;
import com.heo.homework.repository.ClassRepository;
import com.heo.homework.repository.TeacherRepository;
import com.heo.homework.service.TeacherService;
import com.heo.homework.service.WechatLoginService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import com.heo.homework.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TeacherServcieImpl implements TeacherService{

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private WechatLoginService wechatLoginService;

    @Override
    public ResultVO login(String code) {

        String openid = wechatLoginService.auth(code);

        /** 判断是否第一次登录 */
        Teacher teacher = teacherRepository.findByOpenid(openid);
        if( teacher == null){
            /** 第一次登录 创建一个教师 */
            teacher = new Teacher(openid);
            teacherRepository.save(teacher);
        }
        /** 把教师id返回 */
        return ResultVOUtil.success(teacher.getTeacherId());
    }

    /**
     * 获取教师信息
     * @param teacherId
     * @return
     */
    @Override
    public ResultVO getTeacherInfo(String teacherId) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId);
        if (Objects.isNull(teacher)){
            throw new MyException(ResultEnum.TEACHER_NOT_ENPTY);
        }
        UserInfoVO userInfoVO = new UserInfoVO(teacher);
        return ResultVOUtil.success(userInfoVO);
    }

    /**
     * 修改教师资料
     * @param teacherInfoForm
     * @return
     */
    @Override
    public ResultVO modifyTeacherInfo(TeacherInfoForm teacherInfoForm) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherInfoForm.getId());
        if (Objects.isNull(teacher)){
            throw new MyException(ResultEnum.TEACHER_NOT_ENPTY);
        }

        /** 更新教师资料 */
        teacher.setTeacherInfo(teacherInfoForm);
        teacher = teacherRepository.save(teacher);

        /** 返回最新的资料 */
        UserInfoVO userInfoVO = new UserInfoVO(teacher);
        return ResultVOUtil.success(userInfoVO);
    }

    @Override
    public ResultVO createClass(ClassForm classForm) {
        return null;
    }
}
