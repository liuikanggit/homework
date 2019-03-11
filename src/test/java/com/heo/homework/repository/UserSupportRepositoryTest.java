package com.heo.homework.repository;

import com.heo.homework.entity.UserSupport;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserSupportRepositoryTest {

    @Autowired
    private UserSupportRepository userSupportRepository;

    @Test
    public void test(){
        UserSupport userSupport = userSupportRepository.findTodayUserSupport("1","1");
        log.info("{}",userSupport);
    }
}