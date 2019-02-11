package com.heo.homework.repository;

import com.heo.homework.vo.PageVo;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Student2ClassRepositoryTest {

    @Autowired
    private Student2ClassRepository student2ClassRepository;

    @Autowired ClassRepository classRepository;

    @Test
    public void findStudentIdByClassId(){
        log.info("{}",student2ClassRepository.countByClassId("111111"));


    }

    @Test
    public void findClassIdByStudentId(){
        Page page = student2ClassRepository.findClassIdByStudentId("1",new PageRequest(0,5));
        PageVo pageVo = new PageVo(page.getTotalPages(),page.getTotalElements(),page.getContent());
        log.info("{}",pageVo);

        log.info("{}",classRepository.findByClassIdIn(page.getContent()));
    }

    @Test
    public void findClassIdByStudentIds(){
//        List<String> classId = student2ClassRepository.findClassIdByStudentId("1");
//        PageVo pageVo = new PageVo(page.getTotalPages(),page.getTotalElements(),page.getContent());
//        log.info("{}",classId);

    }

}