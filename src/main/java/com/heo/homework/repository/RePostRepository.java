package com.heo.homework.repository;

import com.heo.homework.entity.RePost;
import com.heo.homework.vo.RePostVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-17 19:35
 * @desc
 **/
public interface RePostRepository extends CrudRepository<RePost,Integer> {

    Integer countByPostId(Integer postId);

    @Query(value = "select new com.heo.homework.vo.RePostVO(rp.id,rp.userId,s1.studentName,t1.teacherName,s1.studentAvatarUrl,t1.teacherAvatarUrl,rp2.userId,s2.studentName,t2.teacherName,rp.content,rp.createTime,(select count(rs) from com.heo.homework.entity.ReSupport rs where rs.reId = rp.id)) from RePost rp " +
            "left join com.heo.homework.entity.Student s1 on s1.studentId = rp.userId "+
            "left join com.heo.homework.entity.Teacher t1 on t1.teacherId = rp.userId " +
            "left join RePost rp2 on rp2.id = rp.reId " +
            "left join com.heo.homework.entity.Student s2 on s2.studentId = rp2.userId " +
            "left join com.heo.homework.entity.Teacher t2 on t2.teacherId = rp2.userId " +
            "where rp.postId = ?1 "+
            "order by rp.createTime ")
    List<RePostVO> getRePostVOByPostId(Integer postId);
}
