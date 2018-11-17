package com.heo.homework.repository;

import com.heo.homework.entity.Student2Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Student2ClassRepository extends JpaRepository<Student2Class,Integer>{
    Student2Class findByStudentIdAndClassId(String studentId,String classId);

    @Query(value = "select studentId from student2class where classId = ?1")
    List<String> findStudentIdByClassId(String classId);

}
