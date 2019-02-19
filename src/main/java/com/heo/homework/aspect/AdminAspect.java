package com.heo.homework.aspect;

import com.heo.homework.constant.RedisConstant;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class AdminAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // header中保存token的字段
    public static final String AUTHORIZATION = "Authorization";

    // 用户测试的完成token
    private static final String POST_TOKEN = "token_admin";

    @Pointcut("execution(public * com.heo.homework.controller.AdminController.*(..)) &&" +
            " !execution(public * com.heo.homework.controller.AdminController.login(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        // 查询token
        String token = request.getHeader(AUTHORIZATION);
        if(Strings.isEmpty(token)){
            log.warn("【登录校验】token不存在");
            throw new MyException(ResultEnum.LOGIN_INVALID);
        }

        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, token));
        if(Strings.isEmpty(tokenValue) && !POST_TOKEN.equals(token)){
            throw new MyException(ResultEnum.LOGIN_INVALID);
        }

        String adminName = tokenValue;

        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("管理员:{},ip:{}",adminName,request.getRemoteAddr());
        log.info("请求url:{}",request.getRequestURL());
        log.info("请求参数:{}",request.getQueryString());
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

}
