package com.heo.homework.repository;

import com.heo.homework.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student,String> {
    public Student findByOpenid(String openid);
    public Student findByStudentId(String studentId);
}
