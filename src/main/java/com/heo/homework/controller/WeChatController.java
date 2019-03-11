package com.heo.homework.controller;

import com.heo.homework.constant.RedisConstant;
import com.heo.homework.service.StudentService;
import com.heo.homework.service.TeacherService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WeChatController {

    @Autowired
    StudentService studentService;

    @Autowired
    private TeacherService teacherService;


    /**
     * 学生登录
     * @param code
     * @return
     */
    @PostMapping("/s/login")
    public ResultVO studentLogin(@RequestParam String code,@RequestParam(required = false) String[] formId,
                                 @RequestParam String nickName,
                                 @RequestParam String avatarUrl,
                                 @RequestParam String gender
                                 ) {
        return studentService.login(code,formId,nickName,avatarUrl,gender);
    }

    /**
     * 教师登录
     * @param code
     * @return
     */
    @PostMapping("/t/login")
    public ResultVO teacherLogin(@RequestParam String code,@RequestParam(required = false) String[] formId,
                                 @RequestParam String nickName,
                                 @RequestParam String avatarUrl,
                                 @RequestParam String gender){
        return teacherService.login(code,formId,nickName,avatarUrl,gender);
    }
}
