package com.heo.homework.service.impl;

import com.heo.homework.constant.RedisConstant;
import com.heo.homework.entity.Admin;
import com.heo.homework.entity.Student;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.repository.AdminRepository;
import com.heo.homework.repository.StudentRepository;
import com.heo.homework.service.AdminService;
import com.heo.homework.service.RedisService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.PageVo;
import com.heo.homework.vo.ResultVO;
import com.heo.homework.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public ResultVO login(String userName, String password) {

        Admin admin = adminRepository.findByUserName(userName);
        /** 用户不存 */
        if(null == admin){
            throw new MyException(ResultEnum.ADMIN_EMPTY);
        }
        /** 密码错误 */
        if(password == null || !password.equals(admin.getPassword())){
            throw new MyException(ResultEnum.ADMIN_PASSWORD_ERROR);
        }

        String token = redisService.login(admin.getId()+"");

        log.info("管理员 {} 登录", admin.getUserName());

        return ResultVOUtil.success(token);
    }

    @Override
    public ResultVO logout(String token) {
        redisService.logout();
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO verify(String token) {
        String userName = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, token));
        return ResultVOUtil.success(userName);
    }




}
