package com.heo.homework.controller;

import com.heo.homework.form.LoginIdForm;
import com.heo.homework.form.StudentInfoForm;
import com.heo.homework.service.StudentService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/student/")
@Slf4j
public class SutdentController {

    @Autowired StudentService studentService;

    /**
     * 学生登录
     * @param code
     * @return
     */
    @GetMapping("login")
    public ResultVO login(@RequestParam String code) {
        return studentService.login(code);
    }

    /**
     * 获取学生信息
     * @param LoginIdForm
     * @param bindingResult
     * @return
     */
    @GetMapping("/info")
    public ResultVO getStudentInfo(@Valid LoginIdForm LoginIdForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.getStudentInfo(LoginIdForm.getId());
    }

    /**
     * 修改学生信息
     * @param studentInfoForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/info")
    public ResultVO modifyStudentInfo(@Valid StudentInfoForm studentInfoForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.modifyStudentInfo(studentInfoForm);
    }
}
