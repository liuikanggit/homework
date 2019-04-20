package com.heo.homework.controller;

import com.heo.homework.constant.UserType;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.service.ClassService;
import com.heo.homework.service.PostService;
import com.heo.homework.service.StudentService;
import com.heo.homework.service.UserInfoService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/s")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ClassService classService;

    /**
     * 获取自己信息
     *
     * @param request
     * @return
     */
    @GetMapping("/info")
    public ResultVO getStudentInfo(HttpServletRequest request) {
        String studentId = (String) request.getAttribute("userId");
        return studentService.getStudentInfo(studentId);
    }

    /**
     * 查看别人信息
     */
    @GetMapping("/info/{userId}")
    public ResultVO getOtherInfo(@PathVariable String userId) {
        return userInfoService.getUserInfo(userId);
    }

    /**
     * 学生修改自己信息
     *
     * @param studentInfoForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/info")
    public ResultVO modifyStudentInfo(@Valid UserInfoForm studentInfoForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        String studentId = (String) request.getAttribute("userId");
        return studentService.modifyStudentInfo(studentId, studentInfoForm);
    }

    /**
     * 搜索班级
     *
     * @param classId
     * @return
     */
    @GetMapping("/class")
    public ResultVO searchClass(@RequestParam String classId, HttpServletRequest request) {
        String studentId = (String) request.getAttribute("userId");
        return studentService.searchClass(studentId, classId);
    }

    /**
     * 加入班级
     *
     * @param classId
     * @param password
     * @param request
     * @return
     */
    @PutMapping(value = "/class")
    public ResultVO joinClass(@RequestParam String classId, @RequestParam(required = false, defaultValue = "") String password, HttpServletRequest request) {
        String studentId = (String) request.getAttribute("userId");
        return studentService.joinClass(studentId, classId, password);
    }

    @DeleteMapping(value = "/class/{classId}")
    public ResultVO exitClass(@PathVariable String classId, HttpServletRequest request) {
        String studentId = (String) request.getAttribute("userId");
        return studentService.exitClass(studentId, classId);
    }

    /**
     * 查询自己加入的班级 列表
     *
     * @param request id
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/class/joined")
    public ResultVO getAllClassInfo(HttpServletRequest request,
                                    @RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "50") int size) {
        String studentId = (String) request.getAttribute("userId");
        return studentService.getAllClassInfo(studentId, page, size);
    }

    @GetMapping("/class/user")
    public ResultVO getClassUserInfo(HttpServletRequest request,@RequestParam String classId){
        return classService.getClassUserInfo(classId);
    }

    /**
     * 查询学生的所有作业(分页)
     *
     * @param request
     * @param page    第几页
     * @param size    每页的数量
     * @return
     */
    @GetMapping("/homework")
    public ResultVO getHomework(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "50") int size, HttpServletRequest request) {
        String studentId = (String) request.getAttribute("userId");
        return studentService.getHomework(studentId, page, size);
    }


    /**
     * 获取作业详情
     *
     * @param homeworkId
     * @param request
     * @return
     */
    @GetMapping("/homework/detail")
    public ResultVO getHomeworkDetail(@RequestParam String homeworkId, HttpServletRequest request) {
        String studentId = (String) request.getAttribute("userId");
        return studentService.getHomeworkDetail(studentId, homeworkId);
    }


    /**
     * 提交作业
     *
     * @param homeworkId
     * @param image
     * @param request
     * @return
     */
    @PostMapping("/homework")
    public ResultVO submitHomework(@RequestParam String homeworkId, @RequestParam List<String> image, HttpServletRequest request) {
        String studentId = (String) request.getAttribute("userId");
        if (image.size() == 0){
            return ResultVOUtil.error(1,"错误");
        }
        return studentService.submitHomework(studentId, homeworkId, image);
    }


    /**
     * 创建帖子
     *
     * @param request
     * @param title
     * @param content
     * @param image
     * @return
     */
    @PostMapping("/post")
    public ResultVO createPost(HttpServletRequest request, @RequestParam String title, @RequestParam String content, @RequestParam String[] image) {
        String studentId = (String) request.getAttribute("userId");
        return postService.createPost(studentId, title, content, image);
    }

    /**
     * 获取帖子（分页）
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/post")
    public ResultVO getPost(@RequestParam(required = false) String  self, @RequestParam Integer page, @RequestParam Integer size, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (self!=null){
            return postService.getSelfPost(userId, page, size);
        }
        return postService.getAllPost(userId, page, size);
    }

    /**
     * 给帖子点赞
     *
     * @param request
     * @param postId
     * @return
     */
    @PutMapping("/post/like")
    public ResultVO likePost(HttpServletRequest request, @RequestParam Integer postId) {
        String userId = (String) request.getAttribute("userId");
        return postService.likePost(userId, UserType.STUDENT, postId);
    }

    /**
     * 取消点赞
     *
     * @param request
     * @param postId
     * @return
     */
    @PutMapping("/post/unlike")
    public ResultVO unLikePost(HttpServletRequest request, @RequestParam Integer postId) {
        String userId = (String) request.getAttribute("userId");
        return postService.unLikePost(userId, postId);
    }

    @GetMapping("/post/{id}")
    public ResultVO getPostDetail(HttpServletRequest request,@PathVariable Integer id){
        String userId = (String) request.getAttribute("userId");
        return postService.getPostDetail(userId,UserType.STUDENT,id);
    }

    @PostMapping("/re/post")
    public ResultVO rePost(@RequestParam Integer postId,@RequestParam Integer reId,@RequestParam String content,
                           @RequestParam(required = false) String[] image,HttpServletRequest request){
        String userId = (String) request.getAttribute("userId");
        return postService.rePost(postId,reId,userId,UserType.STUDENT,content,image);
    }

    @PostMapping("/re/like")
    public ResultVO likeRe(HttpServletRequest request, @RequestParam Integer reId){
        String userId = (String) request.getAttribute("userId");
        return postService.reSupport(reId,userId,UserType.STUDENT);
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
