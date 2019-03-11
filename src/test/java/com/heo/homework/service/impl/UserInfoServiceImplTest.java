package com.heo.homework.service.impl;

import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.service.UserInfoService;
import com.heo.homework.vo.ResultVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInfoServiceImplTest {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    @Transactional
    public void like() {
        try {
            userInfoService.like("1", "1");
        } catch (MyException e) {
            Assert.assertEquals(e.getCode(), ResultEnum.LIKE_SELF.getCode());
        }

        try {
            userInfoService.like("1", "2");
        } catch (MyException e) {
            Assert.assertEquals(e.getCode(), ResultEnum.USER_NOT_EXIST.getCode());
        }

        ResultVO resultVO = userInfoService.like("1551528072478514591", "1");
        Assert.assertEquals(resultVO.getCode(), ResultEnum.SUCCESS.getCode());

        for (int i = 0; i < 9; i++) {
            userInfoService.like("1551528072478514591", "1");
        }
        try {
            userInfoService.like("1551528072478514591", "1");
        }catch (MyException e){
            Assert.assertEquals(e.getCode(), ResultEnum.LIKE_EXCEED_10.getCode());
        }


    }
}