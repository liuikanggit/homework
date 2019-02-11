package com.heo.homework.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heo.homework.config.WechatAccountConfig;
import com.heo.homework.dto.MessageParam;
import com.heo.homework.exception.MyException;
import com.heo.homework.service.WechatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WechatMessageServiceImpl implements WechatMessageService{

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";

    private static final String ERR_CODE = "errcode";
    private static final String ERR_MSG = "errmsg";

    private static final Type MAP_TYPE = new TypeToken<Map<String,String>>(){}.getType();

    /** 获取access_token */
    private void getAccessToken(){
        RestTemplate restTemplate = new RestTemplate();
        final String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+
                wechatAccountConfig.getAppId()+
                "&secret="+
                wechatAccountConfig.getSecret();
        String response = restTemplate.getForObject(url,String.class);
        Map<String,String> result = new Gson().fromJson(response,MAP_TYPE);
        if (result.get(ERR_CODE)!=null){
            throw new MyException(Integer.valueOf(result.get(ERR_CODE)) , result.get(ERR_MSG));
        }else{
            String accessToken = result.get(ACCESS_TOKEN);
            int expiresIn = Integer.valueOf(result.get(EXPIRES_IN)) - 60; //默认是7200秒 2小时
            /** 缓存到redis */
            redisTemplate.opsForValue().set(ACCESS_TOKEN,accessToken,expiresIn, TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean sendMessage(MessageParam messageParam) {
        String accessToken = (String) redisTemplate.opsForValue().get(ACCESS_TOKEN);
        if (Objects.isNull(accessToken) || Strings.isEmpty(accessToken)){
            log.info("access_token过期重新获取");
            getAccessToken();
        }
        String formId = messageParam.getFormId();
        if(Strings.isEmpty(formId)){
            formId = (String) redisTemplate.opsForList().rightPop(messageParam.getId());
        }

        if (Objects.isNull(formId)){
            log.error("信息推送失败，formId以及用完,推送结束");
            return false;
        }else if (Strings.isEmpty(formId)){
            log.error("信息推送失败，formId无效,换formId继续");
            sendMessage(messageParam);
        }else {
            messageParam.setFormId(formId);
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken;
            String response = restTemplate.postForObject(url, messageParam.toJson(), String.class);
            Map<String, String> result = new Gson().fromJson(response, MAP_TYPE);
            int errCode = Integer.valueOf(result.get(ERR_CODE));
            if (errCode == 0) {
                log.info("信息推送成功{}", result);
                return true;
            } else if (errCode == 41028 || errCode == 41029){
                log.error("信息推送失败 formId无效,过期，换formId继续", result);
                return sendMessage(messageParam);
            }else{
                log.error("信息推送失败 其他错误.",result);
            }

        }
        return false;
    }
}
