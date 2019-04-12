package com.heo.homework.service.impl;

import com.heo.homework.entity.Class;
import com.heo.homework.entity.Teacher;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.form.ClassForm;
import com.heo.homework.form.UserInfoForm;
import com.heo.homework.repository.ClassRepository;
import com.heo.homework.repository.Student2ClassRepository;
import com.heo.homework.repository.TeacherRepository;
import com.heo.homework.repository.UserSupportRepository;
import com.heo.homework.service.RedisService;
import com.heo.homework.service.TeacherService;
import com.heo.homework.service.WechatLoginService;
import com.heo.homework.service.WechatMessageService;
import com.heo.homework.utils.KeyUtil;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ClassVO;
import com.heo.homework.vo.ResultVO;
import com.heo.homework.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private WechatLoginService wechatLoginService;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserSupportRepository userSupportRepository;

    @Autowired
    private Student2ClassRepository student2ClassRepository;


    @Value("${backDoorCode}")
    private String backDoorCode;


    @Override
    public ResultVO login(String code, String[] formId, String nickName, String avatarUrl, String gender) {
        String openid;
        /** Back door code */
        if (!Strings.isEmpty(backDoorCode) && code.equals(backDoorCode)){
            openid = "test_openid";
        }else{
            /** 用code去换openid */
            openid = wechatLoginService.auth(code);
        }

        /** 判断是否第一次登录 */
        Teacher teacher = teacherRepository.findByOpenid(openid);
        if( teacher == null){
            /** 第一次登录 创建一个学生 */
            teacher = new Teacher(openid);
            teacher.setTeacherName(nickName);
            teacher.setTeacherAvatarUrl(avatarUrl);
            //性别 0：未知、1：男、2：女
            if ("0".equals(gender)){
                teacher.setSex("未填写");
            }else if ("1".equals(gender)){
                teacher.setSex("男");
            }else if ("2".equals(gender)){
                teacher.setSex("女");
            }
            teacher = teacherRepository.save(teacher);
            log.info("有新的教师注册成功了,{}",teacher);
            if (formId != null){
                for (String aFormId:formId)
                    redisService.saveFormId(teacher.getTeacherId(),aFormId);
            }
            wechatMessageService.sendRegisterNotice(teacher.getTeacherId(),teacher.getOpenid(),nickName,"学生");

        }
        /** 登录返回token */
        String token = redisService.login(teacher.getTeacherId());
        return ResultVOUtil.success(token);
    }

    /**
     * 获取教师信息
     * @param teacherId
     * @return
     */
    @Override
    public ResultVO getTeacherInfo(String teacherId) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId);
        if (Objects.isNull(teacher)){
            throw new MyException(ResultEnum.TEACHER_EMPTY);
        }
        UserInfoVO userInfoVO = new UserInfoVO(teacher);
        userInfoVO.setLikedNum(userSupportRepository.getLikeNumByLikedUserId(teacherId));
        return ResultVOUtil.success(userInfoVO);
    }

    /**
     * 修改教师资料
     * @param teacherInfoForm
     * @return
     */
    @Override
    public ResultVO modifyTeacherInfo(String teacherId,UserInfoForm teacherInfoForm) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId);
        if (Objects.isNull(teacher)){
            throw new MyException(ResultEnum.TEACHER_EMPTY);
        }

        /** 更新教师资料 */
        teacher.setTeacherInfo(teacherInfoForm);
        teacher = teacherRepository.save(teacher);

        /** 返回最新的资料 */
        UserInfoVO userInfoVO = new UserInfoVO(teacher);
        return ResultVOUtil.success(userInfoVO);
    }

    /**
     * 根据传过来的表单，创建班级
     * @param classForm
     */
    @Override
    @Transactional
    public ResultVO createClass(String teacherId,ClassForm classForm) {
        Class mClass = new Class();
        /** 设置基本信息 名称，头像，年级，科目，描述，密码，创建教师id */
        mClass.setClassInfo(classForm);
        mClass.setTeacherId(teacherId);
        mClass.setClassId(KeyUtil.getClassKey(classRepository.findAllClassId()));
        classRepository.save(mClass);
        mClass = classRepository.getOne(mClass.getClassId());
        ClassVO classVO = new ClassVO(mClass,0);
        return ResultVOUtil.success(classVO);
    }

    /**
     * 修改班级信息
     * @param classForm
     * @return
     */
    @Override
    @Transactional
    public ResultVO modifyClass(String teacherId,String classId,ClassForm classForm) {

        Class mClass = classRepository.findByClassId(classId);
        if (Objects.isNull(mClass)){
            throw new MyException(ResultEnum.CLASS_NOT_EXIST);
        }

        /** 班级创建的id与老师id不一致 抛出异常 */
        if (!mClass.getTeacherId().equals(teacherId)){
            throw new MyException(ResultEnum.NO_AUTH);
        }
        mClass.setClassInfo(classForm);
        mClass.setTeacherId(teacherId);
        mClass = classRepository.save(mClass);

        Integer count = student2ClassRepository.countByClassId(mClass.getClassId());
        ClassVO classVO = new ClassVO(mClass,count);
        return ResultVOUtil.success(classVO);
    }


}
