package com.heo.homework.repository;

import com.heo.homework.entity.HomeworkDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkDetailRepository extends JpaRepository<HomeworkDetail,String>{
    Page<HomeworkDetail> findAllByStudentIdOrderByUpdateTimeDesc(String studentId, Pageable pageable);

    HomeworkDetail findByStudentIdAndHomeworkId(String studentId,String homeworkId);
}
