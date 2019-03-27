package com.heo.homework.service.impl;

import com.heo.homework.form.HomeworkForm;
import com.heo.homework.service.HomeworkService;
import com.heo.homework.vo.PageVo;
import com.heo.homework.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HomeworkServiceImplTest {

    @Autowired
    private HomeworkService homeworkService;

    @Test
    public void getCreateHomework(){
        ResultVO<PageVo> createHomework = homeworkService.getCreateHomeworkEnd("1552554739114210831", 0, 5);
        log.info("{}",createHomework.getData().getTotalData());

        ResultVO<PageVo> createHomework2 = homeworkService.getCreateHomeworkNotEnd("1552554739114210831", 0, 5);
        log.info("{}",createHomework2.getData().getTotalData());

    }

    @Test
    public void assignmentHomework() {

        HomeworkForm homeworkForm = new HomeworkForm();
        homeworkForm.setDesc("背诵课文");

//        homeworkForm.setEndTime(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));

//        ResultVO result = homeworkService.assignmentHomework("1","111111",homeworkForm);

//        Assert.assertEquals(result.getCode(),Integer.valueOf(0));
    }

    @Test
    public void modifyHomework() {
    }
}