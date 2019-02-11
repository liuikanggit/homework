package com.heo.homework.repository;

import com.heo.homework.entity.Class;
import com.heo.homework.entity.Student;
import com.heo.homework.utils.KeyUtil;
import com.heo.homework.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;



@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ClassRepositoryTest {

    @Autowired
    private ClassRepository classRepository;

    @Test
    public void findAllClassId() {
        List<String> classIds =classRepository.findAllClassId();
        List<String> mClassIds = new ArrayList<>();
        for (int i=0; i<100; i++){
            mClassIds.add(KeyUtil.getClassKey(classIds));
        }
        log.info("classIds:{}",mClassIds);
        Assert.assertNotEquals(0,classIds.size());
    }

    @Test
    public void findAll(){
        Page<Class> classPage = classRepository.findAll(new PageRequest(0,10));
        PageVo page = new PageVo(classPage.getTotalPages(),classPage.getTotalElements(),classPage.getContent());
        log.info("一共：{} 数据:{}",page);
    }
}