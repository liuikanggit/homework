package com.heo.homework.service.impl;

import com.heo.homework.entity.PostSupport;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.repository.PostSupportRepository;
import com.heo.homework.service.PostService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostSupportRepository postSupportRepository;

    @Test
    public void createPost() {
    }

    @Test
    public void getAllPost() {
    }

    @Test
    @Transactional
    public void likePost() {
        long count = postSupportRepository.count();
        postService.likePost("1",0,1);
        try {
            postService.likePost("1",0,1);
        }catch (MyException e){
            Assert.assertEquals(e.getMessage(), ResultEnum.REPEAT_LIST_POST.getMessage());
        }
        Assert.assertEquals(postSupportRepository.count(),count+1);
        postService.unListPost("1",1);
        Assert.assertEquals(postSupportRepository.count(),count);
    }
}