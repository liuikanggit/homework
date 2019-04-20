package com.heo.homework.service.impl;

import com.heo.homework.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ClassServiceImplTest {

    @Autowired
    private ClassService classService;

    @Test
    public void getClassUserInfo() {
        log.info("{}",classService.getClassUserInfo("111111"));
    }
}