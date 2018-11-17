package com.heo.homework.controller;

import com.heo.homework.service.StudentService;
import com.heo.homework.service.TeacherService;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
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
    @GetMapping("/student/login")
    public ResultVO studentLogin(@RequestParam String code,@RequestParam(required = false) String formId) {
        return studentService.login(code,formId);
    }

    /**
     * 教师登录
     * @param code
     * @return
     */
    @GetMapping("/teacher/login")
    public ResultVO teacherLogin(@RequestParam String code,@RequestParam(required = false) String formId){
        return teacherService.login(code,formId);
    }
}
