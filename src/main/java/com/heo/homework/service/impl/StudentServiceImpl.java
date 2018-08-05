package com.heo.homework.service.impl;

import com.heo.homework.entity.Student;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.repository.StudentRepository;
import com.heo.homework.service.StudentService;
import com.heo.homework.service.WechatLoginService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import com.heo.homework.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private WechatLoginService wechatLoginService;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ResultVO login(String code) {

        /** 用code换取openid */
        String openid = wechatLoginService.auth(code);

        /** 判断是否第一次登录 */
        Student student = studentRepository.findByOpenid(openid);
        if( student == null){
            /** 第一次登录 创建一个学生 */
            student = new Student(openid);
            studentRepository.save(student);
        }
        /** 把学生id返回 */
        return ResultVOUtil.success(student.getStudentId());
    }

    /**
     * 获取学生资料
     * @param studentId
     * @return
     */
    @Override
    public ResultVO getStudentInfo(String studentId) {

        Student student = studentRepository.findByStudentId(studentId);
        if (Objects.isNull(student)){
            throw new MyException(ResultEnum.STUDENT_NOT_ENPTY);
        }
        UserInfoVO userInfoVO = new UserInfoVO(student);
        if (userInfoVO.isBlank())
            return ResultVOUtil.success();
        return ResultVOUtil.success(userInfoVO);
    }


    @Override
    public ResultVO modifyStudentInfo(UserInfoForm studentInfoForm) {

        Student student = studentRepository.findByStudentId(studentInfoForm.getId());
        if (Objects.isNull(student)){
            throw new MyException(ResultEnum.STUDENT_NOT_ENPTY);
        }

        /** 更新学生资料 */
        student.setStudentInfo(studentInfoForm);
        student = studentRepository.save(student);

        /** 返回最新的资料 */
        UserInfoVO userInfoVO = new UserInfoVO(student);
        return ResultVOUtil.success(userInfoVO);
    }
}
