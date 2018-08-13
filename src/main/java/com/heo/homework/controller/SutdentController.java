package com.heo.homework.controller;

import com.heo.homework.form.*;
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

    @Autowired
    private StudentService studentService;

    /**
     * 获取学生信息
     * @param loginIdForm 登录的信息
     * @param bindingResult
     * @return
     */
    @GetMapping("/info")
    public ResultVO getStudentInfo(@Valid LoginIdForm loginIdForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.getStudentInfo(loginIdForm.getId());
    }

    /**
     * 学生修改自己信息
     * @param studentInfoForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/info")
    public ResultVO modifyStudentInfo(@Valid UserInfoForm studentInfoForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.modifyStudentInfo(studentInfoForm);
    }

    /**
     * 加入班级
     * @param classIdForm 学生id 班级id
     * @param bindingResult 表单验证结果
     * @return 成功
     */
    @PutMapping("/jion")
    public ResultVO jionClass(@Valid ClassIdForm classIdForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.joinClass(classIdForm);
    }

    /**
     * 查询学生的作业
     * @param loginIdForm 学生id
     * @param bindingResult 表单验证结果
     * @return 作业信息
     */
    @GetMapping("/homework")
    public ResultVO getHomework(@Valid LoginIdForm loginIdForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.getHomework(loginIdForm.getId());
    }

    /**
     * 提交作业
     * @param submitHomeworkForm id：学生id, homeworkId: 作业id，homeworkImageUrl:作业图片地址
     * @param bindingResult
     * @return
     */
    @PostMapping("/homework")
    public ResultVO submitHomework(@Valid SubmitHomeworkForm submitHomeworkForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.submitHomework(submitHomeworkForm);
    }
}
