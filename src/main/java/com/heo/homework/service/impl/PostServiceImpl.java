package com.heo.homework.service.impl;

import com.heo.homework.entity.Post;
import com.heo.homework.entity.PostSupport;
import com.heo.homework.entity.RePost;
import com.heo.homework.entity.ReSupport;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.repository.PostRepository;
import com.heo.homework.repository.PostSupportRepository;
import com.heo.homework.repository.RePostRepository;
import com.heo.homework.repository.ReSupportRepository;
import com.heo.homework.service.PostService;
import com.heo.homework.service.UploadImageService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private RePostRepository rePostRepository;

    @Autowired
    private ReSupportRepository reSupportRepository;


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
        PageVo<List<PostVO>> pageVo = new PageVo<>(post);
        pageVo.setData(pageVo.getData().stream().map(p ->{
            p.setSupportNum(postSupportRepository.countByPostId(p.getId()));
            p.setCommentNum(rePostRepository.countByPostId(p.getId()));
            return p;
        }).collect(Collectors.toList()));
        return ResultVOUtil.success(pageVo);
    }

    @Override
    public ResultVO getSelfPost(String userId, int page, int size) {
        Page<PostVO> post = postRepository.getPostVO(userId,PageRequest.of(page, size));
        PageVo<List<PostVO>> pageVo = new PageVo<>(post);
        pageVo.setData(pageVo.getData().stream().map(p ->{
            p.setSupportNum(postSupportRepository.countByPostId(p.getId()));
            p.setCommentNum(rePostRepository.countByPostId(p.getId()));
            return p;
        }).collect(Collectors.toList()));
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
    public ResultVO unLikePost(String userId, Integer postId) {
        postSupportRepository.delete(postId, userId);
        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public ResultVO getPostDetail(String userId, Integer type, Integer postId) {
        postRepository.look(postId);

        PostDetailVO postDetailVO = postRepository.getPostDetailVO(postId);
        postDetailVO.setLikeNum(postSupportRepository.countByPostId(postId));
        postDetailVO.setLike(postSupportRepository.existsByPostIdAndUserId(postId,userId));
        postDetailVO.setCommentNum(rePostRepository.countByPostId(postId));

        List<RePostVO> reList = rePostRepository.getRePostVOByPostId(postId).stream().map(rePostVO -> {
            rePostVO.setLike(reSupportRepository.existsByReIdAndUserId(rePostVO.getId(),userId));
            return rePostVO;
        }).collect(Collectors.toList());

        postDetailVO.setRe(reList);

        RePostVO[] host = new RePostVO[2];
        reList.stream().forEach(re->{
            if (re.getLikeNum()>=5){
                if (host[0] == null || host[0].getLikeNum()<re.getLikeNum()){
                    host[0] = re;
                }else if (host[1] ==null||host[1].getLikeNum()<re.getLikeNum()){
                    host[1] = re;
                }
            }
        });

        postDetailVO.setHotRe(Arrays.stream(host).filter(rePostVO -> rePostVO != null).collect(Collectors.toList()));

        return ResultVOUtil.success(postDetailVO);
    }

    @Override
    @Transactional
    public ResultVO rePost(Integer postId, Integer reId, String userId, Integer type, String content, String[] images) {
        String image ="";
        if (images!=null) {
            Arrays.stream(images).map(img -> {
                uploadImageService.saveImage(img);
                return img;
            }).collect(Collectors.joining(","));
        }
        RePost rePost = new RePost(postId,reId,userId,type,content,image);
        rePostRepository.save(rePost);
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO reSupport(Integer reId, String userId, Integer type) {
        if (reSupportRepository.existsByReIdAndUserId(reId,userId)) {
            return ResultVOUtil.success("已经点过赞了");
        }
        ReSupport reSupport = new ReSupport(reId,userId,type);
        reSupportRepository.save(reSupport);
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
