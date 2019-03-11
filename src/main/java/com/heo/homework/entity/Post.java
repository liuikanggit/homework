package com.heo.homework.entity;

import javafx.geometry.Pos;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-03-10 14:02
 * @desc 帖子
 **/
@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String creatorId;

    private String title;

    private String content;

    private String image;

    private Date createTime;

    public Post(){}

    public Post(String creatorId,String title,String content,String image){
        this.creatorId = creatorId;
        this.title = title;
        this.content = content;
        this.image = image;
    }

}