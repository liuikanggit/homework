package com.heo.homework.service.impl;

import com.heo.homework.entity.*;
import com.heo.homework.entity.Class;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.repository.*;
import com.heo.homework.service.*;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
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

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserSupportRepository userSupportRepository;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Value("${backDoorCode}")
    private String backDoorCode;


    @Override
    public ResultVO login(String code, String[] formId, String nickName, String avatarUrl, String gender) {
        String openid;
        /** Back door code */
        if (!Strings.isEmpty(backDoorCode) && code.equals(backDoorCode)) {
            openid = "test_openid";
        } else {
            /** 用code去换openid */
            openid = wechatLoginService.auth(code);
        }

        /** 判断是否第一次登录 */
        Student student = studentRepository.findByOpenid(openid);
        if (student == null) {
            /** 第一次登录 创建一个学生 */
            student = new Student(openid);
            student.setStudentName(nickName);
            student.setStudentAvatarUrl(avatarUrl);
            //性别 0：未知、1：男、2：女
            if ("0".equals(gender)) {
                student.setSex("未填写");
            } else if ("1".equals(gender)) {
                student.setSex("男");
            } else if ("2".equals("女")) {
                student.setSex("女");
            }
            student = studentRepository.save(student);
            log.info("有新的用户注册成功了,{}", student);
            if (formId != null) {
                for (String aFormId : formId)
                    redisService.saveFormId(student.getStudentId(), aFormId);
            }
            wechatMessageService.sendRegisterNotice(student.getStudentId(), student.getOpenid(), nickName, "学生");

        }
        /** 登录返回token */
        String token = redisService.login(student.getStudentId());
        return ResultVOUtil.success(token);
    }

    /**
     * 获取学生资料
     *
     * @param studentId 学生id
     * @return 学生资料
     */
    @Override
    public ResultVO getStudentInfo(String studentId) {

        Student student = studentRepository.findByStudentId(studentId);
        if (Objects.isNull(student)) {
            throw new MyException(ResultEnum.STUDENT_EMPTY);
        }
        UserInfoVO userInfoVO = new UserInfoVO(student);
        userInfoVO.setLikedNum(userSupportRepository.getLikeNumByLikedUserId(studentId));
        return ResultVOUtil.success(userInfoVO);
    }


    @Override
    public ResultVO modifyStudentInfo(String studentId, UserInfoForm studentInfoForm) {

        Student student = studentRepository.findByStudentId(studentId);
        if (Objects.isNull(student)) {
            throw new MyException(ResultEnum.STUDENT_EMPTY);
        }

        /** 更新学生资料 */
        student.setStudentInfo(studentInfoForm);
        student = studentRepository.save(student);

        /** 返回最新的资料 */
        UserInfoVO userInfoVO = new UserInfoVO(student);
        return ResultVOUtil.success(userInfoVO);
    }

    /**
     * 搜索班级
     *
     * @param studentId
     * @param classId
     * @return
     */
    @Override
    public ResultVO searchClass(String studentId, String classId) {
        Class mClass = classRepository.findByClassId(classId);
        /** 判断班级是否存在 */
        if (Objects.isNull(mClass)) {
            throw new MyException(ResultEnum.CLASS_NOT_EXIST);
        }

        ClassProfileVO classProfileVO = new ClassProfileVO();
        classProfileVO.setClassId(mClass.getClassId());
        classProfileVO.setClassAvatarUrl(mClass.getClassAvatarUrl());
        classProfileVO.setClassName(mClass.getClassName());
        classProfileVO.setClassSubject(mClass.getClassSubject());
        classProfileVO.setTeacherName(teacherRepository.getTeacherNameByTeacherId(mClass.getTeacherId()));

        classProfileVO.setIsNeedPwd(!Strings.isEmpty(mClass.getClassPassword()));

        /** 查看学生是否重复加入班级 */
        if (!Objects.isNull(student2ClassRepository.findByStudentIdAndClassId(studentId, classId))) {
            classProfileVO.setStatus(1);
        } else {
            classProfileVO.setStatus(0);
        }
        classProfileVO.setJoinNumber(student2ClassRepository.countByClassId(classId));
        return ResultVOUtil.success(classProfileVO);
    }

    /**
     * 加入班级
     *
     * @param studentId
     * @param classId
     * @param password
     * @return 成功
     */
    @Override
    public ResultVO joinClass(String studentId, String classId, String password) {

        /** 判断班级是否存在 */
        Class mClass = classRepository.findByClassId(classId);
        if (Objects.isNull(mClass)) {
            throw new MyException(ResultEnum.CLASS_NOT_EXIST);
        }

        /** 查看学生是否重复加入班级 */
        if (!Objects.isNull(student2ClassRepository.findByStudentIdAndClassId(studentId, classId))) {
            throw new MyException(ResultEnum.REPEAT_THE_CLASS);
        }

        /** 判断密码是否相同 */
        if (!password.equals(mClass.getClassPassword())) {
            throw new MyException(ResultEnum.CLASS_PSW_ERROR);
        }

        /** 加入到班级 */
        Student2Class student2Class = new Student2Class(studentId, classId);

        student2ClassRepository.save(student2Class);

        return ResultVOUtil.success();
    }

    /**
     * 查看学生所有的作业
     *
     * @param studentId 学生id
     * @return 作业信息
     */
    @Override
    public ResultVO getHomework(String studentId, int start, int size) {
        List<HomeworkVO> homeworkVOList = new ArrayList<>();

        Page<HomeworkDetail> homeworkDetailPage = homeworkDetailRepository.findAllByStudentIdOrderByUpdateTimeDesc(studentId, PageRequest.of(start, size));
        List<HomeworkDetail> homeworkDetailList = homeworkDetailPage.getContent();
        for (HomeworkDetail homeworkDetail : homeworkDetailList) {
            HomeworkVO homeworkVO = new HomeworkVO();

            Homework homework = homeworkRepository.findById(homeworkDetail.getHomeworkId()).get();
            Class mClass = classRepository.findByClassId(homework.getClassId());
            Teacher teacher = teacherRepository.findByTeacherId(mClass.getTeacherId());

            homeworkVO.setHomeworkId(homework.getHomeworkId());
            homeworkVO.setSubject(mClass.getClassSubject());
            homeworkVO.setHomeworkDesc(homework.getHomeworkDesc());
            homeworkVO.setTeacherName(teacher.getTeacherName());

            Integer homeworkStatus = homeworkDetail.getHomeworkStatus();
            homeworkVO.setStatus(homeworkStatus);
            if (homeworkStatus > 0) {
                homeworkVO.setSubmitTime(homeworkDetail.getSubmitTime());
            }

            homeworkVO.setBeginTime(homework.getCreateTime());
            homeworkVO.setEndTime(homework.getEndTime());

            homeworkVOList.add(homeworkVO);
        }
        return ResultVOUtil.success(new PageVo<>(homeworkDetailPage.getTotalPages(), homeworkDetailPage.getTotalElements(), homeworkVOList));
    }

    @Override
    public ResultVO getHomeworkDetail(String studentId, String homeworkId) {
        HomeworkDetailVO homeworkDetailVO = new HomeworkDetailVO();
        Homework homework = homeworkRepository.findById(homeworkId).get();
        if (Objects.isNull(homework)) {
            throw new MyException(ResultEnum.HOMEWORK_EMPTY);
        }
        HomeworkDetail homeworkDetail = homeworkDetailRepository.findByStudentIdAndHomeworkId(studentId, homeworkId);
        if (Objects.isNull(homeworkDetail)) {
            throw new MyException(ResultEnum.HOMEWORK_EMPTY);
        }
        Class mClss = classRepository.findByClassId(homework.getClassId());
        if (Objects.isNull(homeworkDetail)) {
            throw new MyException(ResultEnum.CLASS_NOT_EXIST);
        }
        Teacher teacher = teacherRepository.findByTeacherId(mClss.getTeacherId());
        if (Objects.isNull(homeworkDetail)) {
            throw new MyException(ResultEnum.TEACHER_EMPTY);
        }

        homeworkDetailVO.setHomeworkId(homeworkId);
        homeworkDetailVO.setClassId(mClss.getClassId());
        homeworkDetailVO.setClassName(mClss.getClassName());
        homeworkDetailVO.setSubject(mClss.getClassSubject());
        homeworkDetailVO.setHomeworkDesc(homework.getHomeworkDesc());
        homeworkDetailVO.setTeacherId(teacher.getTeacherId());
        homeworkDetailVO.setTeacherName(teacher.getTeacherName());
        homeworkDetailVO.setStatus(homeworkDetail.getHomeworkStatus());
        Integer homeworkStatus = homeworkDetail.getHomeworkStatus();
        homeworkDetailVO.setStatus(homeworkStatus);
        if (homeworkStatus > 0) {
            Integer number = homeworkDetail.getSubmitNumber();
            List<String> imageUrls = homeworkImageRepository.getImageUrlListByHomeworkDetailId(homeworkDetail.getId(), number);
            if (null != imageUrls) {
                homeworkDetailVO.setHomeworkImageUrls(imageUrls);
            }
            homeworkDetailVO.setSubmitTime(homeworkDetail.getSubmitTime());
        }
        homeworkDetailVO.setBeginTime(homework.getCreateTime());
        homeworkDetailVO.setEndTime(homework.getEndTime());

        return ResultVOUtil.success(homeworkDetailVO);
    }

    /**
     * 学生提交作业
     *
     * @param studentId:学生id
     * @param homeworkId:作业id
     * @param imageList,上传的图片
     * @return
     */
    @Override
    @Transactional
    public ResultVO submitHomework(String studentId, String homeworkId, List<String> imageList) {

        /** 查看作业是否存在 */
        Homework homework = homeworkRepository.findById(homeworkId).get();
        if (Objects.isNull(homework)) {
            throw new MyException(ResultEnum.HOMEWORK_EMPTY);
        }
        /** 查看是否已经截止 */
        if (homework.getEndTime().before(new Date())) {
            throw new MyException(ResultEnum.HOMEWORK_ALREADY_END);
        }

        HomeworkDetail homeworkDetail = homeworkDetailRepository.findByStudentIdAndHomeworkId(studentId, homeworkId);
        if (Objects.isNull(homeworkDetail)) {
            throw new MyException(ResultEnum.HOMEWORK_UNABLE_SUBMIT);
        }

        homeworkDetail.setSubmitTime(new Date()); //提交时间
        homeworkDetail.setHomeworkStatus(1); //以提交
        Integer number = homeworkDetail.getSubmitNumber() + 1;//获取提交的次数
        homeworkDetail.setSubmitNumber(number);
        for (String imageUrl : imageList) {
            if (uploadImageService.saveImage(imageUrl)) { //保存图片  如果图片之前没有
                HomeworkImage homeworkImage = new HomeworkImage();
                homeworkImage.setHomeworkId(homeworkDetail.getId());
                homeworkImage.setImageUrl(imageUrl);
                homeworkImage.setNumber(number);
                homeworkImageRepository.save(homeworkImage); //图片地址到数据库
            }
        }

        return ResultVOUtil.success();
    }

    @Override
    public ResultVO getAllClassInfo(String studentId, int pageNum, int size) {
        Page page = student2ClassRepository.findClassIdByStudentId(studentId, PageRequest.of(pageNum, size));
        List<String> classIdList = page.getContent();
        List<Class> classList = classRepository.findByClassIdIn(classIdList);

        List<ClassVO> classVoList = new ArrayList<>();
        for (Class mClass : classList) {
            String teacherName = teacherRepository.getTeacherNameByTeacherId(mClass.getTeacherId());
            Integer classNumber = student2ClassRepository.countByClassId(mClass.getClassId());
            ClassVO classVO = new ClassVO(mClass, teacherName, classNumber);
            classVO.setPassword(null);
            classVoList.add(classVO);
        }
        return ResultVOUtil.success(new PageVo<>(page.getTotalPages(), page.getTotalElements(), classVoList));
    }

    public ResultVO getStudent(int page, int size, String name, String sex, String phone, String classId) {

        PageRequest pageRequest = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "updateTime"));

        Page<Student> studentPage = studentRepository.findAll((Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Strings.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.like(root.get("studentName"), "%" + name + "%"));
            }
            if (Strings.isNotEmpty(sex)) {
                predicates.add(criteriaBuilder.equal(root.get("sex"), sex));
            }
            if (Strings.isNotEmpty(phone)) {
                predicates.add(criteriaBuilder.like(root.get("studentPhone"), "%" + phone + "%"));
            }
            if (Strings.isNotEmpty(classId)) {
                Path<Object> path = root.get("studentId");
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);

                List<String> studentIdList = student2ClassRepository.findStudentIdByClassId(classId);
                studentIdList.forEach(id -> {
                    in.value(id);
                });
                predicates.add(in);
            }

            Predicate lPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            return criteriaBuilder.and(lPredicate);
        }, pageRequest);

        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        studentPage.getContent().forEach(e -> {
            List<Map<String, String>> classMap = new ArrayList<>();
            classRepository.findClassMapByStudentId(e.getStudentId()).forEach(cl -> {
                Map<String, String> map = new HashMap<>();
                map.put("classId", cl[0].toString());
                map.put("className", cl[1].toString());
                classMap.add(map);
            });
            userInfoVOList.add(new UserInfoVO(e, classMap));
        });

        PageVo pageVo = new PageVo(studentPage.getTotalPages(), studentPage.getTotalElements(), userInfoVOList);
        return ResultVOUtil.success(pageVo);
    }

    @Override
    public ResultVO addStudent(UserInfoForm userInfoForm) {

        Student student = new Student("openid");
        student.setStudentInfo(userInfoForm);
        studentRepository.save(student);
        return ResultVOUtil.success();
    }


}
