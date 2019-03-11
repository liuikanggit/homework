package com.heo.homework.repository;

import com.heo.homework.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,String>,JpaSpecificationExecutor<Student> {
    Student findByOpenid(String openid);

    Student findByStudentId(String studentId);

    @Query(value = "select studentId  from Student")
    List<String> findAllStudentId();

}
