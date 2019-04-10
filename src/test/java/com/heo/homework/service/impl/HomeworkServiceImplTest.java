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
        ResultVO<PageVo> createHomework = homeworkService.getCreateHomeworkEnd("400490", 0, 5);
        log.info("结束的{}",createHomework.getData());

        ResultVO<PageVo> createHomework2 = homeworkService.getCreateHomeworkNotEnd("400490", 0, 5);
        log.info("未结束的{}",createHomework2.getData());

    }

    @Test
    public void assignmentHomework() {

        HomeworkForm homeworkForm = new HomeworkForm();
        homeworkForm.setDesc("背诵课文");

    }

    @Test
    public void modifyHomework() {
    }

    @Test
    public void getHomeworkDetail(){
        ResultVO homeworkDetail = homeworkService.getHomeworkDetail("1553615960770219646");
        log.info("{}",homeworkDetail);
    }
}