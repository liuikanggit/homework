package com.heo.homework.repository;

import com.heo.homework.entity.Homework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends CrudRepository<Homework,String> {

    Page<Homework> getHomeworkByTeacherIdOrderByEndTime(String teacherId, Pageable pageable);

}
