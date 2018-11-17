package com.heo.homework.repository;

import com.heo.homework.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,String> {
    Student findByOpenid(String openid);
    Student findByStudentId(String studentId);

    @Query(value = "select studentId  from Student")
    List<String> findAllStudentId();


}
