package com.heo.homework.service.impl;

import com.heo.homework.config.UploadImageConfig;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.service.UploadImageService;
import com.heo.homework.utils.KeyUtil;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

@Service
@Slf4j
public class UploadImageServiceImpl implements UploadImageService {

    @Autowired
    private UploadImageConfig uploadImageConfig;

    @Override
    public ResultVO uploadImage(MultipartFile file, String type) {
        if (file.isEmpty()) {
            throw new MyException(ResultEnum.FILE_NULL);
        }

        String filename = file.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf(".") + 1);

        /** gif  jpg  bmp jpeg png */
        if (!suffixName.matches("[Gg][Ii][Ff]|[Jj][Pp][Gg]|[Bb][Mm][Pp]|[Jj][Pp][Ee][Gg]|[Pp][Nn][Gg]")) {
            throw new MyException(ResultEnum.FILE_TYPE_ERROR);
        }

        /**生成图片名称*/
        String newFilename = "/"+type + "/" + KeyUtil.getUUID() + "." + suffixName;

        try {
            Path p = Paths.get(uploadImageConfig.getTempPath(),"/" + type);
            if (!Files.exists(p)){
                Files.createDirectories(p);
                p = Paths.get(uploadImageConfig.getSavePath(),"/"+ type);
                Files.createDirectories(p);
            }
            Path path = Paths.get(uploadImageConfig.getTempPath(), newFilename);
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(ResultEnum.FILE_NULL);
        }

        return ResultVOUtil.success(newFilename);
    }

    /**
     * 保存图片
     * @param imageName 图片名称
     * @return 图片在保存前是否不存在
     */
    @Override
    public boolean saveImage(String imageName) {

        Path path = Paths.get(uploadImageConfig.getTempPath(), imageName);
        if (Files.exists(path)) {
            try {
                Path p =  Paths.get(uploadImageConfig.getSavePath(), imageName);
                Files.move(path, p, StandardCopyOption.ATOMIC_MOVE);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                throw new MyException(ResultEnum.FILE_SAVE_ERROR);
            }
        } else if (Files.exists(Paths.get(uploadImageConfig.getSavePath(),imageName))){
            return false;
        }else {
            throw new MyException(ResultEnum.FILE_NOT_EXISTS);
        }
    }

    @Override
    public void clearImage() {
        int imageNum = 0;
        int expireNum = 0;
        log.info("正在清除过期图片", imageNum, expireNum);
        List<Path> tempImageDirList = new ArrayList<>();
        for (String dir : uploadImageConfig.getDir()) {
            tempImageDirList.add(Paths.get(uploadImageConfig.getTempPath(), dir));
        }
        try {
            for (Path tempImageDir : tempImageDirList) {
                DirectoryStream<Path> stream = Files.newDirectoryStream(tempImageDir);
                for (Path file : stream) {
                    imageNum++;
                    Long fileCreateTime = Files.readAttributes(file, BasicFileAttributes.class).creationTime().toMillis();
                    Long nowTime = new Date().getTime();
                    if (fileCreateTime < nowTime - uploadImageConfig.getExpireTime() * 24 * 60 * 60 * 1000) {
                        expireNum++;
                        log.info("{} 图片已经过期，以清除!", file.getFileName());
                        Files.delete(file);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("清除过期图片以完成，一共{}张图片，过期{}张", imageNum, expireNum);
    }
}
