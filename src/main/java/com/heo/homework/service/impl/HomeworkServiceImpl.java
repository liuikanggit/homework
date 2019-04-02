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
import com.heo.homework.vo.HomeworkSimpleVO;
import com.heo.homework.vo.HomeworkVO;
import com.heo.homework.vo.PageVo;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    private HomeworkDetailRepository homeworkDetailRepository;

    @Autowired
    private Student2ClassRepository student2ClassRepository;

    @Autowired
    private UploadImageServiceImpl uploadImageService;


    /**
     * 布置作业
     *
     * @param
     * @return
     */
    @Override
    @Transactional
    public ResultVO assignmentHomework(String teacherId, String[] classIds, String[] images, Date date, String desc) {
        for (String classId : classIds) {
            String imageList = "";
            for (String image : images) {
                if (images[0] != image) {
                    imageList += ",";
                }
                uploadImageService.saveImage(image);
                imageList += (image);
            }

            Homework homework = new Homework(classId, teacherId, imageList, date, desc);
            homeworkRepository.save(homework);

            /** 查出班级中所有的学生 */
            List<String> studentIdList = student2ClassRepository.findStudentIdByClassId(classId);
            for (String studentId : studentIdList) {
                HomeworkDetail homeworkDetail = new HomeworkDetail();
                homeworkDetail.setId(KeyUtil.genUniqueKey());
                homeworkDetail.setStudentId(studentId);
                homeworkDetail.setHomeworkId(homework.getHomeworkId());
                homeworkDetailRepository.save(homeworkDetail);
            }
        }
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO getCreateHomeworkEnd(String classId, int page, int size) {
        Page<HomeworkSimpleVO> homeworkSimpleVOPage
                = homeworkRepository.getHomeworkSimpleByClassIdEnd(classId, PageRequest.of(page, size));
        return ResultVOUtil.success(new PageVo(homeworkSimpleVOPage));
    }

    @Override
    public ResultVO getCreateHomeworkNotEnd(String classId, int page, int size) {
        Page<HomeworkSimpleVO> homeworkSimpleVOPage
                = homeworkRepository.getHomeworkSimpleByClassIdNotEnd(classId, PageRequest.of(page, size));
        return ResultVOUtil.success(new PageVo(homeworkSimpleVOPage));
    }

    @Override
    public ResultVO modifyHomework(HomeworkForm homeworkForm) {
        return null;
    }
}
