package com.heo.homework.repository;

import com.heo.homework.entity.HomeworkDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkDetailRepository extends JpaRepository<HomeworkDetail,String>{
    List<HomeworkDetail> findAllByStudentIdOrderByUpdateTimeDesc(String studentId);

    HomeworkDetail findByStudentIdAndHomeworkId(String studentId,String homeworkId);
}
