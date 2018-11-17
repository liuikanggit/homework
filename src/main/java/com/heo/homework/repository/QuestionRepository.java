package com.heo.homework.repository;

import com.heo.homework.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,String>{
}
