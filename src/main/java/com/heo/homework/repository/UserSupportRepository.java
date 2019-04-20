package com.heo.homework.repository;

import com.heo.homework.entity.UserSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;


/**
 * @author 刘康
 * @create 2019-03-11 14:24
 * @desc 用户点赞dao
 **/
public interface UserSupportRepository extends CrudRepository<UserSupport,Integer> {

    @Query(value = "select us from UserSupport us where us.userId = ?1 and us.likedUserId = ?2 and us.createTime = current_date")
    UserSupport findTodayUserSupport(String userId, String likedUserId);

    @Query(value = "select coalesce(SUM(us.num),0) from UserSupport us where us.likedUserId = ?1")
    Integer getLikeNumByLikedUserId(String userId);


    @Query("select u from UserSupport u where u.userId=?1 and u.likedUserId = ?2 and u.createTime = current_date")
    UserSupport existsByUserIdAndLikedUserId(String userId,String likeUserId);
}
