package com.heo.homework.controller;

import com.heo.homework.config.UploadImageConfig;
import com.heo.homework.service.UploadImageService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadImageController {

    @Autowired
    private UploadImageService uploadImageService;

    @Autowired
    private UploadImageConfig uploadImageConfig;

    @PostMapping
    public ResultVO uploadImage(@RequestParam(required = false) MultipartFile file,@RequestParam(required = false,defaultValue = "") String type){

        for (String dir : uploadImageConfig.getDir()) {
            if (type.equals(dir)){
                return uploadImageService.uploadImage(file,type);
            }
        }
        return ResultVOUtil.error(-1,"参数异常");
    }

}
