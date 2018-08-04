package com.heo.homework.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heo.homework.config.WechatAccountConfig;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.service.WechatLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class WechatLoginServiceImpl implements WechatLoginService{


    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Override
    public String auth(String code) {
        /** 用code换取用户openid */
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + wechatAccountConfig.getAppId()
                +"&secret="+
                wechatAccountConfig.getSecret()
                +"&js_code="
                + code
                + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url,String.class);
        Map<String,String> resultMap =  new Gson().fromJson(response,new TypeToken<Map<String, String>>() {}.getType());
        if (resultMap.get("openid") == null){
            throw new MyException(ResultEnum.INVALID_CODE);
        }
        /** 返回拿到的openid */
        return resultMap.get("openid");
    }
}
