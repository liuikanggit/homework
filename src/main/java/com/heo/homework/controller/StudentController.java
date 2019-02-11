package com.heo.homework.controller;

import com.heo.homework.form.*;
import com.heo.homework.service.StudentService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

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
     * 搜索班级
     * @param classIdForm
     * @param bindingResult
     * @return
     */
    @GetMapping("/class")
    public ResultVO searchClass(@Valid ClassIdForm classIdForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.searchClass(classIdForm);
    }

    /**
     * 加入班级
     * @param classIdForm 学生id 班级id
     * @param password 密码
     * @param bindingResult 表单验证结果
     * @return 成功
     */
    @PutMapping(value = "/class")
    public ResultVO joinClass(@Valid ClassIdForm classIdForm,BindingResult bindingResult,@RequestParam(required = false,defaultValue = "") String password){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.joinClass(classIdForm,password);
    }

    /**
     * 查询学生的所有作业(分页)
     * @param loginIdForm 学生id
     * @param bindingResult 表单验证结果
     * @return 作业信息
     */
    @GetMapping("/homework")
    public ResultVO getHomework(@Valid LoginIdForm loginIdForm,BindingResult bindingResult,@RequestParam int page,@RequestParam int size ){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.getHomework(loginIdForm.getId(),page,size);
    }

    /**
     * 获取作业详情
     * @param homeworkIdForm 学生id 作业homeworkId
     * @param bindingResult 表单验证结果
     * @return
     */
    @GetMapping("/homework/detail")
    public ResultVO getHomeworkDetail(@Valid HomeworkIdForm homeworkIdForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.getHomeworkDetail(homeworkIdForm);
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

    /**
     *  查询自己加入的班级
     * @param loginIdForm id
     * @param bindingResult 表单验证结果
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/class/joined")
    public ResultVO getAllClassInfo(@Valid LoginIdForm loginIdForm,BindingResult bindingResult,@RequestParam int page,@RequestParam int size)  {
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return studentService.getAllClassInfo(loginIdForm.getId(),page,size);
    }
}
