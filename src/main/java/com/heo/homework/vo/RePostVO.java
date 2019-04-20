package com.heo.homework.vo;

import com.heo.homework.utils.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-17 19:50
 * @desc
 **/
@Data
public class RePostVO {

    private Integer id;

    private String userId;

    private String username;

    private String avatar;

    private String reUserId;

    private String reUsername;

    private String content;

    private String createTime;

    private Long likeNum;

    private boolean isLike;

    public RePostVO(Integer id, String userId, String username1, String username2, String avatar1, String avatar2, String reUserId, String reUsername1, String reUsername2, String content, Date createTime,Long likeNum) {
        this.id = id;
        this.userId = userId;
        this.username = username1 != null ? username1 : username2;
        this.avatar = avatar1 != null ? avatar1 : avatar2;
        this.reUserId = reUserId;
        this.reUsername = reUsername1!=null?reUsername1:reUsername2;
        this.content = content;
        this.createTime = DateUtil.formatter(createTime,"yy-MM-dd hh:mm");
        this.likeNum = likeNum;
    }

}
