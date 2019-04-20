package com.heo.homework.service.impl;

import com.heo.homework.entity.Student;
import com.heo.homework.entity.Teacher;
import com.heo.homework.entity.UserSupport;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.repository.StudentRepository;
import com.heo.homework.repository.TeacherRepository;
import com.heo.homework.repository.UserSupportRepository;
import com.heo.homework.service.UserInfoService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import com.heo.homework.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-03-11 15:02
 * @desc
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserSupportRepository userSupportRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public ResultVO like(String userId, String likedUserId) {
        if(userId.equals(likedUserId)){
            throw new MyException(ResultEnum.LIKE_SELF);
        }
        if ( !studentRepository.existsById(likedUserId) && !teacherRepository.existsById(likedUserId) ){
            throw new MyException(ResultEnum.USER_NOT_EXIST);
        }
        UserSupport userSupport = userSupportRepository.findTodayUserSupport(userId,likedUserId);
        if (userSupport == null){
            userSupport = new UserSupport(userId,likedUserId);
        }
        if (userSupport.getNum() >= 10){
            throw new MyException(ResultEnum.LIKE_EXCEED_10);
        }
        userSupport.setNumAdd();
        userSupportRepository.save(userSupport);
        return ResultVOUtil.success(userSupport.getNum());
    }

    @Override
    public ResultVO getUserInfo(String userId) {
        UserInfoVO userInfoVO;
        if ( studentRepository.existsById(userId)){
            Student student = studentRepository.getOne(userId);
            userInfoVO = new UserInfoVO(student);
        }else if( teacherRepository.existsById(userId)){
            Teacher teacher = teacherRepository.getOne(userId);
            userInfoVO = new UserInfoVO(teacher);
        } else{
            throw new MyException(ResultEnum.USER_NOT_EXIST);
        }
        userInfoVO.setLikedNum(userSupportRepository.getLikeNumByLikedUserId(userId));
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String selfId = (String) request.getAttribute("userId");
        userInfoVO.setLike(userSupportRepository.existsByUserIdAndLikedUserId(selfId,userId)!=null);
        return ResultVOUtil.success(userInfoVO);
    }
}
