package com.heo.homework.vo;

import lombok.Data;

/**
 * @author 刘康
 * @create 2019-04-01 16:06
 * @desc 用户的简单信息
 **/
@Data
public class UserSimpleVO {

    private String id;

    private String name;

    private String avatar;

    public UserSimpleVO(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
