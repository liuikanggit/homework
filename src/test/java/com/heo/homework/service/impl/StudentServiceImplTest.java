package com.heo.homework.service.impl;

import com.heo.homework.service.StudentService;
import com.heo.homework.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static jdk.nashorn.internal.objects.NativeMath.log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @Test
    @Transactional
    public void joinClass() {
        ResultVO resultVO = studentService.joinClass("1","111111","");
        log.info("result:{}",resultVO);
        Assert.assertEquals(resultVO.getCode(),new Integer(0));
    }

    @Test
    public void getHomeworkDetail(){
        ResultVO result = studentService.getHomeworkDetail("1551528072478514591", "1555054266029228281");
        Assert.assertEquals(result.getCode(),new Integer(0));
        log.info("{}",result.getData());

    }
}