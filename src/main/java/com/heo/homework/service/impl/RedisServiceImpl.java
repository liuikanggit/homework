package com.heo.homework.service.impl;

import com.heo.homework.constant.RedisConstant;
import com.heo.homework.service.RedisService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘康
 * @create 2019-01-31 15:52
 * @desc 登录service的实现
 **/
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String login(String userId) {
        /** 判断用户的token是否已经存在了 */
        String token = redisTemplate.opsForValue().get(userId);
        if (token != null){
            /** 表明用户之前已经登录过一次了.清除之前的登录状态 */
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, token));
        }
        /** 生成token  */
        token = UUID.randomUUID().toString();

        // 保存token
        saveToken(userId,token);
        return token;
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        String userId = (String) request.getAttribute("userId");
        String token = request.getHeader(RedisConstant.AUTHORIZATION);
        /** 移除token */
        redisTemplate.opsForValue().getOperations().delete(userId);
        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, token));
    }


    /**
     * 验证token是否有效 并且 刷新token有效期
     * 把userId放到request中
     * @return
     */
    @Override
    public boolean verify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        /** 从请求头中获取token */
        String token = request.getHeader(RedisConstant.AUTHORIZATION);
        /** 使用token 获取userId */
        String userId = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, token));
        if(Strings.isEmpty(userId)){
            return false;
        }
        /** 把userId放到request中 */
        request.setAttribute("userId",userId);

        // 刷新token时间有效期
        saveToken(userId,token);
        return true;
    }

    private void saveToken(String userId, String token){
        /** 把用户id  作为key，token作为value */
        redisTemplate.opsForValue().set(userId,token,RedisConstant.EXPIRE, TimeUnit.SECONDS);

        /** 再把token_{token}作为key，用户id 作为value */
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token),userId,RedisConstant.EXPIRE,TimeUnit.SECONDS);
    }

}
