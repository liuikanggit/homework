package com.heo.homework.service.impl;

import com.heo.homework.form.HomeworkForm;
import com.heo.homework.service.HomeworkService;
import com.heo.homework.vo.ResultVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeworkServiceImplTest {

    @Autowired
    private HomeworkService homeworkService;

    @Test
    @Transactional
    public void assignmentHomework() {

        HomeworkForm homeworkForm = new HomeworkForm();

        homeworkForm.setId("1");
        homeworkForm.setClassId("111111");
        homeworkForm.setDesc("把课本上第22页的课文抄一遍");
        homeworkForm.setEndTime(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));

        ResultVO result = homeworkService.assignmentHomework(homeworkForm);

        Assert.assertEquals(result.getCode(),Integer.valueOf(0));
    }

    @Test
    public void modifyHomework() {
    }
}