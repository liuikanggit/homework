package com.heo.homework.repository;

import com.heo.homework.entity.HomeworkImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkImageRepository extends JpaRepository<HomeworkImage,Integer>{

    @Query("select imageUrl from HomeworkImage where homeworkId = ?1 and del = 0")
    List<String> getImageUrlListByHomeworkDetailId(String homeworkId);

    @Query("select imageUrl from HomeworkImage where homeworkId = ?1 and number = ?2 and del = 0 and correction is null ")
    List<String> getImageUrlListByHomeworkDetailId(String homeworkId,Integer number);

    @Query("select imageUrl from HomeworkImage where homeworkId = ?1 and number = ?2 and del = 0 and correction is not null ")
    List<String> getImageUrlListByHomeworkDetailId2(String homeworkId,Integer number);

    HomeworkImage getByHomeworkIdAndNumber(String homeworkId,Integer number);

}
