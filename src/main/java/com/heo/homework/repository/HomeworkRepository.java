package com.heo.homework.repository;

import com.heo.homework.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework,String>{
}
