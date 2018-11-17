package com.heo.homework.service.impl;

import com.heo.homework.form.ClassIdForm;
import com.heo.homework.service.StudentService;
import com.heo.homework.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Transient;
import javax.transaction.Transactional;

import static jdk.nashorn.internal.objects.NativeMath.log;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @Test
    @Transactional
    public void joinClass() {
        ClassIdForm classIdForm = new ClassIdForm();
        classIdForm.setId("1");
        classIdForm.setClassId("111111");
        ResultVO resultVO = studentService.joinClass(classIdForm,"");
        log.info("result:{}",resultVO);
        Assert.assertEquals(resultVO.getCode(),new Integer(0));
    }
}