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

import javax.servlet.http.HttpServletRequest;
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
     * @return 教师的个人信息
     */
    @GetMapping("/info")
    public ResultVO getTeacherInfo(HttpServletRequest request){
        String teacherId = (String) request.getAttribute("userId");
        return teacherService.getTeacherInfo(teacherId);
    }

    /**
     * 修改信息
     * @param teacherInfoForm 教师信息
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PostMapping("/info")
    public ResultVO modifyTeacherInfo(HttpServletRequest request,@Valid UserInfoForm teacherInfoForm, BindingResult bindingResult ){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        String teacherId = (String) request.getAttribute("userId");
        return teacherService.modifyTeacherInfo(teacherId,teacherInfoForm);
    }

    /**
     * 创建班级
     * @param classForm 班级表单
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PutMapping("/class")
    public ResultVO createClass(HttpServletRequest request,@Valid ClassForm classForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        String teacherId = (String) request.getAttribute("userId");
        return classService.createClass(teacherId,classForm);
    }

    /**
     * 修改班级信息
     * @param classForm 班级表单
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PostMapping("/class")
    public ResultVO modifyClassInfo(@RequestParam String classId,@Valid ClassForm classForm,BindingResult bindingResult,HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        String teacherId = (String) request.getAttribute("userId");
        return classService.modifyClass(teacherId,classId,classForm);
    }

    /**
     * 得到一个班级信息
     * @param classId 班级id
     * @return 结果
     */
    @GetMapping("/class")
    public ResultVO getClassInfo(@RequestParam String classId){
        return classService.getClassInfo(classId);
    }

    /**
     * 布置作业
     * @param homeworkForm 作业表单
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PostMapping("/homework")
    public ResultVO assignmentHomework(@RequestParam String classId,@Valid HomeworkForm homeworkForm,BindingResult bindingResult,HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        String teacherId = (String) request.getAttribute("userId");
        return homeworkService.assignmentHomework(teacherId,classId,homeworkForm);
    }
}
