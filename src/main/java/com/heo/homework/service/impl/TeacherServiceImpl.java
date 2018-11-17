package com.heo.homework.service.impl;

import com.heo.homework.config.TemplateIDConfig;
import com.heo.homework.dto.MessageParam;
import com.heo.homework.entity.Homework;
import com.heo.homework.entity.Teacher;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.form.ClassForm;
import com.heo.homework.form.HomeworkForm;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.repository.HomeworkRepository;
import com.heo.homework.repository.TeacherRepository;
import com.heo.homework.service.TeacherService;
import com.heo.homework.service.WechatLoginService;
import com.heo.homework.service.WechatMessageService;
import com.heo.homework.utils.DateUtil;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import com.heo.homework.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

@Service
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private WechatLoginService wechatLoginService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private TemplateIDConfig templateIDConfig;

    @Override
    public ResultVO login(String code,String formId) {

        String openid = wechatLoginService.auth(code);

        /** 判断是否第一次登录 */
        Teacher teacher = teacherRepository.findByOpenid(openid);
        if( teacher == null){
            /** 第一次登录 创建一个教师 */
            teacher = new Teacher(openid);
            teacher = teacherRepository.save(teacher);

            /** 推送信息 */
            MessageParam messageParam = new MessageParam(teacher.getTeacherId(),openid,templateIDConfig.getRegisterNotice(),templateIDConfig.getRegisterPath(),formId)
                    .addData("注册成功")
                    .addData(teacher.getTeacherName())
                    .addData("教师")
                    .addData("请尽快完善个人资料")
                    .addData(DateUtil.formatter(teacher.getCreateTime()));
            wechatMessageService.sendMessage(messageParam);
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
            throw new MyException(ResultEnum.TEACHER_EMPTY);
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
    public ResultVO modifyTeacherInfo(UserInfoForm teacherInfoForm) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherInfoForm.getId());
        if (Objects.isNull(teacher)){
            throw new MyException(ResultEnum.TEACHER_EMPTY);
        }

        /** 更新教师资料 */
        teacher.setTeacherInfo(teacherInfoForm);
        teacher = teacherRepository.save(teacher);

        /** 返回最新的资料 */
        UserInfoVO userInfoVO = new UserInfoVO(teacher);
        return ResultVOUtil.success(userInfoVO);
    }


}
