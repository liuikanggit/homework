package com.heo.homework.repository;

import com.heo.homework.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,String>{
    Teacher findByOpenid(String openid);
    Teacher findByTeacherId(String teacherId);

    @Query(value = "select teacherId  from Teacher ")
    List<String> findAllTeacehrId();

    @Query(value = "select teacherName from Teacher where teacherId = ?1")
    String getTeacherNameByTeacherId(String teacherId);
}
