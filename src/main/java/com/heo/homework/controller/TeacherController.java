package com.heo.homework.controller;

import com.heo.homework.form.*;
import com.heo.homework.service.ClassService;
import com.heo.homework.service.HomeworkService;
import com.heo.homework.service.TeacherService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    private final TeacherService teacherService;

    private final ClassService classService;

    private final HomeworkService homeworkService;

    @Autowired
    public TeacherController(TeacherService teacherService, ClassService classService, HomeworkService homeworkService) {
        this.teacherService = teacherService;
        this.classService = classService;
        this.homeworkService = homeworkService;
    }

    /**
     * 获取个人信息
     * @param loginIdForm 登录信息
     * @param bindingResult 表单验证结果
     * @return 教师的个人信息
     */
    @GetMapping("/info")
    public ResultVO getTeacherInfo(@Valid LoginIdForm loginIdForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return teacherService.getTeacherInfo(loginIdForm.getId());
    }

    /**
     * 修改信息
     * @param teacherInfoForm 教师信息
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PostMapping("/info")
    public ResultVO modifyTeacherInfo(@Valid UserInfoForm teacherInfoForm, BindingResult bindingResult ){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return teacherService.modifyTeacherInfo(teacherInfoForm);
    }

    /**
     * 创建班级
     * @param classForm 班级表单
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PutMapping("/class")
    public ResultVO createClass(@Valid ClassForm classForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return classService.createClass(classForm);
    }

    /**
     * 修改班级信息
     * @param classForm 班级表单
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PostMapping("/class")
    public ResultVO modifyClassInfo(@Valid ClassForm classForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return classService.modifyClass(classForm);
    }

    /**
     * 得到一个班级信息
     * @param classIdForm 班级表单
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @GetMapping("/class")
    public ResultVO getClassInfo(@Valid ClassIdForm classIdForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return classService.getClassInfo(classIdForm);
    }

    /**
     * 布置作业
     * @param homeworkForm 作业表单
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PostMapping("/homework")
    public ResultVO assignmentHomework(@Valid HomeworkForm homeworkForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return homeworkService.assignmentHomework(homeworkForm);
    }
}
