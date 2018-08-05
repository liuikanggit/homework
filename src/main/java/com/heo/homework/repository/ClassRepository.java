package com.heo.homework.repository;

import com.heo.homework.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ClassRepository extends JpaRepository<Class,String> {

    @Query(value = "select classId from Class")
    List<String> findAllClassId();

    Class findByClassId(String classId);

}
