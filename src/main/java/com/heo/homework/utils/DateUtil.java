package com.heo.homework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String formatter(Date date, String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
        if(date == null){
            return simpleDateFormat.format(new Date());
        }
        return simpleDateFormat.format(date);
    }

    public static String formatter(Date date){
        return formatter(date,"yyyy-MM-dd");
    }
}
