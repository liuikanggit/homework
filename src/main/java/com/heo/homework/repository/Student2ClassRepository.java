package com.heo.homework.repository;

import com.heo.homework.entity.Student2Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Student2ClassRepository extends CrudRepository<Student2Class,Integer> {

    Student2Class findByStudentIdAndClassId(String studentId,String classId);

    Integer countByClassId(String classId);

    @Query(value = "select studentId from student2class where classId = ?1")
    List<String> findStudentIdByClassId(String classId);

    @Query(value = "select class_id from student2class where student_id = ?1",
            countQuery = "select count(*) from student2class where student_id = ?1",
            nativeQuery = true)
    Page findClassIdByStudentId(String studentId, Pageable pageable);
}
