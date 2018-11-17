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
        MessageParam messageParam = new MessageParam("1534860953513576645", "oAS8c5JEgJZUP9g0s5-ZgLPDqUcs",templateIDConfig.getHomeworkNotice(),templateIDConfig.getHomeworkPath())
                .addData("1")
                .addData("2")
                .addData("3")
                .addData("4")
                .addData("5")
                .addData("6")
                .addData("7");
        wechatMessageService.sendMessage(messageParam);
    }
}