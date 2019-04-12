package com.heo.homework.repository;

import com.heo.homework.entity.Homework;
import com.heo.homework.vo.HomeworkDetailVO;
import com.heo.homework.vo.HomeworkSimpleVO;
import com.heo.homework.vo.UserSimpleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends CrudRepository<Homework, String> {

    Homework getByHomeworkId(String homeworkId);

    Page<Homework> getHomeworkByTeacherIdOrderByEndTime(String teacherId, Pageable pageable);

    @Query(value = "select new com.heo.homework.vo.HomeworkSimpleVO(h.homeworkId,c.className,c.classSubject,h.homeworkDesc,h.createTime,h.endTime,false ) " +
            "from Homework h " +
            "left join com.heo.homework.entity.Class c on h.classId = c.classId " +
            "where h.classId = ?1 and h.endTime > current_date  order by h.endTime",
            countQuery = "select count(h.id) from Homework h left join com.heo.homework.entity.Class c on h.classId = c.classId")
    Page<HomeworkSimpleVO> getHomeworkSimpleByClassIdNotEnd(String teacherId, Pageable pageable);

    @Query(value = "select new com.heo.homework.vo.HomeworkSimpleVO(h.homeworkId,c.className,c.classSubject,h.homeworkDesc,h.createTime,h.endTime,true ) " +
            "from Homework h " +
            "left join com.heo.homework.entity.Class c on h.classId = c.classId " +
            "where h.classId = ?1 and h.endTime <= current_date  order by h.endTime",
            countQuery = "select count(h.id) from Homework h left join com.heo.homework.entity.Class c on h.classId = c.classId")
    Page<HomeworkSimpleVO> getHomeworkSimpleByClassIdEnd(String teacherId, Pageable pageable);

    @Query(value = "select count(h.homeworkId) from Homework h where h.classId = ?1 and h.endTime > current_date")
    Integer countByClassIdNotEnd(String classId);

    @Query(value = "select count(h.homeworkId) from Homework h where h.classId = ?1 and h.endTime <= current_date")
    Integer countByClassIdEnd(String classId);

    @Query( value = "select new com.heo.homework.vo.HomeworkDetailVO(h.homeworkId,h.createTime,h.endTime,h.homeworkDesc," +
            "h.image,count(hd1),count(hd2),count(hd3),coalesce(sum(hd3.score),0) ) from Homework h " +
            "left join com.heo.homework.entity.HomeworkDetail hd1 on hd1.homeworkId = h.homeworkId and hd1.homeworkStatus in (-1,0)" +
            "left join com.heo.homework.entity.HomeworkDetail hd2 on hd2.homeworkId = h.homeworkId and hd2.homeworkStatus > 0 " +
            "left join com.heo.homework.entity.HomeworkDetail hd3 on hd3.homeworkId = h.homeworkId and hd3.homeworkStatus = 2 " +
            "where h.homeworkId = ?1 order by h.homeworkId")
    HomeworkDetailVO findHomeworkDetailVOBy(String homeworkId);


}
