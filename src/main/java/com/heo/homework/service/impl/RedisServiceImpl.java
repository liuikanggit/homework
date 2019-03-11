package com.heo.homework.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heo.homework.config.WechatAccountConfig;
import com.heo.homework.constant.RedisConstant;
import com.heo.homework.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘康
 * @create 2019-01-31 15:52
 * @desc 登录service的实现
 **/
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Override
    public String login(String userId) {
        /** 判断用户的token是否已经存在了 */
        String token = redisTemplate.opsForValue().get(userId);
        if (token != null) {
            /** 表明用户之前已经登录过一次了.清除之前的登录状态 */
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, token));
        }
        /** 生成token  */
        token = UUID.randomUUID().toString();

        // 保存token
        saveToken(userId, token);
        return token;
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userId = (String) request.getAttribute("userId");
        String token = request.getHeader(RedisConstant.AUTHORIZATION);
        /** 移除token */
        redisTemplate.opsForValue().getOperations().delete(userId);
        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, token));
    }


    /**
     * 验证token是否有效 并且 刷新token有效期
     * 把userId放到request中
     *
     * @return
     */
    @Override
    public boolean verify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        /** 从请求头中获取token */
        String token = request.getHeader(RedisConstant.AUTHORIZATION);
        /** 使用token 获取userId */
        String userId = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, token));
        if (Strings.isEmpty(userId)) {
            return false;
        }
        /** 把userId放到request中 */
        request.setAttribute("userId", userId);

        // 刷新token时间有效期
        saveToken(userId, token);
        return true;
    }

    @Override
    public void saveFormId(String userId, String formId) {
        try {
            /** 把formId_userId为key， fromId为value */
            redisTemplate.opsForList().leftPush(String.format(RedisConstant.FORM_ID_PREFIX, userId), formId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFormId(String userId) {
        return redisTemplate.opsForList().rightPop(String.format(RedisConstant.FORM_ID_PREFIX, userId));
    }

    private static final String EXPIRES_IN = "expires_in";
    private static final String ERR_CODE = "errcode";
    private static final String ERR_MSG = "errmsg";

    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {
    }.getType();

    @Override
    public String getAccessToken() {
        String accessToken = redisTemplate.opsForValue().get(RedisConstant.ACCESS_TOKEN);

        if (Objects.isNull(accessToken) || Strings.isEmpty(accessToken)) {
            RestTemplate restTemplate = new RestTemplate();
            final String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
                    wechatAccountConfig.getAppId() +
                    "&secret=" +
                    wechatAccountConfig.getSecret();
            String response = restTemplate.getForObject(url, String.class);
            Map<String, String> result = new Gson().fromJson(response, MAP_TYPE);
            if (result.get(ERR_CODE) != null) {
                log.error("获取accessToken失败,err_code:{},err_msg:{}", result.get(ERR_CODE), result.get(ERR_MSG));
            } else {
                accessToken = result.get(RedisConstant.ACCESS_TOKEN);
                int expiresIn = Integer.valueOf(result.get(EXPIRES_IN)) - 60; //默认是7200秒 2小时
                /** 缓存到redis */
                redisTemplate.opsForValue().set(RedisConstant.ACCESS_TOKEN, accessToken, expiresIn, TimeUnit.SECONDS);
            }
        }
        return accessToken;
    }

    /**
     * 保存token
     *
     * @param userId
     * @param token
     */
    private void saveToken(String userId, String token) {
        /** 把用户id 作为key，token作为value */
        redisTemplate.opsForValue().set(userId, token, RedisConstant.EXPIRE, TimeUnit.SECONDS);

        /** 再把token_{token}作为key，用户id 作为value */
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), userId, RedisConstant.EXPIRE, TimeUnit.SECONDS);
    }


}
