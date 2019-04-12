package com.heo.homework.service.impl;

import com.heo.homework.config.TemplateIDConfig;
import com.heo.homework.constant.HomeworkStatus;
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
import com.heo.homework.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    private HomeworkDetailRepository homeworkDetailRepository;

    @Autowired
    private HomeworkImageRepository homeworkImageRepository;

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
    public ResultVO getHomeworkDetail(String homeworkId) {
        HomeworkDetailVO homeworkDetailVOBy = homeworkRepository.findHomeworkDetailVOBy(homeworkId);
        homeworkDetailVOBy.setUnCorrectStudent(homeworkDetailRepository.getUserSimpleVOByHomeworkStatusIsSubmit(homeworkId));
        homeworkDetailVOBy.setCorrectStudent(homeworkDetailRepository.getUserSimpleVOByHomeworkStatusIsOver(homeworkId));
        return ResultVOUtil.success(homeworkDetailVOBy);
    }

    @Override
    public ResultVO getHomeworkImage(String homeworkId, String studentId) {
        HomeworkDetail homeworkDetail = homeworkDetailRepository.getByHomeworkIdAndStudentId(homeworkId, studentId);
        List<String> image = homeworkImageRepository.getImageUrlListByHomeworkDetailId(homeworkDetail.getId(), homeworkDetail.getSubmitNumber());
        return ResultVOUtil.success(image);
    }

    @Override
    @Transactional
    public ResultVO correctionHomeworkImage(String homeworkId, String userId, Integer score, String comment, String[] images) {
        HomeworkDetail homeworkDetail = homeworkDetailRepository.getByHomeworkIdAndStudentId(homeworkId, userId);
        homeworkDetail.setCheckTime(new Date());
        homeworkDetail.setHomeworkStatus(HomeworkStatus.PASS);
        homeworkDetail.setScore(score);
        homeworkDetail.setComment(comment);
        homeworkDetailRepository.save(homeworkDetail);
        List<HomeworkImage> homeworkImages =  Arrays.stream(images).map(image ->{
            uploadImageService.saveImage(image);
            HomeworkImage homeworkImage = new HomeworkImage();
            homeworkImage.setHomeworkId(homeworkDetail.getId());
            homeworkImage.setImageUrl(image);
            homeworkImage.setNumber(homeworkDetail.getSubmitNumber());
            homeworkImage.setCorrection(1);
            return homeworkImage;
        }).collect(Collectors.toList());
        homeworkImageRepository.saveAll(homeworkImages);
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO modifyHomework(HomeworkForm homeworkForm) {
        return null;
    }
}
