package com.heo.homework.repository;

import com.heo.homework.entity.HomeworkDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkDetailRepository extends CrudRepository<HomeworkDetail,String> {
    Page<HomeworkDetail> findAllByStudentIdOrderByUpdateTimeDesc(String studentId, Pageable pageable);

    HomeworkDetail findByStudentIdAndHomeworkId(String studentId,String homeworkId);

    @Query("select count(hd.id) from HomeworkDetail hd left join com.heo.homework.entity.Homework h on hd.homeworkId = h.homeworkId " +
            " where h.homeworkId = ?1 and h.classId = ?2 and hd.homeworkStatus > 0")
    Integer countBySubmitNum(String hId,String classId);
}
