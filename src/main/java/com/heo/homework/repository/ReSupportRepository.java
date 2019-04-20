package com.heo.homework.repository;

import com.heo.homework.entity.ReSupport;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 刘康
 * @create 2019-04-17 19:35
 * @desc
 **/
public interface ReSupportRepository extends CrudRepository<ReSupport,Integer> {
    boolean existsByReIdAndUserId(Integer reId,String userId);
}
