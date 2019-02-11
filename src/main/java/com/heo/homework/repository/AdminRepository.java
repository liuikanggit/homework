package com.heo.homework.repository;

import com.heo.homework.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Admin findByUserName(String userName);
}
