package com.heo.homework.controller;

import com.heo.homework.aspect.AdminAspect;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.service.AdminService;
import com.heo.homework.service.ClassService;
import com.heo.homework.service.StudentService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @PostMapping("/login")
    public ResultVO login(@RequestParam String name, @RequestParam String password){
        return adminService.login(name,password);
    }

    @PostMapping("verify")
    public ResultVO verify(HttpServletRequest request){
        return  adminService.verify( request.getHeader(AdminAspect.AUTHORIZATION));
    }

    @PostMapping("/logout")
    public ResultVO logout(HttpServletRequest request){
        return adminService.logout(request.getHeader(AdminAspect.AUTHORIZATION));
    }

    @GetMapping("/student")
    public ResultVO getStudentList(@RequestParam int page,@RequestParam int size,
                                   @RequestParam String name,@RequestParam String sex,@RequestParam String phone,
                                   @RequestParam String classId){
        return studentService.getStudent(page,size,name,sex,phone,classId);
    }

    @PostMapping("/student")
    public ResultVO addStudent(@Valid UserInfoForm userInfoForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.addStudent(userInfoForm);
    }

    @GetMapping("/classMap")
    public ResultVO getClassMap(){
        return classService.getClassMap();
    }
}
