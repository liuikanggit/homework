package com.heo.homework.utils;

import java.text.ParseException;
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

    public static Date parse(String dateStr,String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
    public static Date parse(String dateStr){
       return parse(dateStr,"yyyy-MM-dd HH:mm:ss");
    }
}
