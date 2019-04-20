package com.heo.homework.repository;

import com.heo.homework.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class,String> {

    @Query(value = "select classId from Class")
    List<String> findAllClassId();

    Class findByClassId(String classId);

    List<Class> findByClassIdIn(List<String> classIdList);

    @Query(value = "select classId, className from Class")
    List<Object[]> findClassMap();

    @Query(value = "select c.class_id,c.class_name from class c where c.class_id in (select sc.class_id from student2class sc where sc.student_id = ?1)"
            ,nativeQuery = true)
    List<Object[]> findClassMapByStudentId(String studentId);

    @Query(value = "select classId from Class where teacherId = ?1")
    String getClassIdByTeacherId(String teacherId);

    @Query(value = "select classSubject from Class where classId = ?1")
    String getSubjectByClassId(String classId);

    List<Class> getAllByTeacherId(String teacherId);
}
