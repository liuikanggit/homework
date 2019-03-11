package com.heo.homework.repository;

import com.heo.homework.vo.PageVo;
import com.heo.homework.vo.PostVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void getALlPost(){
        Page<PostVO> pageVoPage = postRepository.getAllPost( PageRequest.of(0,2));
        log.info("totalElements{},totalPager:{},content:{}",pageVoPage.getTotalElements(),pageVoPage.getTotalPages(),pageVoPage.getContent());
    }
}