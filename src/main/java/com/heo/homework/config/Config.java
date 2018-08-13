package com.heo.homework.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class Config {

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory config = new MultipartConfigFactory();
        config.setMaxFileSize("3MB");
        config.setMaxRequestSize("15MB");
        return config.createMultipartConfig();
    }
}
