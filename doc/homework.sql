CREATE TABLE post (
  `id` INT NOT NULL AUTO_INCREMENT  PRIMARY KEY COMMENT '主键',
  `creator_id` VARCHAR(32) NOT NULL COMMENT '创建者',
  `title` VARCHAR(100) COMMENT '标题',
  `content` VARCHAR(512) COMMENT '内容',
  `image` VARCHAR(512) COMMENT '图片列表',
  `look` int not null default 0 comment '浏览量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  foreign key (`creator_id`) references student(student_id) on delete cascade on update cascade
) ENGINE = InnoDB  COMMENT '帖子';

CREATE TABLE post_support (
  `id` int not null AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
  `post_id` int not null comment '帖子id',
  `user_id` varchar(32) not null comment '点赞的用户id',
  `user_type` int(1) not null comment '用户类型，0:学生 1:教师，',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  foreign key (`post_id`) references post(id)  on delete cascade on update cascade,
  UNIQUE KEY `post_id2user_id` (`post_id`,`user_id`)
) COMMENT '帖子点赞';

CREATE TABLE user_support(
    `id` int not null AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `user_id` VARCHAR(32) NOT NULL COMMENT '点赞人id',
    `liked_user_id` VARCHAR(32) NOT NULL COMMENT '被点赞的数量',
    `num` INT NOT NULL COMMENT '被点赞的次数，一天只能被点10次',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
)COMMENT '用户点赞';


CREATE TABLE re_post(
  `id` int not null AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
  `post_id` int not null  COMMENT '被回复的帖子的id',
  `re_id` int not null COMMENT '回复的id',
  `user_id` VARCHAR(32) NOT NULL COMMENT '回复人',
  `user_type` int(1) not null comment '用户类型，0:学生 1:教师，',
  `content` VARCHAR(512) NOT NULL COMMENT '回复的内容',
  `image` VARCHAR(512) COMMENT '图片',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  foreign key (`re_id`) references re_post(id)  on delete cascade on update cascade,
  foreign key (`post_id`) references post(id)  on delete cascade on update cascade
) COMMENT '评论';

CREATE TABLE re_support (
      `id` int not null AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
      `re_id` int not null comment '评论id',
      `user_id` varchar(32) not null comment '点赞的用户id',
      `user_type` int(1) not null comment '用户类型，0:学生 1:教师，',
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
      foreign key (`re_id`) references re_post(id)  on delete cascade on update cascade,
      UNIQUE KEY `post_id2user_id` (`re_id`,`user_id`)
) COMMENT '帖子点赞';