package com.heo.homework.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "template")
public class TemplateIDConfig {

    private String homeworkNotice;

    private String homeworkPath;

    private String registerNotice;

    private String registerPath;

}
