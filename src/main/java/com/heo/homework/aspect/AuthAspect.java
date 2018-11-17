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
    private RedisTemplate redisTemplate;

    @Pointcut("execution(public * com.heo.homework.controller.*.*(..))" +
              "&& !execution(public * com.heo.homework.controller.WeChatController.*(..))" +
              "&& !execution(public * com.heo.homework.controller.UploadImageController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String id = request.getParameter("id");

        /** 验证id */
        log.info("id:{}",id);
        if (Strings.isEmpty(id) || !(studentRepository.findAllStudentId().contains(id) || teacherRepository.findAllTeacehrId().contains(id))){
            throw new MyException(ResultEnum.NO_AUTH);
        }

        /** 收集用户formId */
        String[] formIdList = request.getParameterValues("formId");
        if (!Objects.isNull(formIdList) && formIdList.length > 0){
            log.info("formId:{} length:{}",formIdList,formIdList.length);
            for (String formId : formIdList){
                if (Strings.isNotEmpty(formId)){
                    /** 把formId存入以id为key的redis中 */
                    try {
                        redisTemplate.opsForList().leftPush(id,formId);
                    }catch (Exception e){
                    }
                }
            }
        }
    }

}
