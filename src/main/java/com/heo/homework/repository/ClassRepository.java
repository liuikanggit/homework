package com.heo.homework.repository;

import com.heo.homework.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class,String> {
}
