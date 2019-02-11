package com.heo.homework.service.impl;

import com.heo.homework.config.TemplateIDConfig;
import com.heo.homework.dto.MessageParam;
import com.heo.homework.entity.*;
import com.heo.homework.entity.Class;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.form.HomeworkForm;
import com.heo.homework.repository.*;
import com.heo.homework.service.HomeworkService;
import com.heo.homework.service.WechatMessageService;
import com.heo.homework.utils.DateUtil;
import com.heo.homework.utils.KeyUtil;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class HomeworkServiceImpl implements HomeworkService{

    @Autowired
    private HomeworkRepository homeworkRepository;


    @Autowired
    private HomeworkDetailRepository homeworkDetailRepository;

    @Autowired
    private Student2ClassRepository student2ClassRepository;

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassRepository classRepository;


    @Autowired
    private TemplateIDConfig templateIDConfig;

    /**
     * 布置作业
     * @param homeworkForm  班级id 作业描述 截至日期
     * @return
     */
    @Override
    @Transactional
    public ResultVO assignmentHomework(HomeworkForm homeworkForm) {
        Homework homework = new Homework(homeworkForm);

        homeworkRepository.save(homework);

        /** 查出班级中所有的学生 */
        String classId = homeworkForm.getClassId();
        List<String> studentIdList = student2ClassRepository.findStudentIdByClassId(classId);
        for (String studentId : studentIdList){
            HomeworkDetail homeworkDetail = new HomeworkDetail();
            homeworkDetail.setId(KeyUtil.genUniqueKey());
            homeworkDetail.setStudentId(studentId);
            homeworkDetail.setHomeworkId(homework.getHomeworkId());
            homeworkDetailRepository.save(homeworkDetail);

            /** 推送信息给学生 */
            Student student = studentRepository.findByStudentId(studentId);
            String teacherName = teacherRepository.getTeacherNameByTeacherId(homeworkForm.getId());
            Class mClass = classRepository.findByClassId(homework.getClassId());
            MessageParam messageParam = new MessageParam(student.getStudentId(),student.getOpenid(), templateIDConfig.getHomeworkNotice(),templateIDConfig.getHomeworkPath())
                    .addData(student.getStudentName()) //学生姓名
                    .addData(teacherName)  //发布人
                    .addData(mClass.getClassName())  //班级名称
                    .addData(mClass.getClassSubject())  //科目
                    .addData(homework.getHomeworkDesc())  //作业内容
                    .addData(DateUtil.formatter(homework.getCreateTime(),"yyyy年MM月dd日"))  //发布时间
                    .addData(DateUtil.formatter(homework.getEndTime(),"yyyy年MM月dd日")); //完成时间
            wechatMessageService.sendMessage(messageParam);

        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO modifyHomework(HomeworkForm homeworkForm) {
        return null;
    }
}
