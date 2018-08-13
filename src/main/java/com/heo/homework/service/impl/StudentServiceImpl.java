package com.heo.homework.service.impl;

import com.heo.homework.entity.*;
import com.heo.homework.entity.Class;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.form.ClassIdForm;
import com.heo.homework.form.HomeworkIdForm;
import com.heo.homework.form.SubmitHomeworkForm;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.repository.*;
import com.heo.homework.service.StudentService;
import com.heo.homework.service.UploadImageService;
import com.heo.homework.service.WechatLoginService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.HomeworkVO;
import com.heo.homework.vo.ResultVO;
import com.heo.homework.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private WechatLoginService wechatLoginService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private Student2ClassRepository student2ClassRepository;

    @Autowired
    private HomeworkDetailRepository homeworkDetailRepository;

    @Autowired
    private HomeworkImageRepository homeworkImageRepository;

    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UploadImageService uploadImageService;

    @Override
    public ResultVO login(String code) {

        /** 用code换取openid */
        String openid = wechatLoginService.auth(code);

        /** 判断是否第一次登录 */
        Student student = studentRepository.findByOpenid(openid);
        if( student == null){
            /** 第一次登录 创建一个学生 */
            student = new Student(openid);
            studentRepository.save(student);
        }
        /** 把学生id返回 */
        return ResultVOUtil.success(student.getStudentId());
    }

    /**
     * 获取学生资料
     * @param studentId 学生id
     * @return 学生资料
     */
    @Override
    public ResultVO getStudentInfo(String studentId) {

        Student student = studentRepository.findByStudentId(studentId);
        if (Objects.isNull(student)){
            throw new MyException(ResultEnum.STUDENT_NOT_ENPTY);
        }
        UserInfoVO userInfoVO = new UserInfoVO(student);
        if (userInfoVO.isBlank())
            return ResultVOUtil.success();
        return ResultVOUtil.success(userInfoVO);
    }


    @Override
    public ResultVO modifyStudentInfo(UserInfoForm studentInfoForm) {

        Student student = studentRepository.findByStudentId(studentInfoForm.getId());
        if (Objects.isNull(student)){
            throw new MyException(ResultEnum.STUDENT_NOT_ENPTY);
        }

        /** 更新学生资料 */
        student.setStudentInfo(studentInfoForm);
        student = studentRepository.save(student);

        /** 返回最新的资料 */
        UserInfoVO userInfoVO = new UserInfoVO(student);
        return ResultVOUtil.success(userInfoVO);
    }

    /**
     * 加入班级
     * @param classIdForm 学生id 和 班级id
     * @return 成功
     */
    @Override
    public ResultVO joinClass(@Valid ClassIdForm classIdForm) {
        String studentId = classIdForm.getId();
        String classId = classIdForm.getClassId();

        /** 判断班级是否存在 */
        if(Objects.isNull(classRepository.findByClassId(classId))){
            throw new MyException(ResultEnum.CLASS_NOT_ENPTY);
        }
        /** 查看学生是否重复加入班级 */
        if( ! Objects.isNull(student2ClassRepository.findByStudentIdAndAndClassId(studentId,classId))){
            throw new MyException(ResultEnum.REPEAT_THE_CLASS);
        }
        Student2Class student2Class = new Student2Class(classIdForm.getId(),classIdForm.getClassId());

        student2ClassRepository.save(student2Class);

        return ResultVOUtil.success();
    }

    /**
     * 查看学生所有的作业
     * @param studentId 学生id
     * @return 作业信息
     */
    @Override
    public ResultVO getHomework(String studentId) {

        List<HomeworkVO> homeworkVOList = new ArrayList<>();

        List<HomeworkDetail> homeworkDetailList = homeworkDetailRepository.findAllByStudentIdOrderByUpdateTimeDesc(studentId);

        for (HomeworkDetail homeworkDetail: homeworkDetailList) {
            HomeworkVO homeworkVO = new HomeworkVO();

            Homework homework = homeworkRepository.findById(homeworkDetail.getHomeworkId()).get();
            Class mClss = classRepository.findByClassId(homework.getClassId());
            Teacher teacher = teacherRepository.findByTeacherId(mClss.getTeacherId());

            homeworkVO.setHomeworkId(homework.getHomeworkId());
            homeworkVO.setClassId(mClss.getClassId());
            homeworkVO.setClassName(mClss.getClassName());
            homeworkVO.setSubject(mClss.getClassSubject());
            homeworkVO.setHomeworkDesc(homework.getHomeworkDesc());
            homeworkVO.setTeacherName(teacher.getTeacherName());
            homeworkVO.setTeacherId(teacher.getTeacherId());
            homeworkVO.setStatus(homeworkDetail.getHomeworkStatus());
            List<String> imageUrls = homeworkImageRepository.getImageUrlListByHomeworkDetailId(homeworkDetail.getId());
            homeworkVO.setHomeworkImageUrls(imageUrls);
            homeworkVO.setBeginTime(homework.getCreateTime());
            homeworkVO.setEndTime(homework.getEndTime());

            homeworkVOList.add(homeworkVO);
        }

        return ResultVOUtil.success(homeworkVOList);
    }

    /**
     * 学生提交作业
     * @param submitHomeworkForm id:学生id， homeworkId:作业id, imageUrl,上传的图片
     * @return
     */
    @Override
    @Transactional
    public ResultVO submitHomework(SubmitHomeworkForm submitHomeworkForm) {
        String studentId = submitHomeworkForm.getId();
        String homeworkId = submitHomeworkForm.getHomeworkId();
        List<String> imageUrlList = Arrays.asList(submitHomeworkForm.getHomeworkImageUrls().split(",|，"));

        /** 查看作业是否存在 */
        Homework homework = homeworkRepository.findById(homeworkId).get();
        if (Objects.isNull(homework)){
            throw new MyException(ResultEnum.HOMEWORK_NOT_ENPTY);
        }
        /** 查看是否已经截止 */
        if (homework.getEndTime().before(new Date())){
            throw new MyException(ResultEnum.HOMEWORK_ALREADY_END);
        }

        HomeworkDetail homeworkDetail = homeworkDetailRepository.findByStudentIdAndHomeworkId(studentId,homeworkId);
        if (Objects.isNull(homeworkDetail)){
            throw new MyException(ResultEnum.HOMEWORK_UNABLE_SUBMIT);
        }

        homeworkDetail.setSubmitTime(new Date()); //提交时间
        homeworkDetail.setHomeworkStatus(1); //以提交
        for (String imageUrl :imageUrlList) {
            if (uploadImageService.saveImage(imageUrl)) { //保存图片  如果图片之前没有
                HomeworkImage homeworkImage = new HomeworkImage();
                homeworkImage.setHomeworkDetailId(homeworkDetail.getId());
                homeworkImage.setImageUrl(imageUrl);
                homeworkImageRepository.save(homeworkImage); //图片地址到数据库
            }
        }

        return ResultVOUtil.success();
    }
}
