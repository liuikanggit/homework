package com.heo.homework.service.impl;

import com.heo.homework.config.TemplateIDConfig;
import com.heo.homework.dto.MessageParam;
import com.heo.homework.service.WechatMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WechatMessageServiceImplTest {

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private TemplateIDConfig templateIDConfig;

    @Test
    public void sendMessage() {

    }
}