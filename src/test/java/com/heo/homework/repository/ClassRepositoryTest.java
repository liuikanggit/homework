package com.heo.homework.repository;

import com.heo.homework.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}