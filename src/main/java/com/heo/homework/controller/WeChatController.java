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
@RequestMapping("/api")
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
    public ResultVO sutdnetLogin(@RequestParam String code) {
        return studentService.login(code);
    }

    @GetMapping("/teacher/login")
    public ResultVO teacherLogin(@RequestParam String code){
        return teacherService.login(code);
    }
}
