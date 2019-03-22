package com.heo.homework.aspect;

import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.repository.StudentRepository;
import com.heo.homework.repository.TeacherRepository;
import com.heo.homework.service.RedisService;
import com.sun.javafx.binding.StringFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RedisService redisService;

    /**
     * 验证学生
     */
    @Before("execution(public * com.heo.homework.controller.StudentController.*(..))")
    public void verifyStudent() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        tokenVerify();
        authVerifyStudent(request);
        collectionFormId(request);
    }

    private void authVerifyStudent(HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");
        if (!studentRepository.existsById(userId)) {
            throw new MyException(ResultEnum.NO_AUTH);
        }
    }

    /**
     * 验证教师
     */
    @Before("execution(public * com.heo.homework.controller.TeacherController.*(..))")
    public void verifyTeacher() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        tokenVerify();
        authVerifyTeacher(request);
        collectionFormId(request);
    }

    private void authVerifyTeacher(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (!teacherRepository.existsById(userId)) {
            throw new MyException(ResultEnum.NO_AUTH);
        }
    }

    /** 验证图片 */
    @Before("execution(public * com.heo.homework.controller.UploadImageController.uploadImage(..))")
    public void verify(){
        tokenVerify();
    }

    /**
     * 验证token
     */
    private void tokenVerify() {

        /** 验证token是否有效 */
        if (!redisService.verify()) {
            throw new MyException(ResultEnum.LOGIN_INVALID);
        }
    }

    /**
     * 采集用户的formId
     *
     * @param request
     */
    private void collectionFormId(HttpServletRequest request) {
        String[] formIdList = request.getParameterValues("formId");
        if (!Objects.isNull(formIdList) && formIdList.length > 0 && !formIdList[0].equals("[]")) {
            String userId = (String) request.getAttribute("userId");
            String key = StringFormatter.format("formId_%s", userId).toString();
            log.info("formId:{} length:{}", formIdList, formIdList.length);
            for (String formId : formIdList) {
                redisService.saveFormId(userId, formId);
            }
        }
    }

}
