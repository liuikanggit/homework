package com.heo.homework.service.impl;

import com.heo.homework.entity.Post;
import com.heo.homework.entity.PostSupport;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.repository.PostRepository;
import com.heo.homework.repository.PostSupportRepository;
import com.heo.homework.service.PostService;
import com.heo.homework.service.UploadImageService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.PageVo;
import com.heo.homework.vo.PostVO;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import javax.transaction.Transactional;

/**
 * @author 刘康
 * @create 2019-03-10 16:42
 * @desc 帖子服务实现
 **/
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostSupportRepository postSupportRepository;

    @Autowired
    private UploadImageService uploadImageService;

    @Override
    public ResultVO createPost(String userId, String title, String content, String[] image) {
        String imageList = "";
        if (image != null) {
            StringBuffer sb = new StringBuffer();
            for (String img : image) {
                if (image[0] != img) {
                    sb.append(",");
                }
                uploadImageService.saveImage(img);
                sb.append(img);
            }
            imageList = sb.toString();
        }

        Post post = new Post(userId, title, content, imageList);
        postRepository.save(post);
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO getAllPost(String userId, int page, int size) {
        Page<PostVO> post = postRepository.getAllPost(PageRequest.of(page, size));
        PageVo<PostVO> pageVo = new PageVo<>(post);
        return ResultVOUtil.success(pageVo);
    }

    @Override
    public ResultVO likePost(String userId, Integer userType, Integer postId) {
        /** 验证postId是否存在 */
        validatePostId(postId);
        /** 验证是否已经点过赞了 */
        if(postSupportRepository.existsByPostIdAndUserId(postId,userId)){
            throw new MyException(ResultEnum.REPEAT_LIST_POST);
        }
        PostSupport ps = new PostSupport(postId, userId, userType);
        postSupportRepository.save(ps);
        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public ResultVO unListPost(String userId, Integer postId) {
        postSupportRepository.delete(postId, userId);
        return ResultVOUtil.success();
    }

    /**
     * 验证post是否存在
      * @param postId 帖子id
     */
    private void validatePostId(Integer postId){
        if (!postRepository.existsById(postId)){
            throw new MyException(ResultEnum.POST_NOT_EXIST);
        }
    }

}
