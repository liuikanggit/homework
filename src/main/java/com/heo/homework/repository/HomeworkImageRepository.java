package com.heo.homework.repository;

import com.heo.homework.entity.HomeworkImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkImageRepository extends JpaRepository<HomeworkImage,Integer>{

    @Query("select imageUrl from HomeworkImage where homeworkDetailId = ?1 and del = 0")
    List<String> getImageUrlListByHomeworkDetailId(String homeworkDetailId);

    @Query("select imageUrl from HomeworkImage where homeworkDetailId = ?1 and number = ?2 and del = 0")
    List<String> getImageUrlListByHomeworkDetailId(String homeworkDetailId,Integer number);
}
