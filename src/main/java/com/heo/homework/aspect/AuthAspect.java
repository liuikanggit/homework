package com.heo.homework.aspect;

import com.heo.homework.entity.Teacher;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.repository.StudentRepository;
import com.heo.homework.repository.TeacherRepository;
import com.heo.homework.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Pointcut("execution(public * com.heo.homework.controller.SutdentController.*(..)) || execution(public * com.heo.homework.controller.TeacherController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String id = request.getParameter("id");
        log.info("id:{}",id);
        if (Strings.isEmpty(id) || !(studentRepository.findAllStudentId().contains(id) || teacherRepository.findAllTeacehrId().contains(id))){
            throw new MyException(ResultEnum.NO_AUTH);
        }
    }

}
