package com.heo.homework.repository;

import com.heo.homework.entity.Post;
import com.heo.homework.vo.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 刘康
 * @create 2019-03-10 14:10
 * @desc 帖子dao
 **/
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query(value = "select new com.heo.homework.vo.PostVO(p.id,s.studentName,s.studentAvatarUrl,p.title,p.content,p.image,0,0,p.createTime) from Post p left join com.heo.homework.entity.Student s on p.creatorId = s.studentId",
            countQuery = "select count(id) from Post")
    Page<PostVO> getAllPost(Pageable pageable);
}
