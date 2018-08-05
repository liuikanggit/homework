package com.heo.homework.controller;

import com.heo.homework.form.ClassForm;
import com.heo.homework.form.ClassIdForm;
import com.heo.homework.form.LoginIdForm;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.service.ClassService;
import com.heo.homework.service.TeacherService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ClassService classService;

    /**
     * 获取个人信息
     * @param loginIdForm
     * @param bindingResult
     * @return
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
     * @param teacherInfoForm
     * @param bindingResult
     * @return
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
     * @param classForm
     * @param bindingResult
     * @return
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
     * @param classForm
     * @param bindingResult
     * @return
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
     * @param ClassIdForm
     * @param bindingResult
     * @return
     */
    @GetMapping("/class")
    public ResultVO getClassInfo(@Valid ClassIdForm ClassIdForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return classService.getClassInfo(ClassIdForm);
    }
}
