package com.heo.homework.repository;

import com.heo.homework.entity.HomeworkDetail;
import com.heo.homework.vo.UserSimpleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkDetailRepository extends CrudRepository<HomeworkDetail,String> {
    Page<HomeworkDetail> findAllByStudentIdOrderByUpdateTimeDesc(String studentId, Pageable pageable);

    HomeworkDetail findByStudentIdAndHomeworkId(String studentId,String homeworkId);

    @Query("select count(hd.id) from HomeworkDetail hd left join com.heo.homework.entity.Homework h on hd.homeworkId = h.homeworkId " +
            " where h.homeworkId = ?1 and h.classId = ?2 and hd.homeworkStatus > 0")
    Integer countBySubmitNum(String hId,String classId);

    @Query("select new com.heo.homework.vo.UserSimpleVO(s.studentId,s.studentName,s.studentAvatarUrl) from com.heo.homework.entity.Student s " +
            "left join HomeworkDetail hd on hd.studentId = s.studentId " +
            "where hd.homeworkStatus = 1 and hd.homeworkId = ?1")
    List<UserSimpleVO> getUserSimpleVOByHomeworkStatusIsSubmit(String homeworkId);

    @Query("select new com.heo.homework.vo.UserSimpleVO(s.studentId,s.studentName,s.studentAvatarUrl) from com.heo.homework.entity.Student s " +
            "left join HomeworkDetail hd on hd.studentId = s.studentId " +
            "where hd.homeworkStatus = 2 and hd.homeworkId = ?1")
    List<UserSimpleVO> getUserSimpleVOByHomeworkStatusIsOver(String homeworkId);
}
