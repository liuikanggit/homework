package com.heo.homework.repository;

import com.heo.homework.entity.PostSupport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 刘康
 * @create 2019-03-11 10:05
 * @desc
 **/
public interface PostSupportRepository extends CrudRepository<PostSupport,Integer> {

    @Modifying
    @Query(value = "delete FROM PostSupport ps where ps.postId = ?1 and ps.userId = ?2")
    void delete(Integer postId,String userId);

    boolean existsByPostIdAndUserId(Integer postId,String userId);

    Integer countByPostId(Integer postId);

}
