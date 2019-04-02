package com.heo.homework.repository;

import com.heo.homework.entity.Homework;
import com.heo.homework.vo.HomeworkSimpleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends CrudRepository<Homework, String> {

    Page<Homework> getHomeworkByTeacherIdOrderByEndTime(String teacherId, Pageable pageable);

    @Query(value = "select new com.heo.homework.vo.HomeworkSimpleVO(h.homeworkId,c.className,c.classSubject,h.createTime,h.endTime,false ) " +
            "from Homework h " +
            "left join com.heo.homework.entity.Class c on h.classId = c.classId " +
            "where h.classId = ?1 and h.endTime > current_date  order by h.endTime",
            countQuery = "select count(h.id) from Homework h left join com.heo.homework.entity.Class c on h.classId = c.classId")
    Page<HomeworkSimpleVO> getHomeworkSimpleByClassIdNotEnd(String teacherId, Pageable pageable);

    @Query(value = "select new com.heo.homework.vo.HomeworkSimpleVO(h.homeworkId,c.className,c.classSubject,h.createTime,h.endTime,true ) " +
            "from Homework h " +
            "left join com.heo.homework.entity.Class c on h.classId = c.classId " +
            "where h.classId = ?1 and h.endTime <= current_date  order by h.endTime",
            countQuery = "select count(h.id) from Homework h left join com.heo.homework.entity.Class c on h.classId = c.classId")
    Page<HomeworkSimpleVO> getHomeworkSimpleByClassIdEnd(String teacherId, Pageable pageable);

    @Query(value = "select count(h.homeworkId) from Homework h where h.classId = ?1 and h.endTime > current_date")
    Integer countByClassIdNotEnd(String classId);

    @Query(value = "select count(h.homeworkId) from Homework h where h.classId = ?1 and h.endTime <= current_date")
    Integer countByClassIdEnd(String classId);

}
