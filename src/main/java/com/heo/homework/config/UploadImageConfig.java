package com.heo.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "image")
public class UploadImageConfig {

    private List<String> dir;

    private String tempPath;

    private String savePath;

    private int expireTime; //图片过期时间 单位天
}
