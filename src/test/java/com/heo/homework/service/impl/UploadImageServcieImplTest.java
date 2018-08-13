package com.heo.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UploadImageServcieImplTest {


    @Autowired
    private UploadImageServcieImpl uploadImageServcie;

    @Test
    public void saveImage() {
        uploadImageServcie.saveImage("avatar/1533977295392817826.png");
    }

    @Test
    public void clearImage(){
        uploadImageServcie.clearImage();
    }
}