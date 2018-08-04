package com.heo.homework.repository;

import com.heo.homework.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,String>{
    public Teacher findByOpenid(String openid);
    public Teacher findByTeacherId(String teacherId);
}
