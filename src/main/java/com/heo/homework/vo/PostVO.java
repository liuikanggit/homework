package com.heo.homework.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heo.homework.utils.DateSerializer;
import javafx.geometry.Pos;
import lombok.Data;

import java.util.Date;

/**
 * @author 刘康
 * @create 2019-03-10 14:29
 * @desc 返回给前端的post实体类
 **/
@Data
public class PostVO {

    private Integer id;

    private String username;

    private String avatar;

    private String title;

    private String content;

    private String image;

    private Integer supportNum;

    private Integer commentNum;

    @JsonSerialize(using = DateSerializer.class)
    private Date createTime;

    public PostVO(Integer id,String username,String avatar,String title,String content,String image,Integer supportNum,Integer commentNum,Date createTime){
        this.id = id;
        this.username = username;
        this.avatar =avatar;
        this.title =title;
        this.content = content;
        this.image = image;
        this.supportNum = supportNum;
        this.commentNum = commentNum;
        this.createTime = createTime;
    }

}
