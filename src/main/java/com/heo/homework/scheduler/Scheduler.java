package com.heo.homework.scheduler;

import com.heo.homework.service.UploadImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private UploadImageService uploadImageService;

    @Scheduled(cron = "0 0 0 ? * 1")
    public void clearExpireImage() {
        uploadImageService.clearImage();
    }


}
