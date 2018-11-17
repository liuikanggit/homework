package com.heo.homework.service;

import com.heo.homework.dto.MessageParam;

public interface WechatMessageService {
    boolean sendMessage(MessageParam messageParam);
}
