package com.heo.homework.repository;

import com.heo.homework.entity.Post;
import com.heo.homework.vo.PostDetailVO;
import com.heo.homework.vo.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 刘康
 * @create 2019-03-10 14:10
 * @desc 帖子dao
 **/
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query(value = "select new com.heo.homework.vo.PostVO(p.id,s.studentId,s.studentName,s.studentAvatarUrl,p.title,p.content,p.image,p.look,p.createTime) from Post p " +
            "left join com.heo.homework.entity.Student s on p.creatorId = s.studentId " +
            "order by p.createTime desc ",
            countQuery = "select count(id) from Post")
    Page<PostVO> getAllPost(Pageable pageable);

    @Query(value = "select new com.heo.homework.vo.PostVO(p.id,s.studentId,s.studentName,s.studentAvatarUrl,p.title,p.content,p.image,p.look,p.createTime) from Post p " +
            "left join com.heo.homework.entity.Student s on p.creatorId = s.studentId " +
            "where p.creatorId = ?1 " +
            "order by p.createTime desc ",
            countQuery = "select count(id) from Post where creatorId = ?1")
    Page<PostVO> getPostVO(String userId,Pageable pageable);


    @Query(value = "update Post p set p.look = p.look+1 where p.id = ?1")
    @Modifying
    void look(Integer postId);

    @Query(value = "select new com.heo.homework.vo.PostDetailVO(p.id,s.studentId,s.studentName,s.studentAvatarUrl,p.title,p.content,p.image,p.look,p.createTime) from Post p " +
            "left join com.heo.homework.entity.Student s on p.creatorId = s.studentId " +
            "where p.id = ?1 ")
    PostDetailVO getPostDetailVO(Integer postId);

}
