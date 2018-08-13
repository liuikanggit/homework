package com.heo.homework.repository;

import com.heo.homework.entity.Student2Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Student2ClassRepository extends JpaRepository<Student2Class,Integer>{
    Student2Class findByStudentIdAndAndClassId(String studentId,String classId);
}
