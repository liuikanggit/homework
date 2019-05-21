package com.heo.homework.vo;

import com.heo.homework.utils.DateUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-17 19:49
 * @desc
 **/
@Data
public class PostDetailVO {

    private Integer id;

    private String userId;

    private String username;

    private String avatar;

    private String title;

    private String content;

    private List<String> images;

    private String createTime;
    /**
     * 浏览量
     */
    private Integer lookNum;

    private Integer likeNum;
    private boolean isLike;

    private Integer commentNum;

    private List<RePostVO> hotRe;

    private List<RePostVO> re;

    public PostDetailVO(Integer id, String userId, String username, String avatar, String title, String content, String image, Integer lookNum, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.title = title;
        this.content = content;
        if (image.length() != 0) {
            this.images = Arrays.asList(image.split(","));
        } else {
            this.images = new ArrayList<>();
        }
        this.lookNum = lookNum;
        this.createTime = DateUtil.formatter(createTime, "yy-MM-dd HH:mm");
    }


}
