package com.heo.homework.service;

import com.heo.homework.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {

    /** 上传文件到临时目录 */
    ResultVO uploadImage(MultipartFile multipartFile,String type);

    /** 临时文件保存到这是文件中 */
    boolean saveImage(String imageName);

    /** 清除没用的图片 */
    void clearImage();
}
