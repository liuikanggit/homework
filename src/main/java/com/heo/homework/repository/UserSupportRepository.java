package com.heo.homework.repository;

import com.heo.homework.entity.UserSupport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



/**
 * @author 刘康
 * @create 2019-03-11 14:24
 * @desc
 **/
public interface UserSupportRepository extends CrudRepository<UserSupport,Integer> {

    @Query(value = "select us from UserSupport us where us.userId = ?1 and us.likedUserId = ?2 and us.createTime = current_date")
    UserSupport findTodayUserSupport(String userId, String likedUserId);

}
