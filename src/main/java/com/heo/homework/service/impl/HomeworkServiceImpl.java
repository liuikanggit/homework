package com.heo.homework.service.impl;

import com.heo.homework.constant.HomeworkStatus;
import com.heo.homework.entity.Homework;
import com.heo.homework.entity.HomeworkDetail;
import com.heo.homework.entity.HomeworkImage;
import com.heo.homework.form.HomeworkForm;
import com.heo.homework.repository.*;
import com.heo.homework.service.HomeworkService;
import com.heo.homework.service.WechatMessageService;
import com.heo.homework.utils.DateUtil;
import com.heo.homework.utils.KeyUtil;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.HomeworkDetailVO;
import com.heo.homework.vo.HomeworkSimpleVO;
import com.heo.homework.vo.PageVo;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private WechatMessageService wechatMessageService;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TeacherRepository teacherRepository;


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
            homework = homeworkRepository.save(homework);
            final String id = homework.getHomeworkId();

            /** 查出班级中所有的学生 */
            List<String> studentIdList = student2ClassRepository.findStudentIdByClassId(classId);
            for (String studentId : studentIdList) {
                HomeworkDetail homeworkDetail = new HomeworkDetail();
                homeworkDetail.setId(KeyUtil.genUniqueKey());
                homeworkDetail.setStudentId(studentId);
                homeworkDetail.setHomeworkId(homework.getHomeworkId());
                homeworkDetailRepository.save(homeworkDetail);
            }

            String subjectName = classRepository.getSubjectByClassId(classId);
            String teacherName = teacherRepository.getTeacherNameByTeacherId(teacherId);
            studentIdList.stream().forEach(sId -> wechatMessageService.sendHomeworkNotice(sId,id,subjectName,teacherName,desc, DateUtil.formatter(new Date(),"yyyy-MM-dd HH:mm"),DateUtil.formatter(date,"yyyy-MM-dd HH:mm")));
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
