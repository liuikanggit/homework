package com.heo.homework.controller;

import com.heo.homework.constant.UserType;
import com.heo.homework.form.*;
import com.heo.homework.service.*;
import com.heo.homework.utils.DateUtil;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/t")
public class TeacherController {

    private final TeacherService teacherService;

    private final ClassService classService;

    private final HomeworkService homeworkService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    public TeacherController(TeacherService teacherService, ClassService classService, HomeworkService homeworkService) {
        this.teacherService = teacherService;
        this.classService = classService;
        this.homeworkService = homeworkService;
    }

    /**
     * 获取个人信息
     *
     * @return 教师的个人信息
     */
    @GetMapping("/info")
    public ResultVO getTeacherInfo(HttpServletRequest request) {
        String teacherId = (String) request.getAttribute("userId");
        return teacherService.getTeacherInfo(teacherId);
    }

    /**
     * 查看别人信息
     */
    @GetMapping("/info/{userId}")
    public ResultVO getOtherInfo(@PathVariable String userId) {
        return userInfoService.getUserInfo(userId);
    }

    /**
     * 修改信息
     *
     * @param teacherInfoForm 教师信息
     * @param bindingResult   表单验证结果
     * @return 结果
     */
    @PostMapping("/info")
    public ResultVO modifyTeacherInfo(HttpServletRequest request, @Valid UserInfoForm teacherInfoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        String teacherId = (String) request.getAttribute("userId");
        return teacherService.modifyTeacherInfo(teacherId, teacherInfoForm);
    }

    /**
     * 创建班级
     *
     * @param classForm     班级表单
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PostMapping("/class")
    public ResultVO createClass(HttpServletRequest request, @Valid ClassForm classForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        String teacherId = (String) request.getAttribute("userId");
        return teacherService.createClass(teacherId, classForm);
    }

    /**
     * 修改班级信息
     *
     * @param classForm     班级表单
     * @param bindingResult 表单验证结果
     * @return 结果
     */
    @PutMapping("/class")
    public ResultVO modifyClassInfo(@RequestParam String classId, @Valid ClassForm classForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        String teacherId = (String) request.getAttribute("userId");
        return teacherService.modifyClass(teacherId, classId, classForm);
    }

    /**
     * 得到一个班级信息
     *
     * @param classId 班级id
     * @return 结果
     */
    @GetMapping("/class/{classId}")
    public ResultVO getClassInfo(@PathVariable String classId) {
        return classService.getClassInfo(classId, true);
    }


    /**
     * 获取自己创建的班级
     *
     * @return
     */
    @GetMapping("/class")
    public ResultVO getClassInfo(HttpServletRequest request) {
        String teacherId = (String) request.getAttribute("userId");
        return classService.getClassInfo(teacherId);
    }

    /**
     * 获取班级信息和班级中所有的学生信息
     *
     * @return
     */
    @GetMapping("/class/user")
    public ResultVO getClassUserInfo(HttpServletRequest request, @RequestParam String classId) {
        String teacherId = (String) request.getAttribute("userId");
        return classService.getClassUserInfo(classId, teacherId);
    }

    /**
     * 布置作业
     *
     * @return 结果
     */
    @PostMapping("/homework")
    public ResultVO assignmentHomework(@RequestParam String[] classId
            , @RequestParam String[] image,
                                       @RequestParam String endDate,
                                       @RequestParam String desc, HttpServletRequest request) {
        String teacherId = (String) request.getAttribute("userId");
        Date date = DateUtil.parse(endDate);
        return homeworkService.assignmentHomework(teacherId, classId, image, date, desc);
    }

    /**
     * 获取自己布置的作业 分页
     *
     * @param page
     * @param size
     * @param request
     * @return
     */
    @GetMapping("/homework")
    public ResultVO getHomework(@RequestParam String classId,
                                @RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "50") int size,
                                @RequestParam(required = false, defaultValue = "0") int end, HttpServletRequest request) {
        if (end > 0) {
            return homeworkService.getCreateHomeworkEnd(classId, page, size);
        } else {
            return homeworkService.getCreateHomeworkNotEnd(classId, page, size);
        }

    }

    @GetMapping("/homework/{id}")
    public ResultVO getHomeworkDetail(@PathVariable String id){
        return homeworkService.getHomeworkDetail(id);
    }

    @GetMapping("/homework/image")
    public ResultVO getHomeworkDetail(@RequestParam String homeworkId,@RequestParam String userId){
        return homeworkService.getHomeworkImage(homeworkId,userId);
    }

    @PostMapping("/homework/image")
    public ResultVO get(@RequestParam String homeworkId,@RequestParam String userId,@RequestParam Integer score,@RequestParam String comment,String[] images){
        return homeworkService.correctionHomeworkImage(homeworkId,userId,score,comment,images);
    }

    /**
     * 给帖子点赞
     *
     * @param request
     * @param postId
     * @return
     */
    @PostMapping("/post/like")
    public ResultVO likePost(HttpServletRequest request, @RequestParam Integer postId) {
        String userId = (String) request.getAttribute("userId");
        return postService.likePost(userId, UserType.STUDENT, postId);
    }

    /**
     * 给用户点赞
     *
     * @param request
     * @param userId
     * @return
     */
    @PutMapping("/like")
    public ResultVO likeUser(HttpServletRequest request, @RequestParam String userId) {
        String selfId = (String) request.getAttribute("userId");
        return userInfoService.like(selfId, userId);
    }
}
