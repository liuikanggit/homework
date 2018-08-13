package com.heo.homework.utils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        return System.currentTimeMillis() + String.valueOf(getRandomNuber());
    }

    public static synchronized String getClassKey(List<String > classIds) {
        String classId = String.valueOf(getRandomNuber());
        while (classIds.contains(classId)){
            classId = String.valueOf(getRandomNuber());
        }
        return classId;
    }


    public static Integer getRandomNuber(){
        return new Random().nextInt(900000) + 100000;
    }
}
