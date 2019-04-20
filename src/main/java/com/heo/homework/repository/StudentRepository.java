package com.heo.homework.repository;

import com.heo.homework.entity.Student;
import com.heo.homework.vo.UserSimpleVO;
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

    @Query("select s.openid from Student s where s.studentId = ?1")
    String getOpenidByStudentId(String studentId);

    @Query(value = "select studentId  from Student")
    List<String> findAllStudentId();

    @Query("select new com.heo.homework.vo.UserSimpleVO(s.studentId,s.studentName,s.studentAvatarUrl) " +
            "from com.heo.homework.entity.Student2Class sc left join Student s on sc.studentId = s.studentId " +
            "where sc.classId = ?1")
    List<UserSimpleVO> getUserSimpleByClassId(String classId);

}
