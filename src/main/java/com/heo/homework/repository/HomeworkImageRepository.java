package com.heo.homework.repository;

import com.heo.homework.entity.HomeworkImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeworkImageRepository extends JpaRepository<HomeworkImage,Integer>{

    @Query("select imageUrl from HomeworkImage where homeworkDetailId = homeworkDetailId and del = 0")
    List<String> getImageUrlListByHomeworkDetailId(String homeworkDetailId);
}
