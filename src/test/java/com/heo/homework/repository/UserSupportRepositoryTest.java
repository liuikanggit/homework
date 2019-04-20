package com.heo.homework.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserSupportRepositoryTest {

    @Autowired
    private UserSupportRepository userSupportRepository;

    @Test
    public void test(){
        log.info("{}",userSupportRepository.existsByUserIdAndLikedUserId("1552554739114210831","1551528072478514591")!=null);
    }
}